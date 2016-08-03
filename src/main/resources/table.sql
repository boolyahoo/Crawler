create table news(
  id int not null auto_increment,
  url varchar(255) not null,
  time_stamp date not null,
  title varchar(255) not null,
  content text not null,
  primary key(id)
) DEFAULT CHARSET=utf8;
