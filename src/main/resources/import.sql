Insert into PERSON (PERSON_TYPE,ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CONTACT,FIRSTNAME,LASTNAME,DT_LASTCONNECTION,LOGIN,PWD) values ('user','E9916BB90CAE44E8BCBC249475E3CDA5',null,to_timestamp('26/12/19 14:50:25,411000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('26/12/19 14:52:10,613000000','DD/MM/RR HH24:MI:SSXFF'),'enable',null,'05050505','Désiré','Labité',to_timestamp('26/12/19 14:52:10,613000000','DD/MM/RR HH24:MI:SSXFF'),'user','****');
Insert into PERSON (PERSON_TYPE,ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CONTACT,FIRSTNAME,LASTNAME,DT_LASTCONNECTION,LOGIN,PWD) values ('user','0F8191DC365B4EE2B502AED090C7D489',null,to_timestamp('26/12/19 14:50:43,533000000','DD/MM/RR HH24:MI:SSXFF'),null,'enable',null,'02020202','Souleymane','Digbeu',null,'log 2','****');


Insert into PROJECT (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,DESCRIPTION,DT_ENDPROJECT,DT_STARTPROJECT,INTVERSION,NAME,ID_PROJECTLEAD) values ('3D4B8BCC65C5444AAE86960DD7D58C73',null,to_timestamp('26/12/19 15:09:04,935000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('28/12/19 12:41:52,976000000','DD/MM/RR HH24:MI:SSXFF'),'enable',null,'Projet 1',to_timestamp('18/12/19 11:20:54,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('16/12/19 10:08:31,000000000','DD/MM/RR HH24:MI:SSXFF'),'4','Projet 1',null);
Insert into PROJECT (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,DESCRIPTION,DT_ENDPROJECT,DT_STARTPROJECT,INTVERSION,NAME,ID_PROJECTLEAD) values ('3631B433670C48CB912583915D73C334',null,to_timestamp('26/12/19 15:45:35,704000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('28/12/19 11:51:02,099000000','DD/MM/RR HH24:MI:SSXFF'),'enable',null,'Projet 3',to_timestamp('18/12/20 11:20:54,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('16/12/20 10:08:31,000000000','DD/MM/RR HH24:MI:SSXFF'),'2','Projet 3',null);
Insert into PROJECT (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,DESCRIPTION,DT_ENDPROJECT,DT_STARTPROJECT,INTVERSION,NAME,ID_PROJECTLEAD) values ('266F8F28F3764669983740DDF4FCB81C',null,to_timestamp('26/12/19 15:41:30,840000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('28/12/19 11:51:58,683000000','DD/MM/RR HH24:MI:SSXFF'),'enable',null,'Projet 2',to_timestamp('18/12/19 11:20:54,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('16/12/20 10:08:31,000000000','DD/MM/RR HH24:MI:SSXFF'),'1','Projet 2',null);
Insert into PROJECT (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,DESCRIPTION,DT_ENDPROJECT,DT_STARTPROJECT,INTVERSION,NAME,ID_PROJECTLEAD) values ('1A03B5850D7D4DB3BBC49626EAD0DC02',null,to_timestamp('26/12/19 17:50:07,962000000','DD/MM/RR HH24:MI:SSXFF'),null,'enable',null,'Projet 1',to_timestamp('18/12/19 11:20:54,000000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('16/12/19 10:08:31,000000000','DD/MM/RR HH24:MI:SSXFF'),'0','Projet 1',null);

Insert into TASK (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CODE,DESCRIPTION,INTVERSION,NAME,NBREESTIMATEHOURS,ID_USER,ID_PROJECT) values ('66F6A155117E452799829E9203A18814',null,to_timestamp('26/12/19 14:52:53,749000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('26/12/19 18:40:10,587000000','DD/MM/RR HH24:MI:SSXFF'),'enable',null,'T002','Tache 2','1','Tache 2','3',null,'3D4B8BCC65C5444AAE86960DD7D58C73');
Insert into TASK (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CODE,DESCRIPTION,INTVERSION,NAME,NBREESTIMATEHOURS,ID_USER,ID_PROJECT) values ('98332651C4434FB4B371F3A2760173E7',null,to_timestamp('26/12/19 14:52:59,685000000','DD/MM/RR HH24:MI:SSXFF'),null,'enable',null,'T001','Tache 1','0','Tache 1','4',null,null);
Insert into TASK (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CODE,DESCRIPTION,INTVERSION,NAME,NBREESTIMATEHOURS,ID_USER,ID_PROJECT) values ('A76B8422B67D4D198AD6B3F7E50F528F',null,to_timestamp('26/12/19 14:53:04,589000000','DD/MM/RR HH24:MI:SSXFF'),null,'enable',null,'T002','Tache 2','0','Tache 2','3',null,null);
Insert into TASK (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CODE,DESCRIPTION,INTVERSION,NAME,NBREESTIMATEHOURS,ID_USER,ID_PROJECT) values ('D3DE4B6CA9D64A279BD1362DEEAC1D48',null,to_timestamp('26/12/19 14:53:10,086000000','DD/MM/RR HH24:MI:SSXFF'),to_timestamp('26/12/19 18:43:13,745000000','DD/MM/RR HH24:MI:SSXFF'),'enable',null,'T005','Tache 5','1','Tache 5','4',null,'3D4B8BCC65C5444AAE86960DD7D58C73');

Insert into ACTIVITY (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CODE,DESCRIPTION,END_DATE,LABEL,START_DATE,ID_TASK,ACTIVITYCOMPLETIONRATE) values ('2352E34458854377BC256058E9804038',null,to_timestamp('26/12/19 14:55:22,018000000','DD/MM/RR HH24:MI:SSXFF'),null,'enable',null,'ACT005','Activité quelconque 1',to_timestamp('18/12/19 11:20:54,000000000','DD/MM/RR HH24:MI:SSXFF'),'Activité quelconque 1',to_timestamp('16/12/19 10:08:31,000000000','DD/MM/RR HH24:MI:SSXFF'),null,0.0);
Insert into ACTIVITY (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,CODE,DESCRIPTION,END_DATE,LABEL,START_DATE,ID_TASK,ACTIVITYCOMPLETIONRATE) values ('2E5F362C685B4B75BBA79538AD85F4A0',null,to_timestamp('26/12/19 14:57:32,131000000','DD/MM/RR HH24:MI:SSXFF'),null,'enable',null,'ACT005','Activité quelconque 2',to_timestamp('18/12/19 11:20:54,000000000','DD/MM/RR HH24:MI:SSXFF'),'Activité quelconque 2',to_timestamp('16/12/19 10:08:31,000000000','DD/MM/RR HH24:MI:SSXFF'),null,0.0);

Insert into OPERATION (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,DESCRIPTION,NAME) values ('1',null,null,null,'enable',null,'Modification','Modification');
Insert into OPERATION (ID,CREATEDBY,DT_CREATED,DT_UPDATED,STATUS,UPDATEDBY,DESCRIPTION,NAME) values ('2',null,null,null,null,null,'Désignation responsable projet','Désignation responsable projet');