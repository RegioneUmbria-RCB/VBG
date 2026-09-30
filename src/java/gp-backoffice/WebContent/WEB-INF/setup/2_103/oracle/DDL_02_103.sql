alter table PEC_INBOX_ALLEGATI modify FKIDPEC varchar2(200 char);
alter table PEC_INBOX modify id varchar2(200 char);
ALTER TABLE OGGETTI MODIFY PERCORSO VARCHAR2(200);
alter table albero_coefficienti_r modify tipo varchar2(50 char) null;
alter table albero_coefficienti_r modify codice_coefficente varchar2(6 char);
ALTER TABLE inventarioproc_endo ADD ordine NUMBER(6,0);