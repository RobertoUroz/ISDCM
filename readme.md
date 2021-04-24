ISDCM LABS Q2 2020-2021

Para instalar la DB en NetBeans:

- Copiar la carpeta isdcm_db y pegarla en la carpeta Derby, la ubicación de la carpeta es algo parecido a: C:\Users\\<your-User>\AppData\Roaming\NetBeans\Derby\isdcm_db
- En NetBeans, en la conexión de la DB debajo de services, cambiar las propiedades de la conexión para que la URL quede así: "jdbc:derby://localhost:1527/isdcm_db"

SQLCommands to initiate DB:

create table "RUROZ".USUARIOS
(
	NOMBRE CLOB,
	APELLIDOS CLOB,
	EMAIL CLOB,
	USERNAME VARCHAR(256) not null primary key,
	PASSWORD CLOB
);

create table "RUROZ".VIDEOS
(
	ID NUMERIC(5) not null primary key,
	TITULO VARCHAR(100) not null,
	AUTOR VARCHAR(100) not null,
	FECHACREACION DATE not null,
	DURACION TIME not null,
	REPRODUCCIONES NUMERIC(5) default 0 not null,
	DESCRIPCION VARCHAR(255) not null,
	FORMATO VARCHAR(5) not null,
	URL CLOB,
	USERNAME VARCHAR(256),
	CONSTRAINT fk_myVideos
        FOREIGN KEY (username)
        REFERENCES usuarios (username)
);

create table "RUROZ".PREFERENCIASUSUARIO
(
	USERNAME VARCHAR(256) NOT NULL PRIMARY KEY,
	REPRODUCTOR SMALLINT,
	LISTAVIDEOS SMALLINT,
	COLOR SMALLINT
);

CREATE SEQUENCE VIDEOS_SEQ INCREMENT BY 1 START WITH 0;