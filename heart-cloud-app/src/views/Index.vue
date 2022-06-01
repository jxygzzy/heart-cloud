<template>
    <div>
        <el-container>

            <!-- 页头 -->
            <el-header>
                <el-container>

                    <el-aside width="">

                        <!-- 导航面包屑 -->
                        <el-breadcrumb separator-class="el-icon-arrow-right" style="margin: 40px 0;font-size: 20px;">
                            <el-breadcrumb-item
                                    v-for="(item,index) in breadList"
                                    :key="index"
                                    @click.native="rollback(item.dirId)"
                            >{{item.name}}
                            </el-breadcrumb-item>
                        </el-breadcrumb>
                    </el-aside>


                    <el-main style="text-align: right; font-size: 12px;line-height: 60px;">
												<template v-if="moveFile.fileName !== ''">
													<div style="display: inline-block;width: 600px;text-align: left;">
														已选择文件：{{moveFile.fileName}}
														&nbsp;
														&nbsp;
														<el-button @click="moveFileToDir" type="text">移动至当前目录</el-button>
														<el-button @click="cancelmoveFile" type="text">取消</el-button>
													</div>
												</template>
                        <!-- 下拉框 -->
                        <el-dropdown @command="handleCommand">
                            <i class="el-icon-plus"
                               style="margin-right: 15px;line-height: 30px;text-align: center;height: 30px;width: 30px;font-size: 15px;color: #eee;border-radius: 25px;background-color: #446dff;"></i>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command="upload">上传文件</el-dropdown-item>
                                <el-dropdown-item command="create">新建文件夹</el-dropdown-item>
                                <el-dropdown-item command="saveShare">保存分享文件</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </el-main>
                </el-container>
                <hr/>
            </el-header>

            <!-- 主体 -->
            <el-main>

                <!-- 上传文件的dialog -->
                <el-dialog title="上传文件" width="20%" :visible.sync="uploadTableVisible">
                    <el-upload
                            class="upload-demo"
                            ref="upload"
                            action="https://jsonplaceholder.typicode.com/posts/"
                            :on-remove="removeFile"
                            :file-list="fileList"
                            :on-change="changeFile"
                            :limit="1"
                            :on-exceed="exceedHandle"
                            :auto-upload="false">
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                        <el-button style="margin-left: 10px;" size="small" type="success" @click="doUpload">上传到服务器
                        </el-button>
                    </el-upload>
                </el-dialog>
								
								<!-- 转存文件的dialog -->
								<el-dialog title="保存分享文件" width="20%" :visible.sync="saveShareTableVisible">
								    <el-input v-model="shareToken" placeholder="输入口令保存分享文件到此目录" style="width: 250px;"></el-input>
										&nbsp;&nbsp;
										<el-button  icon="el-icon-document-add" @click="saveShareclick" round></el-button>
								</el-dialog>

								

                <!-- 新建文件夹 -->
                <el-dialog title="新建文件夹" :visible.sync="createTableVisible" width="500px">
                    <el-image
                            style="width: 126px; height: 126px;"
                            :src="wjjImg"
                            fit="contain">
                    </el-image>
                    <el-input v-model="dirName" placeholder="请输入内容"></el-input>
                    <el-button @click="createDir" type="primary" round style="margin-top: 10px;margin-left: 375px;">确定
                    </el-button>
                </el-dialog>

                <!-- 分享文件 -->
                <el-dialog title="分享文件" :visible.sync="shareTableVisible" width="350px">
                    <span>有效时限:&nbsp;&nbsp;</span>
                    <el-input-number  style="width: 150px;" v-model="shareDays" min=1 max=365></el-input-number>
										<span>&nbsp;&nbsp;天</span>
                    <br/><br/>
                    <el-button @click="submitShareFile" type="primary" round
                               style="margin-top: 10px;margin-left: 225px;">确定
                    </el-button>
                </el-dialog>

                <!-- 表格显示 -->
                <el-table
                        :data="tableData"
                        height="80vh"
                        @row-dblclick="dblclick"
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
													<el-button v-if="scope.row.type===1" @click="downloadClick(scope.row)" type="text"
																		 size="small">下载
													</el-button>
													<el-button v-if="scope.row.type===1" type="text" size="small" @click="shareClick(scope.row)">分享</el-button>
													
													<el-button v-if="scope.row.type===1" type="text" size="small" @click="moveClick(scope.row)">移动</el-button>
													<el-button
																	@click.native.prevent="deleteRow(scope.$index, tableData)"
																	type="text"
																	size="small">
															删除
													</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-main>
        </el-container>
    </div>
