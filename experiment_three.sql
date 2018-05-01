--***********************实验三***********************--
insert
into Student(Sno,Sname,Ssex,Sage,Sdep)
values('95007','张丽娜','女',18,'IS')

insert
into SC(Sno,Cno)
values('95007','1')

create table DeptAvg(
Dept char(20) primary key,
Davg smallint
)
insert
into DeptAvg(Dept,Davg)
select Sdep,avg(Grande)
from Student left outer join SC on (Student.Sno=SC.Sno)
group by Sdep

update Student
set Sage=22
where Student.Sno='201215123'

update Student
set Sage=Sage+1
where Sage is not null

update SC
set Grande=0
where Sno in(
	select Sno
	from Student
	where Student.Sdep='IS'
)

delete
from Student
where Sno='95001'

delete
from SC
where Sno in(
	select Sno
	from Student
	where Sdep='IS'
)

create view CS_S(Vno,Vname,Vage,Vdep)
as select
	Sno,Sname,Sage,Sdep
	from Student
	where Sdep='CS'

--drop view CS_S

insert
into CS_S
values('95002','李华华',18,'IS')

insert
into CS_S
values('95003','王冬冬',20,'CS')

select Vno,Vage
from CS_S

select Vno,Vage
from CS_S
where Vno in(
	select Sno
	from SC
	where Cno='2'
)

create view S_C_GREADE(S_C_no,S_C_name,S_C_Cno,S_C_Cname,S_C_Grand)
as select distinct
	Student.Sno,Student.Sname,SC.Cno,Course.Cname,SC.Grande
	From SC left outer join Student on(sc.sno=student.sno) left outer join Course on(SC.cno=Course.cno)

create view V_IS_Score(V_IS_Sno,V_IS_Sname)
as select
	Student.Sno,Student.Sname
	from Student
	where Student.Sno in(
		select
		Sno
		from SC
		where SC.Grande>90 and SC.Cno='1')

create view V_NUM_AVG(V_Dept,V_Num,V_Age_Avg)
as select
	Sdep,count(Sno),avg(Sage)
	from Student
	group by Sdep

select *
from S_C_GREADE

select *
from V_IS_Score

select *
from V_NUM_AVG

select *
from S_C_GREADE
where S_C_GREADE.S_C_Cno='1'

create view V_MAStudent(Vno,Vname,Vage,Vdept)
as select
	Sno,Sname,Sage,Sdep
	from Student
	where Sdep='MA'

select *
from V_MAStudent

select *
from V_MAStudent
where Vage<20

update V_MAStudent
set Vname='王五'
where Vno='2002151211'

insert
into V_MAStudent
values('200215126','赵新','20','MA')

drop view V_MAStudent

drop view V_NUM_AVG

--*************2部分*************--
--1）向选题表中插入一条记录（‘H06’,‘学生成绩管理系统’，‘已选’，‘T03’,’0706034107’）
insert
into Desion
values('H06','学生成绩管理系统','已选',null,null,'T03','0706034107')

--2）将教师李霞的学历改为“博士”。
update Teacher
set Ttitle='博士'
where Tname='李霞'

--3）删除“1组”所有学生的选题情况。
delete
from Desion
where Desion.Sno in(
	select Sno
	from Student
	where Sgroup='1')


--4) 建立选题学生选题情况的视图（包括学号，姓名，题目名称，指导教师姓名，指导老师联系电话）
--（视图名称和视图中包含的属性名称自拟）
create view V_S_Course(Vno,Vname,VHname,VTname,VTtel)
as select Student.Sno,Student.Sname,Desion.Hname,Teacher.Tname,Teacher.Ttel
from Student left outer join Desion on(Student.Sno=Desion.Sno) left outer join Teacher on (Desion.Tno=Teacher.Tno)

select *
from V_S_Course

--5）利用4）建立的视图查询所有杨坚老师所带学生的学号，姓名和题目名称。
select Vno,Vname,VHname
from V_S_Course
where VTname='杨坚'