<style>
	@import url("./css/global.css");
	
</style>



<template>
	<div class="n-layout">
		<aside>
			<h3><img src="../../assets/logo.png" />心跳企业云盘</h3>

			<ul class="n-layout-menu">
				<li :class="{'n-layout-menu-cur': item.path === $route.path}" v-for="item in menus" :key="item.path" @click="$router.push(item.path)"><i :class="item.icon"></i>{{item.name}}</li>
			</ul>

			<footer>
				<img src="../../assets/user.png" />
				超级管理员
				<el-dropdown >
					<i class="el-icon-setting" ></i>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item command="logout" @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</footer>

		</aside>


		<article>
			<router-view />
		</article>


	</div>



</template>

<script>
	import {removeToken} from '@/request/token'
	export default{
		data(){
			return{
				menus:[
					{name: '用户管理', icon: 'el-icon-user', path: '/admin/user'},
					{name: '系统日志', icon: 'el-icon-video-camera-solid', path: '/admin/log'},
				],
			}
		},
		mounted(){
			if(!window.localStorage.getItem("adminUser")) this.$router.replace('/admin/login')
		},
		methods:{
			
			logout(){
				window.localStorage.removeItem("adminUser")
				removeToken()
				this.$router.replace('/admin/login')
			}
		}
	}
</script>
