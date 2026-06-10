import request from './index'

export function saveSetting(data) {
  return request.post('/setting/saveSetting', data)
}

export function refreshCache() {
  return request.post('/setting/refreshCache')
}

export function getSetting(data) {
  return request.post('/setting/getSetting', data)
}

export function mailTest(data) {
  return request.post('/setting/mailTest', data)
}
