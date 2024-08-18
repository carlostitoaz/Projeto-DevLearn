create database devlearn;

use devlearn;

create table tipo_usuario (
	id_tipo_usuario int auto_increment primary key,
    tipo varchar(50) not null);

create table usuario (
	id_usuario int auto_increment primary key,
    nome varchar(100) not null,
    email varchar(100) unique not null,
    senha varchar(255) not null,
    id_tipo_usuario int,
    data_cadastro datetime default current_timestamp,
    inativo boolean default false,
    foreign key (id_tipo_usuario) references tipo_usuario(id_tipo_usuario));

create table curso (
	id_curso int auto_increment primary key,
    titulo varchar(255) not null,
    descricao text,
    id_usuario int,
    data_criacao datetime default current_timestamp,
    publicado boolean default false,
    inativo boolean default false,
    foreign key (id_usuario) references usuario(id_usuario));
    
create table modulo (
	id_modulo int auto_increment primary key,
    id_curso int,
    titulo varchar(255) not null,
    descricao text,
    ordem int not null,
    publicado boolean default false,
    foreign key (id_curso) references curso(id_curso));
    
create table aula (
	id_aula int auto_increment primary key,
    id_modulo int,
    titulo varchar(255) not null,
    descricao text,
    url_video varchar(255),
    ordem int not null,
    publicado boolean default false,
    foreign key (id_modulo) references modulo(id_modulo));

create table tipo_atividade (
	id_tipo_atividade int auto_increment primary key,
    tipo varchar(255) not null);
    
create table atividade (
	id_atividade int auto_increment primary key,
    id_modulo int, 
    id_tipo_atividade int,
    titulo varchar(255) not null, 
    descricao text,
    data_criacao datetime default current_timestamp,
    publicado boolean default false,
    foreign key (id_modulo) references modulo(id_modulo),
    foreign key (id_tipo_atividade) references tipo_atividade(id_tipo_atividade));
    
create table pergunta (
	id_pergunta int auto_increment primary key,
    id_atividade int,
    pergunta text not null,
    resposta_correta varchar(255) not null,
    publicado boolean default false,
    foreign key (id_atividade) references atividade(id_atividade));
    
create table resposta_atividade (
	id_resposta_atividade int auto_increment primary key,
    id_atividade int,
    id_usuario int, 
    id_pergunta int,
    resposta text,
    nota decimal(5, 2),
    data_envio datetime default current_timestamp,
    foreign key (id_atividade) references atividade(id_atividade),
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_pergunta) references pergunta(id_pergunta));
    
create table progresso (
	id_progresso int auto_increment primary key,
    descricao varchar(50) not null);
    
create table progresso_aula (
	id_progresso_aula int auto_increment primary key,
    id_aula int, 
    id_usuario int, 
    id_progresso int,
    tempo_assistido decimal(5, 2),
    data_conclusao datetime,
    foreign key (id_aula) references aula(id_aula),
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_progresso) references progresso(id_progresso));

create table progresso_atividade (
	id_progresso_atividade int auto_increment primary key,
    id_atividade int,
    id_usuario int,
    id_progresso int,
    data_conclusao datetime,
    foreign key (id_atividade) references atividade(id_atividade),
    foreign key (id_usuario) references usuario(id_usuario),
    foreign key (id_progresso) references progresso(id_progresso));

create table certificado (
	id_certificado int auto_increment primary key,
    id_curso int, 
    id_usuario int,
    codigo_certificado varchar(100) unique not null,
    foreign key (id_curso) references curso(id_curso),
    foreign key (id_usuario) references usuario(id_usuario));

create table avaliacao (
	id_avaliacao int auto_increment primary key,
    id_curso int, 
    id_usuario int, 
    nota int check(nota >=1 and nota <=1),
    comentario text,
    data_avaliacao datetime default current_timestamp,
    foreign key (id_curso) references curso(id_curso),
    foreign key (id_usuario) references usuario(id_usuario));
    
create table matricula (
	id_matricula int auto_increment primary key,
    id_curso int, 
    id_usuario int,
    data_matricula datetime default current_timestamp,
    avanco_conclusao decimal(5, 2) default 0.00,
    foreign key (id_curso) references curso(id_curso),
    foreign key (id_usuario) references usuario(id_usuario));

create table categoria (
	id_categoria int auto_increment primary key,
    nome varchar(100) not null);
    
create table curso_categoria (
	id_curso int,
    id_categoria int,
    primary key (id_curso, id_categoria),
    foreign key (id_curso) references curso(id_curso),
    foreign key (id_categoria) references categoria(id_categoria));
    
create table forum (
	id_forum int auto_increment primary key,
    id_curso int, 
    foreign key (id_curso) references curso(id_curso));
    
create table forum_topico (
	id_forum_topico int auto_increment primary key,
    id_forum int,
    id_usuario int,
    titulo varchar(255) not null,
    conteudo text,
    data_criacao datetime default current_timestamp,
    foreign key (id_forum) references forum(id_forum),
    foreign key (id_usuario) references usuario(id_usuario));
    
create table forum_topico_resposta (
	id_forum_topico_resposta int auto_increment primary key,
    id_forum_topico int,
    id_usuario int,
    conteudo text not null,
    data_criacao datetime default current_timestamp,
    foreign key (id_forum_topico) references forum_topico(id_forum_topico),
    foreign key (id_usuario) references usuario(id_usuario));
    