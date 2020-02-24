<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum" :loading="loading">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>设置</el-breadcrumb-item>
        <el-breadcrumb-item>个人信息</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>

    <el-col :span="24" class="warp-main">
      <el-form ref="userForm" :model="userForm" :rules="userFormrules" label-width="80px">
        <el-form-item label="账号">
          <el-input v-model="userForm.useranme" disabled></el-input>
        </el-form-item>
        <el-form-item prop="name" label="姓名">
          <el-input v-model="userForm.name"></el-input>
        </el-form-item>
        <el-form-item prop="mobile" label="手机">
          <el-input v-model="userForm.mobile"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSaveProfile">修改并保存</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
  import API from '../../../api/api_user';
  import {bus} from '../../../bus.js'

  export default {
    data() {
      return {
        loading: false,
        userForm: {
          useranme: '',
          name: '',
          mobile:'',
          email: ''
        },
        userFormrules: {

          email: [
            {required: true, message: '请输入邮箱', trigger: 'blur'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change'}
          ]
        },
      }
    },
    methods: {
      handleSaveProfile() {
        let that = this;
        that.$refs.userForm.validate((valid) => {
          if (valid) {
            that.loading = true;
            let user = JSON.parse(window.localStorage.getItem('CurrUser'));
            //user.nickname = that.userForm.nickname;

            user.name = that.userForm.name;
            user.email = that.userForm.email;

            let args = {
              userId: user.userId,
              name: that.userForm.name,
              email: that.userForm.email,
              mobile: that.userForm.mobile
            };


            API.changeProfile(args).then(function (result) {
              that.loading = false;
              if (result && parseInt(result.errcode) === 0) {
                //修改成功


                that.$message.success({showClose: true, message: '修改成功', duration: 2000});
              } else {
                that.$message.error({showClose: true, message: result.msg, duration: 2000});
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
      }
    },
    mounted() {
      let user = localStorage.getItem('CurrUser');
      if (user) {
        user = JSON.parse(user);
        this.userForm.useranme = user.username;
        this.userForm.nickname = user.nickname || '';
        this.userForm.email = user.email || '';
        this.userForm.name = user.name || '';
      }
    }
  }
</script>
