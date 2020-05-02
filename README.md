# BookManager
> 在校大作业，利用豆瓣API实现IBSN编码查询图书，展示图书详情等
# 相关技能
1. 多组件开发，项目分为核心库core， 自定义view库customui，和业务app层
2. 简单封装了单activity多fragment的框架，代表类为`BaseActivity`,`BaseBottomActivity`,`BaseFragment`
3. 简单封装了fragment跳转的路由框架，可以实现根据后台发送的数据跳转指定fragment（该项目未使用上）
4. 基于retrofit + okhttp3封装了一个网络框架，并实现了两个基础拦截器
5. 全局异常捕获
6. 简单封装了多类型adapter，使用bindtype的方式即可绑定多类型item
7. 简单使用了jetpack的Lifecycle打印了fragment生命周期日志
8. 其他自定义view

# TODO
1. 豆瓣API现在基本不对外开放，因此阉割掉了搜索功能
2. 多组件下代码混淆始终报了一个反射时noSuchMethod的错误，一直未解决
