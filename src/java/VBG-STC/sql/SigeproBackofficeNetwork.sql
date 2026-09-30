
--Esiste la pratica del mittente? idEnte, idSportello, idPratica e idProcedimento?
select idpratica, idprocedimento from pratiche where idente='D612' and idsportello='SUE' and idpratica='SUE200' and idprocedimento is null;
--Esiste una pratica collegata per il destinatario specificato?
select pratiche.id, pratiche.idente, pratiche.idsportello, pratiche.idpratica, pratiche.numpratica, pratiche.idprocedimento, pratichecollegate.id, pratichecollegate.idente, pratichecollegate.idsportello, pratichecollegate.idpratica, pratichecollegate.numpratica, pratichecollegate.idprocedimento From messaggipratiche, pratiche, pratiche pratichecollegate where pratichecollegate.id=messaggipratiche.fkidrisposta and messaggipratiche.fkidrichiesta=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUE' and pratiche.idpratica='SUE200'
union
select pratiche.id, pratiche.idente, pratiche.idsportello, pratiche.idpratica, pratiche.numpratica, pratiche.idprocedimento, pratichecollegate.id, pratichecollegate.idente, pratichecollegate.idsportello, pratichecollegate.idpratica, pratichecollegate.numpratica, pratichecollegate.idprocedimento From messaggipratiche, pratiche, pratiche pratichecollegate where pratichecollegate.id=messaggipratiche.fkidrichiesta and messaggipratiche.fkidrisposta=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUE' and pratiche.idpratica='SUE200';
--Esiste l'attività richiesta nel mittente?
select idpratica, idprocedimento from pratiche, attivita where attivita.fkidpratiche=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUE' and pratiche.idpratica='SUE200' and pratiche.idprocedimento is null and attivita.idattivita='AA02';

-----------------------------------------------------------
-----------------------------------------------------------


--Richiesta pratiche collegate
select pratiche.idente, pratiche.idsportello, pratiche.idpratica, pratiche.numpratica, pratiche.idprocedimento, pratichecollegate.idente, pratichecollegate.idsportello, pratichecollegate.idpratica, pratichecollegate.numpratica, pratichecollegate.idprocedimento From messaggipratiche, pratiche, pratiche pratichecollegate where pratichecollegate.id=messaggipratiche.fkidrisposta and messaggipratiche.fkidrichiesta=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUAP' and pratiche.idpratica=100
union
select pratiche.idente, pratiche.idsportello, pratiche.idpratica, pratiche.numpratica, pratiche.idprocedimento, pratichecollegate.idente, pratichecollegate.idsportello, pratichecollegate.idpratica, pratichecollegate.numpratica, pratichecollegate.idprocedimento From messaggipratiche, pratiche, pratiche pratichecollegate where pratichecollegate.id=messaggipratiche.fkidrichiesta and messaggipratiche.fkidrisposta=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUAP' and pratiche.idpratica=100;
--Non può essere inviata un'attività se non si è inviata precedentemente la pratica
--Viene verificata la presenza della pratica di SUE collegata alla pratica 100 idprocedikmento=1 del SUAP 
select pratiche.idente, pratiche.idsportello, pratiche.idpratica, pratiche.numpratica, pratiche.idprocedimento, pratichecollegate.idente, pratichecollegate.idsportello, pratichecollegate.idpratica, pratichecollegate.numpratica, pratichecollegate.idprocedimento From messaggipratiche, pratiche, pratiche pratichecollegate where pratichecollegate.id=messaggipratiche.fkidrisposta and messaggipratiche.fkidrichiesta=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUAP' and pratiche.idpratica=100 and pratiche.idprocedimento=1 and pratichecollegate.idente='D612' and pratichecollegate.idsportello='SUE'
union
select pratiche.idente, pratiche.idsportello, pratiche.idpratica, pratiche.numpratica, pratiche.idprocedimento, pratichecollegate.idente, pratichecollegate.idsportello, pratichecollegate.idpratica, pratichecollegate.numpratica, pratichecollegate.idprocedimento From messaggipratiche, pratiche, pratiche pratichecollegate where pratichecollegate.id=messaggipratiche.fkidrichiesta and messaggipratiche.fkidrisposta=pratiche.id and pratiche.idente='D612' and pratiche.idsportello='SUAP' and pratiche.idpratica=100 and pratiche.idprocedimento=1 and pratichecollegate.idente='D612' and pratichecollegate.idsportello='SUE';
--Individuazione dell'id interno a partire da numpratica
Select pratiche.id from pratiche where idente='D612' and idsportello='SUE' and numpratica='200SUE';
--Lista richieste attività che non hanno ricevuto una risposta