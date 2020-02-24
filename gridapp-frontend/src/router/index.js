import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/common/Home'
import Dashboard from '@/pages/Dashboard'
import Index from '@/common/index.vue'
////////////
import RoleList from '@/components/admin/role/roleList'

import DeptList from '@/components/admin/dept/deptList'
import userDeptPermission from  '@/components/admin/dept/userDeptPermission'
import UserList from '@/components/admin/user/userList'
import UserChangePwd from '@/components/admin/user/changepwd'
import UserProfile from '@/components/admin/user/profile'
import SysPara from   '@/components/admin/menu/sysParaList'
import MenuList from '@/components/admin/menu/menuList'

import LogList from '../components/admin/log/List'
///////////
import FileList from '../components/cms/file/List.vue'




// 懒加载方式，当路由被访问的时候才加载对应组件
const Login = resolve => require(['@/pages/Login'], resolve)

Vue.use(Router)

let router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/index',
      name: '首页',
      component: Index
    },
    {
      path: '/login',
      name: '登录',
      component: resolve => {
        require.ensure([], () => resolve(require('../pages/Login')), 'login')
      }
    },
    {
      path: '/',
      name: 'home',
      component: Home,
      redirect: '/dashboard',
      leaf: true, // 只有一个节点
      menuShow: true,
      iconCls: 'fa fa-home', // 图标样式class
      children: [
        {path: '/dashboard', component: Dashboard, name: '首页', menuShow: true}
      ]
    },
    {
      path: '/',
      component: Home,
      name: '用户管理',
      menuShow: true,
      leaf: true, // 只有一个节点
      iconCls: 'fa fa-user', // 图标样式class
      children: [
        {path: '/admin/user', component: UserList, name: '用户列表', menuShow: true}
      ]
    },
    {
      path: '/',
      component: Home,
      name: '菜单管理',
      menuShow: true,
      leaf: true, // 只有一个节点
      iconCls: 'fa fa-server', // 图标样式class
      children: [
        {path: '/admin/menu', component: MenuList, name: '菜单列表', menuShow: true}
      ]
    },
    {
      path: '/',
      component: Home,
      name: '角色管理',
      menuShow: true,
      leaf: true,
      iconCls: 'fa fa-group',
      children: [
        {path: '/admin/role', component: RoleList, name: '角色管理', menuShow: true},
      ]
    },
    {
      path: '/',
      component: Home,
      name: '机构管理',
      menuShow: true,
      leaf: true,
      iconCls: 'fa fa-group',
      children: [
        {path: '/admin/dept', component: DeptList, name: '机构管理', menuShow: true},
      ]
    },
    {
      path: '/',
      component: Home,
      name: '参数管理',
      menuShow: true,
      leaf: true,
      iconCls: 'fa fa-group',
      children: [
        {path: '/admin/sysPara', component: SysPara, name: '机构管理', menuShow: true},
      ]
    },
    {
      path: '/',
      component: Home,
      name: '机构授权',
      menuShow: true,
      leaf: true,
      iconCls: 'fa fa-group',
      children: [
        {path: '/admin/userDeptPermission', component: userDeptPermission, name: '机构授权', menuShow: true},
      ]
    },
    {
      path: '/',
      component: Home,
      name: '文件管理',
      menuShow: true,
      leaf: true,
      iconCls: 'fa fa-group',
      children: [
        {path: '/cms/file', component: FileList, name: '文件管理', menuShow: true},
      ]
    },
    {
      path: '/',
      component: Home,
      name: '日志管理',
      menuShow: true,
      leaf: true,
      iconCls: 'fa fa-group',
      children: [
        {path: '/base/log', component: LogList, name: '日志管理', menuShow: true},
      ]
    },
    {
      path: '/',
      component: Home,
      name: '设置',
      menuShow: true,
      iconCls: 'iconfont icon-setting1',
      children: [
        {path: '/user/profile', component: UserProfile, name: '个人信息', menuShow: true},
        {path: '/user/changepwd', component: UserChangePwd, name: '修改密码', menuShow: true}
      ]
    },

    {
      path: '/',
      component: Home,
      name: '注册服务',
      menuShow: true,
      iconCls: 'iconfont icon-setting1',
      children: [
        {path: '/eureka',  redirect: 'http://localhost:8001/',target:'_blank',name: '注册服务', menuShow: true},
      ]
    },
  ]
})

router.beforeEach((to, from, next) => {
  // console.log('to:' + to.path)
  if (to.path.startsWith('/login')) {
    window.localStorage.removeItem('access-token');
    window.localStorage.removeItem("menus");
    window.localStorage.removeItem("perms");
    window.localStorage.removeItem("CurrUser");
    //window.localStorage.removeItem('access-user')
    next()
  } else if (to.path.startsWith('/index')) {
    next()
  } else {
    //let user = JSON.parse(window.localStorage.getItem('access-token'))
    let user = window.localStorage.getItem('access-token');
    if (!user) {
      next({path: '/login'})
    } else {
      next()
    }
  }
})

export default router
