
    alter table shop1.sm_category 
        drop 
        foreign key fk_category_1;

    alter table shop1.sm_customer 
        drop 
        foreign key fk_customer_1;

    alter table shop1.sm_login 
        drop 
        foreign key fk_login_1;

    alter table shop1.sm_menu 
        drop 
        foreign key fk_menu_1;

    alter table shop1.sm_product 
        drop 
        foreign key fk_product_1;

    alter table shop1.sm_role_menu 
        drop 
        foreign key fk_role_menu_1;

    alter table shop1.sm_role_menu 
        drop 
        foreign key fk_role_menu_2;

    alter table shop1.sm_supplier 
        drop 
        foreign key fk_supplier_1;

    drop table if exists shop1.sm_account;

    drop table if exists shop1.sm_category;

    drop table if exists shop1.sm_customer;

    drop table if exists shop1.sm_login;

    drop table if exists shop1.sm_menu;

    drop table if exists shop1.sm_product;

    drop table if exists shop1.sm_role;

    drop table if exists shop1.sm_role_menu;

    drop table if exists shop1.sm_supplier;

    drop table if exists shop1.sm_user;

    create table shop1.sm_account (
        id bigint not null,
        city varchar(255),
        province varchar(255),
        street varchar(255),
        village varchar(255),
        zip_code varchar(255),
        email varchar(80),
        name varchar(80) not null,
        phone varchar(20),
        registered_no varchar(13) not null unique,
        type varchar(20) not null,
        primary key (id)
    ) comment='Account' ENGINE=InnoDB;

    create table shop1.sm_category (
        id bigint not null auto_increment,
        descn varchar(1000),
        name varchar(80) not null unique,
        seq integer check (seq<=99999),
        parent_id bigint,
        primary key (id)
    ) comment='상품분류' ENGINE=InnoDB;

    create table shop1.sm_customer (
        point bigint check (point>=0),
        id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB;

    create table shop1.sm_login (
        login_id varchar(12) not null,
        login_datetime datetime,
        logout_datetime datetime,
        seq integer,
        primary key (login_id, seq)
    ) ENGINE=InnoDB;

    create table shop1.sm_menu (
        id bigint not null auto_increment,
        descn varchar(2000),
        name varchar(160) not null,
        display_order integer,
        parent_id bigint,
        primary key (id)
    ) comment='메뉴' ENGINE=InnoDB;

    create table shop1.sm_product (
        id bigint not null auto_increment,
        full_descn longtext,
        name varchar(80) not null,
        short_descn varchar(1000),
        category_id bigint not null,
        primary key (id)
    ) comment='상품' ENGINE=InnoDB;

    create table shop1.sm_role (
        id bigint not null auto_increment check (id<=9999),
        name varchar(80) not null unique,
        primary key (id)
    ) comment='역할' ENGINE=InnoDB;

    create table shop1.sm_role_menu (
        role_id bigint not null,
        menu_id bigint not null,
        unique (role_id, menu_id)
    ) ENGINE=InnoDB;

    create table shop1.sm_supplier (
        id bigint not null,
        primary key (id)
    ) ENGINE=InnoDB;

    create table shop1.sm_user (
        login_id varchar(12) not null,
        is_locked bit,
        is_passwd_expired bit,
        passwd varchar(12),
        reg_datetime datetime,
        primary key (login_id)
    ) comment='사용자' ENGINE=InnoDB;

    create index ix_category_1 on shop1.sm_category (name);

    create index ix_category_2 on shop1.sm_category (parent_id);

    alter table shop1.sm_category 
        add index fk_category_1 (parent_id), 
        add constraint fk_category_1 
        foreign key (parent_id) 
        references shop1.sm_category (id);

    alter table shop1.sm_customer 
        add index fk_customer_1 (id), 
        add constraint fk_customer_1 
        foreign key (id) 
        references shop1.sm_account (id);

    alter table shop1.sm_login 
        add index fk_login_1 (login_id), 
        add constraint fk_login_1 
        foreign key (login_id) 
        references shop1.sm_user (login_id);

    alter table shop1.sm_menu 
        add index fk_menu_1 (parent_id), 
        add constraint fk_menu_1 
        foreign key (parent_id) 
        references shop1.sm_menu (id);

    create index ix_product_name on shop1.sm_product (name);

    alter table shop1.sm_product 
        add index fk_product_1 (category_id), 
        add constraint fk_product_1 
        foreign key (category_id) 
        references shop1.sm_category (id);

    alter table shop1.sm_role_menu 
        add index fk_role_menu_1 (role_id), 
        add constraint fk_role_menu_1 
        foreign key (role_id) 
        references shop1.sm_role (id);

    alter table shop1.sm_role_menu 
        add index fk_role_menu_2 (menu_id), 
        add constraint fk_role_menu_2 
        foreign key (menu_id) 
        references shop1.sm_menu (id);

    alter table shop1.sm_supplier 
        add index fk_supplier_1 (id), 
        add constraint fk_supplier_1 
        foreign key (id) 
        references shop1.sm_account (id);
