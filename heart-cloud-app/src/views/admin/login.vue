<style>
	@import url("./css/global.css");
</style>
<template>
	<div class="n-login">
		
		<div class="n-login-form">
			<ul class="n-login-tabs">
				<li class="n-login-tabs-cur">登录</li>
				<li></li>
			</ul>
			<div>
				<el-input  prefix-icon="el-icon-user" v-model="form.username" type="text" placeholder="请输入账号" />
				<el-input  prefix-icon="el-icon-key" v-model="form.password" type="password" placeholder="请输入密码" />
				<button v-loading="loading" @click="login ">登录</button>
			</div>
			
		</div>
		
		
	</div>
	
</template>

<script>
	import api from '@/api/admin'
	import {setToken} from '@/request/token'
	export default {
		data() {
			return {
				form:{
					username: 'heartadmin',
					password:'123456'
				},
				loading:false,
			}
		},
		
		components: {
		},
		mounted() {
			if(window.localStorage.getItem("adminUser")) this.$router.replace('/admin/user')
		},
		methods: {
			// 登录
			async login(){
				const {username,password} = this.form;
				if(!username || !password) return this.$msg('请完善信息！',3)
				const res = await this._req(api.login(  this.form ))
				if(!res) return;
				this.$msg('登录成功');
				setToken(res.data.tokenValue)
				window.localStorage.setItem("adminUser", JSON.stringify(res.data));
				this.$router.replace('/admin/user')
			},
			
			
			async _req(p,tg = this){
				if(!p) return false;
				tg = tg || this;
				
				tg.loading = true;
				const res = await p.catch(e=> (this.$msg('请求失败',2), {}) );
				tg.loading = false;
				return res.data && res.data.code === 200 && res.data;
			},
			$msg(message = '操作成功',type = 1){
				
				this.$message({ type:  ['','success','error','warning','info'][type], message});
			},
		},
		

	}
</script>
