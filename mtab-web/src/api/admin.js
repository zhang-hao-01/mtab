import request from './index'

export function userList(data) {
  return request.post('/admin/userList', data)
}

export function userUpdate(data) {
  return request.post('/admin/userUpdate', data)
}

export function userDelete(id) {
  return request.post('/admin/userDelete', { id })
}

export function exportData(data) {
  return request.post('/admin/export', data)
}

export function getServicesStatus() {
  return request.get('/admin/getServicesStatus')
}

export function getUserLine(data) {
  return request.post('/admin/getUserLine', data)
}

export function getHotTab() {
  return request.get('/admin/getHotTab')
}

export function userLoginRecord(userId) {
  return request.get('/admin/userLoginRecord', { params: { userId } })
}

export function repair() {
  return request.post('/admin/repair')
}
