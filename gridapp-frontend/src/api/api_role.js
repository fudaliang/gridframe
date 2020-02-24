/**
 * Created by bootdo.
 * 角色相关api
 */
import * as API from '../tools/axios/index'

export default {

  //查询获取role列表(通过page分页)
  findList: params => {
    return API.GET('/priv-admin/role/read', params)
  },

  //查询获取一条role信息
  findById: id => {
    return API.GET('/priv-admin/role/userId/'+id)
  },

  add: params => {
    return API.POST(`/priv-admin/role/create`, params)
  },
  update: (id, params) => {
    return API.PUT('/priv-admin/role/update', params)
  },

  //单个删除role
  remove: params => {
    return API.DELETE(`/priv-admin/role/delete`, params )
  },

  //批量删除，传ids数组

}
