-- -----------------------------------------------------
-- Schema cafe
-- -----------------------------------------------------
create schema if not exists `cafe` default character set utf8mb4 collate utf8mb4_general_ci;
use `cafe`;

-- -----------------------------------------------------
-- Table `cafe`.`category`
-- -----------------------------------------------------
create table if not exists `cafe`.`category`
(
    `id`         BIGINT       not null auto_increment,
    `name`       VARCHAR(255) not null,
    `created_at` TIMESTAMP    not null default current_timestamp,
    `updated_at` TIMESTAMP    not null default current_timestamp on update current_timestamp,
    primary key (`id`)
) engine = InnoDB comment = '상품이 분류하는 카테고리 정보 테이블';


-- -----------------------------------------------------
-- Table `cafe`.`option_group`
-- -----------------------------------------------------
create table if not exists `cafe`.`option_group`
(
    `id`         INT         not null auto_increment comment 'option group id',
    `name`       VARCHAR(50) not null comment '옵션 그룹의 이름. ex.  size',
    `created_at` TIMESTAMP   not null default current_timestamp,
    `updated_at` TIMESTAMP   not null default current_timestamp on update current_timestamp,
    primary key (`id`)
) engine = InnoDB comment = '옵션을 분류하는 옵션 그룹 테이블';


-- -----------------------------------------------------
-- Table `cafe`.`options`
-- -----------------------------------------------------
create table if not exists `cafe`.`options`
(
    `id`              BIGINT(20)  not null auto_increment comment 'option id',
    `option_group_id` INT         not null comment 'option group id',
    `name`            VARCHAR(50) not null comment '옵션의 이름. ex. size의 경우 small, large',
    `created_at`      TIMESTAMP   not null default current_timestamp,
    `updated_at`      TIMESTAMP   not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    index `idx_options_option_group_id` (`option_group_id` asc),
    constraint `fk_options_option_group_id` foreign key (`option_group_id`) references `cafe`.`option_group` (`id`)
) engine = InnoDB comment = '옵션 테이블. 옵션은 하나의 옵션 그룹을 가지며, 상품에 따라서 같은 옵션 그룹에 속하는 옵션이더라도 일부 옵션만 선택 가능할 수도 있다.';


-- -----------------------------------------------------
-- Table `cafe`.`product`
-- -----------------------------------------------------
create table if not exists `cafe`.`product`
(
    `id`              BIGINT       not null auto_increment comment 'product id',
    `category_id`     BIGINT       not null comment 'category id',
    `name`            VARCHAR(255) not null comment '상품 이름',
    `name_cosonant`   VARCHAR(255) not null comment '상품 이름 자음',
    `cost`            INT          not null comment '상품 제조 원가',
    `price`           INT          not null comment '상품 가격',
    `description`     VARCHAR(600) not null comment '설명',
    `barcode`         VARCHAR(255) not null comment '바코드',
    `expiration_date` DATE         not null comment '유통기한',
    `created_at`      TIMESTAMP    not null default current_timestamp,
    `updated_at`      TIMESTAMP    not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    index `idx_product_name` (`name` asc),
    index `idx_product_category_id` (`category_id` asc)
) engine = InnoDB comment = '상품 정보 테이블';


-- -----------------------------------------------------
-- Table `cafe`.`product_option`
-- -----------------------------------------------------
create table if not exists `cafe`.`product_option`
(
    `id`           BIGINT    not null auto_increment comment 'product option id',
    `option_id`    BIGINT    not null comment 'option id',
    `product_id`   BIGINT    not null comment 'product id',
    `option_price` INT       not null comment '해당 옵션을 추가할 때의 추가 가격',
    `created_at`   TIMESTAMP not null default current_timestamp,
    `updated_at`   TIMESTAMP not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    index `idx_product_option_product_id` (`product_id` asc),
    constraint `fk_product_option_product_id` foreign key (`product_id`) references `cafe`.`product` (`id`)
) engine = InnoDB comment = '상품의 선택가능한 옵션 목록을 저장하는 관계 테이블';


-- -----------------------------------------------------
-- Table `cafe`.`user`
-- -----------------------------------------------------
create table if not exists `cafe`.`user`
(
    `id`           BIGINT       not null auto_increment comment 'user id',
    `phone_number` VARCHAR(255) not null comment '유저 휴대폰 번호',
    `password`     VARCHAR(255) not null comment '유저 비밀번호. 암호화되어 저장',
    `created_at`   TIMESTAMP    not null default current_timestamp,
    `updated_at`   TIMESTAMP    not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    unique index `uk_user_phone_number` (`phone_number` asc)
) engine = InnoDB comment = '유저 정보 테이블';


-- -----------------------------------------------------
-- Table `cafe`.`role`
-- -----------------------------------------------------
create table if not exists `cafe`.`role`
(
    `id`         INT         not null auto_increment comment 'role id',
    `name`       VARCHAR(50) not null comment 'role 이름',
    `created_at` TIMESTAMP   not null default current_timestamp,
    `updated_at` TIMESTAMP   not null default current_timestamp on update current_timestamp,
    primary key (`id`)
) engine = InnoDB comment = 'RBAC를 위한 role 테이블';


-- -----------------------------------------------------
-- Table `cafe`.`user_role`
-- -----------------------------------------------------
create table if not exists `cafe`.`user_role`
(
    `id`         BIGINT    not null auto_increment comment 'user role id',
    `user_id`    BIGINT    not null comment 'user id',
    `role_id`    INT       not null comment 'role id',
    `created_at` TIMESTAMP not null default current_timestamp,
    `updated_at` TIMESTAMP not null default current_timestamp on update current_timestamp,
    primary key (`id`),
    index `idx_user_role_user_id` (`user_id` asc),
    constraint `fk_user_role_user_id` foreign key (`user_id`) references `cafe`.`user` (`id`)
) engine = InnoDB comment = '유저 별 role 관계 테이블';
