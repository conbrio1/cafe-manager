CREATE TABLE revinfo
(
    rev      bigint not null auto_increment,
    revtstmp bigint,
    primary key (rev)
);

/* 각 도메인별 생성해야 할 스크립트
    Ref: https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#envers-generateschema

   create table Sample (
    seq ...
    field ...
   )

   create table Sample_AUD (
    REV integer not null,
    REVTYPE tinyint,
    REVEND integer
    { 나머지는 Sample의 column들 중 NotAudited annotation 붙은 칼럼 제외한 항목들 }
   )
*/