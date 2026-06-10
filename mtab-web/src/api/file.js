import request from './index'

export function list(data) {
  return request.post('/file/list', data)
}

export function del(ids) {
  return request.post('/file/del', { ids })
}

export function scanLocal() {
  return request.post('/file/scanLocal')
}
