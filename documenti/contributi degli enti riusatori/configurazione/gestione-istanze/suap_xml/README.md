# Generazione del file SUAP.XML

La funzionalità permette di generare un file secondo le specifiche impresainungiorno.gov.it di:

- pratica_suap-2.0.0.xsd
- pratica_suap-1.0.1.xsd

La funzionalità può venire agganciata all'inserimento di una istanza, oppure nella maschera di protocollazione di un movimento dove sarà presente un pulsante di funzionalità "**GENERA XML PRATICA SUAP**".

Nel caso di creazione del file durante l'inserimento della pratica il documento sarà salvato nella sezione **DOCUMENTI ISTANZA**, mentre nel caso di movimenti nella sezione **ALLEGATI** del movimento.

## Configurazione

Per attivare la funzionalità è necessario attivare la regola:

- SUAP_XML (Permette la gestione della generazione del file SUAP XML all'interno delle istanze)

Questi i parametri che possono essere impostati:

|NOME|DESCRIZIONE|NOTE
|---|-----|---
|URL|Indirizzo del servizio di creazione pratica| obbligatorio.
|VALIDA|Se effettuare la validazione formale. Valori attesi 1 o 0 (DEFAULT) | Indica se validare o meno lo schema prima di creare il file xml che rappresenta la pratica. Per effettuare la validazione impostarlo a 1 (va lasciato a 0)
|VIS_BOTTONE_SU_PROT_MOVIMENTO|Valori attesi 1 o 0 (DEFAULT)|Indica se mostrare o meno il bottone per la generazione del SUAP XML nella maschera di protocollazione dei movimenti. Per mostrare il bottone, impostarlo a 1
|GENERA_SU_INSERIMENTO_ISTANZA|Valori attesi 1 o 0 (DEFAULT)|Indica se in fase di inserimento istanza il sistema deve generare automaticamente il SUAP XML ed inserirlo tra i documenti dell'istanza (la generazione avviene prima della generazione del riepilogo e dell'eventuale protocollazione ). Per generare il file, impostarlo a 1

