
    drop table shop1.loginHistory cascade constraints;

    drop table shop1.menus cascade constraints;

    drop table shop1.sm_account cascade constraints;

    drop table shop1.sm_category cascade constraints;

    drop table shop1.sm_customer cascade constraints;

    drop table shop1.sm_menu cascade constraints;

    drop table shop1.sm_product cascade constraints;

    drop table shop1.sm_role cascade constraints;

    drop table shop1.sm_supplier cascade constraints;

    drop table shop1.sm_user cascade constraints;

    drop sequence shop1.seq_category;

    drop sequence shop1.seq_product;

    drop sequence shop1.seq_role;

    create table shop1.loginHistory (
        login_id varchar2(12 char) not null,
        dummy varchar2(255 char),
        idx number(10,0) not null,
        primary key (login_id, idx)
    );

    create table shop1.menus (
        role_id number(19,0) not null,
        menu_id number(19,0) not null
    );

    create table shop1.sm_account (
        id number(19,0) not null,
        email varchar2(80 char),
        name varchar2(80 char) not null,
        phone varchar2(20 char),
        registered_no varchar2(255 char) not null unique,
        type varchar2(20 char) not null,
        primary key (id)
    );

    comment on table shop1.sm_account is
        'Account';

    create table shop1.sm_category (
        id number(19,0) not null,
        descn varchar2(1000 char),
        name varchar2(80 char) not null,
        parent_id number(19,0),
        seq number(10,0),
        primary key (id)
    );

    comment on table shop1.sm_category is
        'ìíë¶ë¥';

    create table shop1.sm_customer (
        id number(19,0) not null,
        point number(19,0),
        primary key (id)
    );

    create table shop1.sm_menu (
        id number(19,0) not null,
        descn varchar2(255 char),
        name varchar2(160 char) not null,
        display_order number(10,0),
        parent_id number(19,0),
        primary key (id)
    );

    comment on table shop1.sm_menu is
        'ë©ë´';

    create table shop1.sm_product (
        id number(19,0) not null,
        category_id number(19,0) not null,
        full_descn clob,
        name varchar2(80 char) not null,
        short_descn varchar2(1000 char),
        primary key (id)
    );

    comment on table shop1.sm_product is
        'ìí';

    create table shop1.sm_role (
        id number(19,0) not null,
        name varchar2(80 char) not null unique,
        primary key (id)
    );

    comment on table shop1.sm_role is
        'ì­í ';

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
        'ì¬ì©ì';

    alter table shop1.loginHistory 
        add constraint FK24F314EB2D72F47F 
        foreign key (login_id) 
        references shop1.sm_user;

    alter table shop1.menus 
        add constraint FK62F96F46F240FD 
        foreign key (role_id) 
        references shop1.sm_role;

    alter table shop1.menus 
        add constraint FK62F96F4ED83AC5D 
        foreign key (menu_id) 
        references shop1.sm_menu;

    alter table shop1.sm_category 
        add constraint FKEE69654343B15F51 
        foreign key (parent_id) 
        references shop1.sm_category;

    alter table shop1.sm_customer 
        add constraint FKF88282354D5E965 
        foreign key (id) 
        references shop1.sm_account;

    alter table shop1.sm_menu 
        add constraint FK82DAA8243044F132 
        foreign key (parent_id) 
        references shop1.sm_menu;

    alter table shop1.sm_product 
        add constraint FK583E880A23F35A7D 
        foreign key (category_id) 
        references shop1.sm_category;

    alter table shop1.sm_supplier 
        add constraint FK8842A21154D5E965 
        foreign key (id) 
        references shop1.sm_account;

    create sequence shop1.seq_category;

    create sequence shop1.seq_product;

    create sequence shop1.seq_role;
