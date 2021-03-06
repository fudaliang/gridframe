import * as API from '../tools/axios/index'

export default {
  files: params => {
    return API.GET('/api-cms/file', params)
  },
  getToken:params =>{
    return API.GET('/api-cms/file/getToken',params)
  },
  remove: params=>{
    return API.DELETE('/api-cms/file', params)
  }
}
