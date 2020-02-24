/**
 * Created by fudaliang.
 * 部门相关api
 */
import * as API from '../tools/axios/index'

export default {

  //查询获取user dept列表(通过page分页) ,支持按照名称查询
  findList: params => {
    return API.GET('/priv-admin/userDeptPermission/listbypage', params)
  },

//增加新的授权关系
  add: params => {
    return API.POST(`/priv-admin/userDeptPermission/create`, params)
  },

  //批量增加新的授权关系
  batchAdd: params => {
    return API.POST(`/priv-admin/userDeptPermission/batchSave`, params)
  },

  //更新 授权模式
  update: (id, params) => {
    return API.PUT('/priv-admin/userDeptPermission/update', params)
  },

  //单个删除user dept
  remove: params => {
    return API.DELETE(`/priv-admin/userDeptPermission/delete`,params)
  },

  //批量删除 user dept，传ids数组
  removeBatch: (ids) => {
    return API.DELETE(`/priv-admin/userDeptPermission/batchRemove/${ids}`)
  }

}
