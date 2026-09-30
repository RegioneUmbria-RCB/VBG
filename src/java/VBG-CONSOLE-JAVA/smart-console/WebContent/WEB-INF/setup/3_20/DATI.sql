INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE, FLAG_GESTCOMUNE)
VALUES ('SIT_CONSOLE', 'Integrazione dalla console ai vari SIT dei comuni che fanno parte della console', 1);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE)
VALUES ('SIT_CONSOLE', 'URL_WSSIT', 'Url del servizio SIT per l''installazione aspnet di backend per il comune comunelocale. Es.
https://devel3.vbg.community/aspnet/webservices/wssigepro/wssit.asmx');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE)
VALUES ('SIT_CONSOLE', 'ALIAS_BACKEND_LOCALE', 'Alias da utilizzare per interrogare il comune locale. Es. E256');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE)
VALUES ('SIT_CONSOLE', 'REDIRECT_TO_SIT_PAGE', 'Se impostato a 1 e se la verticalizzazione è attiva permette il redirect automatico dallo step GestioniLocalizzazioni allo step GestioniLocalizzazioniSit.');
