<style>
	@import url("./css/global.css");
</style>
<template>
	
	<div class="n-admin" v-loading="loading">
		
		<el-table :data="tbData">
			<el-table-column label="ID" prop="id"></el-table-column>
			<el-table-column label="名称" prop="name"></el-table-column>
			<el-table-column label="状态" prop="status"  :formatter="v=>['未审核','启用'][ v.status] || '-'" ></el-table-column>
			<el-table-column label="电话" prop="tel"></el-table-column>
			<el-table-column label="用户名" prop="username"></el-table-column>
			<el-table-column label="邮箱" prop="email"></el-table-column>
			<el-table-column label="操作">
				<template #default="{row}">
					<el-button type="text" @click="(auditConf.show = true, auditConf.id = row.id)">审核</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<el-pagination  @current-change="v=> params.page = v"  @size-change='v=> params.limit = v'   v-bind="{ 'page-sizes': [5, 10, 20, 30, 40, 50, 100], 'current-page':params.page, 'pager-count':5, layout:'total, prev, pager, next, sizes', total:params._total, 'page-size': params.limit}"  />
		
		
		
		<el-dialog :visible.sync="auditConf.show" title="审核" width="300px">
			
			<el-select v-model="auditConf.code" >
				<el-option v-for="opt in opts.audit" :key="opt.value" v-bind="opt"></el-option>
			</el-select>
			
			
			<footer slot="footer">
				<el-button @click="audit">确定</el-button>
			</footer>
		</el-dialog>
		
		
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
				},
				opts:{
					audit: [
						{label: '拒绝',value: 0},
						{label: '通过',value: 1},
					]
				},
				auditConf:{
					code: '',
					id: null,
					row:null,
					show:false,
				},
			}
		},
		mounted(){
			this.getData();
		},
		methods:{
			async getData(){
				const res = await this._req(api.getUser( ))
				if(!res) return;
				this.params.page = 1;
				this.params._total = res.data.length;
				this.tbData = res.data;
			},
			
			async audit(){
				const {id, code} = this.auditConf;
				const res = await this._req(api.auditUser(  {id, code}  ))
				if(!res) return;
				this.auditConf.show = false;
				this.getData();
				this.$msg();
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
				
				this.$message({  type:  ['','success','error','warning','info'][type], message});
			},
			
		}
	}
</script>
