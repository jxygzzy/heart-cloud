<template>
	<div>
		<el-menu
		  style="min-height: calc(100vh - 75px);width: 240px;"
		  default-active="/"
			router
			background-color ="#f5f5f6"
		  class="el-menu-container">
		  <div style="height: 100px;display: flex;">
			   <el-image
			        style="width: 30px; height: 100px;padding-left: 50px;"
			        :src="imgurl"
			        :fit="fit">
					</el-image>
					<div style="flex: 1;font-size: 15px;height: 100px;line-height: 100px;padding-right: 55px;"><b>心跳企业云盘</b></div>
		  </div>
		  <div class="el-menu-items">
				<el-menu-item index="/">
					<i class="el-icon-files"></i>
					<span slot="title">文件</span>
				</el-menu-item>
				<el-menu-item index="share">
					<i class="el-icon-share"></i>
					<span slot="title">分享</span>
				</el-menu-item>
				<el-menu-item index="recycle">
					<i class="el-icon-delete"></i>
					<span slot="title">回收站</span>
				</el-menu-item>
			</div>
		</el-menu>
		<hr>
		<!-- 修改昵称的dialog -->
		<el-dialog title="修改昵称" width="20%" :visible.sync="editNameTableVisible">
		    <el-input v-model="newName" placeholder="输入新的昵称" style="width: 180px;"></el-input>
				&nbsp;&nbsp;
				<el-button  icon="el-icon-edit" @click="changeName" round></el-button>
		</el-dialog>
		
		<!-- 修改密码的dialog -->
		<el-dialog title="修改密码" width="20%" :visible.sync="editPwdTableVisible">
		    <el-input v-model="pwd" placeholder="请输入原密码" style="width: 230px;" show-password></el-input>
				<br /><br />
		    <el-input v-model="newpwd" placeholder="请输入新的密码" style="width: 230px;" show-password></el-input>
				<br /><br />
		    <el-input v-model="checkpwd" placeholder="请重新输入新的密码" style="width: 230px;" show-password></el-input>
				<br /><br />
				<el-button  icon="el-icon-edit" @click="changePwd" round style="width: 230px;">确认修改</el-button>
		</el-dialog>
		
		<div class="info-box">
			<el-image
			 style="width: 32x; height: 32px;padding:0 24px;"
			 :src="userImg"
			 :fit="fit">
			</el-image>
			{{ name }}
			<el-dropdown style="flex: 1;" @command="handleCommand">
			  <i class="el-icon-setting" style="margin-right: 15px;color: blue;"></i>
			  <el-dropdown-menu slot="dropdown">
			    <el-dropdown-item command="editName">修改昵称</el-dropdown-item>
					<el-dropdown-item command="editPwd">修改密码</el-dropdown-item>
			    <el-dropdown-item command="logout">退出登录</el-dropdown-item>
			  </el-dropdown-menu>
			</el-dropdown>
		</div>
	</div>
</template>

<script>
	
	import logo from '../assets/logo.png'
	import user from '../assets/user.png'
	import {removeToken} from '../request/token'
	import {editName,getUserInfo,editPwd} from '../api/user.js'
	export default{
		name:'Aside',
		data(){
			return {
				imgurl:logo,
				userImg:user,
				fit:'contain',
				name:'',
				newName:'',
				editNameTableVisible:false,
				editPwdTableVisible:false,
				pwd:'',
				newpwd:'',
				checkpwd:''
			}
		},
		methods:{
			logout(){
				removeToken()
				this.$router.push({path: '/login'})
			},
			changeName(){
				editName(this.newName).then(res => {
					this.$message({type: 'success', message: '修改成功!', showClose: true})
					this.name = this.newName
				}).catch(()=>{
					this.$message({type: 'error', message: '修改失败!', showClose: true})
				})
				this.editNameTableVisible = false
			},
			changePwd(){
				if(this.newpwd !== this.checkpwd){
					this.$message({type: 'error', message: '前后输入的密码不一致!', showClose: true})
				}else{
					editPwd(this.pwd,this.newpwd).then(res => {
						this.$message({type: 'success', message: '修改成功!', showClose: true})
						this.editPwdTableVisible = false
					}).catch(() => {
						this.$message({type: 'error', message: res.data.msg, showClose: true})
					})
				}
			},
			handleCommand(command) { //如果是退出按钮
				if(command === "logout"){
					this.logout()
					this.$message({type: 'success', message: '退出成功!', showClose: true})
				}else if(command === "editName"){
					this.newName = ''
					this.editNameTableVisible = true;
				}else if(command === "editPwd"){
					this.pwd = ''
					this.newpwd = ''
					this.checkpwd = ''
					this.editPwdTableVisible = true
				}
			}
		},
		created() {
			this.name = localStorage.getItem("name")
		}
		
	}
</script>

<style>
.info-box{
	height: 73px;
	width: 241px;
	background-color: #f5f5f6;
	display: flex;
	align-items: center;
}

</style>