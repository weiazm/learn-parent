drop schema if exists test;
Create table test.contact(
	id int(11) unsigned not null auto_increment comment '自增主键',
	name varchar(100) not null comment '姓名',
	mobile varchar(20) not null comment '电话',
	primary key(id)
);
Insert into test.contact(id,name,mobile) values(1,'张三','13800138000');
