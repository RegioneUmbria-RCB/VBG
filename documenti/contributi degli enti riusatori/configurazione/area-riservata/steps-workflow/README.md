# Steps del Workflow

Questa documentazione descrive tutti gli step disponibili per la configurazione del workflow di inserimento istanza. Ogni step è identificato da un valore dell'enumerazione `WorkflowSteps` e contiene le informazioni necessarie per il routing e la visualizzazione.

Il nuovo formato negli steps del workflow è il seguente:

```XML
  <Step>
    <StepId>StepID</StepId>
    <Title>Titolo step</Title>
    <Description>Descrizione step</Description>
    <Control>Path dello step **DEPRECATO IN FAVORE DI STEPID**</Control>
    <ControlProperty name="NomeProperty">ValoreProperty</ControlProperty>
  </Step>
```

In particolare l'elemento **Control** è deprecato e dovrà essere sostituito dal nuovo
elemento **StepId** per garantire la portabilità degli steps dalla vecchia area riservata alla nuova.

> Per semplificare il lavoro del configuratore **si consiglia di utilizzare l'editor grafico** che 
> permette di sostituire in maniera trasparente i elementi control con i nuovi step id

## Lista degli Steps

### Benvenuto
- **ID**: `Benvenuto`
- **Descrizione**: Pagina di benvenuto del workflow
- **Framework Path**: `~/reserved/inserimentoistanza/benvenuto.aspx`
- **Core Path**: `benvenuto`

### Gestione Privacy
- **ID**: `GestionePrivacy`
- **Descrizione**: Pagina per la gestione della privacy
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneprivacy.aspx`
- **Core Path**: `gestione-privacy`

### Gestione Interventi
- **ID**: `GestioneInterventi`
- **Descrizione**: Pagina per la gestione degli interventi
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneinterventi.aspx`
- **Core Path**: `gestione-interventi`
- **Note**: Merge Point

### Gestione Interventi ATECO

⚠️ DEPRECATO SU AR CORE, viene automaticamente sostituito da GestioneInterventi

- **ID**: `GestioneInterventiAteco`
- **Descrizione**: Pagina per la gestione degli interventi ATECO
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneinterventiateco.aspx`
- **Core Path**: `gestione-interventi`
- **Note**: Merge Point

### Gestione Endo (deprecato)
- **ID**: `GestioneEndoOld`
- **Descrizione**: Pagina per la gestione degli endo (deprecato)
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneendo.aspx`
- **Core Path**: `gestione-endo`
- **Stato**: ⚠️ **DEPRECATO**

### Gestione Endo
- **ID**: `GestioneEndo`
- **Descrizione**: Pagina per la gestione degli endo
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneendov2.aspx`
- **Core Path**: `gestione-endo`

### Verifica Procedura
- **ID**: `GestioneEndoVerificaScia`
- **Descrizione**: Verifica procedura della domanda in base agli endoprocedimenti selezionati
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneendoverificascia.aspx`
- **Core Path**: `gestione-endo-verifica-scia`

### Gestione Anagrafiche
- **ID**: `GestioneAnagrafiche`
- **Descrizione**: Pagina per la gestione delle anagrafiche
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneanagrafiche.aspx`
- **Core Path**: `gestione-anagrafiche`

### Gestione Anagrafiche Semplificata
- **ID**: `GestioneAnagraficheSemplificata`
- **Descrizione**: Pagina per la gestione delle anagrafiche semplificate
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneanagrafichesemplificata.aspx`
- **Core Path**: `gestione-anagrafiche`
- **Stato**: ⚠️ **DEPRECATO**

### Gestione Dati Dinamici
- **ID**: `GestioneDatiDinamici`
- **Descrizione**: Pagina per la gestione dei dati dinamici
- **Framework Path**: `~/reserved/inserimentoistanza/gestionedatidinamici.aspx`
- **Core Path**: `gestione-dati-dinamici`

### Gestione Sottoscriventi
- **ID**: `GestioneSottoscriventi`
- **Descrizione**: Pagina per la gestione dei sottoscriventi
- **Framework Path**: `~/reserved/inserimentoistanza/gestionesottoscriventi.aspx`
- **Core Path**: `gestione-sottoscriventi`

### Gestione Delega a Trasmettere
- **ID**: `GestioneDelegaATrasmettere`
- **Descrizione**: Pagina per la gestione della delega a trasmettere
- **Framework Path**: `~/reserved/inserimentoistanza/gestionedelegaatrasmettere.aspx`
- **Core Path**: `gestione-delega-a-trasmettere`

### Gestione Domicilio Elettronico
- **ID**: `GestioneDomicilioElettronico`
- **Descrizione**: Pagina per la gestione del domicilio elettronico
- **Framework Path**: `~/reserved/inserimentoistanza/gestionedomicilioelettronico.aspx`
- **Core Path**: `gestione-domicilio-elettronico`

