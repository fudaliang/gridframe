package com.softcloud.grid.common.constants;
/********************************
 * @author  xiaohei1999
 * @version V1.0
 * 
*   共计四位：0标识没有权限；1标识有权限。所有指令，无论是系统菜单、还是设备操作接口必须属于下面六种指令类型的一种。从右计算
*  第一位、预留
*  第二位、预留
* 第三位、设备其它命令，设备普通命令、如果对象是一个设备，并且自带一些命令。那么可以把除管理命令外的命令归为这一组
* 第四位、 设备管理命令、如果对象是一个设备，启动、停止、强制重启、初始化操、升级、回退作属于管理命令。必须具备设备管理权限才能操作
* 第五位、删除操作、删除dept内对象数据的操作
* 第六位、修改操作、修改dept内对象数据的操作
* 第七位、增加操作、对dept内对象数据增加操作
* 第八为、读取操作，对dept内对象数据的读取操作
 * ***********************************/
public class Permission {
    public final static int PERMISSION_8READ=128;  //10000000
    public final static int PERMISSION_7CREATE=64; //01000000
    public final static int PERMISSION_6UPDATE=32; //00100000
    public final static int PERMISSION_5DELETE=16; //00010000
    public final static int PERMISSION_4ADM_CMD=8; //00001000
    public final static int PERMISSION_3OTH_CMD=4; //00000100
    public final static int PERMISSION_2NOUSED=2;  //00000010
    public final static int PERMISSION_1Public=1;  //00000001  
}
