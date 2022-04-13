# Guida

## Descrizione progetto

Questo progetto nasce con lo scopo di popolare il campo "fabbricato" per tutte le localizzazione delle pratiche in cui non è stato valorizzato.  

## Casi d'uso

La funzionalità prevede un batch schedulato che si preoccuperà di popolare tale campo prendendo i riferimenti dal SIT dell'ente e in caso di successo aggiornerà i dati presenti nel database.
Ogni pratica che non verrà aggiornata verrà annotata in un file di log suddiviso per responsabile dell'istruttoria, successivamente, tali file verranno inviati per mail (se questa è stata specificata) ai responsabili coinvolti.

## Configurazione del programma

Nel file "application.yml" (presente all'interno del jar nel percorso BOOT-INF/classes/) sono presenti le configurazioni del progetto.

Nella sezione **logs: path:** è impostata la directory in cui verranno trascritti i file di log con le informazioni relative alle pratiche.

Nella sezione **templatemail: filepath:** verrà specificato il file XML di template della mail che ogni responsabile riceverà.

Nella sezione **condizionesql: rownum:** verrà specificato il numero di record che saranno processati ogni volta che si esegue il batch.

## Modalità di utilizzo

Creare una cartella (**batch_codiceimmobile**) e importare i file ".jar" e "template.xml" e creare la sottocartella "logs".

### Schedulazione sotto Windows

Passaggio 1: crea un file batch e posizionalo in una cartella in cui disponi di autorizzazioni sufficienti.

Passaggio 2: apri Utilità di pianificazione.

Passaggio 3: selezionare Crea attività di base dal riquadro Azioni a destra della finestra.

Passaggio 4: in Crea attività di base, digita il nome e fai clic su Avanti.

Passaggio 5: dal Trigger seleziona quando avviare l'attività e fai clic su Avanti.

Passaggio 6: quindi specifica inizio e ricorrenza e fare clic su Avanti.

Passaggio 7: ora fai clic su Browser e seleziona il file batch che desideri eseguire.

Passaggio 8: Valorizzare il parametro "Inzio (facoltativo)"  con il percorso della cartella in cui è presente il batch.

Passaggio 9: infine, fare clic su Fine per creare l'attività.

Ora che abbiamo creato un'attività, dobbiamo assicurarci che venga eseguita con il massimo privilegio.

Quindi fare doppio clic sull'attività appena creata.

Passaggio 10: fare clic su Esegui con privilegio massimo, quindi fare clic su OK.
