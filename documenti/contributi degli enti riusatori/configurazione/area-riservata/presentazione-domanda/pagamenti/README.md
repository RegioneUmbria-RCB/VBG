# Configurazione dei pagamenti on line

I pagamenti tramite area riservata si attivano inserendo nel workflow della domanda i seguenti steps:

- ~/reserved/inserimentoistanza/pagamenti/VerificaStatoPagamenti*.aspx
- ~/reserved/inserimentoistanza/pagamenti/GestionePagamenti*.aspx
- ~/reserved/inserimentoistanza/pagamenti/Pagamento*.aspx

Dove l'asterisco va sostituito con il sistema di pagamenti integrato con l'area riservata.
Vedi ad esempio Pagamenti Siena o [Pagamenti tramite Nodo Pagamenti Generico](nodo-pagamenti/README.md)

## Configurazione web.config area riservata

Aggiungere nel web.config nella sezione &lt;log4net&gt; il seguente appender e logger
Per poter tracciare tutte le operazioni di pagamento.

**!!!QUESTO PASSAGGIO E' FONDAMENTALE ALTRIMENTI NON SI POSSONO TRACCIARE EVENTUALI ERRORI AVVENUTI IN FASE DI PAGAMENTO!!!**

```xml
<appender name="pagamentiLog" type="log4net.Appender.RollingFileAppender">
    <file value="Logs/pagamenti-nodo-generico.log.txt"/>
    <appendToFile value="true"/>
    <maxSizeRollBackups value="10"/>
    <maximumFileSize value="1500KB"/>
    <rollingStyle value="Size"/>
    <layout type="log4net.Layout.PatternLayout">
        <conversionPattern value="%d [%t] %-5p %c - %m%n"/>
    </layout>
</appender>

<logger name="Init.Sigepro.FrontEnd.Pagamenti" additivity="False">
    <level value="DEBUG"/>
    <appender-ref ref="pagamentiLog"/>
</logger>

<logger name="Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti" additivity="False">
    <level value="DEBUG"/>
    <appender-ref ref="pagamentiLog"/>
</logger>
```

## Steps di pagamento

### VerificaStatoPagamenti*.aspx

Lo step ha la responsabilità di verificare se per la pratica che si sta compilando sono già state avviate delle operazioni di pagamento.
Qualora queste venissero trovate si cercherà di verificarne l'esito.

- in caso di esito positivo il pagamento viene completato e il controllo viene passato allo step successivo
- in caso di esito negativo o nel caso in cui non sia possibile verificare l'esito, all'utente viene mostrato un messaggio e gli viene offerta la possibilità di annullare il pagamento in corso e riavviare l'operazione (verificare nella specifica integrazione quali sono le step properties che permettono di personalizzare il messaggio per l'utente)

Lo step transiterà direttamente allo step successivo qualora non venissero trovate operazioni di pagamento in sospeso.

### GestionePagamenti*.aspx

Lo step ha la responsabilità di raccogliere informazioni su quali oneri verranno pagati on-line, su quali sono già stati pagati e quali non sono dovuti.
Nel caso in cui la pratica non preveda oneri lo step transiterà direttamente allo step successivo.

### Pagamento*.aspx

Lo step ha la responsabilità di

- inizializzare il pagamento nel sistema di pagamenti esterno
- di trasferire l'utente verso l'interfaccia di pagamento
- di gestire il rientro dal sistema di pagamento
- di verificare l'esito del pagamento
  
In quest'ultima fase se l'esito del pagamento non è subito noto può effettuare più chiamate verso pagine esterne di verifica esito oppure la stessa pagina può ricaricarsi (il comportamento dipende dal sistema di pagamento integrato)
Nel caso in cui il pagamento sia fallito o nel caso in cui si sia verificato un errore nel pagamento allora la pagina non permetterà la prosecuzione della domanda.
