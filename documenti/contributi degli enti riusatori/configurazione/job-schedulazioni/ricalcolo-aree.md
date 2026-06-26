# Esempio funzionante job schedulato

```classe job
RicalcolaAreeJob
```

```metodo
execute
```

```properties(deploy.properties)
# PARAMETRI DELLO SCHEDULER
# abilita o disabilita lo scheduler
scheduler.autoStartup=true
# rappresenta l'alias dove sono memorizzati i job dello scheduler
scheduler.idcomunealias=E256
```

```sql
insert into job_repository (ID, IDCOMUNEALIAS, JOB_NAME, DESCRIPTION, ACTIVE, TRIGGER_TYPE, START_DELAY, REPEAT_INTERVAL, CRON_EXPRESSION, JOB_CLASS_NAME, SOFTWARE) values('<nuovo_progressivo>','<idcomunealias>','RICALCOLA AREE ISTANZE','RICALCOLA AREE ISTANZE','1','CRON',NULL,NULL,'<tempo_di_schedulazione>','it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.jobs.RicalcolaAreeJob',<software>);
```

Creata la classe RicalcolaAreeJob, è sufficiente che i valori delle properties siano settati come sopra indicato, e che nella tabella job_repository ci sia il relativo record come sopra indicato.
Impostati questi parametri, il job verrà eseguito secondo i criteri impostati.
Nella job_repository è importante che siano valorizzate le colonne :

- id (con un progressivo)
- idcomunealias (che ti permette di focalizzarti sull'idcomune impostato), 
- job_name (nome da assegnare al job)
- descrizione (descrizione da assegnare al job)
- active = 1 (che ti permette di schedulare il determinato job, lo riconosce come job da schedulare all'avvio dell'applicazione),
- trigger_type = CRON (che ti permette di riconoscerlo come job da schedulare secondo un certo criterio) 
- cron_expression (es. 0 15 13 ? * * *  che significa 'alle 13.15 di ogni giorno'),
- job_class_name (es. it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.jobs.RicalcolaAreeJob perché nel nostro caso è la classe RicalcolaAreeJob)
- software (impostando il software è possibile indicare al job di focalizzarsi sul software indicato)

ATTENZIONE!
Per il job in questione, il job non si focalizzerà sull'idcomunealias associato alla property scheduler.idcomunealias, ma all'idcomunealias associato alla colonna IDCOMUNEALIAS
della tabella job_repository