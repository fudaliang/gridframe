<template>
  <el-tabs type="border-card">
    <!--按照部门授权---------------------------------->
    <el-tab-pane label="按照用户授权">
      <el-row class="warp">
        <el-col :span="24" class="warp-breadcrum">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
            <el-breadcrumb-item>授权管理 / 按照用户授权</el-breadcrumb-item>
          </el-breadcrumb>
        </el-col>

        <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
          <!--工具条-->
          <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters" border-radius>
              <el-form-item>
                <el-autocomplete
                  class="inline-input"
                  v-model="filters.username"
                  :fetch-suggestions="userQuerySearch"
                  placeholder="请输入用户名称"
                  @select="userHandleSelect"
                ></el-autocomplete>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" v-on:click="handleUserSearch" v-if="perms.READ === 1">查询当前授权</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="showAddByUserDialog" v-if="perms.CREATE === 1">新增部门授权</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            用户ID：{{filters.userId}}    用户名：{{filters.username}}      登录账号：    所在机构：{{filters.name}}
          </el-col>
          <!--列表-->
          区域权限列表 -----------------------------------------------------
          <el-table :data="userDept" highlight-current-row @selection-change="selsChange"
                    style="width: 100%;">

            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column type="index" lable="序号" width="60"></el-table-column>
            <el-table-column prop="deptId" lable="a" sortable v-if="false"></el-table-column>
            <el-table-column prop="username" label="用户名称" sortable></el-table-column>
            <el-table-column prop="name" label="部门名称" sortable></el-table-column>
            <el-table-column prop="readPermission" label="读取" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="addPermission" label="新增" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="updatePermission" label="修改" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="deletePermission" label="删除" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="admPermission" label="管理命令" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="otherCMDPermission" label="其它命令" :formatter="formatPermission"></el-table-column>

            <el-table-column label="操作" width="150">
              <template slot-scope="scope">
                <el-button size="mini" @click="showEditByUserDialog(scope.$index,scope.row)" v-if="perms.UPDATE === 1">编辑</el-button>
                <el-button type="danger" @click="delUserDept(scope.$index,scope.row)" size="mini" v-if="perms.DELETE === 1">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10"
                           :total="total"
                           style="float:right;">
            </el-pagination>
          </el-col>
          <!--编辑界面-->
          <el-dialog title="编辑" :visible.sync="editFormByUserVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
              <el-form-item label="用户ID" >
                {{editForm.userId}}
              </el-form-item>
              <el-form-item label="用户名称" >
                {{editForm.username}}
              </el-form-item>
              <el-form-item label="部门名称" >
                {{editForm.name}}
              </el-form-item>
              <el-form-item label="部门名称" >
                <el-checkbox v-model="editForm.readPermission" auto-complete="off">读取权限</el-checkbox>
                <el-checkbox v-model="editForm.addPermission" auto-complete="off">新增权限</el-checkbox>
                <el-checkbox v-model="editForm.updatePermission" auto-complete="off">更新权限</el-checkbox>
                <el-checkbox v-model="editForm.deletePermission" auto-complete="off">删除权限</el-checkbox>
                <el-checkbox v-model="editForm.admPermission" auto-complete="off">管理命令执行权</el-checkbox>
                <el-checkbox v-model="editForm.otherCMDPermission" auto-complete="off">普通命令执行权</el-checkbox>
              </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click.native="editFormByUserVisible = false">取消</el-button>
              <el-button type="primary" @click.native="editSubmit">提交</el-button>
            </div>
          </el-dialog>

          <!--新增界面-->
          <el-dialog title="新增" :visible.sync="addFormByUserVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
              <el-form-item label="用户ID" >
                {{addForm.userId}}
              </el-form-item>
              <el-form-item label="用户名称" >
                {{addForm.username}}
              </el-form-item>
              <el-form-item label="部门名称" >
                <el-autocomplete
                  class="inline-input"
                  v-model="editForm.deptName"
                  :fetch-suggestions="deptQuerySearch"
                  placeholder="请输入部门名称"
                  @select="deptHandleSelect"
                ></el-autocomplete>
              </el-form-item>

              <el-form-item label="部门名称" >
                <el-checkbox v-model="addForm.readPermission" auto-complete="off">读取权限</el-checkbox>
                <el-checkbox v-model="addForm.addPermission" auto-complete="off">新增权限</el-checkbox>
                <el-checkbox v-model="addForm.updatePermission" auto-complete="off">更新权限</el-checkbox>
                <el-checkbox v-model="addForm.deletePermission" auto-complete="off">删除权限</el-checkbox>
                <el-checkbox v-model="addForm.admPermission" auto-complete="off">管理命令执行权</el-checkbox>
                <el-checkbox v-model="addForm.otherCMDPermission" auto-complete="off">普通命令执行权</el-checkbox>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click.native="addFormByUserVisible = false">取消</el-button>
              <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
          </el-dialog>

        </el-col>

      </el-row>
    </el-tab-pane>


    <!--按照部门授权---------------------------------->
    <el-tab-pane label="按照部门授权">

      <el-row class="warp">
        <el-col :span="24" class="warp-breadcrum">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
            <el-breadcrumb-item>授权管理 / 按照部门授权</el-breadcrumb-item>
          </el-breadcrumb>
        </el-col>

        <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
          <!--工具条-->
          <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
              <el-form-item>

                <el-autocomplete
                  class="inline-input"
                  v-model="filters.name"
                  :fetch-suggestions="deptQuerySearch"
                  placeholder="请输入部门名称"
                  @select="deptHandleSelect"
                ></el-autocomplete>

              </el-form-item>
              <el-form-item>
                <el-button type="primary" v-on:click="handleDeptSearch" v-if="perms.READ === 1">查询当前授权</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="showAddByDeptDialog" v-if="perms.CREATE === 1">新增用户授权</el-button>
              </el-form-item>
            </el-form>
          </el-col>

          <!--列表-->
          <el-table :data="userDept" highlight-current-row @selection-change="selsChange"
                    style="width: 100%;">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column type="index" lable="序号" width="60"></el-table-column>
            <el-table-column prop="deptId" lable="a" sortable v-if="false"></el-table-column>
            <el-table-column prop="name" label="部门名称" sortable></el-table-column>
            <el-table-column prop="username" label="用户名称" sortable></el-table-column>
            <el-table-column prop="readPermission" label="读取" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="addPermission" label="新增" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="updatePermission" label="修改" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="deletePermission" label="删除" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="admPermission" label="管理命令" :formatter="formatPermission"></el-table-column>
            <el-table-column prop="otherCMDPermission" label="其它命令" :formatter="formatPermission"></el-table-column>


            <el-table-column label="操作" width="150">
              <template slot-scope="scope">
                <el-button size="mini" @click="showEditByUserDialog(scope.$index,scope.row)" v-if="perms.UPDATE === 1">编辑</el-button>
                <el-button type="danger" @click="delUserDept(scope.$index,scope.row)" size="mini" v-if="perms.DELETE === 1">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10"
                           :total="total"
                           style="float:right;">
            </el-pagination>
          </el-col>
          <!--编辑界面-->
          <el-dialog title="编辑" :visible.sync="editFormByDeptVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
              <el-form-item label="用户ID" >
                {{editForm.userId}}
              </el-form-item>
              <el-form-item label="用户名称"  >
                {{editForm.username}}
              </el-form-item>
              <el-form-item label="部门名称"  >
                {{editForm.name}}
              </el-form-item>

              <el-form-item label="部门权限"  >
                <el-checkbox v-model="editForm.readPermission" auto-complete="off">读取权限</el-checkbox>
                <el-checkbox v-model="editForm.addPermission" auto-complete="off">新增权限</el-checkbox>
                <el-checkbox v-model="editForm.updatePermission" auto-complete="off">更新权限</el-checkbox>
                <el-checkbox v-model="editForm.deletePermission" auto-complete="off">删除权限</el-checkbox>
                <el-checkbox v-model="editForm.admPermission" auto-complete="off">管理命令执行权</el-checkbox>
                <el-checkbox v-model="editForm.otherCMDPermission" auto-complete="off">普通命令执行权</el-checkbox>
              </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click.native="editFormByDeptVisible = false">取消</el-button>
              <el-button type="primary" @click.native="editSubmit">提交</el-button>
            </div>
          </el-dialog>

          <!--新增界面-->
          <el-dialog title="新增" :visible.sync="addFormByDeptVisible" :close-on-click-modal="false">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
              <el-form-item label="部门名称"  >
                {{addForm.name}}
              </el-form-item>
              <el-form-item label="用户ID"  >
                {{addForm.userId}}
              </el-form-item>
              <el-form-item label="用户名称"  >
                <el-autocomplete
                  class="inline-input"
                  v-model="filters.username"
                  :fetch-suggestions="userQuerySearch"
                  placeholder="请输入用户名称"
                  @select="userHandleSelect"
                ></el-autocomplete>
              </el-form-item>
              <el-form-item label="部门权限" >
                <el-checkbox v-model="addForm.readPermission" auto-complete="off">读取权限</el-checkbox>
                <el-checkbox v-model="addForm.addPermission" auto-complete="off">新增权限</el-checkbox>
                <el-checkbox v-model="addForm.updatePermission" auto-complete="off">更新权限</el-checkbox>
                <el-checkbox v-model="addForm.deletePermission" auto-complete="off">删除权限</el-checkbox>
                <el-checkbox v-model="addForm.admPermission" auto-complete="off">管理命令执行权</el-checkbox>
                <el-checkbox v-model="addForm.otherCMDPermission" auto-complete="off">普通命令执行权</el-checkbox>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click.native="addFormByDeptVisible = false">取消</el-button>
              <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
          </el-dialog>

        </el-col>

      </el-row>

    </el-tab-pane>
  </el-tabs>
