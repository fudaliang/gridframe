/**
 * Created by fudaliang.
 * 处理权限位，用来设置前端功能是否可以操作。该方法根据登录用户的模块权限设置每种类型功能的可用性。
 * 权限数理来自用户登录时后台返回的perms 数据。
 * 共计四位：0标识没有权限；1标识有权限。所有指令，无论是系统菜单、还是设备操作接口必须属于下面六种指令类型的一种。从左计算
 * 第一位、读取操作，对dept内对象数据的读取操作
 * 第二位、增加操作、对dept内对象数据增加操作
 * 第三位、修改操作、修改dept内对象数据的操作
 * 第四位、删除操作、删除dept内对象数据的操作
 * 第五位、设备管理命令、如果对象是一个设备，启动、停止、强制重启、初始化操、升级、回退作属于管理命令。必须具备设备管理权限才能操作。
 * 第六位、设备其它命令，设备普通命令、如果对象是一个设备，并且自带一些命令。那么可以把除管理命令外的命令归为这一组
 * 第七位、预留
 * 第八为、公共命令，任何人都可以访问的命令
 * READ8  : 128 ;  //10000000
 * CREATE7  : 64;//01000000
 * UPDATE6 : 32 ;//00100000
 * DELETE5 : 16 ; //00010000
 * ADM_CMD4 : 8 ; //00001000
 * OTH_CMD3 : 4; //00000100
 * Public1 : 1  //00000001
 *
 */
export default {

      // 设置页面初始权限数组
      initPermission: componentStr => {
        let that= this;
        let perms = JSON.parse(window.localStorage.getItem('perms'));
        const CurrPerms = componentStr ? perms.filter(d => d.component.indexOf(componentStr) > -1) : perms;
        let componentPerms=CurrPerms[0].componentPerms;
        let permissionFlag ={
          READ : ( componentPerms & 128  ? 1 : 0) ,  //10000000
          CREATE :  componentPerms & 64  ? 1 : 0 ,//01000000
          UPDATE :  componentPerms & 32   ? 1 : 0 ,//00100000
          DELETE :  componentPerms & 16  ? 1 : 0, //00010000
          ADM_CMD :  componentPerms & 8   ? 1 : 0, //00001000
          OTH_CMD :  componentPerms & 4  ? 1 : 0 , //00000100
          Public :  componentPerms & 1  ? 1 : 0  //00000001
        };

        //console.log('componentPerms',componentPerms);

        return permissionFlag;
      }





}
