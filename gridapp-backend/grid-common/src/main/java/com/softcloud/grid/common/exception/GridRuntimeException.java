

package com.softcloud.grid.common.exception;


public class GridRuntimeException extends RuntimeException {
    /**
     * serialVersionUID: 序列号.
     */
    private static final long serialVersionUID = -7524567037833875533L;

    private String code;

    private String msg;

    public GridRuntimeException(String message) {
        this.code = "500";
        this.msg = message;
    }

    public GridRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public GridRuntimeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
