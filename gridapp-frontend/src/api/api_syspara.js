/**
 * Created by fudaliang.
 * 部门相关api
 */
import * as API from '../tools/axios/index'

export default {

  //查询获取syspara列表(通过page分页) ,支持按照名称查询
  findList: params => {
    return API.GET('/priv-admin/sysPara/read', params)
  },
  //获取指定参数类型的所有参数列表,一般用在前端获取下拉列表值
  listByParaType: params => {
    return API.GET('/priv-admin/sysPara/listByParaType',params)
  },
  //获取系统中定义的参数类型列表
  listParaType: params => {
    return API.GET('/priv-admin/sysPara/listParaType',params)
  },
  add: params => {
    return API.POST(`/priv-admin/sysPara/create`, params)
  },
  update: (id, params) => {
    return API.PUT('/priv-admin/sysPara/update', params)
  },

  //单个删除dept
  remove: params => {
    return API.DELETE(`/priv-admin/sysPara/delete`,params)
  },

}
