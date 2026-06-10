import request from './index'

export function update(data) {
  return request.post('/link/update', data)
}

export function get() {
  return request.get('/link/get')
}

export function history() {
  return request.get('/link/history')
}

export function delBack(id) {
  return request.post('/link/delBack', { id })
}

export function rollBack(id) {
  return request.post('/link/rollBack', { id })
}

export function reset() {
  return request.post('/link/reset')
}
