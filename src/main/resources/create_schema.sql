/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  alann
 * Created: 06/02/2018
 */

CREATE TABLE usuario (
	id serial PRIMARY KEY,
	nome varchar(35) not null,
	senha varchar(20) not null,
        status boolean default false,
	imagem varchar not null,
        descricao varchar not null
);

CREATE TABLE postagem (
        id serial PRIMARY KEY,
	menssagem varchar not null,
	id_usuario int,
	CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE favoritos (
        id serial PRIMARY KEY,
        id_usuario int not null,
        id_postagem int not null,
	CONSTRAINT fk_usuarioFavoritos FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT fk_postagemFavoritos FOREIGN KEY (id_postagem) REFERENCES postagem (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE amigo (
        id_usuario int,
        id_amigo int,
        CONSTRAINT fk_usuarioAmigo FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON DELETE CASCADE ON UPDATE CASCADE
);

