package com.softcloud.grid.common.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.softcloud.grid.common.constants.Permission;

/********************************
 * @author xiaohei1999
 * @version V1.0
 * 
 * ***********************************/

public class PermissionBit {

	
	// 设置权限
	public static int grantPermission(int Permission,int deptPerm) {
		return Permission|deptPerm;
	}
	//取消权限
	public static int revokePermission(int Permission,int deptPerm) {
		return (~Permission)&deptPerm;
	}
	
	//判断是否有权
	public static boolean havePermission(int memuPerm,int deptPerm) {
		
		if ((memuPerm&deptPerm) >0) {
			return true;
		}else {
			return false;
		}
	}

	//将字符串权限转换位数字
	public static int binStrToInt(String str) {
		int i=Integer.parseInt(str,2);
		return i;
	}
	
	//将数字权限转换位二进制字符串
	public static String intToBinStr(int i) {
		String s=Integer.toBinaryString(i);
		return s;
	}
	
	public static int grantRead(int deptPerm) {
		return grantPermission(Permission.PERMISSION_8READ,deptPerm);
	}

	public static int revokeRead(int deptPerm) {
		return revokePermission(Permission.PERMISSION_8READ,deptPerm);
	}

	public static int grantCreate(int deptPerm) {
		return grantPermission(Permission.PERMISSION_7CREATE,deptPerm);
	}
	
	public static int revokeCreate(int deptPerm) {
		return revokePermission(Permission.PERMISSION_7CREATE,deptPerm);
	}
	
	public static int grantUpdate(int deptPerm) {
		return grantPermission(Permission.PERMISSION_6UPDATE,deptPerm);
	}
	
	public static int revokeUpdate(int deptPerm) {
		return revokePermission(Permission.PERMISSION_6UPDATE,deptPerm);
	}
	
	public static int grantDelete(int deptPerm) {
		return grantPermission(Permission.PERMISSION_5DELETE,deptPerm);
	}
	
	public static int revokeDelete(int deptPerm) {
		return revokePermission(Permission.PERMISSION_5DELETE,deptPerm);
	}
	
	public static int grantAdmCMD(int deptPerm) {
		return grantPermission(Permission.PERMISSION_4ADM_CMD,deptPerm);
	}
	
	public static int revokeAdmCMD(int deptPerm) {
		return revokePermission(Permission.PERMISSION_4ADM_CMD,deptPerm);
	}
	
	public static int grantOthCMD(int deptPerm) {
		return grantPermission(Permission.PERMISSION_3OTH_CMD,deptPerm);
	}
	
	public static int revokeOthCMD(int deptPerm) {
		return revokePermission(Permission.PERMISSION_3OTH_CMD,deptPerm);
	}
	
}
