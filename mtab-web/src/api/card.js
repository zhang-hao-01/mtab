import request from './index'

export function index() {
  return request.get('/card/index')
}

export function installNum(id) {
  return request.post('/card/installNum', { id })
}
