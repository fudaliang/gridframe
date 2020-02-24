<template>

	<div class="login" ref="loginHeight">
		<img src="../assets/images/pic-login.png" alt="" class="mainImg" />
		<div class="allLoginContainer">
			<div class="titleImg"></div>
			<div class='login-container'>
				<h3 class="title">用户登录</h3>
				<el-form ref="AccountFrom" :model="account" :rules="rules" label-position="left" label-width="0px" class="demo-ruleForm formContent">
					<div class="userTip">用户名</div>
					<el-form-item prop="username">
						<!--<input type="text" autocomplete="new-password" style="display: none;"/>-->
						<el-input class="login_input" type="text" @keyup.enter.native="handleLogin" v-model.trim="account.username" auto-complete="off" placeholder="请输入用户名"></el-input>
					</el-form-item>
					<div class="userTip">密码</div>
					<el-form-item prop="pwd">
						<input type="password" style="display: none;" autocomplete="new-password" />
						<el-input class="login_input" type="password" @keyup.enter.native="handleLogin" v-model.trim="account.pwd" autocomplete="new-password" placeholder="请输入密码"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" class="submitBTN" @click.prevent="handleLogin" :loading="loading">登录</el-button>
					</el-form-item>
				</el-form>
			</div>
		</div>
	</div>

</template>

<script>
	import API from "../api/api_user";
	export default {
		data() {
			let validUserName = (rule, value, callback) => {
				if(value.length < 1 || value.length > 20) {
					callback(new Error('用户名不符合！'))
				} else {
					callback()
				}
			}
			let validPwd = (rule, value, callback) => {
				if(value.length < 1 || value.length > 20) {
					callback(new Error('密码长度不符合！'))
				} else {
					callback()
				}
			}
			return {
				loading: false,
				account: {
					username: "",
					pwd: ""
				},
				rules: {
					username: [{
							required: true,
							message: "请输入用户名！",
							trigger: "blur"
						},
						{
							validator: validUserName,
							trigger: "blur"
						}
					],
					pwd: [{
							required: true,
							message: "请输入密码！",
							trigger: "blur"
						},
						{
							validator: validPwd,
							trigger: "blur"
						}
					]
				},
				checked: true
			}
		},
		methods: {
			handleLogin() {
				let time = new Date().getTime();
				this.$refs.AccountFrom.validate(valid => {
					if(valid) {
						this.loading = true;
						let loginParams = {
							username: this.account.username,
							pwd: this.account.pwd,
							cookiesGenerateDate: this.$CU.toTime(time)
						};
						API.login(loginParams).then(res => {
							this.loading = false;
							if(res && res.code === 0) {
								res.router.unshift({
									path: '/home',
									name: '首页',
									component: '',
									id: 8888,
									children: []
								})
								localStorage.setItem("access-token", res.token);
								localStorage.setItem("menus", JSON.stringify(res.router));
								localStorage.setItem("perms", JSON.stringify(res.perms));
								localStorage.setItem("CurrUser", JSON.stringify(res.user))
								this.$router.push({
									path: "/"
								});
							} else {
								this.$message.error({
									message: res.msg,
									duration: 2000
								});
							}
						}).catch(err => {
							console.log(err);
						});
					}
				});
			}
		}
	};
</script>
<style>
	body {
		background: #dfe9fb;
	}
</style>
<style lang="less" scoped>
	body,
	html {
		width: 100%;
		height: 100%;
	}

	.login {
		width: 100%;
		height: 100%;
		position: fixed;
		background-image: url('../assets/images/bg-login.png');
		background-position: center;
		background-size: cover;
		background-position-y: center;
		background-position-x: calc(100%);
		background-repeat: no-repeat;
		.mainImg {
			position: absolute;
			top: 255px;
			left: 50px;
		}
		.endImg {
			height: 100%;
		}
		.titleImg {
			width: 500px;
			background-image: url('../assets/images/logo-login.png');
			height: 50px;
			background-repeat: no-repeat;
		}
		.allLoginContainer {
			position: absolute;
			top: 50%;
			left: 50%;
			margin-top: -220px;
		}
		.login-container {
			height: 400px;
			-webkit-border-radius: 4px;
			border-radius: 4px;
			-moz-border-radius: 4px;
			background-clip: padding-box;
			border: 1px solid #eaeaea;
			background: -ms-linear-gradient(top, #ace, #00c1de);
			/* IE 10 */
			background: -moz-linear-gradient(top, #ace, #00c1de);
			/*火狐*/
			background: -webkit-gradient( linear, 0% 0%, 0% 100%, from(#ace), to(#00c1de));
			/*谷歌*/
			background: -webkit-linear-gradient( top, #ace, #00c1de);
			/*Safari5.1 Chrome 10+*/
			background: -o-linear-gradient(top, #ace, #00c1de);
			background: #FFFFFF;
			box-shadow: 0 0 8px 0 #DFE3FA;
			/*Opera 11.10+*/
			.formContent {
				margin-top: 50px;
				padding: 0 70px;
			}
			.title {
				text-align: center;
				width: 500px;
				background-color: #DFE3FA;
				border-radius: 4px 4px 0px 0px;
				height: 60px;
				line-height: 60px;
				font-family: PingFangSC-Regular;
				font-size: 18px;
				color: #414655;
				letter-spacing: 0;
			}
			.userTip {
				font-family: PingFangSC-Regular;
				font-size: 12px;
				color: #959595;
				text-align: left;
			}
			.remember {
				margin: 0px 0px 35px 0px;
			}
			.submitBTN {
				font-family: PingFangSC-Regular;
				font-size: 14px;
				color: #FFFFFF;
				letter-spacing: 0;
				width: 150px;
				height: 40px;
				margin-top: 30px;
			}
		}
	}
</style>
