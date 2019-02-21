create database Alumnos
go

use  Alumnos
go


create table alumno
(
legajo int primary key,
nombre varchar(50)
)


create table cursos
(
codigo int primary key,
nombre char(3),
turno char(1)
 
)

create table notas
(
 legajo int,
 parcial int,
 nota int,
 codCurso int
 primary key (legajo,parcial)

 constraint fkleg foreign key (legajo) references alumno (legajo),
 constraint fkcurso foreign key (codCurso) references cursos (codigo) 
)



insert into alumno(legajo,nombre)values (1,'juan pedro')
insert into alumno(legajo,nombre)values (2,'edith roura')
insert into alumno(legajo,nombre)values (3,'juan pelotg')
insert into alumno(legajo,nombre)values (4,'facundo rivas')

insert into cursos (codigo,nombre,turno)values(1,'1w1','M')
insert into cursos (codigo,nombre,turno)values(2,'1w2','M')
insert into cursos (codigo,nombre,turno)values(3,'1w3','T')
insert into cursos (codigo,nombre,turno)values(4,'1w4','N')




select avg (nota) from notas where nota>0