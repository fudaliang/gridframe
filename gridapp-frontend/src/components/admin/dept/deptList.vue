<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>部门管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>

    <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="filters">
          <el-form-item>
            <el-input v-model="filters.name" placeholder="部门名称" @keyup.enter.native="handleSearch"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="handleSearch" v-if="perms.READ === 1">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="showAddDialog" v-if="perms.CREATE === 1">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->
      <el-table :data="dept" highlight-current-row @selection-change="selsChange"
                style="width: 100%;">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" lable="序号" width="60"></el-table-column>
        <el-table-column prop="deptId" lable="a" sortable v-if="false"></el-table-column>
        <el-table-column prop="name" label="部门名称" sortable></el-table-column>
        <el-table-column prop="pname" label="上级部门" sortable></el-table-column>
        <el-table-column prop="remark" label="备注" sortable></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="mini" @click="showEditDialog(scope.$index,scope.row)" v-if="perms.UPDATE === 1">编辑</el-button>
            <el-button type="danger" @click="delDept(scope.$index,scope.row)" size="mini" v-if="perms.DELETE === 1">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                       style="float:right;">
        </el-pagination>
      </el-col>
      <!--编辑界面-->
      <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
          <el-form-item label="部门名称" prop="name">
            <el-input v-model="editForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="editForm.remark" :rows="2"></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="editSubmit">提交</el-button>
        </div>
      </el-dialog>

      <!--新增界面-->
      <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
          <el-form-item label="部门名称" prop="roleName">
            <el-input v-model="addForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="addForm.remark" :rows="8"></el-input>
          </el-form-item>

          <el-form-item label="上级部门" prop="pname">
            <el-input v-model="addForm.pname" :rows="8"></el-input>
            <el-tree
              :data="depts"
              node-key="deptID"
              check-strictly="true"
              :props="treeProps"
              @node-click="handleTreeClick"
              >
            </el-tree>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
        </div>
      </el-dialog>

    </el-col>
  </el-row>
</template>
<script>
  import USER_API from "../../../api/api_user";
  import API from '../../../api/api_dept';
  import  PERM from  '../../../tools/permission';
  export default {
    data() {
      return {
        filters: {
          name: ''
        },
        perms:'',
        dept: [],
        user: [],
        role: [],
        total: 0,
        page: 1,
        limit: 10,
        loading: false,
        sels: [], //列表选中列
        menus: [],
        menuIds: [],//角色拥有的权限
        depts:[],
        treeProps: {
          children: 'children',
          label: 'text'
        },

        currUser: {
          userId: '',
            username: '',
            name: '',
            deptId: ''
        },

        //编辑相关数据
        editFormVisible: false,//编辑界面是否显示
        editFormRules: {
          name: [
            {required: true, message: '请输入部门名称', trigger: 'blur'}
          ],
          remark: [
            {required: false, message: '请输入简介', trigger: 'blur'}
          ]
        },
        editForm: {
          deptId:'',
          name: '',
          remark: ''
        },



        //新增相关数据
        addFormVisible: false,//新增界面是否显示
        addLoading: false,
        addFormRules: {
          name: [
            {required: true, message: '请输入部门名称', trigger: 'blur'}
          ],
          pname: [
            {required: false, message: '请选择上级部门', trigger: 'blur'}
          ],
          remark: [
            {required: false, message: '请输入简介', trigger: 'blur'}
          ]
        },
        addForm: {
          name: '',
          parentId:'',
          pname:'',
          remark: ''
        },

      }
    },
    methods: {
      handleCurrentChange(val) {
        this.page = val;
        this.search();
      },
      handleSearch() {
        this.total = 0;
        this.page = 1;
        this.perms=  PERM.initPermission('Dept');
        this.search();

      },

      search() {
        let that = this;
        let params = {
          page: that.page,
          limit: 10,
          name: that.filters.name
        };

        that.loading = true;
        API.findList(params).then(function (result) {
          that.loading = false;
          if (result && result.rows) {
            that.total = result.total;
            that.dept = result.rows;
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
      delDept: function (index, row) {
        let that = this;
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'}).then(() => {
          that.loading = true;
          API.remove({deptId: row.deptId}).then(function (result) {
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
      //显示编辑界面
      showEditDialog: function (index, row) {
        this.editFormVisible = true;
        this.editForm = Object.assign({}, row);
        let that = this

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
                that.editFormVisible = false;
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
      //显示新增界面，界面上新增按钮的动作。
      showAddDialog: function () {
        this.addFormVisible = true;
        let that = this
        //获取本模块操作人当前登陆信息
        let currUser = JSON.parse(window.localStorage.getItem('CurrUser'));

        API.findChildTree({deptId: currUser.deptId}).then(function (result) {
           that.depts = result;

         })
      },

      handleTreeClick:function (data) {

        this.addForm.pname=data.text;
        this.addForm.parentId=data.parentId;

      },
      //新增
      addSubmit: function () {
        let that = this;
        this.$refs.addForm.validate((valid) => {
          if (valid) {
            that.loading = true;
            let para = Object.assign({}, this.addForm);
            API.add(para).then(function (result) {
              that.loading = false;
              if (result && parseInt(result.code) === 0) {
                that.$message.success({showClose: true, message: '新增成功', duration: 2000});
                that.$refs['addForm'].resetFields();
                that.addFormVisible = false;
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

