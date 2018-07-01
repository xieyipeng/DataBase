create table city(
city_name char(20) primary key,
city_regional char(10) not null,
);

create table weather(
weather_date char(8),
city char(20),
shidu char(5),
quality char(5),
wendu char(5),
sunrise char(5),
sunset char(5),
weather_type char(20),
weather_week char(20),
high char(20),
low char(20),
primary key (weather_date,city),
foreign key (city)references city(city_name)
);

create table management(
userName char(20) primary key,
wordPass char(20)
)

create table ordinaryUser(
userName char(20) primary key,
wordPass char(20)
)
insert into management(userName,wordPass)
values('123','123')