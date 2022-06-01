import Vue from 'vue'
import VueRouter from 'vue-router'
import {getUserInfo} from '../api/login'
import {getToken} from '../request/token'

Vue.use(VueRouter)



const routes = [
	{
		path: '/admin/login',
		component: r => require.ensure([], () => r(require('@/views/admin/login')), 'login')
	},
	{
		path:'/admin',
		component:r => require.ensure([], () => r(require('@/views/admin/layout')), 'layout'),
		children:[
			
			{
				path: '/admin/log',
				component: r => require.ensure([], () => r(require('@/views/admin/log')), 'log')
			},
			{
				path: '/admin/user',
				component: r => require.ensure([], () => r(require('@/views/admin/user')), 'user')
			},
		]
	},
	
	
	{
		path:'',
		component:r => require.ensure([], () => r(require('@/views/Home')), 'home'),
		children:[
			{  
				path: '/',
				name: 'Index',
				component: r => require.ensure([], () => r(require('@/views/Index')), 'index')
			},
			{
				path: '/recycle',
				name: 'Recycle',
				component: r => require.ensure([], () => r(require('@/views/Recycle')), 'recycle')
			},
			{
				path: '/share',
				name: 'Share',
				component: r => require.ensure([], () => r(require('@/views/Share')), 'share')
			},
		]
	},
	{
      path: '/login',
      component: r => require.ensure([], () => r(require('@/views/Login')), 'login')
    },
	{
		path: '/404',
		name: 'NotFound',
		component: r => require.ensure([], () => r(require('@/views/NotFound')), '404')
	},
	
	// 所有未定义路由，全部重定向到404页
	{
		path: '*',
		redirect: '/404'
	}
]

//创建router实例，传'routes'配置
const router = new VueRouter({
	routes
})

router.beforeEach((to, from, next) => {
	
//获取token
  if (getToken()) {
	  //如果是去往登录页面和注册页面，放行
    if (to.path === '/login'|| to.path === '/register') {
	      next()
    } else {
		//判断token是否过期
		getUserInfo(getToken()).then(res =>{
			if(res.data.code === 200){
				localStorage.setItem("name",res.data.data.name)
				next()
			}else{
				localStorage.removeItem("name")
			    Message({
				  type: 'warning',
				  showClose: true,
				  message: '请先登录哦'
			    })
			    next('/login')
			}
		}).catch(()=>{
			//发生异常
				localStorage.removeItem("name")
				Message({
					type: 'warning',
					showClose: true,
					message: '未知错误！'
				})
				next('/login')
			})

    }
  } else {
	  //未登录
		// if(to.path === '/login')
		// 	next('/login')
		// else
		// 	next()
		if(to.path === '/login' || to.path === '/admin/login'){
			next()
		}else{
			next('/login')
		}
  }
})

export default router