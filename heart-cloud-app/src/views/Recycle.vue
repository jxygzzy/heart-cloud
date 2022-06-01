<template>
  <div>
  	<el-container>
  	
  	    <!-- 页头 -->
  	    <el-header height = '60px'>
  	        <el-container>
  	
  	            <el-aside width=""  style="margin: 35px 0;font-size: 20px;">
  								回收站
  	            </el-aside>
  							<el-main style="text-align: right;line-height: 60px;">
  								
  							</el-main>
  	        </el-container>
  	    </el-header>
  	    <!-- 主体 -->
  	    <el-main>
  				
  				<el-table
  				        :data="tableData"
  				        height="80vh"
									v-loading = "loading"
  				        style="width: 100%">
  				    <el-table-column
  				            prop="type"
  				            label="类型"
  				            width="80px">
  				        <template slot-scope="scope">
  				            &nbsp;&nbsp;<i :class="scope.row.type===1?'el-icon-document':'el-icon-folder'"
  				                           style="color: deepskyblue;"></i>
  				        </template>
  				    </el-table-column>
  				    <el-table-column
  				            prop="name"
  				            label="名称"
  				            width="">
  				    </el-table-column>
  				    <el-table-column
  				            fixed="right"
  				            label="操作"
  				            width="300px">
  				
  				        <!-- 文件操作 -->
  				        <template slot-scope="scope">
										<el-button
										        @click.native.prevent="cancelDel(scope.$index, tableData)"
										        type="text"
										        size="small">
										    恢复
										</el-button>
  				            <el-button
  				                    @click.native.prevent="confirmDel(scope.$index, tableData)"
  				                    type="text"
															style="color: red;"
  				                    size="small">
  				                彻底删除
  				            </el-button>
  				        </template>
  				    </el-table-column>
  				</el-table>
  			</el-main>
  		</el-container>
  </div>
</template>
<script>
import {Recovery,Destroy,getReFile} from "../api/recycle"
export default {
  name: "Recycle",
  methods:{
    //恢复文件
    RecoveryFile(){},
    //获取回收站列表
    loadData(){
			this.loading = true
      getReFile().then( res => {
        this.tableData = res.data.data
				this.loading = false
      }).catch((error) => {
        if (error !== 'error') {
          this.$message({type: 'error', message: res.data.msg, showClose: true})
        }
				this.loading = false
      })
    },
    //恢复删除
    cancelDel(index,rows){
			Recovery(rows[index].recycleId).then(res => {
				this.$message({
				    type: 'success',
				    message: '恢复成功!'
				})
				this.loadData()
			}).catch(() => {
				this.$message({
				    type: 'error',
				    message: '恢复失败！'
				});
			})
		},
		//彻底删除
		confirmDel(index,rows){
			this.$confirm('此操作将永久删除该文件或文件夹，该操作不可逆， 是否继续?', '提示', {
			    confirmButtonText: '确定',
			    cancelButtonText: '取消',
			    type: 'warning'
			}).then(() => {
					Destroy(rows[index].recycleId).then(res => {
						this.$message({
						    type: 'success',
						    message: '彻底删除成功!'
						})
						this.loadData()
					}).catch(() => {
						this.$message({
						    type: 'error',
						    message: '删除失败！'
						});
					})
			}).catch(() => {
			    this.$message({
			        type: 'info',
			        message: '已取消操作'
			    });
			});
		}
  },
  data() {
    return {
      tableData: [
				// {
				//     "recycleId": 2,
				//     "name": "1076949.png",
				//     "type": 1
				// },
			],
			loading:false
    }
  },
	created() {
		this.loadData()
	}
}
</script>

<style>
</style>