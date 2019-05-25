# API
统一请求前缀
```
/lostfound/api/v1
```
## 开放接口

统一请求前缀

```
/public
```

### 1. 获取学校列表 OK

#### 接口地址（HTTP GET）

```
/schools
```

#### 请求示例
```java
/lostfound/api/v1/public/schools
```

#### 请求参数

无

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
      "schools": [
          {
                "schoolId": "12345678123456781234567812345678",
                "schoolName": "东华理工大学" 
          },
          {
                "schoolId": "12345678123456781234567812345677",
                "schoolName": "南昌大学" 
          },
          {
                "schoolId": "12345678123456781234567812345676",
                "schoolName": "江西财经大学" 
          }
      ]
  },
  "ext" : null
}
```

#### 返回参数
| 参数    | 类型    | 是否必须 | 长度/取值范围 | 说明         |
| ------- | ------- | -------- | ------------- | ------------ |
| success | boolean | y        | -             | 请求是否成功 |
| code    | int     | y        | [1000, 9999]  | 返回码       |
| msg     | String  | y        | -             | 说明         |
| data    | Object  | y        | -             | 数据         |
| ext     | Object  | y        | -             | 扩展数据     |

> 注：如上，所有接口返回参数为统一格式，下文不再赘述，仅对`data`内参数进行必要说明

##### school
| 参数       | 类型   | 是否必须 | 长度/取值范围 | 说明     |
| ---------- | ------ | -------- | ------------- | -------- |
| schoolId   | String | y        | -             | 学校id   |
| schoolName | String | y        | -             | 学校名称 |

#### 错误返回示例
```java
{
  "success" : false,
  "code" : 1002,
  "msg" : "缺少参数",
  "data" : { },
  "ext" : "org.springframework.web.bind.MissingServletRequestParameterException"
}
```
### 2. 获得验证码图片 OK

#### 接口地址（HTTP GET）

```
/verifyCode
```

#### 请求示例
```java
/lostfound/api/v1/public/verifyCode
```

#### 请求参数

无

#### 返回示例

无

#### 返回参数
无

### 3. 认证/登录 OK

> 当前仅限东华理工大学学生使用

#### 接口地址（HTTP POST）

```
/recognize
```

#### 请求示例

```java
{
    "schoolId": "12345678123456781234567812345678",
    "username": "201520180508",
    "password": "123456",
    "email": "cpwu@foxmail.com",
    "code": "15218"
}
```

#### 请求参数

| 参数     | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| -------- | ------ | -------- | ------------- | ------ |
| schoolId | String | y        | 32            | 学校id |
| username | String | y        | 1-64          | 学号   |
| password | String | y        | 1-64          | 密码   |
| email    | String | y        | 256           | 邮箱   |
| code     | String | y        | 5             | 验证码 |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
      "user": {
          "studentNum": "201520180508",
          "realName": "cpwu",
          "icon": "www.baidu.com/icon.png",
          "email": "cpwu@foxmail.com"
          "schoolName": "东华理工大学",
          "gender": 1,
          "createTime": "2019-04-10 19:06:10",
          "lastLogin": "2019-04-10 19:06:10",
          "kind": 0
      }
  },
  "ext" : null
}
```

#### 返回参数

#### user

| 参数       | 类型   | 是否必须 | 长度/取值范围 | 说明               |
| ---------- | ------ | -------- | ------------- | ------------------ |
| studentNum | String | y        | -             | 学号               |
| realName   | String | y        | -             | 真实姓名           |
| icon       | String | y        | -             | 头像地址，默认null |
| email      | String | y        | -             | 邮箱               |
| schoolName | String | y        | -             | 学校名称           |
| gender     |        |          |               |                    |
| createTime |        |          |               |                    |
| lastLogin  |        |          |               |                    |
| kind       | int    | y        | -             | 用户类型           |

> 若该用户未激活，则`user`为`null`, 该用户需要验证邮箱以激活账号后才返回`user`参数

