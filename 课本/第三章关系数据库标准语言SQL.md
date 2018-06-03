# 第三章关系数据库标准语言SQL

## SQL概述

### SQL的特点

1. 综合统一。
2. 高度非过程化。
3. 面向集合的操作方式。
4. 以同一种语法结构提供多种使用方式。
5. 语言简洁易学易用。



### 学生-课程数据库

后面的操作都是基于这几个表：

![](https://s1.ax2x.com/2018/06/02/7ewmQ.png)

![](https://s1.ax2x.com/2018/06/02/7U3Z2.png)



### 数据定义

#### 模式的定义与删除：

加黑的是可以换掉的。。。

**<>**表示从中选一个

1. 定义模式：

create schema "**模式名**" authorization **用户名**；

2. 删除模式：

drop schema **模式名** <cascade|restrict>;

#### 基本表的定义修改删除

1. 定义基本表：

create table <表名>（...)

例：

```java
create table Student(
	Sno char(9) primery key,//列级完整性条件，Sno是主码
	Sname char(20) unique,//Sname取唯一值
    Ssex char(2) not null,//列级完整性条件,Ssex不能取空值
    Sage smallint,
    Sdept char(20)
    foreign key (Sname) references Course (C_Sname)//表级完整性约束条件，Sname是外码，被参照表是Course，被参照列是C_Sname
);
```

2. 数据类型：

![](https://s1.ax2x.com/2018/06/03/7N7ah.png)

3. 模式与表：

每一个基本表都属于一个模式，一个模式包含多个基本表。定义基本表时一般有三种方法定义他的模式：

* ```java
  create table "S-T".Student(···);//Student所属的模式是S-T
  ```

* 在创建模式与剧中同时创建表

* 设置所属的表

4. 修改基本表：

```java
alter table <表名>
[add [column] <新列名> <数据类型> [完整性约束条件]]//add 语句用于增加新的列或完整性条件
[add [表级完整性约束条件]]
[drop [column] <列名> [cascade|restrict]]//删除列，指定cascade会删除引用了该列的其他对象，如视图；restrict，rdbms将拒绝删除该列。
[drop constraint <完整性约束名>[restrict|cascade]]//删除约束
[alter column <列名> <数据类型>]
```

5. 删除基本表：

```java
drop table <表名>[restrict|cascade];
```

#### 索引的建立与删除

1. 建立索引：

```java
create [unique][cluster] index <索引名>//unique表名此索引的每一个索引值只对应唯一的数据记录，cluster表示建立的索引是聚簇索引
on <表名>(<列名>[<次序>]···);//asc(升序)，desc(降序)。
```

2. 修改索引：

```java
alter index <旧索引名> rename to <新名>;
```

3. 删除索引：

```java
drop index <索引名>
```

#### 数据字典



### 数据查询

```java
select [all|distinct]<目标列表达式>[,<目标列表达式>]···//默认为all
from <表名或视图名>[,<表名或视图名>]|(<select语句)[as]<别名>
[where <条件表达式>]
[group by <列名1>[having<条件表达式>]]//如果有group by子句，则将结果按照列名1的值进行分组，该属性列值相等的元组为一个组。通常在每组中作用聚集函数。
[order by <列名2>[asc|desc]];
```

#### 单表查询

1. 选择表中的若干列：

* 查询指定列：

```java
select Sno,Sname
from Student;
```

* 查询全部列：

```java
select *
from student;
```

* 查询经过计算的值：

```java
select Sname,2014-Sage
from Student;
```

2. 选择表中的若干元组：

* 清除取值重复的行：

```java
select distinct Sno
from SC;
```

* 查询满足条件的元组：

![](https://s1.ax2x.com/2018/06/03/7kMcp.png)

> 比较大小：

```java
select Sname
from Student
where Sdept='CS';
```

> 确定范围：

```java
select Sname,Sdept,Sage
from Student
where Sage between 20 and 23;//包括20岁和23岁
```

> 字符匹配：

**%代表任意长度的字符串**

**_ 代表任意单个字符**

```java
select *
from Student
where Sname like '刘%';
```

**若查询的字符中包含‘_'，那么要使用escape换码字符**

```java
select *
from Student
where Sname like 'DB\_Design' escape'\';
```

> 空值的查询：

```java
select *
from Student
where Sname is null;
```

* order by子句：

**order by子句对查询结果按照一个或多个属性列的升序（asc）或降序（desc）排列，默认升序**

```java
select Sno,Grade
from SC
where Cno='3'
order by Grade desc;
```

* 聚集函数：

![](https://s1.ax2x.com/2018/06/03/7ksZG.png)

> 查询学生总人数：

```java
select count(*)
from Student;
```

> 查询选秀了课程的学生人数：

```java
select count(distinct Sno)
from SC;
```

> 计算算修1号课程的学生的平均成绩：

```java
select avg(Grade)
from SC
where Cno='1';
```

> 查询选修1号课程的学生的最高成绩：

```java
select max(Grand)
from SC
where Cno='1';
```

> 查询学生1607094128选修课程的总分数：

```java
select sum(Ccredit)
from Sc,Course
where Sno='1607094128' and SC.Cno=Course.Cno
```

* group by子句

作用：值相等的为一组

> 求各个课程号及相应的选课人数

```java
select Cno,count(Sno)
from Sc
group by Cno;
```

#### 连接查询

1. 等值与非等值连接查询：
2. 自身连接：一个表和自己进行连接

```java
select first.Cno,second.Cpno
from Course first,Course second
where first.Cpo=second.Cno;
```

3. 外连接：
4. 多表连接：

#### 嵌套查询

1. 带有IN谓词的子查询

**相关子查询和不相关子查询**

2. 带有比较运算符的子查询
3. 带有any（some）或all谓词的子查询

```java
select Sname,Sage
from Student
where Sage < any(select Sage
			    from Student
			    where Sdept='CS')
and Sdep!='CS';
```

4. 带有exists谓词的子查询

**exists即存在量词**



### 数据更新

#### 插入数据

1. 插入元组

```java
insert
into <表名>(···)
values(···)
```

2. 修改数据

```java
updata <表名>
set <列名>=<表达式>
where <条件语句>
```

3. 删除数据

```java
delete
from <表名>
where <条件>
```



### 视图

#### 定义试图

1. 建立视图

```java
create view <视图名>
as <子查询>
[with check option];//表示以后对该视图进行操作时，系统自动加上试图定义时的谓词条件
```

2. 删除试图

```java
drop view <视图名>[cascade];/.cascade表明与他关联的都删掉
```

3. 更新视图