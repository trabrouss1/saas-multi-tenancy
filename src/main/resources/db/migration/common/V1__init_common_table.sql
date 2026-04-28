create table company
(
    deleted         boolean      not null,
    created_at      timestamp(6) not null,
    updated_at      timestamp(6),
    admin_email     varchar(255) not null
        unique,
    admin_full_name varchar(255) not null,
    admin_password  varchar(255) not null,
    admin_username  varchar(255) not null
        unique,
    code            varchar(255) not null
        unique,
    email           varchar(255) not null
        unique,
    id              varchar(255) not null
        primary key,
    name            varchar(255) not null,
    status          varchar(255) not null
        constraint company_status_check
            check ((status)::text = ANY
        ((ARRAY [
            'PENDING'::character varying,
            'ACTIVE'::character varying,
            'SUSPENDED'::character varying,
            'INACTIVE'::character varying
        ])::text[]))
);


create table users
(
    deleted    boolean      not null,
    enabled    boolean      not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6),
    company_id varchar(255)
        constraint fk_company_id references company,
    created_by varchar(255) not null,
    email      varchar(255),
    first_name varchar(255),
    id         varchar(255) not null
        primary key,
    last_name  varchar(255),
    password   varchar(255),
    role       varchar(255)
        constraint users_role_check
            check ((role)::text = ANY
        ((ARRAY ['ROLE_PLATFORM_ADMIN'::character varying, 'ROLE_COMPANY_ADMIN'::character varying, 'ROLE_ADMINISTRATOR'::character varying, 'ROLE_USER'::character varying, 'ROLE_SALES_OPERATOR'::character varying])::text[])),
    updated_by varchar(255),
    username   varchar(255) not null
        unique
);

