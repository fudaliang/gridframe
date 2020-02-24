import * as API from '../tools/axios/index'

export default {
  list: params => {
    return API.GET('/grid-base/log', params)
  },
  remove: params => {
    return API.DELETE('/grid-base/log', params)
  }
}
