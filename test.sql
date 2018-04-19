create database XSCJ
on(
name='XSCJ_Data',
filename='F:\github\DataBase\XSCJ\XSCJ.mdf',
size=5MB,
maxsize=10MB,
filegrowth=10%
)
log on(
name='XSCJ_Log',
filename='F:\github\DataBase\XSCJ\XSCJ.ldf',
size=2MB,
maxsize=5MB,
filegrowth=1MB
)
go

create table Course(
Cno char(4) primary key,
Cname char(40) not NULL,
Cpno char(4),
Ccredit smallint,
foreign key(Cpno) references course(Cno)
);

alter table Course add Cterm char(4);

create unique index Cname_idx on Course(Cname);

alter table Course drop column Cterm;

drop index Course.Cname_idx;

create table Student(
Sno char(9) primary key,
Sname char(20) unique,
Ssex char(2),
Sage smallint,
Sdep char(20)
);

drop table Student;

--drop database XSCJ;
--drop database BSXT;

create database BSXT
on(
name='BSXT_Data',
filename='F:\github\DataBase\BSXT\BSXT.mdf',
size=5MB,
maxsize=10MB,
filegrowth=10%
)
log on(
name='BSXT_Log',
filename='F:\github\DataBase\BSXT\BSXT.ldf',
size=2MB,
maxsize=5MB,
filegrowth=1MB
)
go

alter database BSXT
modify file(
name='BSXT_Data',
maxsize=unlimited
)
go

create table Student(
Sno char(10) primary key,
Sname char(10) not null,
Ssex char(2) check(Ssex='男' or Ssex='女'),
Sclass char(10),
Stel char(11) not null,
Sgroup char(1) not null,
Spassword char(10) not null
);

create table Teacher(
Tno char(10) primary key,
Tname char(10) not null unique,
Tsex char(2) check(Tsex='男' or Tsex='女'),
Tdept char(20)  not null default('计算机科学与技术系'),
Tdegree char(8),
Ttitle char(10),
Tright bit not null check(Tright='true' or Tright='false'),
Ttel char(11) not null,
Temail char(50),
Tgroup char(1) not null,
Tpassword char(10) not null
)

create table Desion(
Hoo char(4) primary key,
Hname char(50) not null,
Hstatus char(5) not null,
Hcontent char(200),
dircetion char(200),
Tno char(10),
Sno char(10),
foreign key (Tno) references Teacher(Tno),
foreign key (Sno) references Student(Sno)
)

create table SC(
Sno char(9) not null,
Cno char(4) not null,
Grande char(4) not null
)

--查询选修了课程的学生的学号；
select distinct Sno
from SC

--查询学生姓名和出生年份，将列别名改为XM和CSNF；
select Sname Xm,(2018-Sage) CSNF
from Student

--查询年龄在18-20之间的学号，姓名，系
select Sno,Sname,Sdep
from Student
where Sage>=18 and Sage<=20;

--查询信息系（IS）、数学系（MA）和计算机科学系（CS）学生的姓名和性别。 
select Sname,Ssex
from Student
where Sdep='CS';

select Sname,Ssex
from Student
where Sdep='MA';

select Sname,Ssex
from Student
where Sdep='IS';

--用LIKE查询课程名为DB_S程的课程号和学分。
select Cno,Ccredit
from Course
where Cname like '数据%';

--查所有有成绩的学生学号和课程号。
select distinct Sno,Cno
from SC
where SC.Grande!='NULL';

--查询全体学生情况，查询结果按所在系的系号升序排列，同一系中的学生按年龄升序排列

--查询选修了课程的学生人数。

--计算1号课程的学生平均成绩。

--查询有3人以上（含3人）同学选修课程的课程号。

--查询1号课程的间接先修课。

--查询每个学生的学号、姓名、选修的课程名及成绩。
select Student.Sno,Sname,Cname,Grande
from Course,SC,Student
where SC.Sno=Student.Sno and SC.Cno=Course.Cno;

--找出每个学生等于他选修课程平均成绩的课程号。




--查询全体学生的学号和姓名和电话。

--查询全体学生的基本情况。

--查询带了毕业生的老师的教师号。

--查询教师“朱龙”基本情况。

--查询所有姓“王”同学的基本情况。

--统计每种学历老师的人数。

--查询1组老师所带毕业生的基本情况（包括学号，姓名，教师号，教师姓名，毕业设计题目）
--(注:1组老师只能带1组的学生)

--查询所有学生的选题情况（包括没有选题的学生），包括学号，姓名，题目名称，教师名称。
--(注:要求老师的组号和学生的组号相一致.)
