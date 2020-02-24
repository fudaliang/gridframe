/**
 * 菜单相关的api
 * @author bootdo 1992lcg@163.com
 */
import * as API from '../tools/axios/index'

export default {

  menus: params=>{
    return API.GET('/priv-admin/menu/read',params)
  },
  editMenu: params=>{
    return API.PUT('/priv-admin/menu/update',params)
  },
  menuIdsByRoleId: params=>{
    return API.GET('/priv-admin/menu/roleId',params)
  },
  add: params=>{
    return API.POST('/priv-admin/menu/create',params)
  },
  remove: params=>{
    return API.DELETE('/priv-admin/menu/delete',params)
  }
}
