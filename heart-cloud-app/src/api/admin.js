import request from '../request'

const getToken = ()=> JSON.parse(window.localStorage.getItem("adminUser") || '{}').tokenValue;
export default{
	login: data=> request({url: '/admin/login', data, method: 'post' }),
	auditUser: params=> request({url: '/admin//user/audit/'+ params.id, params, method: 'get', headers: {'heart-token': getToken()} }),
	getUser: params=> request({url: '/admin/user/list',method: 'get', params, headers: {'heart-token': getToken()} }),
	getLog: params=> request({url: '/admin/log',method: 'get', params, headers: {'heart-token': getToken()} }),
	
}




