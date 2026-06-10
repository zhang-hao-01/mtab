import request from './index'

export function get(params) {
  return request.get('/note/get', { params })
}

export function sort(ids) {
  return request.post('/note/sort', { ids })
}

export function getText(id) {
  return request.get('/note/getText', { params: { id } })
}

export function setWeight(data) {
  return request.post('/note/setWeight', data)
}

export function del(id) {
  return request.post('/note/del', { id })
}

export function add(data) {
  return request.post('/note/add', data)
}

export function update(data) {
  return request.post('/note/update', data)
}
