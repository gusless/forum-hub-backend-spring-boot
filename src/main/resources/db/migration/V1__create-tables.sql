create table usuarios(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)
);

create table cursos(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    categoria varchar(100) not null,

    primary key(id)

);

create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensagem varchar(255) not null unique,
    dataCriacao datetime not null,
    status varchar(100) not null,
    autor_id bigint not null,
    curso_id bigint not null,

    primary key(id),
    constraint fk_topicos_autor_id foreign key(autor_id) references usuarios(id),
    constraint fk_topicos_curso_id foreign key(curso_id) references cursos(id)
);

create table respostas(

    id bigint not null auto_increment,
    mensagem varchar(2500) not null,
    dataCriacao datetime not null,
    topico_id bigint not null,
    autor_id bigint not null,

    primary key(id),
    constraint fk_respostas_topico_id foreign key(topico_id) references topicos(id),
    constraint fk_respostas_autor_id foreign key(autor_id) references usuarios(id)

);