### Gestione Endo Presenti
- **ID**: `GestioneEndoPresenti`
- **Descrizione**: Pagina per la gestione degli endo presenti
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneendopresenti.aspx`
- **Core Path**: `gestione-endo-presenti`

### Gestione Allegati Endo Presenti
- **ID**: `GestioneEndoPresentiAllegati`
- **Descrizione**: Pagina per la gestione degli allegati degli endo presenti
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneendopresenti_allegati.aspx`
- **Core Path**: `null`
- **Stato**: ⚠️ **DEPRECATO**

### Testo Libero
- **ID**: `TestoLibero`
- **Descrizione**: Pagina per l'inserimento di testo libero
- **Framework Path**: `~/reserved/inserimentoistanza/testo-libero.aspx`
- **Core Path**: `testo-libero`

### Gestione Informativa
- **ID**: `GestioneInformativa`
- **Descrizione**: Pagina per la gestione dell'informativa
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneinformativa.aspx`
- **Core Path**: `gestione-informativa`

### Gestione Allegati Intervento
- **ID**: `GestioneAllegatiIntervento`
- **Descrizione**: Pagina per la gestione degli allegati dell'intervento
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneallegatiintervento.aspx`
- **Core Path**: `allegati-intervento`

### Gestione Allegati Dati Dinamici
- **ID**: `GestioneAllegatiDatiDinamici`
- **Descrizione**: Pagina per la gestione degli allegati dei dati dinamici
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneallegatidatidinamici.aspx`
- **Core Path**: `allegati-dati-dinamici`

### Gestione Allegati Endo
- **ID**: `GestioneAllegatiEndo`
- **Descrizione**: Pagina per la gestione degli allegati degli endo
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneallegatiendo.aspx`
- **Core Path**: `allegati-endo`

### Gestione Stradario
- **ID**: `GestioneStradario`
- **Descrizione**: Pagina per la gestione dello stradario
- **Framework Path**: `~/reserved/inserimentoistanza/gestionestradario.aspx`
- **Core Path**: `gestione-localizzazioni`
- **Stato**: ⚠️ **DEPRECATO**

### Gestione Dati Catastali
- **ID**: `GestioneDatiCatastali`
- **Descrizione**: Pagina per la gestione dei dati catastali
- **Framework Path**: `~/reserved/inserimentoistanza/gestionedaticatastali.aspx`
- **Core Path**: `null`
- **Stato**: ⚠️ **DEPRECATO**

### Gestione Localizzazioni
- **ID**: `GestioneLocalizzazioni`
- **Descrizione**: Pagina per la gestione delle localizzazioni
- **Framework Path**: `~/reserved/inserimentoistanza/gestionelocalizzazioni.aspx`
- **Core Path**: `gestione-localizzazioni`

### Gestione Localizzazioni SIT
- **ID**: `GestioneLocalizzazioniSit`
- **Descrizione**: Pagina per la gestione delle localizzazioni SIT
- **Framework Path**: `~/reserved/inserimentoistanza/gestionelocalizzazionisit.aspx`
- **Core Path**: `gestione-localizzazioni-sit`

### Gestione localizzazioni con Cartografico
- **ID**: `GestioneLocalizzazioniCartografico`
- **Descrizione**: Pagina per la gestione delle localizzazioni integrate con un sistema cartografico
- **Framework Path**: `~/reserved/inserimentoistanza/gestionelocalizzazionicartografico.aspx`
- **Core Path**: `gestione-localizzazioni-cartografico`

### Dati Istanza CE
- **ID**: `DatiIstanzaCe`
- **Descrizione**: Pagina per la gestione dei dati dell'istanza CE
- **Framework Path**: `~/reserved/inserimentoistanza/datiistanzace.aspx`
- **Core Path**: `dati-istanza-ce`

### Dati Istanza
- **ID**: `DatiIstanza`
- **Descrizione**: Pagina per la gestione dei dati dell'istanza
- **Framework Path**: `~/reserved/inserimentoistanza/datiistanza.aspx`
- **Core Path**: `dati-istanza-ce`
- **Stato**: ⚠️ **DEPRECATO**

### Gestione Procure
- **ID**: `GestioneProcure`
- **Descrizione**: Pagina per la gestione delle procure
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneprocure.aspx`
- **Core Path**: `gestione-procure`

### Gestione Oneri
- **ID**: `GestioneOneri`
- **Descrizione**: Pagina per la gestione degli oneri
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneoneri.aspx`
- **Core Path**: `gestione-oneri`

