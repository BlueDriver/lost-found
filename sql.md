```sql
# 创建数据库
CREATE DATABASE  `LOST_FOUND` DEFAULT CHARACTER SET utf8mb4;

# 切换数据库
USE LOST_FOUND;

#  初始化学校
INSERT INTO `LOST_FOUND`.`t_school`
	(`campus_id`, `about`, `address`, `campus_name`, `create_time`, 
     `creator_id`, `icon`, `phone_number`, `record_status`, 
     `school_id`, `school_name`, `status`) 
	VALUES ('000', 'ECUT', 'nc', '广兰校区', '2019-04-08 07:10:58',
            '000', 'aa', '0791418418', 1, '000', '东华理工大学', 1);
INSERT INTO `LOST_FOUND`.`t_school`
	(`campus_id`, `about`, `address`, `campus_name`, `create_time`, 
     `creator_id`, `icon`, `phone_number`, `record_status`, 
     `school_id`, `school_name`, `status`) 
	VALUES ('001', 'NCU', 'nc', '前湖', '2019-04-08 07:11:58',
            '000', 'aa', '0791418418', 1, '001', '南昌大学', 1);     
            
# 初始化物品类别
INSERT INTO LOST_FOUND.t_category (level, name, target_id, about, create_time, creator_id, image, record_status) VALUES (1, '书籍', '000', null, '2019-04-15 05:54:56', 'system', null, 1);
INSERT INTO LOST_FOUND.t_category (level, name, target_id, about, create_time, creator_id, image, record_status) VALUES (1, '其他', '000', '其他类别', '2019-05-18 08:59:50', '001', null, 1);
INSERT INTO LOST_FOUND.t_category (level, name, target_id, about, create_time, creator_id, image, record_status) VALUES (1, '电子数码', '000', '电子数码', '2019-05-25 03:13:26', '001', null, 1);

# 初始化用户表
INSERT INTO LOST_FOUND.t_user (id, activate_code, campus_id, create_time, email, icon, kind, last_login, password, phone_number, real_name, record_status, school_id, status, username, gender) VALUES ('316a62ea3a444711927d872609296dbf', '5cca55cf9cdb4212acd1363038de5fc1', '000', '2019-04-18 18:50:41', 'admin@qq.com', null 2, '2019-05-24 22:16:26', '4QrcOUm6Wau+VuBX8g+IPg==', '13511112222', '赵大海', 1, '000', 1, '201520180517', 1);
INSERT INTO LOST_FOUND.t_user (id, activate_code, campus_id, create_time, email, icon, kind, last_login, password, phone_number, real_name, record_status, school_id, status, username, gender) VALUES ('6529d0739c344ccba3f4f0f820edcc98', 'a939235bd94c4c6188f4a07b88ca4331', '000', '2019-05-24 09:21:57', 'user@foxmail.com', null, 0, '2019-05-25 11:19:10', '4QrcOUm6Wau+VuBX8g+IPg==', null, '钱二喜', 1, '000', 1, '201520180508', 1);

# 初始化学生表
INSERT INTO LOST_FOUND.t_student (id, academy, campus_name, class_num, create_time, gender, id_card_num, major, name, nation, school_id, student_kind, student_num, user_id) VALUES ('f3c622a6ee004ae5ab8ce7f052bbb275', '软件学院', '南昌校区', '1521805', '2019-04-18 18:50:08', 1, '123', '软件工程', '赵大海', '汉族', '000', '普通本科学生', '201520180517', '316a62ea3a444711927d872609296dbf');
INSERT INTO LOST_FOUND.t_student (id, academy, campus_name, class_num, create_time, gender, id_card_num, major, name, nation, school_id, student_kind, student_num, user_id) VALUES ('fe77beef0ca34bba8812273ad8c00aa6', '软件学院', '南昌校区', '1521805', '2019-05-24 09:21:57', 1, '456', '软件工程', '钱二喜', '汉族', '000', '普通本科学生', '201520180508', '6529d0739c344ccba3f4f0f820edcc98');

# 初始化通知
INSERT INTO LOST_FOUND.t_notice (id, content, create_time, creator_id, end_time, fix_top, image, kind, level, link, record_status, start_time, status, target_id, title) VALUES ('2629cced6e5a4da78fb430f4a59765ee', '我是管理员，有事随时联系哈', '2019-04-22 09:06:04', 'ab3c42e0eb9f4c79aa6a824873b7b704', null, 0, null, 0, 1, null, 1, null, 1, '000', '大家好吗？');
INSERT INTO LOST_FOUND.t_notice (id, content, create_time, creator_id, end_time, fix_top, image, kind, level, link, record_status, start_time, status, target_id, title) VALUES ('6520c1aaa750465cb419ce8498c09fee', '系统开发进入尾声工作，大家可以进行试用了，首先认证并激活邮箱，再登录既可以发表和查看别人发的东西，自己也可以发东西，快来玩玩吧！', '2019-04-22 07:41:09', '001', null, 1, null, 0, 1, null, 1, null, 1, '000', '系统开发试用通知');
INSERT INTO LOST_FOUND.t_notice (id, content, create_time, creator_id, end_time, fix_top, image, kind, level, link, record_status, start_time, status, target_id, title) VALUES ('e8f8b8558e3642d1a6d5633048703997', '差不多这样了哦', '2019-04-22 20:29:38', '001', null, 0, null, 0, 1, null, 1, null, 1, '000', '做完了哈');


```

