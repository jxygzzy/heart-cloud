<style>
	@import url("./css/global.css");
</style>
<template>
	
	<div class="n-admin" v-loading="loading">
		
		<el-table :data="tbData.slice( (params.page - 1) * params.limit, params.page * params.limit  )">
			<el-table-column label="ID" prop="id"></el-table-column>
			<el-table-column label="登录时间" prop="loginTime" :formatter="v=> formatTime(v.loginTime)"></el-table-column>
			<el-table-column label="用户ID" prop="userId"  ></el-table-column>
		</el-table>
		
		
		<el-pagination  @current-change="v=> params.page = v"  @size-change='v=> params.limit = v'   v-bind="{ 'page-sizes': [5, 10, 20, 30, 40, 50, 100], 'current-page':params.page, 'pager-count':5, layout:'total, prev, pager, next, sizes', total:params._total, 'page-size': params.limit}"  />
		
	</div>
</template>

<script>
	import api from '@/api/admin'
	export default{
		data(){
			return{
				loading:false,
				tbData:[],
				params:{
					page:1,
					limit: 10,
					_total: 0,
				}
			}
		},
		mounted(){
			this.getData();
		},
		methods:{
			async getData(){
				const res = await this._req(api.getLog(  ))
				if(!res) return;
				this.params.page = 1;
				this.params._total = res.data.length;
				this.tbData = res.data;
			},
			
			
			async _req(p,tg = this){
				if(!p) return false;
				tg = tg || this;
				
				tg.loading = true;
				// const res = await p.catch(e=> (this.$msg('请求失败',2), {}) );
				const res = await p;
				tg.loading = false;
				return res.data && res.data.code === 200 && res.data;
			},
			$msg(message = '操作成功',type = 1){
				
				this.$message({  type:  ['','success','error','warning','info'][type], message});
			},
			formatTime(t,type){
				const dateObj = new Date(t);
				const 	repairZero = (s,len = 2)=> (s+'').length < len ?  new Array(len - (s+'').length).fill('0').join('') + s : s;
				const [date,time] = [
					`${dateObj.getFullYear()}-${repairZero(dateObj.getMonth() + 1 )}-${repairZero(dateObj.getDate())}`,
					`${repairZero(dateObj.getHours())}:${repairZero(dateObj.getMinutes())}:${repairZero(dateObj.getSeconds())}`
				]
				
				switch(type){
					case 'date': return date;
					case 'time': return time;
					default: return date + ' ' + time;
				}
			}
		}
	}
</script>