### 4. 用户激活 OK

#### 接口地址（HTTP GET）

```
/activate/code
```

#### 请求示例

```java
/lostfound/api/v1/public/activate/12345678874563217854123669874521
```

#### 请求参数

| 参数 | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| ---- | ------ | -------- | ------------- | ------ |
| code | String | y        | 32            | 激活码 |

#### 返回示例

无

> 说明
>
> 1. 若激活成功，跳转success页面
> 2. 若激活码已失效，跳转invalid页面

#### 返回参数

无

### 5. 用户登录 OK

#### 接口地址（HTTP POST）

```
/signIn
```

#### 请求示例

```java
{
    "username": "201520180508",
    "password": "12345",
    "vcode": "12345"
}
```

#### 请求参数

| 参数     | 类型   | 是否必须 | 长度/取值范围 | 说明                                                  |
| -------- | ------ | -------- | ------------- | ----------------------------------------------------- |
| username | String | y        | 1-64          | 用户登录名<br/>学生为学号，教职工为工号，管理员为邮箱 |
| password | String | y        | 1-64          | 登录密码（独立密码）                                  |
| vcode    | String | y        | 5             | 验证码                                                |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
      "user": {
          "studentNum": "201520180508",
          "realName": "cpwu",
          "icon": "www.baidu.com/icon.png",
          "email": "cpwu@foxmail.com",
          "phoneNumber": "15911112222",
          "schoolName": "东华理工大学",
          "gender": 1,
          "createTime": "2019-04-10 19:06:10",
          "lastLogin": "2019-04-10 19:06:10",
          "kind": 0
      }
  },
  "ext" : null
}
```

#### 返回参数

#### user

| 参数       | 类型   | 是否必须 | 长度/取值范围 | 说明               |
| ---------- | ------ | -------- | ------------- | ------------------ |
| studentNum | String | y        | -             | 学号               |
| realName   | String | y        | -             | 真实姓名           |
| icon       | String | y        | -             | 头像地址，默认null |
| email      | String | y        | -             | 邮箱               |
| schoolName | String | y        | -             | 学校名称           |
| gender     |        |          |               |                    |
| createTime |        |          |               |                    |
| lastLogin  |        |          |               |                    |
| kind       | int    | y        | -             | 用户类型           |

## 管理员/用户公用接口

统一请求前缀
```
/common
```

### 1. 获取类别列表 OK

#### 接口地址（HTTP POST）

```
/category
```

#### 请求示例
```java
http://localhost:8080/api/v1/common/category
```
#### 请求参数
无

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
      "list": [
          {
              "name": "书籍字画",
              "about": "书籍、绘画等相关物品",
              "image": null,
              "creatorId": "1234",
              "createTime": "2019-04-11 16:42:10",
              "level": 1,
              "targetId": "000"
          }
      ]
  },
  "ext" : null
}
```

#### 返回参数

#### list

| 参数       | 类型   | 是否必须 | 长度/取值范围 | 说明               |
| ---------- | ------ | -------- | ------------- | ------------------ |
| name       | String | y        | -             |                    |
| about      | String | y        | -             |                    |
| image      | String | y        | -             | 头像地址，默认null |
| creatorId  | String | y        | -             | 邮箱               |
| createTime | Date   | y        | -             |                    |
| level      | int    | y        |               |                    |
| targetId   | String | y        | -             |                    |

### 2. 获取公告列表

#### 接口地址（HTTP POST）

```
/category
```

#### 请求示例

```java
http://localhost:8080/api/v1/common/category
```

#### 请求参数

