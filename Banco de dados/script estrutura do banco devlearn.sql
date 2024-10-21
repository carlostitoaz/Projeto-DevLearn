DROP DATABASE IF EXISTS devlearn;

CREATE DATABASE devlearn;

USE devlearn;

CREATE TABLE tipo_usuario (
    id_tipo_usuario INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) not null
);

INSERT INTO tipo_usuario (tipo) VALUES ("Professor");
INSERT INTO tipo_usuario (tipo) VALUES ("Aluno");

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    id_tipo_usuario INT,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    inativo BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_tipo_usuario) REFERENCES tipo_usuario(id_tipo_usuario)
);

CREATE TABLE curso (
    id_curso INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    id_usuario INT,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    publicado BOOLEAN DEFAULT FALSE,
    inativo BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE aula (
    id_aula INT AUTO_INCREMENT PRIMARY KEY,
    id_curso INT,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    url_video VARCHAR(255),
    publicado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
);

CREATE TABLE matricula (
    id_matricula INT AUTO_INCREMENT PRIMARY KEY,
    id_curso INT,
    id_usuario INT,
    data_matricula DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE curso_categoria (
    id_curso INT,
    id_categoria INT,
    PRIMARY KEY (id_curso, id_categoria),
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso),
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);
