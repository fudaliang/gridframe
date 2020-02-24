package com.softcloud.grid.common.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ClassName:SSHUtils <br/>
 * Function: ADD  FUNCTION. <br/>
 * Reason: ADD  REASON. <br/>
 * @version
 * @since JDK 1.8
 * @see
 */
@Component
public class SSHUtils {

    private static final Logger log = LoggerFactory.getLogger(SSHUtils.class);
    private static final String FAILMESSAGE = "Failed!";

    //    @Value("${hostname}")
    //    private String hostname;
    //
    //    @Value("${loginname}")
    //    private String loginname;
    //
    //    @Value("${password}")
    //    private String password;
    //
    //    @Value("${certs-base-path}")
    //    private String certsBasePath;

    /**
     * getConnection:与远程服务器创建链接. <br/>
     *
     * @author tjr5683
     * @param hostname
     *            远程服务器IP地址
     * @param username
     *            远程服务器用户名
     * @param password
     *            远程服务器密码
     * @return Connection对象
     * @since JDK 1.8
     */
    public static Connection getConnection(String hostname, String username, String password) {
        Connection conn = null;
        try {
            conn = new Connection(hostname);
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                throw new IOException("Authentication failed");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return conn;
    }

    /**
     * execCommand:执行一条远程命令. <br/>
     *
     * @author tjr5683
     * @param hostname
     *            远程服务器IP地址
     * @param username
     *            远程服务器用户名
     * @param password
     *            远程服务器密码
     * @param command
     *            远程指令
     * @return boolean类型，true操作成功
     * @since JDK 1.8
     */
    public boolean execCommand(String hostname, String username, String password, String command) {
        Connection conn = getConnection(hostname, username, password);
        Session sess = null;
        if (conn == null) {
            return false;
        }
        try {
            sess = conn.openSession();
            sess.execCommand(command);
            try (InputStream stdout = new StreamGobbler(sess.getStdout());
                    BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout))) {
                while (true) {
                    String line = stdoutReader.readLine();
                    if (line == null) {
                        break;
                    }
                }
                return true;
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            if (sess != null) {
                sess.close();
            }
            conn.close();
        }
    }

    /**
     * execCommandForStringResult:执行一条远程命令[使用于只返回单行结果的命令]. <br/>
     *
     * @author tjr5683
     * @param hostname
     *            远程服务器IP地址
     * @param username
     *            远程服务器用户名
     * @param password
     *            远程服务器密码
     * @param command
     *            远程指令
     * @return 返回指令执行结果字符串的拼接
     * @since JDK 1.8
     */
    public String execCommandForStringResult(String hostname, String username, String password, String command) {
        String result = "";
        Connection conn = getConnection(hostname, username, password);
        if (conn == null) {
            return FAILMESSAGE;
        }
        Session sess = null;
        try {
            sess = conn.openSession();
            sess.execCommand(command);
            try (InputStream stdout = new StreamGobbler(sess.getStdout());
                    BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout))) {
                while (true) {
                    String line = stdoutReader.readLine();
                    if (line == null) {
                        break;
                    }
                    result += line;
                }
                return result;
            } catch (Exception e) {
                log.error(e.getMessage());
                return FAILMESSAGE;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return FAILMESSAGE;
        } finally {
            if (sess != null) {
                sess.close();
            }
            conn.close();
        }
    }

    /**
     * generateServerJenkinsDir:根据前端提供的IP在IPIPE服务器建立相应存放证书的目录，IPIPE用户密码IP配置在yml文件中.
     * <br/>
     *
     * @author tjr5683
     * @param jenkinsIP
     *            master节点IP地址
     * @since JDK 1.8
     */
    //    public void generateServerJenkinsDir(String jenkinsIP) {
    //        System.out.print(hostname + loginname + password);
    //        String command = "mkdir -p " + certsBasePath + jenkinsIP;
    //        System.out.println(execCommand(hostname, loginname, password, command));
    //    }

    /**
     * downloadFiles:从远程服务器获取多个文件. <br/>
     *
     * @author tjr5683
     * @param hostname
     *            远程服务器IP地址
     * @param username
     *            远程服务器用户名
     * @param password
     *            远程服务器密码
     * @param localPath
     *            IPIPE服务器本地路径
     * @param remoteFiles
     *            远程服务器文件路径
     * @return boolean类型，true操作成gridapp   * @since JDK 1.8
     */
    public boolean downloadFiles(String hostname, String username, String password, String localPath, String[] remoteFiles) {
        Connection conn = getConnection(hostname, username, password);
        if (conn == null) {
            return false;
        }
        try {
            SCPClient scpClient = conn.createSCPClient();
            scpClient.get(remoteFiles, localPath);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            conn.close();
        }
    }

    /**
     * downloadFile:从远程服务器获取单个文件. <br/>
     *
     * @author tjr5683
     * @param hostname
     *            远程服务器IP地址
     * @param username
     *            远程服务器用户名
     * @param password
     *            远程服务器密码
     * @param localPath
     *            IPIPE服务器文件路径
     * @param remoteFile
     *            远程服务器文件路径
     * @return boolean类型，true操作成功
     * @since JDK 1.8
   */
   public boolean downloadFile(String hostname, String username, String password, String localPath, String remoteFile) {
        Connection conn = getConnection(hostname, username, password);
        if (conn == null) {
            return false;
        }
        try {
            SCPClient scpClient = conn.createSCPClient();
            scpClient.get(remoteFile, localPath);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            conn.close();
        }
    }

    /**
     * uploadFile:将单个文件下发到远程服务器. <br/>
     *
     * @author tjr5683
     * @param hostname
     *            远程服务器IP地址
     * @param username
     *            远程服务器用户名
     * @param password
     *            远程服务器密码
     * @param localFile
     *            IPIPE服务器文件路径
     * @param remotePath
     *            远程服务器文件路径
     * @return boolean类型，true操作成功
     * @since JDK 1.8
     */
    public boolean uploadFile(String hostname, String username,String password, String localFile,String remotePath) {
        Connection conn = getConnection(hostname, username, password);
        if (conn == null) {
            return false;
        }
        try {
            SCPClient scpClient = conn.createSCPClient();
            scpClient.put(localFile, remotePath);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            conn.close();
        }
    }

    /*public void main(String args[]) {
        //Connection conn = getConnection("22.11.9.87", "root", "Bocsoft01!9");
        //System.out.println(execCommand(conn, "ls -la"));
        generateServerJenkinsDir("22.11.9.87");
    }*/

}
