<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>设置</el-breadcrumb-item>
        <el-breadcrumb-item>修改密码</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>

    <el-col :span="24" class="warp-main">
      <el-form ref="editForm" :model="editForm" :rules="editFormRules" label-width="120px">
        <el-form-item label="原密码" prop="pwd">
          <el-input v-model="editForm.pwd" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd">
          <el-input v-model="editForm.newPwd" type="password" ></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPwd">
          <el-input v-model="editForm.confirmPwd" type="password" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleChangepwd('editForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>
<script>
  import USER_API from "../../../api/api_user";
  export default {
    data(){
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.editForm.confirmPwd !== '') {
            this.$refs.editForm.validateField('confirmPwd');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.editForm.newPwd) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }

      };
      return {

        editForm: {
          userId: '',
          username: '',
          deptId: '',
          pwd: '',
          newPwd: '',
          confirmPwd: ''
        },
        editFormRules: {
          userId:  [
            {required: false, message: ' ', trigger: 'blur'}
          ],
          username:  [
            {required: false, message: ' ', trigger: 'blur'}
          ],
          deptId:  [
            {required: false, message: ' ', trigger: 'blur'}
          ],
          pwd: [
            {required: true, message: '请输入原密码', trigger: 'blur'}
          ],
          newPwd: [
            { validator: validatePass, trigger: 'blur' },
            { min:6, max: 16, message: '长度在6 到 18 个字符', trigger: 'change' }

          ],
          confirmPwd: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleChangepwd(formName) {
        this.$refs[formName].validate((valid) => {
          console.log("aaaa")
          if (valid) {
            let that = this;
            let CurrUserStr=localStorage.getItem("CurrUser");
            var CurrUser=JSON.parse(CurrUserStr);
            that.loading = true;
            let params = Object.assign({}, that.editForm);
            params.userId=CurrUser.userId;
            params.username=CurrUser.username;
            params.deptId=CurrUser.deptId;
            USER_API.changePassword(params).then(function (result) {
              if (0 === result.code) {
                // that.loading = false;
                // that.$message;
                that.$message.success({
                  showClose: true,
                  message: "修改成功",
                  duration: 2000
                });
                that.$refs["editForm"].resetFields();
                that.editFormVisible = false;
                that.search();
              }else if (0 != result.code){
                that.$message.success({
                  showClose: true,
                  message: result.msg,
                  duration: 2000
                });
              }else {
                that.$message.error({
                  showClose: true,
                  message: "修改失败:",
                  duration: 2000
                });
              }
            });
          }else {
            return false;
          }

        })
      }
    }
  }
</script>
