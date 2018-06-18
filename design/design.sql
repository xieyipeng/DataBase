create table city(
city_name char(20) primary key,
city_regional char(10) not null,
city_introduction char(20)
);

create table weather(
weather_no char(4) primary key,
weather_type char(20),
weather_level char(20)
);

create table date_time(
D_no char(4) primary key,
D_date date,
D_time time
);

create table C_W_D_state(
S_city_name char(20),
S_date_no char(4),
S_weather_no char(4),
S_long_to time,
primary key(S_city_name,S_date_no,S_weather_no,S_long_to),
foreign key (S_city_name) references city(city_name),
foreign key (S_date_no) references date_time(D_no),
foreign key (S_weather_no) references weather(weather_no)
); 