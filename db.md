## 数据库：LOST_FOUND

```sql	
# 创建数据库
CREATE DATABASE  `LOST_FOUND` DEFAULT CHARACTER SET utf8mb4;
```

```sql
# 创建用户，%为主机通配符，表示任意主机
CREATE USER 'wcp'@'%' IDENTIFIED BY 'wcp0508';
# 授权
GRANT ALL ON LOST_FOUND.* TO 'wcp'@'%';
```

> 参考[链接](https://www.cnblogs.com/sos-blue/p/6852945.html)

### T_SCHOOL（校区表）OK

| 字段          | 类型     | 长度 | 可Null | 默认         | 主键 | 唯一 | 说明                                                        |
| ------------- | -------- | ---- | ------ | ------------ | ---- | ---- | ----------------------------------------------------------- |
| campus_id     | varchar  | 64   | n      |              | y    |      | 校区id，为学校则同school_id                                 |
| school_id     | varchar  | 64   | n      |              |      |      | 学校id                                                      |
| campus_name   | varchar  | 128  | n      |              |      |      | 校区名称                                                    |
| school_name   | varchar  | 128  | n      |              |      | y    | 学校名称                                                    |
| address       | varchar  | 256  | y      |              |      |      | 地址                                                        |
| about         | varchar  | 1024 | y      |              |      |      | 描述                                                        |
| phone_number  | varchar  | 16   | y      |              |      |      | 电话                                                        |
| icon          | varchar  | 256  | n      | 'school.png' |      |      | 学校图标                                                    |
| status        | int      |      | n      | 1            |      | y    | 状态：<br/>0：无效<br/>1：正常<br/>2：已冻结 <br/>3：已注销 |
| create_time   | datetime |      | n      |              |      |      | 创建时间                                                    |
| creator_id    | varchar  | 64   | n      |              |      |      | 创建人id                                                    |
| record_status | int      |      | n      | 1            |      |      | 记录状态：<br />0：已删除<br />1：有效                      |


### T_USER（用户表）OK

| 字段          | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                                                         |
| ------------- | -------- | ---- | ------ | ---- | ---- | ---- | ------------------------------------------------------------ |
| id            | varchar  | 64   | n      |      | y    |      | 用户id                                                       |
| username      | varchar  | 64   | n      |      |      | y    | 用户登录名<br/>学生为学号，教职工为工号，管理员为邮箱        |
| password      | varchar  | 64   | n      |      |      |      | 登录密码（独立）md5加密后存储                                |
| activate_code | varchar  | 64   | n      |      |      |      | 激活码                                                       |
| email         | varchar  | 256  | n      |      |      | y    | 邮箱                                                         |
| phone_number  | varchar  | 16   | y      |      |      |      | 手机号码                                                     |
| real_name     | varchar  | 256  | n      |      |      |      | 真实姓名                                                     |
| gender        | int      |      | n      |      |      |      | 性别                                                         |
| icon          | varchar  | 256  | y      |      |      |      | 头像                                                         |
| school_id     | varchar  | 64   | y      |      |      |      | 学校id                                                       |
| campus_id     | varchar  | 64   | y      |      |      |      | 校区id                                                       |
| create_time   | datetime |      | n      |      |      |      | 创建时间                                                     |
| last_login    | datetime |      | y      | null |      |      | 用户最后登录时间                                             |
| kind          | int      |      | n      | 0    |      |      | 用户类型：<br/>0：学生<br/>1：教职工<br/>2：管理员<br />3：超级管理员 |
| status        | int      |      | n      | 0    |      |      | 用户状态<br/>0：无效（未激活）<br/>1：正常（已激活）<br/>2：已冻结 <br/>3：已注销<br/>4：审核中 |
| record_status | int      |      | n      | 1    |      |      | 记录状态：<br />0：已删除<br />1：有效                       |

### T_MANAGER（管理员表）OK

| 字段         | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明             |
| ------------ | -------- | ---- | ------ | ---- | ---- | ---- | ---------------- |
| id           | varchar  | 64   | n      |      | y    |      | id               |
| campus_id    | varchar  | 64   | n      |      |      | y    | 校区id（或学校） |
| user_id      | varchar  | 64   | n      |      |      | y    | user_id          |
| address      | varchar  | 128  | n      |      |      |      | 联系地址         |
| phone_number | 16       | 16   | n      |      |      |      | 电话             |
| email        | varchar  | 256  | n      |      |      |      | 邮件             |
| creator_id   | varchar  | 64   | n      |      |      |      | 创建人id         |
| create_time  | datetime |      | n      |      |      |      | 创建时间         |



### T_STUDENT（学生信息表）OK

> 信息来自官网身份认证

| 字段         | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                   |
| ------------ | -------- | ---- | ------ | ---- | ---- | ---- | ---------------------- |
| id           | varchar  | 64   | n      |      | y    |      | 学生ID                 |
| school_id    | varchar  | 64   | n      |      |      |      | 学校id                 |
| user_id      | varchar  | 64   | n      |      |      | y    | 对应T_USER的id         |
| student_num  | varchar  | 32   | n      |      |      | y    | 学号                   |
| name         | varchar  | 256  | n      |      |      |      | 姓名                   |
| gender       | int      |      | n      | -1   |      |      | 性别：-1未知，0女，1男 |
| id_card_num  | varchar  | 64   | n      |      |      |      | 身份证号               |
| student_kind | varchar  | 32   | n      |      |      |      | 学生类别               |
| nation       | varchar  | 32   | n      |      |      |      | 民族                   |
| academy      | varchar  | 128  | n      |      |      |      | 学院                   |
| major        | varchar  | 128  | n      |      |      |      | 专业                   |
| class_num    | varchar  | 32   | n      |      |      |      | 班级                   |
| campus_name  | varchar  | 128  | n      |      |      |      | 校区名称               |
| create_time  | datetime |      | n      |      |      |      | 创建时间               |



### T_WX_USER（微信用户表）

>  针对微信端（后期任务）

| 字段        | 类型    | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                   |
| ----------- | ------- | ---- | ------ | ---- | ---- | ---- | ---------------------- |
| user_id     | varchar | 64   | n      |      |      | y    | 对应T_USER的id         |
| open_id     | varchar | 64   | n      |      | Y    |      | 用户openID             |
| avatar_url  | varchar | 256  | n      |      |      |      | 头像地址               |
| nick_name   | varchar | 256  | n      |      |      |      | 昵称                   |
| gender      | int     |      | n      | -1   |      |      | 性别：-1未知，0女，1男 |
| country     | varchar | 256  | n      |      |      |      | 国家                   |
| province    | varchar | 256  | n      |      |      |      | 省份                   |
| city        |         |      |        |      |      |      |                        |
| language    |         |      |        |      |      |      |                        |
| create_time |         |      |        |      |      |      |                        |
| last_login  |         |      |        |      |      |      |                        |
| status      |         |      |        |      |      |      |                        |
|             |         |      |        |      |      |      |                        |
|             |         |      |        |      |      |      |                        |

### T_NOTICE（公告信息/轮播表）OK

| 字段          | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                                                         |
| ------------- | -------- | ---- | ------ | ---- | ---- | ---- | ------------------------------------------------------------ |
| id            | varchar  | 64   | n      |      | Y    |      | ID                                                           |
| kind          | int      |      | n      | 0    |      |      | 类型：0公告，1轮播                                           |
| title         | varchar  | 128  | n      |      |      |      | 标题                                                         |
| link          | varchar  | 256  | y      |      |      |      | 链接                                                         |
| image         | varchar  | 256  | y      |      |      |      | 图片                                                         |
| content       | varchar  | 1024 | n      |      |      |      | 公告内容<br/>（HTML）                                        |
| level         | int      |      | n      |      |      |      | 级别类型：<br/>0：系统级别<br/>1：校园级别<br/>2：校区级别<br/>3：个人级别<br/>4：所有管理员<br />5：所有普通用户 |
| fix_top       | int      |      | n      | 0    |      |      | 是否置顶<br/>0：否<br/>1：是                                 |
| target_id     | varchar  | 64   | n      |      |      |      | 目标用户id<br/>系统级别为"system"                            |
| start_time    | datetime |      | y      |      |      |      | 开始展示时间                                                 |
| end_time      | datetime |      | y      |      |      |      | 结束展示时间                                                 |
| create_time   | datetime |      | n      |      |      |      | 创建时间                                                     |
| creator_id    | varchar  | 64   | n      |      |      |      | 创建人id                                                     |
| status        | int      |      | n      | 0    |      |      | 状态<br/>0：无效<br/>1：正常<br/>2：未开始<br/>3：已过期     |
| record_status | int      |      | n      | 1    |      |      | 记录状态：<br />0：已删除<br />1：有效                       |

### T_APPLY（流程申请表）

| 字段          | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                                                         |
| ------------- | -------- | ---- | ------ | ---- | ---- | ---- | ------------------------------------------------------------ |
| id            | varchar  | 64   | n      |      | Y    |      | 流程id                                                       |
| user_id       | varchar  | 64   | n      |      |      |      | 申请人id                                                     |
| create_time   | datetime |      | n      |      |      |      | 创建时间                                                     |
| campus_id     | varchar  | 64   | n      |      |      |      | 校区/学校id                                                  |
| kind          | int      |      | n      |      |      |      | 类型：<br/>0：失物发布<br/>1：寻物发布<br/>2：身份认证<br/>3：物品认领<br/>4：账号申诉 |
| about         | varchar  | 256  | n      |      |      |      | 流程说明                                                     |
| target_id     | varchar  | 64   | n      |      |      |      | 操作对象id                                                   |
| detail        | varchar  | 1024 | n      |      |      |      | 详情（json）                                                 |
| status        | int      |      | n      |      |      |      | 流程状态：<br/>0：处理中<br/>1：已通过<br/>2：已拒绝<br/>3：已取消 |
| current_node  | varchar  | 64   | n      |      |      |      | 当前结点id                                                   |
| record_status | int      |      | n      | 1    |      |      | 记录状态：<br />0：已删除<br />1：有效                       |



### T_NODE（流程结点表）

| 字段          | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                                                         |
| ------------- | -------- | ---- | ------ | ---- | ---- | ---- | ------------------------------------------------------------ |
| id            | varchar  | 64   | n      |      | y    |      | 结点id                                                       |
| apply_id      | varchar  | 64   | n      |      |      | y    | 流程id                                                       |
| node_order    | int      |      | n      |      |      | y    | 结点序号，0开始                                              |
| about         | varchar  | 128  | n      |      |      |      | 结点说明                                                     |
| status        | int      |      | n      |      |      |      | 状态：<br/>0：处理中<br/>1：已通过<br/>2：已拒绝<br/>3：已取消<br/> |
| handler_id    | varchar  | 64   | n      |      |      |      | 处理人id                                                     |
| handle_time   | datetime |      | y      |      |      |      | 处理时间                                                     |
| reason        | varchar  | 128  | n      |      |      |      | 理由说明                                                     |
| record_status | int      |      | n      | 1    |      |      | 记录状态：<br />0：已删除<br />1：有效                       |



### T_CATEGORY（物品类别表）OK


| 字段          | 说明                                                       | 类型     | 长度 | 可Null | 默认           | 主键 | 唯一 |
| ------------- | ---------------------------------------------------------- | -------- | ---- | ------ | -------------- | ---- | ---- |
| name          | 类型名                                                     | varchar  | 128  | n      |                | y    |      |
| about         | 说明                                                       | varchar  | 256  | y      |                |      |      |
| image         | 图标                                                       | varchar  | 256  | y      | "category.png" |      |      |
| creator_id    | 创建人id                                                   | varchar  | 64   | n      |                |      |      |
| create_time   | 创建时间                                                   | datetime | 64   | n      |                |      |      |
| level         | 级别类型：<br/>0：系统级别<br/>1：校园级别<br/>2：校区级别 | int      |      | n      |                | y    |      |
| target_id     | 作用对象id，系统级别为“system”                             | varchar  | 64   | n      |                | y    |      |
| record_status | 记录状态：<br />0：已删除<br />1：有效                     | int      |      | n      | 1              |      |      |


### T_LOST_FOUND（寻物招领表）OK

| 字段          | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                                                         |
| ------------- | -------- | ---- | ------ | ---- | ---- | ---- | ------------------------------------------------------------ |
| id            | varchar  | 64   | n      |      | Y    |      | id                                                           |
| user_id       | varchar  | 64   | n      |      |      |      | 用户id                                                       |
| campus_id     | varchar  | 64   | n      |      |      |      | 校区id                                                       |
| kind          | int      |      | n      |      |      |      | 类型：<br/>0：失物发布<br/>1：认领发布                       |
| title         | varchar  | 128  | n      |      |      |      | 标题                                                         |
| about         | varchar  | 512  | n      |      |      |      | 说明                                                         |
| location      | varchar  | 512  | y      |      |      |      | 地点                                                         |
| images        | varchar  | 1024 | y      |      |      |      | 图片链接(list)                                               |
| category_id   | varchar  | 128  | n      |      |      |      | 类型id                                                       |
| fix_top       | int      |      | n      | 0    |      |      | 是否置顶<br/>0：否<br/>1：是                                 |
| look_count    | int      |      | n      | 0    |      |      | 浏览次数                                                     |
| create_time   | datetime |      | n      |      |      |      | 创建时间                                                     |
| status        | int      |      | n      |      |      |      | 状态：<br />0：待审批<br/>1：寻回中（审批通过）<br/>2：驳回（审批不通过）<br />3：已寻回<br/>4：已关闭 |
| claimant_id   | varchar  | 64   | y      |      |      |      | 认领者id                                                     |
| deal_time     | datetime |      | y      |      |      |      | 处理完成时间                                                 |
| record_status | int      |      | n      | 1    |      |      | 记录状态：<br />0：已删除<br />1：有效                       |

### T_COMMENT（评论表）OK
| 字段          | 说明                                 | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 |
| ------------- | ------------------------------------ | -------- | ---- | ------ | ---- | ---- | ---- |
| id            | id                                   | varchar  | 64   | n      |      | Y    |      |
| lost_found_id | 寻招id                               | varchar  | 64   | n      |      |      |      |
| user_id       | 用户id                               | varchar  | 64   | n      |      |      |      |
| content       | 内容                                 | varchar  | 128  | n      |      |      |      |
| create_time   | 创建时间                             | datetime |      | n      |      |      |      |
| record_status | 记录状态：<br/>0：已删除<br/>1：有效 | int      |      | n      | 1    |      |      |




### T_FEEDBACK（反馈表）OK

| 字段          | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                                     |
| ------------- | -------- | ---- | ------ | ---- | ---- | ---- | ---------------------------------------- |
| id            | varchar  | 64   | n      |      | Y    |      | id                                       |
| kind          | int      |      | n      |      |      |      | 反馈类型<br/>0：使用反馈<br/>1：信息举报 |
| target_id     | varchar  | 64   | y      |      |      |      | 主题贴id（互动回复或招领id）             |
| school_id     | varchar  | 63   | n      |      |      |      | 学校id                                   |
| user_id       | varchar  | 64   | n      |      |      |      | 用户id                                   |
| username      | varchar  | 64   | n      |      |      |      | 用户名（学号）                           |
| realName      | varchar  | 256  | n      |      |      |      | 真名                                     |
| subject       | varchar  | 256  | n      |      |      |      | 主题                                     |
| content       | varchar  | 1024 | n      |      |      |      | 内容                                     |
| create_time   | datetime |      | n      |      |      |      | 创建时间                                 |
| status        | int      |      | n      | 0    |      |      | 状态：<br/>0：未读<br/>1：已读           |
| handler_id    | varchar  | 64   |        |      |      |      | 处理人id                                 |
| handler_name  | varchar  | 256  |        |      |      |      | 处理人名字                               |
| handler_email | varchar  | 256  |        |      |      |      | 处理人邮箱                               |
| answer        | varchar  | 1024 | y      |      |      |      | 回复内容                                 |
| handler_time  | datetime |      | y      |      |      |      | 处理时间                                 |
| record_status | int      |      | n      | 1    |      |      | 记录状态：<br />0：已删除<br />1：有效   |



### T_LOG（日志表）OK

| 字段         | 类型     | 长度 | 可Null | 默认 | 主键 | 唯一 | 说明                  |
| ------------ | -------- | ---- | ------ | ---- | ---- | ---- | --------------------- |
| id           | Long     | 64   | n      |      | Y    |      | 日志id                |
| service_code | Integer  | -    | n      |      |      |      | 服务码                |
| user_id      | varchar  | 64   | n      |      |      |      | 用户id                |
| action_code  | Integer  | -    | n      |      |      |      | 动作<br/>1234对应CRUD |
| target_name  | varchar  | 64   |        |      |      |      | 操作对象名            |
| target_id    | varchar  | 64   | y      |      |      |      | 操作对象主键id        |
| about        | varchar  | 256  | y      |      |      |      | 说明                  |
| ip           | varchar  | 256  | y      |      |      |      | ip地址                |
| time_cost    | Long     | -    | n      |      |      |      | 耗时ms                |
| create_time  | datetime | -    | n      |      |      |      | 创建时间              |

