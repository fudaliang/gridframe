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
            <el-button type="primary" v-on:click="handleSearch">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="showAddDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--列表-->

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                       style="float:right;">
        </el-pagination>
      </el-col>
      <!--编辑界面-->

    </el-col>
  </el-row>
</template>
<script>
  import USER_API from "../../../api/api_user";
  import API from '../../../api/api_dept';

  export default {
    data() {
      return {
        filters: {
          name: ''
        },
        total: 0,
        page: 1,
        limit: 10,
        loading: false,
        treeProps: {
          children: 'children',
          label: 'text'
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

    methods: {
      handleCurrentChange(val) {
        this.page = val;
        this.search();
      },
      handleSearch() {
        this.total = 0;
        this.page = 1;
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
       // this.addForm = {};  //大亮，这句引起的问题，这里改变了这个组件的数据结构
        let that = this
        //获取本模块操作人当前登陆信息
        let currUser = JSON.parse(window.localStorage.getItem('CurrUser'));

        API.findChildTree({deptId: currUser.deptId}).then(function (result) {
           that.depts = result;

         })
      },

    },
    mounted() {
      this.handleSearch();

    }
  }
</script>

