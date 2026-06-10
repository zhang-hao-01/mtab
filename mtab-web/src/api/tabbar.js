import request from './index'

export function update(data) {
  return request.post('/tabbar/update', data)
}

export function get() {
  return request.get('/tabbar/get')
}