无

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
      "list": [
          {
              "name": "书籍字画",
              "about": "书籍、绘画等相关物品",
              "image": null,
              "creatorId": "1234",
              "createTime": "2019-04-11 16:42:10",
              "level": 1,
              "targetId": "000"
          }
      ]
  },
  "ext" : null
}
```

#### 返回参数

#### list

| 参数       | 类型   | 是否必须 | 长度/取值范围 | 说明               |
| ---------- | ------ | -------- | ------------- | ------------------ |
| name       | String | y        | -             |                    |
| about      | String | y        | -             |                    |
| image      | String | y        | -             | 头像地址，默认null |
| creatorId  | String | y        | -             | 邮箱               |
| createTime | Date   | y        | -             |                    |
| level      | int    | y        |               |                    |
| targetId   | String | y        | -             |                    |



## 用户相关接口

统一请求前缀
```
/user
```

### 1. 发布招领信息 OK

#### 接口地址（HTTP POST）

```
/pub
```

#### 请求示例

```java
 {
     applyKind: 0,
     categoryName: 0,
     title: "",
     about: "",
     location: null,
     images:[],//srcList
 }
```

#### 请求参数

| 参数         | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| ------------ | ------ | -------- | ------------- | ------ |
| applyKind    | String | y        | 32            | 学校id |
| categoryName | String | y        | 1-64          | 学号   |
| title        | String | y        | 1-64          | 密码   |
| about        | String | y        | 256           | 邮箱   |
| location     | String | y        | 5             | 验证码 |
| images       |        |          |               |        |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {},
  "ext" : null
}
```

#### 返回参数
如上

### 2. 查询招领列表 OK

#### 接口地址（HTTP POST）

```
/page
```

#### 请求示例

```java
{
  "kind": 0,
  "category": "",
  "keyword": "",
  "username": "",
  "pageNum": 0,
  "pageSize": 10
}
```

#### 请求参数

| 参数     | 类型 | 是否必须 | 长度/取值范围 | 说明 |
| -------- | ---- | -------- | ------------- | ---- |
| kind     |      |          |               |      |
| category |      |          |               |      |
| keyword  |      |          |               |      |
| username |      |          |               |      |
| pageNum  |      |          |               |      |
| pageSize |      |          |               |      |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
    "page" : {
      "total" : 1,
      "totalPage" : 1,
      "pageNum" : 0,
      "pageSize" : 10,
      "list" : [ {
        "id" : "00000000001",
        "icon" : null,
        "kind" : 0,
        "username" : "201520180508",
        "realName": "赵大海",
        "time" : "2019-04-16 03:55:56",
        "location" : "y1",
        "title" : "i lost a book",
        "about" : "about",
        "images" : [ ],
        "category" : "电子数码",
        "lookCount" : 0,
        "commentCount" : 0
      } ]
    }
  },
  "ext" : null
}
```

#### 返回参数

#### page

| 参数 | 类型 | 是否必须 | 长度/取值范围 | 说明 |
| ---- | ---- | -------- | ------------- | ---- |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |

### 3. 查询招领详情 OK

#### 接口地址（HTTP POST）

```
/detail
```

#### 请求示例

```java
http://localhost:8080/api/v1/user/detail?id=553de5ced25c4f819ae34485473a3907
```

#### 请求参数

| 参数 | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| ---- | ------ | -------- | ------------- | ------ |
| id   | String | y        | -             | 启事id |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
    "item" : {
      "id" : "44be3e3484134f16ae7b4e69c115517b",
      "icon" : null,
      "kind" : 0,
      "userId" : "ab3c42e0eb9f4c79aa6a824873b7b704",
      "username" : "201520180508",
      "time" : "2019-04-17 14:52:22",
      "location" : null,
      "title" : "+V有福利",
      "about" : "+V福利",
      "images" : [ "upload_3540757245517624377.jpg" ],
      "category" : "书籍",
      "lookCount" : 6,
      "status" : 1,
      "dealTime" : null,
      "isSelf" : true,
      "email" : "cpwu@foxmail.com",
      "phoneNumber" : null
    }
  },
  "ext" : null
}
```

#### 返回参数

#### item

