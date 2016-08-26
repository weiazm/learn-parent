drop schema if exists test;
Create table test.contact(
	Id int(11) unsigned not null auto_increment comment '自增主键',
	Name varchar(100) not null comment '姓名',
	Mobile varchar(20) not null comment '电话',
	primary key(id)
);
Insert into test.contact(id,name,mobile) values(1,'张三','13800138000');
