import request from './index'

export function list(data) {
  return request.post('/linkStore/list', data)
}

export function listManager(data) {
  return request.post('/linkStore/listManager', data)
}

export function getFolder() {
  return request.get('/linkStore/getFolder')
}

export function getFolderAdmin() {
  return request.get('/linkStore/getFolderAdmin')
}

export function add(data) {
  return request.post('/linkStore/add', data)
}

export function addPublic(data) {
  return request.post('/linkStore/addPublic', data)
}

export function push(data) {
  return request.post('/linkStore/push', data)
}

export function getIcon(url) {
  return request.get('/linkStore/getIcon', { params: { url } })
}

export function installNum(id) {
  return request.post('/linkStore/installNum', { id })
}

export function createFolder(data) {
  return request.post('/linkStore/createFolder', data)
}

export function moveGroup(data) {
  return request.post('/linkStore/moveGroup', data)
}

export function moveFolder(data) {
  return request.post('/linkStore/moveFolder', data)
}

export function sortFolder(data) {
  return request.post('/linkStore/sortFolder', data)
}

export function del(ids) {
  return request.post('/linkStore/del', { ids })
}

export function domains(data) {
  return request.post('/linkStore/domains', data)
}
