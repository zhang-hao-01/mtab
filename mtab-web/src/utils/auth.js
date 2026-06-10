const TOKEN_KEY = 'mtab_token'
const USER_ID_KEY = 'mtab_user_id'

export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

export function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token)
}

export function getUserId() {
  return localStorage.getItem(USER_ID_KEY)
}

export function setUserId(userId) {
  localStorage.setItem(USER_ID_KEY, userId)
}

export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_ID_KEY)
}

export function isLoggedIn() {
  return !!getToken() && !!getUserId()
}
