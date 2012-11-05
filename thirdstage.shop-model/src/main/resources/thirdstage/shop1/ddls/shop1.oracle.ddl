
    drop table shop1.sm_account cascade constraints;

    drop table shop1.sm_category cascade constraints;

    drop table shop1.sm_customer cascade constraints;

    drop table shop1.sm_login cascade constraints;

    drop table shop1.sm_menu cascade constraints;

    drop table shop1.sm_product cascade constraints;

    drop table shop1.sm_role cascade constraints;

    drop table shop1.sm_role_menu cascade constraints;

    drop table shop1.sm_supplier cascade constraints;

    drop table shop1.sm_user cascade constraints;

    drop sequence shop1.seq_category;

    drop sequence shop1.seq_menu;

    drop sequence shop1.seq_product;

    drop sequence shop1.seq_role;

    create table shop1.sm_account (
        id number(19,0) not null,
        city varchar2(255 char),
        province varchar2(255 char),
        street varchar2(255 char),
        village varchar2(255 char),
        zip_code varchar2(255 char),
        email varchar2(80 char),
        name varchar2(80 char) not null,
        phone varchar2(20 char),
        registered_no varchar2(13 char) not null unique,
        type varchar2(20 char) not null,
        primary key (id)
    );

    comment on table shop1.sm_account is
        'Account';

    create table shop1.sm_category (
        id number(19,0) not null,
        descn varchar2(1000 char),
        name varchar2(80 char) not null unique,
        seq number(10,0) check (seq<=99999),
        parent_id number(19,0),
        primary key (id)
    );

    comment on table shop1.sm_category is
        '상품분류';

    create table shop1.sm_customer (
        point number(19,0) check (point>=0),
        id number(19,0) not null,
        primary key (id)
    );

    create table shop1.sm_login (
        login_id varchar2(12 char) not null,
        login_datetime timestamp,
        logout_datetime timestamp,
        seq number(10,0),
        primary key (login_id, seq)
    );

    create table shop1.sm_menu (
        id number(19,0) not null,
        descn varchar2(2000 char),
        name varchar2(160 char) not null,
        display_order number(10,0),
        parent_id number(19,0),
        primary key (id)
    );

    comment on table shop1.sm_menu is
        '메뉴';

    create table shop1.sm_product (
        id number(19,0) not null,
        full_descn clob,
        name varchar2(80 char) not null,
        short_descn varchar2(1000 char),
        category_id number(19,0) not null,
        primary key (id)
    );

    comment on table shop1.sm_product is
        '상품';

    create table shop1.sm_role (
        id number(19,0) not null check (id<=9999),
        name varchar2(80 char) not null unique,
        primary key (id)
    );

    comment on table shop1.sm_role is
        '역할';

    create table shop1.sm_role_menu (
        role_id number(19,0) not null,
        menu_id number(19,0) not null,
        unique (role_id, menu_id)
    );

    create table shop1.sm_supplier (
        id number(19,0) not null,
        primary key (id)
    );

    create table shop1.sm_user (
        login_id varchar2(12 char) not null,
        is_locked number(1,0),
        is_passwd_expired number(1,0),
        passwd varchar2(12 char),
        reg_datetime timestamp,
        primary key (login_id)
    );

    comment on table shop1.sm_user is
        '사용자';

    create index ix_category_1 on shop1.sm_category (name);

    create index ix_category_2 on shop1.sm_category (parent_id);

    alter table shop1.sm_category 
        add constraint fk_category_1 
        foreign key (parent_id) 
        references shop1.sm_category;

    alter table shop1.sm_customer 
        add constraint fk_customer_1 
        foreign key (id) 
        references shop1.sm_account;

    alter table shop1.sm_login 
        add constraint fk_login_1 
        foreign key (login_id) 
        references shop1.sm_user;

    alter table shop1.sm_menu 
        add constraint fk_menu_1 
        foreign key (parent_id) 
        references shop1.sm_menu;

    create index ix_product_name on shop1.sm_product (name);

    alter table shop1.sm_product 
        add constraint fk_product_1 
        foreign key (category_id) 
        references shop1.sm_category;

    alter table shop1.sm_role_menu 
        add constraint fk_role_menu_1 
        foreign key (role_id) 
        references shop1.sm_role;

    alter table shop1.sm_role_menu 
        add constraint fk_role_menu_2 
        foreign key (menu_id) 
        references shop1.sm_menu;

    alter table shop1.sm_supplier 
        add constraint fk_supplier_1 
        foreign key (id) 
        references shop1.sm_account;

    create sequence shop1.seq_category;

    create sequence shop1.seq_menu;

    create sequence shop1.seq_product;

    create sequence shop1.seq_role;
