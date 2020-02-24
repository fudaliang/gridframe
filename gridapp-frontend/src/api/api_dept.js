/**
 * Created by fudaliang.
 * 部门相关api
 */
import * as API from '../tools/axios/index'

export default {

  //查询获取dept列表(通过page分页) ,支持按照名称查询
  findList: params => {
    return API.GET('/priv-admin/dept/read', params)
  },


 //查询指定部门的下级部门，以树形结构返回。
  findChildTree: params=>{
    return API.GET('/priv-admin/dept/childTree',params)
  },

  add: params => {
    return API.POST(`/priv-admin/dept/create`, params)
  },
  update: (id, params) => {
    return API.PUT('/priv-admin/dept/update', params)
  },

  //单个删除dept
  remove: params => {
    return API.DELETE(`/priv-admin/dept/delete`,params)
  },

  //批量删除，传ids数组
  removeBatch: (ids) => {
    return API.DELETE(`/priv-admin/dept/batchRemove/${ids}`)
  }

}
