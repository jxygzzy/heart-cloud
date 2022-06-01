import request from '../request'

//登录接口
export function login(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

//获取用户信息
export function getUserInfo(token) {
  return request({
    headers: {'heart-token': token},
    url: '/user/info',
    method: 'get'
  })
}

//注册接口
export function register(username, password,name,email,tel) {
  const data = {
    username,
    password,
		name,
		email,
		tel
  }
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

