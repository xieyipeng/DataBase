--***********************实验二***********************--
--**********************第一部分**********************--
create table SC(
Sno char(9),
Cno char(4),
Grande smallint,
PRIMARY KEY(SNO,CNO),
)

--查询选修了课程的学生的学号；
select distinct Sno
from SC

--查询学生姓名和出生年份，将列别名改为XM和CSNF；
select Sname Xm,2018-Sage CSNF
from Student

--查询年龄在18-20之间的学号，姓名，系
select Sno,Sname,Sdep
from Student
where Sage>=18 and Sage<=20;

--查询信息系（IS）、数学系（MA）和计算机科学系（CS）学生的姓名和性别。
/* 
select Sname,Ssex
from Student
where Sdep='CS';

select Sname,Ssex
from Student
where Sdep='MA';

select Sname,Ssex
from Student
where Sdep='IS';
*/
select Sname,Ssex
from Student
where Sdep in ('IS','MA','CS')

--用LIKE查询课程名为DB_S程的课程号和学分。
select Cno,Ccredit
from Course
where Cname like '数据%'

--查所有有成绩的学生学号和课程号。
/*
select distinct Sno,Cno
from SC
where SC.Grande!='NULL'
*/
select distinct Sno,Cno
from SC
where SC.Grande is not NULL

--查询全体学生情况，查询结果按所在系的系号升序排列，同一系中的学生按年龄升序排列
--！
select *
from Student
order by Sdep,Sage

--查询选修了课程的学生人数。
--！！！！
select Cno,count(Sno) as 学生人数
from SC
where Cno='3'
group by Cno

--计算1号课程的学生平均成绩。
--！
select Cno,avg(Grande) as 平均成绩
from SC
where Cno='1'
group by Cno

--查询有3人以上（含3人）同学选修课程的课程号。
--！
select Cno
from SC
group by Cno
having count(Sno)>=3

--查询1号课程的间接先修课。
--！！！！！！
select first.Cno,second.Cpno Cppno
from Course first,Course second
where first.Cno='1' and second.Cno=first.Cpno

--查询每个学生的学号、姓名、选修的课程名及成绩。
select Student.Sno,Sname,Cname,Grande
from Course,SC,Student
where SC.Sno=Student.Sno and SC.Cno=Course.Cno;

--找出每个学生等于他选修课程平均成绩的课程号。
--！
select SC.Sno,Cno
from SC,(select SNO,avg(Grande) NewGrande
		 from SC 
		 group by Sno)as NewSC
where SC.Sno=NewSC.Sno and Grande=NewGrande

--**********************第二部分**********************--
--查询全体学生的学号和姓名和电话。
select Sno,Sname,Stel
from Student

--查询全体学生的基本情况。
select *
from Student

--查询带了毕业生的老师的教师号。
select Tno
from Teacher
where Tright='true'

--查询教师“朱龙”基本情况。
select *
from Teacher
where Tname='朱龙'

--查询所有姓“王”同学的基本情况。
select *
from Student
where Sname like '王%'

--统计每种学历老师的人数。
--！
select Tdegree,count(Tno) counts
from Teacher
group by Tdegree

--查询1组老师所带毕业生的基本情况（包括学号，姓名，教师号，教师姓名，毕业设计题目）
--(注:1组老师只能带1组的学生)
--！
select Student.Sno,Sname,Teacher.Tno,Tname,Hname
FROM Student,Desion,Teacher
WHERE Teacher.Tno=Desion.Tno and Desion.Sno=Student.Sno 
	and Tgroup=1 and Sgroup=1

--查询所有学生的选题情况（包括没有选题的学生），包括学号，姓名，题目名称，教师名称。
--(注:要求老师的组号和学生的组号相一致.)
/*
create view S_B as
select STUDENT.SNO,SNAME,SSEX,SCLASS,STEL,SGROUP,SPASSWORD,Hno,Hname,Tno
from Student left outer join Desion ON(Desion.Sno=Student.Sno)

select S_B.Sno,S_B.Sname,S_B.Hname,Tname
from S_B left outer join Teacher on(S_B.Tno=Teacher.Tno)
*/
select distinct Student.Sno,Sname,Hname,Desion.Tno
from Student,Desion,Teacher
where Student.Sgroup=Teacher.Tgroup and Desion.Sno=Student.Sno
