import request from './index'

export function index() {
  return request.get('/searchEngine/index')
}

export function list(data) {
  return request.post('/searchEngine/list', data)
}

export function add(data) {
  return request.post('/searchEngine/add', data)
}

export function del(ids) {
  return request.post('/searchEngine/del', { ids })
}

export function searchEngine() {
  return request.get('/searchEngine/searchEngine')
}

export function saveSearchEngine(data) {
  return request.post('/searchEngine/saveSearchEngine', data)
}

export function sort(data) {
  return request.post('/searchEngine/sort', data)
}
