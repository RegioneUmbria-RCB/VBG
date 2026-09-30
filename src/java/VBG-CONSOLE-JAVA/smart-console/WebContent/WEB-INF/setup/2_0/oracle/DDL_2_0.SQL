Alter table COMUNIASSOCIATISOFTWARE add (MAIL varchar2(200 char));
comment on column COMUNIASSOCIATISOFTWARE.MAIL is 'Contiene l''indirizzo di posta elettronica (da utilizzare come mittente) del comune associato';
alter table COMUNIASSOCIATISOFTWARE add (MAILPEC varchar2(200 char));
comment on column COMUNIASSOCIATISOFTWARE.MAILPEC is 'Contiene l''indirizzo di posta elettronica certificata (da utilizzare come mittente) del comune associato';
alter table TIPIMOVIMENTO add (FK_LETTERETIPO number(4,0));
comment on column TIPIMOVIMENTO.FK_LETTERETIPO is 'Contiene la lettera per la stampa del pdf del modello-riepilogo secondo DPR160';
alter table TIPIMOVIMENTO add constraint FK_TIPIMOV_LETTERETIPO foreign key (IDCOMUNE,FK_LETTERETIPO) references LETTERETIPO(IDCOMUNE,CODICELETTERA);