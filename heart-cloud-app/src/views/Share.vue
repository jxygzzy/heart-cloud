<template>
	<div>
		<el-container>
		
		    <!-- 页头 -->
		    <el-header height = '60px'>
		        <el-container>
		
		            <el-aside width=""  style="margin: 35px 0;font-size: 20px;">
									分享
		            </el-aside>
								<el-main style="text-align: right;line-height: 60px;">
									
								</el-main>
		        </el-container>
		    </el-header>
		    <!-- 主体 -->
		    <el-main>
					
					<el-table
									v-loading = "loading"
					        :data="tableData"
					        height="80vh"
					        style="width: 100%">
					    <el-table-column
					            prop="type"
					            label="类型"
					            width="80px">
					        <template slot-scope="scope">
					            &nbsp;&nbsp;<i class="el-icon-document"
					                           style="color: deepskyblue;"></i>
					        </template>
					    </el-table-column>
					    <el-table-column
					            prop="fileName"
					            label="名称"
					            width="360px">
					    </el-table-column>
							<el-table-column
							        prop="token"
							        label="分享口令"
							        width="260px">
							</el-table-column>
							<el-table-column
							        prop="expireTime"
							        label="过期时间"
							        width="">
							</el-table-column>
					    <el-table-column
					            fixed="right"
					            label="操作"
					            width="300px">
					
					        <!-- 文件操作 -->
					        <template slot-scope="scope">
					            <el-button
					                    @click.native.prevent="cancelShare(scope.$index, tableData)"
					                    type="text"
					                    size="small">
					                撤销分享
					            </el-button>
					        </template>
					    </el-table-column>
					</el-table>
				</el-main>
			</el-container>
	</div>
</template>


<script>
import {shareFileList,cancelShareFile} from '../api/share.js'
export default {
	name: 'Share',
	data() {
	    return {
	      tableData:[
				],
				dialogVisible: false,
				loading : false
	    };
	},
	methods: {
		loadData(){
			this.loading = true
			shareFileList().then(res => {
				var list = res.data.data
				for(var i = 0;i<list.length;i++){
					list[i].expireTime = list[i].expireTime.substr(0,10)
				}
				this.tableData = list
				this.loading = false
			}).catch(() => {
					this.$message({
							type: 'info',
							message: '加载失败!'
					});
					this.loading = false
			})
		},
		cancelShare(index,rows){
			this.$confirm('此操作将取消该文件分享, 是否继续?', '提示', {
			    confirmButtonText: '确定',
			    cancelButtonText: '取消',
			    type: 'warning'
			}).then(() => {
					cancelShareFile(rows[index].shareId).then(res => {
						this.$message({
						    type: 'success',
						    message: '撤销分享成功!'
						})
						this.loadData()
					})
			}).catch(() => {
			    this.$message({
			        type: 'info',
			        message: '已取消操作'
			    });
			});
		}
	},
	created() {
		this.loadData()
	}
}
</script>

<style>
</style>