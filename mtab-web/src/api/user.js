import request from './index'

export function login(data) {
  return request.post('/user/login', data)
}

export function register(data) {
  return request.post('/user/register', data)
}

export function forgetPass(data) {
  return request.post('/user/forgetPass', data)
}

export function newMail(data) {
  return request.post('/user/newMail', data)
}

export function loginOut() {
  return request.post('/user/loginOut')
}

export function get() {
  return request.get('/user/get')
}

export function updateInfo(data) {
  return request.post('/user/updateInfo', data)
}

export function unbindQQ() {
  return request.post('/user/unbindQQ')
}
