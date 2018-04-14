create table Course(
Cno char(4) primary key,
Cname char(40) not NULL,
Cpno char(4),
Ccredit smallint,
foreign key(Cpno) references course(Cno)
);

alter table Course add Cterm char(4);

create unique index Cname_idx on Course(Cname);

--alter table Course drop column Cterm; 

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
filename='C:\Program Files\Microsoft SQL Server\MSSQL.1\MSSQL\DATA\BSXT.mdf',
size=5MB,
maxsize=10MB,
filegrowth=10%
)
log on(
name='BSXT_Log',
filename='C:\Program Files\Microsoft SQL Server\MSSQL.1\MSSQL\DATA\BSXT.ldf',
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