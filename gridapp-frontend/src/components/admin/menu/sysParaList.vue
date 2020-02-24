<!------------------------------------------------------------
该界面是一个模板界面，功能是实现参数管理。 实现了翻页、按钮权限控制、动态布局、自动滚屏、后台取参数列表。
element ui 组件完成
需要修改的内容包括
0、在API目录下增加api_syspara.js api接口，该界面通过axios方式调用后台rest接口。
1、 修改 route  目录下面的 index.js 增加相应的模块
2、editForm  addForm 中的属性，要合后台返回的属性名一致
3、editFormRules  addFormRules  用来检查表单输入值是否合法， 根据需要修改，不用也可以。
4、 修改“##########主显示取列表####################” 标记的主显示区 . 显示区的字段就是2、3步中定义的属性。每一行后面后“编辑”、“删除”两个按钮，
用来打开相应的界面。
5、 修改“#################编辑界面#################”标记的编辑界面，属性来自editForm，默认情况编辑界面会显示当前行的数据。输入数据的检查规则定义来自editFormRules
6、 修改“#####新增界面######” 标记的新增界面，属性来自addForm 。 输入数据的检查规则定义来自addFormRules
7、在“#####引入API####”标记的位置引入 自定义的api ，如本例中第一步定义的api_syspara.js
8、修改 search() 函数中params 的最后一个属性， 该属性的值来自界面查询框，该属性名称要和数据模型中的某个属性一致，那么就会用这个属性作为过滤条件查询
   如果前面正确，此时主界面能够显示数据了  this.perms=  PERM.initPermission('Role');  代码用于根据用户权限设按钮可见性
9、检查 delLine函数，该函数用来删除主显示区内的一个行。 delLine调用在api_syspara定义的remove方法。 注意修改给remove函数的传入值要和后台定义的contrller一致。
   本例中controller定义的参数是id ，因此将paraId作为参数传给id
10、检查 editSubmit 函数，该函数用来向后台提交 editForm表单数据，调用 api_syspara定义的update方法。 第一个参数是paraId，第二个是form数据，注意传参正确
11、检查 addSubmit 函数该函数用来向后台提交 editForm表单数据，调用 api_syspara定义的add方法。  注意传参正确