</template>

<script>

    import {getDirFile, createFolder, delFolder,getFileUrl,delFile,fileMove} from "../api/file"
		import {shareFile,saveShareFile} from "../api/share.js"
    import wjj from '../assets/wjj.png'
    import {policy} from "../api/policy";
    import {getUUID} from "../util/uuid";
    import axios from "axios";
    import request from "../request";
    import {getToken} from "../request/token";

    export default {
        name: 'Index',
        data() {
            return {
                loading: '',
                action: '',
                fileList: [],
								moveFile:{
									fileName:'',
									fileId:null
								},
                dataObj: {
                    token: '',
                    key: '',
                    host: '',
                    file: ''
                },
								loading:false,
                file: '',
                fileName: '',
                dirId: '',//当前显示文件夹的id
								shareToken: '',
                tabPosition: 'left',
                shareFileId: null,
                dirName: "新建文件夹",
                shareDays: 7,
                wjjImg: wjj,//一张图片
								downloadUrl:'',
                breadList: [{
                    name: "文件",
                    dirId: ''
                }],
                uploadTableVisible: false,
                createTableVisible: false,
                shareTableVisible: false,
								saveShareTableVisible: false,
                tableData: [
                    // {
                    //     "id": 1,
                    //     "name": "测试",
                    //     "parentId": null,
                    //     "path": null,
                    //     "userId": 1,
                    //     "createTime": null,
                    //     "type": 0
                    // }
                ]
            };
        },
        methods: {
            removeFile(file, fileList) {
                this.fileList = []
                this.file = ''
                this.fileName = ''
            },
            changeFile(file, fileList) {
                this.fileList.push({
                    name: file.name,
                    url: file
                })
                this.file = file.raw
                this.fileName = file.name
            },
            exceedHandle() {
                this.$message.error('最多上传1个文件')
            },
            doUpload() {
                if (this.fileList.length < 1) {
                    this.$message.warning("请先选取文件")
                    return
                }
                policy().then(response => {
                    console.log("R: ", response)
                    this.action = response.data.data.url
                    console.log("R2: ", this.dataObj)
                    const formData = new FormData()
                    // 传参
                    formData.append('file', this.file)
                    formData.append('token', response.data.data.token)
                    formData.append('key', getUUID() + '-' + this.fileName)
                    formData.append('host', response.data.data.url)

                    // 创建一个干净的axios
                    let $upload = axios.create({withCredentials: false});
                    this.loading = this.$loading({
                        lock: true,
                        text: '正在上传',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    })
                    $upload.post(this.action, formData).then(res => {
                        request({
                            headers: {'heart-token': getToken()},
                            url: '/file/save',
                            method: 'post',
                            data: {
                                name: this.fileName,
                                key: res.data.key,
                                dirId: this.dirId
                            }
                        }).then(resp => {
                            this.uploadTableVisible = false
                            this.fileList = []
                            this.file = ''
                            this.fileName = ''
                            this.loading.close();
                            this.loadData()
                            this.$message.success(resp.data.msg)
                        }).catch(err => {
                            this.$message.error(err)
                            this.loading.close();
                        })
                    }).catch(err => {
                        this.$message.error(err)
                        this.loading.close();
                    })
                }).catch(err => {
                    this.$message.error(err)
                })
            },
            handleCommand(command) {
                if (command === "upload") { //上传文件
                    this.uploadTableVisible = true
                } else if (command === "create") {//新建文件夹
                    this.createTableVisible = true
                } else if(command === "saveShare"){ //保存分享文件
										this.saveShareTableVisible = true
								}
            },
						cancelmoveFile(){
							this.moveFile.fileId = null
							this.moveFile.fileName = ''
						},
						moveClick(row) {
							this.moveFile.fileId = row.id
							this.moveFile.fileName = row.name
						},
            shareClick(row) { //分享文件
                this.shareFileId = row.id
								console.log(this.shareFileId)
                this.shareTableVisible = true
            },
            submitShareFile() { 
							shareFile(this.shareDays,this.shareFileId).then(res => {
								let code = res.data.data.token
								this.$notify({
									title: '分享文件成功！',
									message: '口令为：'+code,
									type: 'success',
								})
							}).catch((error) => {
									this.$message({
											type: 'error',
											message: '分享失败！'
									});
                })
							this.shareTableVisible = false
							this.shareDays = 7
							this.shareFileId = null
            },
						moveFileToDir(){
							fileMove(this.moveFile.fileId,this.dirId).then(res => {
								this.loadData()
								    this.$message({
								        type: 'success',
								        message: '移动成功!'
								    });
										this.loadData()
								}).catch((error) => {
								    this.$message({
								        type: 'error',
								        message: '移动失败!'
								    });
								})
							this.moveFile.fileId = null
							this.moveFile.fileName = ''
						},
						downloadClick(row){ //下载文件
							getFileUrl(row.id).then( res => {
								window.open(res.data.url)
							}).catch((error) => {
                    this.$message({
                        type: 'error',
                        message: '下载失败!'
                    });
                })
						},
            createDir() {
                createFolder(this.dirName, this.dirId).then(res => {
                    this.loadData()
                    this.$message({
                        type: 'success',
                        message: '创建成功!'
                    });
                }).catch((error) => {
                    this.$message({
                        type: 'error',
                        message: '创建失败!'
                    });
                })
                this.createTableVisible = false
                this.dirName = '新建文件夹'
            },
						saveShareclick(){
							saveShareFile(this.shareToken,this.dirId).then(res => {
								this.loadData()
								this.$message({
								    type: 'success',
								    message: '转存成功!'
								});
							}).catch((error) => {
                    this.$message({
                        type: 'error',
                        message: '转存失败!'
                    });
                })
							this.saveShareTableVisible = false
							this.shareToken = ''
						},
            rollback(preId) {
                if (preId === this.dirId) {
                    return
                }
                this.dirId = preId;
                this.loadData();
                while (this.breadList.length > 1) {
                    this.breadList.pop();
                    if (preId === this.breadList[this.breadList.length - 1].dirId) {
                        break
                    }
                }
            },
            dblclick(row) {
                if (row.type === 0) {
                    //如果类型是文件夹
                    //双击跳到下一个文件夹

                    //保存上一个文件夹id，用于回退
                    this.breadList.push({
                        name: row.name,
                        dirId: row.id
                    })
                    //更新当前文件夹id
                    this.dirId = row.id;

                    this.loadData()
                }else{
									this.downloadClick(row)
								}
            },
            loadData() {
								this.loading = true
                getDirFile(this.dirId).then(res => {
                    this.tableData = res.data.data
										this.loading = false
                }).catch((error) => {
                    if (error !== 'error') {
                        this.$message({type: 'error', message: res.data.msg, showClose: true})
                    }
                })
            },
            deleteRow(index, rows) {
                this.$confirm('此操作将删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
										if(rows[index].type === 0){
											delFolder(rows[index].id).then(res => {
											    this.$message({
											        type: 'success',
											        message: '删除成功!'
											    });
											    rows.splice(index, 1);
											}).catch(() => {
											    this.$message({
											        type: 'info',
											        message: '删除失败!'
											    });
											})
										}else{
											delFile(rows[index].id).then(res => {
											    this.$message({
											        type: 'success',
											        message: '删除成功!'
											    });
											    rows.splice(index, 1);
											}).catch(() => {
											    this.$message({
											        type: 'info',
											        message: '删除失败!'
											    });
											})
										}
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
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
