import request from '../request'
import { getToken } from '../request/token'

//修改昵称
export function editName(name) {
  const data = {
    name,
  }
  return request({
    headers: {'heart-token': getToken()},
    url: '/user/name',
    method: 'post',
    data
  })
}

//修改密码
export function editPwd(password,newpassword) {
  const data = {
    password,
		newpassword
  }
  return request({
    headers: {'heart-token': getToken()},
    url: '/user/password',
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
