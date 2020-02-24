1.把部门增删改查 功能做好

2.实现 部门列表分页查询

3、基础框架中，增加数据权限过滤功能，用户在使用功能是判断用户是否有操作这个资源的权限
 a 修改token， 增加deptid属性
 b 前台component初始化时获取current user
 c 后台所有操作检查用户是否有权使用功能     检查用户所在的deptid是否有数据操作/显示权限。
mapper DO  DAO service  DTO html
修改deptservice 操作时考虑 user dept表
修改userservice 操作时考虑 user dept表

修改deptcongroler 增加获取授权区域的 url  修改deptservice 增加获取授权区域的服务  
注意事项！！！！！！！！！！！！！！！！！！！！！！！！！1
CommonConstants  类需要审查，里面的token等是怎么设置的
================================
html四个方法对饮的注解
@PutMapping()

@GetMapping() 
1、一般会有个url作为@GetMapping(url)的参数，表示在上级url下面匹配这个url
2、如果@GetMapping()没有参数，表示调用父级url时触发这个方法

@DeleteMapping()  匹配制定url的delete 方法 ，delete是html的四个主要方法之一。


==========================================
@RequestParam
PageUtils list(@RequestParam Map<String, Object> params) { 强制匹配，将请求中的参数匹配为 key，value形式的map

@RequestBody
public R update(@RequestBody DeptDO sysDept)  表示用这个DO匹配前端提供的参数，原始是同名变量匹配


@ResponseBody

@PathVariable
