INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('SERVIZI_CONSOLE_AREARISERVATA', 'Permette di impostare i parametri per effettuare chiamate ai servizi .net dell''installazione locale', 0);
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SERVIZI_CONSOLE_AREARISERVATA', 'URL_RICERCA_PRATICA_CONSOLE', 'Url da utilizzare per invocare il servizio di ricerca pratiche (es. http://devel3/aspnet/webservices/wsareariservata/wcfservices/ricercapratiche/wsricercapraticheservice.svc)');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('SERVIZI_CONSOLE_AREARISERVATA', 'URL_VISURA_ISTANZA_CONSOLE', 'Url da utilizzare per effettuare la visura al termine della presentazione istanza tramite la console. Funziona a patto che: <ol><li>La security utilizzata sia la stessa della console</li><li>La console .net riesce a staccare un token applicativo con la stessa username e password sul comune in cui si fa la visura</li><li>L''alias utilizzato dal comune su cui si fa la visura sia lo stesso utilizzato come sportello destinatario nei parametri di stc</li></ol> (es. http://devel3.init.gruppoinit.it/aspnet/WebServices/WsSIGePro/Istanze.asmx )');


INSERT INTO verticalizzazioni (idcomune, modulo, attivo, software, codicecomune, id) SELECT idcomune, 'SERVIZI_CONSOLE_AREARISERVATA', attivo, software, codicecomune,(SELECT MAX(id)+1 FROM verticalizzazioni WHERE verticalizzazioni.idcomune = v.idcomune) FROM verticalizzazioni v WHERE modulo='AREA_RISERVATA';

INSERT INTO verticalizzazioniparametri (idcomune, modulo, parametro, valore, software, codicecomune, id) SELECT idcomune, 'SERVIZI_CONSOLE_AREARISERVATA', 'URL_VISURA_ISTANZA_CONSOLE', valore, software, codicecomune, (SELECT MAX(id)+1 FROM verticalizzazioniparametri WHERE verticalizzazioniparametri.idcomune = vp.idcomune) FROM verticalizzazioniparametri vp WHERE modulo='AREA_RISERVATA' AND parametro='URL_VISURA_ISTANZA_CONSOLE'; 

UPDATE verticalizzazioniparametribase SET descrizione = '<b>DISMESSO</b>: Vedi SERVIZI_CONSOLE_AREARISERVATA.URL_VISURA_ISTANZA_CONSOLE' WHERE modulo='AREA_RISERVATA' AND parametro='URL_VISURA_ISTANZA_CONSOLE';