</template>
<script>
  import API_USER from "../../../api/api_user";
  import API_DEPT from '../../../api/api_dept';
  import API from "../../../api/api_userdept";
  import PERM from "../../../tools/permission";
  export default {
    data() {
      return {
        filters: {
          name: '',  // 部门名称
          username: '' , // 用户真实姓名
          queryDeptId:'',
          queryUserId:''
        },
       perms:'',
        userDept: [],
        users: [],
        depts: [],
        total: 0,
        page: 1,
        limit: 10,
        loading: false,

        currUser: {
          userId: '',
          username: '',
          name: '',
          deptId: ''
        },
        treeProps: {
          children: 'children',
          label: 'text'
        },
        //编辑相关数据
        editFormByUserVisible: false,//按用户授权编辑界面是否显示
        editFormByDeptVisible: false,//按部门授权编辑界面是否显示
        userFocus:false,
        editFormRules: {
          name: [
            {required: true, message: '请输入部门名称', trigger: 'blur'}
          ],
          remark: [
            {required: false, message: '请输入简介', trigger: 'blur'}
          ]
        },
        editForm: {
          id: '',
          userId: '',
          deptId: '',
          permission: '',
          transmit: '',
          username: '',
          name: '',
          readPermission: '',
          addPermission: '',
          updatePermission: '',
          deletePermission: '',
          admPermission: '',
          otherCMDPermission: ''
        },


        //新增相关数据
        addFormByDeptVisible: false,//按部门新增界面是否显示
        addFormByUserVisible: false,//按用户新增界面是否显示
        addLoading: false,
        addFormRules: {
          name: [
            {required: true, message: '请输入部门名称', trigger: 'blur'}
          ],
          userame: [
            {required: true, message: '请输入用户名称', trigger: 'blur'}
          ],
        },
        addForm: {
          userId: '',
          deptId: '',
          permission: '',
          transmit: '',
          username: '', //用户真是姓名
          name: '',  // 部门名称
          readPermission: '',
          addPermission: '',
          updatePermission: '',
          deletePermission: '',
          admPermission: '',
          otherCMDPermission: ''
        },

      }
    },
    methods: {
      userHandleSelect(item) {
        let that = this;
        const users = this.users;
        let dps = users.filter(function (p) {
          return p.id === item.id;
        });
        that.filters.queryUserId = item.id;
      },

      userQuerySearch(queryString, cb) {
        const users = this.users;
        const results = queryString ? users.filter(d => d.value.indexOf(queryString) > -1) : users;
        cb(results);
      },
      deptHandleSelect(item) {
        let that = this;
        const depts = this.depts;
        let dps = depts.filter(function (p) {
          return p.id === item.id;
        });
        that.filters.queryDeptId = item.id;
      //  console.log(that.filters.queryDeptId);
      },

      deptQuerySearch(queryString, cb) {
        const depts = this.depts;
        const results = queryString ? depts.filter(d => d.value.indexOf(queryString) > -1) : depts;

        cb(results);
      },
      formatPermission: function (row, column, cellValue, index) {
        return cellValue == true ? "有权" : cellValue == false ? "无权" : "无权";
      },

      handleCurrentChange(val) {
        this.page = val;
        this.search();
      },
      handleUserSearch() {
        this.total = 0;
        this.page = 1;
        //在使用用户搜索时，自动将使用部门名称搜索字段重置。避免复合查询条件影响结果
        this.filters.queryDeptId = '';
        this.userDept=[];
        this.search();

      },
      handleDeptSearch() {
        this.total = 0;
        this.page = 1;
        //在使用部门搜索时，自动将使用用户名称搜索字段重置。避免复合查询条件影响结果
        this.filters.queryUserId = '';
        this.userDept=[];
        this.search();

      },
      handleSearch() {
        let that = this;
        this.perms=  PERM.initPermission('userDeptPermission');

        let currUser = JSON.parse(window.localStorage.getItem('CurrUser'));
        let params = {
          page: 1,
          limit: 5000,
          name: that.filters.name,
          queryFlag: 'child',
          deptId: currUser.deptId
        };

        API_DEPT.findList(params).then(function (result) {
          that.loading = false;
          if (result && result.rows) {
            that.total = result.total;
            that.depts = result.rows.map(dep => ({value: dep.name, id: dep.deptId}));
          }
        }, function (err) {
          that.loading = false;
          that.$message.error({showClose: true, message: err.toString(), duration: 2000});
        }).catch(function (error) {
          that.loading = false;
          console.log(error);
          that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
        });

        let paramsu = {
          page: 1,
          limit: 5000,
          name: that.filters.name,
          deptId: currUser.deptId
        };

        that.loading = true;
        API_USER.findList(paramsu).then(function (result) {
          that.loading = false;
          if (result && result.page.rows) {
            that.total = result.page.total;
            that.users = result.page.rows.map(u => ({value: u.name ,id:u.userId}));
          }
        }, function (err) {
          that.loading = false;
          that.$message.error({showClose: true,message: err.toString(),duration: 2000});
        }).catch(function (error) {
          that.loading = false;
          console.log(error);
          that.$message.error({showClose: true,message: "请求出现异常",duration: 2000});
        });
      },


      search() {
        let that = this;
        let params = {
          page: that.page,
          limit: 10,
          deptId: that.filters.queryDeptId,  //部门名称
          userId: that.filters.queryUserId
        };

        that.loading = true;
        API.findList(params).then(function (result) {
          that.loading = false;
          if (result && result.rows) {
            that.total = result.total;
            that.userDept = result.rows;
          }
        }, function (err) {
          that.loading = false;
          that.$message.error({showClose: true, message: err.toString(), duration: 2000});
        }).catch(function (error) {
          that.loading = false;
          console.log(error);
          that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
        });
      },
      selsChange: function (sels) {
        this.sels = sels;
      },


      //删除
      delUserDept: function (index, row) {
        let that = this;
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'}).then(() => {
          that.loading = true;
          API.remove({id: row.id}).then(function (result) {
            that.loading = false;
            if (result && parseInt(result.code) === 0) {
              that.$message.success({showClose: true, message: '删除成功', duration: 1500});
              that.search();
            }
            //请求成功，但是返回业务逻辑错误
            if (result && parseInt(result.code) != 0) {
              that.$message.success({showClose: true, message: result.msg, duration: 1500});
              that.search();
            }

          }, function (err) {
            that.loading = false;
            that.$message.error({showClose: true, message: err.toString(), duration: 2000});
          }).catch(function (error) {
            that.loading = false;
            console.log(error);
            that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
          });
        }).catch(() => {
        });
      },
      //显示按用户编辑界面
      showEditByUserDialog: function (index, row) {
        this.editFormByUserVisible = true;
        this.editForm = Object.assign({}, row);
      },
      //显示按部门编辑界面
      showEditByDeptDialog: function (index, row) {
        this.editFormByDeptVisible = true;
        this.editForm = Object.assign({}, row);


      },
      //编辑
      editSubmit: function () {
        let that = this;
        this.$refs.editForm.validate((valid) => {
          if (valid) {
            this.loading = true;
            let para = Object.assign({}, this.editForm);
            // para.menuIds = that.getMenuIds()
            API.update(para.deptId, para).then(function (result) {
              that.loading = false;
              if (result && parseInt(result.code) === 0) {
                that.$message.success({showClose: true, message: '修改成功', duration: 2000});
                that.$refs['editForm'].resetFields();

                that.search();
              } else {
                that.$message.error({showClose: true, message: '修改失败', duration: 2000});
              }
            }, function (err) {
              that.loading = false;
              that.$message.error({showClose: true, message: err.toString(), duration: 2000});
            }).catch(function (error) {
              that.loading = false;
              console.log(error);
              that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
            });
          }
        });
        this.editFormByDeptVisible = false;
        this.editFormByUserVisible = false;
      },
      //显示新增界面，界面上新增按钮的动作。
      showAddByUserDialog: function () {
        let that = this;
        that.addForm.userId = that.filters.queryUserId;
        that.addForm.username = that.filters.username;
        if ( that.filters.username === undefined || that.filters.username.length ==0  ) {
          alert('用户名不为空，且必须时已注册用户，请输入已注册用户名！');
          that.addFormByUserVisible = false;
          // TODO： 应该让输入框获取焦点。
        }else {

        that.addFormByUserVisible = true;
        }
      },

      showAddByDeptDialog: function () {
        let that = this
        that.addForm.deptId = that.filters.queryDeptId;
        that.addForm.name = that.filters.name;
        if ( that.filters.name === undefined ||that.filters.name.length ==0   ) {
          alert('在新增部门操作人员前，请输入部门名称！');
          that.addFormByDeptVisible = false;
          // TODO： 应该让输入框获取焦点。
        }else {
          that.addFormByDeptVisible = true;
        }


      },

      //新增
      addSubmit: function () {
        let that = this;
        this.addForm.deptId = that.filters.queryDeptId;
        this.addForm.userId = that.filters.queryUserId;
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            that.loading = true;
            let para = Object.assign({}, this.addForm);
            API.add(para).then(function (result) {
              that.loading = false;
              if (result && parseInt(result.code) === 0) {
                that.$message.success({showClose: true, message: '新增成功', duration: 2000});
                that.$refs['addForm'].resetFields();
                that.addFormByDeptVisible = false;
                that.addFormByUserVisible = false;
                that.search();
              } else {
                that.$message.error({showClose: true, message: '修改失败', duration: 2000});
              }
            }, function (err) {
              that.loading = false;
              that.$message.error({showClose: true, message: err.toString(), duration: 2000});
            }).catch(function (error) {
              that.loading = false;
              console.log(error);
              that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
            });

          }
        });
      },
      //批量删除
      batchDeleteBook: function () {
        let ids = this.sels.map(item => item.id).toString();
        let that = this;
        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          that.loading = true;
          API.removeBatch(ids).then(function (result) {
            that.loading = false;
            if (result && parseInt(result.errcode) === 0) {
              that.$message.success({showClose: true, message: '删除成功', duration: 1500});
              that.search();
            }
          }, function (err) {
            that.loading = false;
            that.$message.error({showClose: true, message: err.toString(), duration: 2000});
          }).catch(function (error) {
            that.loading = false;
            console.log(error);
            that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
          });
        }).catch(() => {

        });
      },
      //获取选中、半选中节点
      /**
       getMenuIds: function () {
        return this.$refs.menuAddTree.getCheckedKeys().concat(this.$refs.menuAddTree.getHalfCheckedKeys())
      }, **/
      /**
       * (keys, leafOnly) 接收两个参数，1. 勾选节点的 key 的数组 2. boolean 类型的参数，若为 true 则仅设置叶子节点的选中状态，默认值为 false
       */
      setMenuIds: function (keys) {
        this.$refs.menuAddTree.setCheckedKeys(keys, true)
      }
    },
    mounted() {
      this.handleSearch();

    }
  }
</script>