| 参数 | 类型 | 是否必须 | 长度/取值范围 | 说明 |
| ---- | ---- | -------- | ------------- | ---- |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |

### 4. 查询启事评论 OK

#### 接口地址（HTTP POST）

```
/comments
```

#### 请求示例

```java
http://localhost:8080/api/v1/user/comments?id=44be3e3484134f16ae7b4e69c115517b
```

#### 请求参数

| 参数 | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| ---- | ------ | -------- | ------------- | ------ |
| id   | String | y        | -             | 启事id |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
    "comments" : [ {
      "id" : "6f3f1222f87a4208b43ef10135ed65db",
      "icon" : null,
      "username" : "201520180508",
      "time" : "2019-04-17 15:03:18",
      "content" : "人生的奔跑不在于瞬间的爆发，而在于图中的坚持！"
    } ]
  },
  "ext" : null
}
```

#### 返回参数

#### comments

| 参数 | 类型 | 是否必须 | 长度/取值范围 | 说明 |
| ---- | ---- | -------- | ------------- | ---- |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |

### 5. 删除招领信息 OK

#### 接口地址（HTTP POST）

```
/removeLost
```

#### 请求示例

```java
{
    idList: [
        "00000000000000001"
    ]
}
```

#### 请求参数

| 参数 | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| ---- | ------ | -------- | ------------- | ------ |
| id   | String | y        | -             | 启事id |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" :{},
  "ext" : null
}
```

#### 返回参数

#### 如上

### 6. 发布评论 OK

#### 接口地址（HTTP POST）

```
/comment
```

#### 请求示例

```java
{
    "targetId": "00000000001",
    "content": "thank u!"
}
```

#### 请求参数

| 参数     | 类型   | 是否必须 | 长度/取值范围 | 说明     |
| -------- | ------ | -------- | ------------- | -------- |
| targetId | String | y        | -             | 启事id   |
| content  | String | y        | 1-128         | 评论内容 |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {},
  "ext" : null
}
```

#### 返回参数

如上

### 7. 删除评论 OK

#### 接口地址（HTTP POST）

```
/removeComment
```

#### 请求示例

```java
{
    idList: [
        "00000000000000001"
    ]
}
```

#### 请求参数

| 参数 | 类型   | 是否必须 | 长度/取值范围 | 说明   |
| ---- | ------ | -------- | ------------- | ------ |
| id   | String | y        | -             | 启事id |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" :{},
  "ext" : null
}
```

#### 返回参数

如上

### 8. 查询用户消息 OK

#### 接口地址（HTTP POST）

```
/message
```

#### 请求示例

```java
http://localhost:8080/api/v1/user/message
```

#### 请求参数

| 参数 | 类型 | 是否必须 | 长度/取值范围 | 说明 |
| ---- | ---- | -------- | ------------- | ---- |
|      |      |          |               |      |

#### 返回示例

```java
{
  "success" : true,
  "code" : 1000,
  "msg" : "处理成功",
  "data" : {
    "list" : [ {
      "id" : "6f3f1222f87a4208b43ef10135ed65db",
      "userId": "0000000000001",
      "icon" : null,
      "username" : "201520180508",
      "time" : "2019-04-17 15:03:18",
      "title": "我丢了一只袜子",
      "content" : "人生的奔跑不在于瞬间的爆发，而在于图中的坚持！"
    } ]
  },
  "ext" : null
}
```

#### 返回参数

#### list

| 参数 | 类型 | 是否必须 | 长度/取值范围 | 说明 |
| ---- | ---- | -------- | ------------- | ---- |
|      |      |          |               |      |
|      |      |          |               |      |
|      |      |          |               |      |

### 4. 重置/修改密码




### 发送反馈

### 修改头像



## 管理员相关接口

接口前缀

```javascript
/pub
```

### 1. 获取统计信息

```javascript
/data
```

### 2. 查看用户详情 OK

### 3. 冻结用户 OK

### 4. 新增类别

### 4. 获取所有类别