### Gestione Ammissibilità Intervento
- **ID**: `GestioneAmmissibilitaIntervento`
- **Descrizione**: Pagina per la gestione dell'ammissibilità dell'intervento
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneammissibilitaintervento.aspx`
- **Core Path**: `gestione-ammissibilita-intervento`

### Riepilogo Domanda
- **ID**: `RiepilogoDomanda`
- **Descrizione**: Pagina per il riepilogo della domanda
- **Framework Path**: `~/reserved/inserimentoistanza/riepilogodomandahtml.aspx`
- **Core Path**: `riepilogo-domanda`

## Steps di Pagamento

### Verifica Stato Pagamenti
- **ID**: `VerificaStatoPagamenti`
- **Descrizione**: Pagina per la verifica dello stato dei pagamenti
- **Framework Path**: `~/reserved/inserimentoistanza/pagamenti/verificastatopagamentinodopagamenti.aspx`
- **Core Path**: `pagamenti/verifica-stato-pagamenti`

### Gestione Pagamento
- **ID**: `GestionePagamento`
- **Descrizione**: Pagina per la gestione dei pagamenti
- **Framework Path**: `~/reserved/inserimentoistanza/pagamenti/gestionepagamentinodopagamenti.aspx`
- **Core Path**: `pagamenti/gestione-pagamenti`

### Pagamento
- **ID**: `Pagamento`
- **Descrizione**: Pagina per il pagamento
- **Framework Path**: `~/reserved/inserimentoistanza/pagamenti/pagamentonodopagamenti.aspx`
- **Core Path**: `pagamenti/pagamento`
  
### Gestione Verifica Soggetti Firmatari
- **ID**: `GestioneVerificaSoggettiFirmatari`
- **Descrizione**: Pagina per la verifica dei soggetti firmatari
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneverificasoggettifirmatari.aspx`
- **Core Path**: `gestione-verifica-soggetti-firmatari`

## Steps Specifici per Enti

### Gestione Files Excel
- **ID**: `GestioneFilesExcel`
- **Descrizione**: Pagina per la gestione dei files Excel
- **Framework Path**: `~/reserved/inserimentoistanza/gestionefilesexcel.aspx`
- **Core Path**: `gestione-files-excel`

### Gestione Accesso Atti Trieste
- **ID**: `GestioneAccessoAttiTrieste`
- **Descrizione**: Pagina per la gestione dell'accesso agli atti a Trieste
- **Framework Path**: `~/reserved/inserimentoistanza/triesteaccessoatti.aspx`
- **Core Path**: `gestione-accesso-atti-trieste`

### Gestione Transiti
- **ID**: `GestioneTransiti`
- **Descrizione**: Pagina per la gestione dei transiti
- **Framework Path**: `~/reserved/inserimentoistanza/gestionetransiti.aspx`
- **Core Path**: `gestione-transiti`

### Gestione Localizzazioni Modena
- **ID**: `GestioneLocalizzazioniModena`
- **Descrizione**: Pagina per la gestione delle localizzazioni a Modena
- **Framework Path**: `~/reserved/inserimentoistanza/localizzazioni-modena/gestione-localizzazioni-modena.aspx`
- **Core Path**: `gestione-localizzazioni-modena`

### Gestione Localizzazioni SIT Modena
- **ID**: `GestioneLocalizzazioniSitModena`
- **Descrizione**: Pagina per la gestione delle localizzazioni SIT a Modena
- **Framework Path**: `~/reserved/inserimentoistanza/gestionelocalizzazionisitmodena.aspx`
- **Core Path**: `gestione-localizzazioni-sit-modena`

## Steps LDP

### Benvenuto LDP
- **ID**: `BenvenutoLdp`
- **Descrizione**: Pagina di benvenuto del workflow LDP
- **Framework Path**: `~/reserved/inserimentoistanza/benvenutoldp.aspx`
- **Core Path**: `benvenuto-ldp`

### Gestione Allegato LDP
- **ID**: `GestioneAllegatoLdp`
- **Descrizione**: Pagina per la gestione degli allegati LDP
- **Framework Path**: `~/reserved/inserimentoistanza/gestioneallegatoldp.aspx`
- **Core Path**: `allegato-ldp`

### Integrazione LDP Livorno
- **ID**: `IntegrazioneLdpLivorno`
- **Descrizione**: Pagina per l'integrazione LDP a Livorno
- **Framework Path**: `~/reserved/inserimentoistanza/integrazione-ldp-livorno.aspx`
- **Core Path**: `integrazione-ldp-livorno`

## Note Tecniche

- **Framework Path**: Percorso utilizzato nell'architettura legacy basata su ASP.NET WebForms
- **Core Path**: Percorso utilizzato nell'architettura moderna
- **Merge Point**: Indica che lo step può essere raggiunto da percorsi multipli nel workflow
- **Deprecato**: Steps mantenuti per compatibilità ma non più utilizzati nei nuovi workflow

Gli steps deprecati dovrebbero essere evitati nelle nuove configurazioni e sostituiti con le loro versioni aggiornate quando disponibili.