此时界面基本增删改查、以及配合后台的权限控制已经完成
-------------------------------------------------------------->
<template>
  <el-row class="warp">
    <el-col :span="24" class="warp-breadcrum">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }"><b>首页</b></el-breadcrumb-item>
        <el-breadcrumb-item>参数管理</el-breadcrumb-item>
      </el-breadcrumb>
    </el-col>
    该功能影响整个系统运行，小心！另外所有数据只能通过delflag=0的方式逻辑删除。不能直接再数据库delete。
    <el-col :span="24" class="warp-main" v-loading="loading" element-loading-text="拼命加载中">
      <!--工具条-->
      <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
        <el-form :inline="true" :model="filters">
          <el-form-item>
            <el-autocomplete
              class="inline-input"
              v-model="filters.username"
              :fetch-suggestions="FilterHelpResult"
              placeholder="请输入参数类型描述"
              @select="handleUserSelect"
            ></el-autocomplete>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-on:click="handleSearch" v-if="perms.READ === 1">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="showAddDialog" v-if="perms.CREATE === 1">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>

      <!--##########主显示取列表####################-->
      <el-table :data="sysPara" highlight-current-row @selection-change="selsChange"
                style="width: 100%;">
        <!-- 下面内容需要根据数据模型修改-->
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="paraId" lable="a" sortable v-if="false"></el-table-column>
        <el-table-column prop="paraName" label="参数名称" sortable></el-table-column>
        <el-table-column prop="value" label="存储值" sortable></el-table-column>
        <el-table-column prop="paraType" label="参数类型" sortable></el-table-column>
        <el-table-column prop="typeDesc" label="类型描述" sortable></el-table-column>
        <el-table-column prop="delFlag" label="类型状态" sortable>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.delFlag === 0">停用</el-tag>
            <el-tag v-if="scope.row.delFlag === 1">启用</el-tag>
          </template>
        </el-table-column>


        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button size="mini" @click="showEditDialog(scope.$index,scope.row)" v-if="perms.UPDATE === 1">编辑</el-button>
            <el-button type="danger" @click="delLine(scope.$index,scope.row)" size="mini" v-if="perms.DELETE === 1">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                       style="float:right;">
        </el-pagination>
      </el-col>
      <!-- #################编辑界面######################-->
      <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
          <!-- 下面内容需要根据数据模型修改-->

          ！！！要保证参数类型+参数值全局唯一！！！
          <el-form-item label="基本信息" prop="paraId">
             参数ID:{{editForm.paraId}} 创建时间: {{editForm.gmtCreate}} ! 修改用户ID:{{editForm.userIdModified}} ! 修改时间: {{editForm.gmtModified}} !
          </el-form-item>
          delFlag
          <el-form-item label="参数状态" >
            <el-radio-group v-model="editForm.delFlag">
              <el-radio :label="0">停用</el-radio>
              <el-radio :label="1">启用</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="参数名称" prop="paraName">
            <el-input v-model="editForm.paraName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="参数值" prop="value">
            <el-input v-model="editForm.value" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="参数类型" prop="paraType">
            <el-input v-model="editForm.paraType" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="类型描述" prop="typeDesc">
            <el-input v-model="editForm.typeDesc" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="显示次序" prop="orderNum">
            <el-input v-model="editForm.orderNum" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="上级参数" prop="parentId">
            <el-input v-model="editForm.parentId" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="上级参数" prop="parentId">
            <el-input v-model="editForm.parentId" auto-complete="off"></el-input>
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

      <!--#####新增界面######-->
      <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
        <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
          <!-- 下面内容需要根据数据模型修改-->
          ！！！要保证参数类型+参数值全局唯一,否则无法提交！！！

          <el-form-item label="参数名称" prop="paraName">
            <el-input v-model="addForm.paraName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="参数值" prop="value">
            <el-input v-model="addForm.value" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="参数类型" prop="paraType">
            <el-input v-model="addForm.paraType" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="类型描述" prop="typeDesc">
            <el-input v-model="addForm.typeDesc" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="显示次序" prop="orderNum">
            <el-input v-model="addForm.orderNum" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="上级参数" prop="parentId">
            <el-input v-model="addForm.parentId" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="上级参数" prop="parentId">
            <el-input v-model="addForm.parentId" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" v-model="addForm.remark" :rows="2"></el-input>
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
  //-- #####引入API####
  import API from '../../../api/api_syspara';
  import  PERM from  '../../../tools/permission';
  export default {
    data() {
      return {
        filters: {
          name: ''
        },
        perms:'',
        sysPara: [],
        sysParaType: [],
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
          paraName: [
            {required: true, message: '请输入参数名称', trigger: 'blur'}
          ],
          paraType: [
            {required: true, message: '请输入参数类型', trigger: 'blur'}
          ],
          value: [
            {required: false, message: '请输入参数值', trigger: 'blur'}
          ],
          typeDesc: [
            {required: false, message: '请输入参数类型简介', trigger: 'blur'}
          ],
          remark: [
            {required: false, message: '请输入参数简介', trigger: 'blur'}
          ]
        },
        editForm: {
          paraId:'',
          paraName:'',
          value:'',
          paraType:'',
          typeDesc:'',
          orderNum:'',
          parentId:'',
          remark:'',
          delFlag:''
        },



        //新增相关数据
        addFormVisible: false,//新增界面是否显示
        addLoading: false,
        addFormRules: {
          paraName: [
            {required: true, message: '请输入参数名称', trigger: 'blur'}
          ],
          paraType: [
            {required: true, message: '请输入参数类型', trigger: 'blur'}
          ],
          value: [
            {required: false, message: '请输入参数值', trigger: 'blur'}
          ],
          typeDesc: [
            {required: false, message: '请输入参数类型简介', trigger: 'blur'}
          ],
          remark: [
            {required: false, message: '请输入参数简介', trigger: 'blur'}
          ]
        },
        addForm: {
          paraId:'',
          paraName:'',
          value:'',
          paraType:'',
          typeDesc:'',
          orderNum:'',
          parentId:'',
          remark:''
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
        this.perms=  PERM.initPermission('SysPara');
        this.search();

      },
      selsChange: function (sels) {
        this.sels = sels;
      },
      // 自动根据用户已经输入字符过滤输入框建议
      FilterHelpResult(queryString, cb) {
        const sysParaType = this.sysParaType;
        const results = queryString ? sysParaType.filter(d => d.value.indexOf(queryString) > -1) : sysParaType;
        cb(results);
      },
      //处理用户对输入框建议的选择
      handleUserSelect(item) {
        let that = this;
        that.filters.name = item.value;
      },
      search() {
        let that = this;
        that.loading = true;

       //获取所有参数类型
        let params1 = {
          page: 0,
          limit: 1000
        };
        API.listParaType(params1).then(function (result) {
          if (result) {
            that.sysParaType = result.map(u => ({value: u.typeDesc ,id:u.paraType}));
          }
        }, function (err) {
          that.loading = false;
          that.$message.error({showClose: true, message: err.toString(), duration: 2000});
        }).catch(function (error) {
          that.loading = false;
          console.log(error);
          that.$message.error({showClose: true, message: '请求出现异常', duration: 2000});
        });

      // 查询主页面数据
        let params = {
          page: that.page,
          limit: 10,
          typeDesc: that.filters.name  // 这属性要和数据模型的属性名称一致，用来作为查询条件
        };

        API.findList(params).then(function (result) {
          that.loading = false;
          if (result && result.rows) {
            that.total = result.total;
            that.sysPara = result.rows;
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

      //删除
      delLine: function (index, row) {
        let that = this;
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'}).then(() => {
          that.loading = true;
          API.remove({id: row.paraId}).then(function (result) {
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

      },
      //编辑
      editSubmit: function () {
        let that = this;
        this.$refs.editForm.validate((valid) => {
          if (valid) {
            this.loading = true;
            let para = Object.assign({}, this.editForm);
           // para.menuIds = that.getMenuIds()
            API.update(para.paraId, para).then(function (result) {
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

