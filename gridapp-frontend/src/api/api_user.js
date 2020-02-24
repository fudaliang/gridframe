/**
 * Created by bootdo.
 * 用户相关api
 */
import * as API from '../tools/axios/index'

export default {
  //登录
  login: params => {
    return API.POST('/priv-admin/login', params)
  },
  //登出
  logout: params => {
    return API.GET('/priv-admin/logout', params)
  },
  tokenUser: params =>{
    return API.GET('/priv-admin/user/currentUser',params)
  },
  //修改自己个人信息
  changeProfile: params => {
    //return API.PATCH('/api/v1/users/profile', params)
    return API.PUT('/priv-admin/user/update',params)
  },
  //修改自己的密码
  changePassword: params => {
    return API.PUT('/priv-admin/user/chpasswd', params)
  },

  //修改自己的密码
  resetPassword: params => {
    return API.PUT('/priv-admin/user/resetpasswd', params)
  },

  //查询获取user列表(通过page分页)
  findList: params => {
    return API.GET('/priv-admin/user/listByPage', params)
  },

  //增加用户
  addUser:params =>{
    return API.POST('/priv-admin/user/create',params)
  },
  //修改用户
  editUser:params =>{
    return API.PUT('/priv-admin/user/update',params)
  },
  //删除用户
  removeUser:params =>{
    return API.DELETE('/priv-admin/user/delete',params)
  }
}
