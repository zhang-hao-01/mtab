import request from './index'

export function update(data) {
  return request.post('/config/update', data)
}

export function get() {
  return request.get('/config/get')
}
