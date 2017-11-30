alter table ARBOL
   drop constraint FK_ARBOL_RELATIONS_SEMILLA;

alter table FLOR
   drop constraint FK_FLOR_RELATIONS_SEMILLA;

drop index RELATIONSHIP_1_FK;

drop table ARBOL cascade constraints;

drop index RELATIONSHIP_2_FK;

drop table FLOR cascade constraints;

drop table SEMILLA cascade constraints;

/*==============================================================*/
/* Table: ARBOL                                                 */
/*==============================================================*/
create table ARBOL 
(
   SEMILLA_ID           NUMBER               not null,
   ALTURA               NUMBER               not null,
   constraint PK_ARBOL primary key (SEMILLA_ID, ALTURA)
);

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_1_FK on ARBOL (
   SEMILLA_ID ASC
);

/*==============================================================*/
/* Table: FLOR                                                  */
/*==============================================================*/
create table FLOR 
(
   SEMILLA_ID           NUMBER               not null,
   FLOR_COLOR           VARCHAR2(30 BYTE)    not null,
   constraint PK_FLOR primary key (SEMILLA_ID, FLOR_COLOR)
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_2_FK on FLOR (
   SEMILLA_ID ASC
);

/*==============================================================*/
/* Table: SEMILLA                                               */
/*==============================================================*/
create table SEMILLA 
(
   SEMILLA_ID           INTEGER              not null,
   SEMILLA_NOMBRE       VARCHAR2(30)         not null,
   SEMILLA_TIPO         VARCHAR2(30)         not null,
   SEMILLA_PRECIO       INTEGER              not null,
   constraint PK_SEMILLA primary key (SEMILLA_ID)
);

alter table ARBOL
   add constraint FK_ARBOL_RELATIONS_SEMILLA foreign key (SEMILLA_ID)
      references SEMILLA (SEMILLA_ID);

alter table FLOR
   add constraint FK_FLOR_RELATIONS_SEMILLA foreign key (SEMILLA_ID)
      references SEMILLA (SEMILLA_ID);
