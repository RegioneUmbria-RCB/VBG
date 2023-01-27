# Manuale d'uso del configuratore dei calcoli

In questa sezione verrà trattata la configurazione di un calcolo ( ad esempio COSAP ) e il successivo utilizzo di questa configurazione all'interno delle schede dinamiche dell'istanza

## Prerequisiti

Backend ( VBG ) alla versione 2.110 o successiva
OPZIONALE: Area Riservata alla versione 2.110 o successiva

## Abilitazione della funzionalità

La funzionalità di configurazione di un calcolo si attiva abilitando la voce di menu **Utilità** -> **Configuratore Calcoli**

![Operatore con menu abilitato](./immagini/menu-abilitato.png)

## Interfaccia di configurazione

Una volta acceduti al menu, verrà mostrata la lista degli eventuali calcoli già configurati con la possibilità di:

 1. Crearne uno nuovo ( attraverso il pulsante **NUOVO** presente in fondo alla lista )
 2. Andare in modifica di una configurazione esistente ( cliccando il codice presente nella prima colonna della tabella )
 3. Modificare la descrizione di una configurazione ( il salvataggio avviene automaticamente togliendo il cursore dal campo )
 4. Eliminare una configurazione esistente

![Elenco configurazioni esistenti](./immagini/elenco-configurazioni.png)

Mentre invece la pagina di gestione di un calcolo si divide in due aree

 1. Area delle sezioni utilizzabili ( a sinistra )
 2. Area del dettaglio della configurazione ( a destra )

![Composizione di una configurazione](./immagini/configurazione-calcolo.png)

L'area delle sezioni utilizzabili è la parte in cui vengono definiti i raggruppamenti logici delle sezioni; in questa guida verrà preso come esempio la configurazione di un calcolo per la tariffa di Occupazione Suolo Pubblico; pertanto avremo dei raggruppamenti logici
strutturati in questa maniera

1. **TIPO_TARIFFA**
Macro sezione per dividere il calcolo riguardante i mezzi pubblicitari e insegne da quello riguardante le occupazioni di suolo pubblico

2. **DURATA_OCCUPAZIONE**
Suddivisione tra le occupazioni Permanenti e Temporanee

3. **TIPO_OCCUPAZIONE**
Contiene tutte le tipologie di occupazione che l'Ente intende gestire tramite VBG; ad esempio Cartelli pubblicitari,
( ad esempio nel raggruppamento "TIPO_TARIFFA" avremo tutte le tipologie di tariffe gestite come "Mezzi pubblicitari e insegne" e "Occupazione suolo pubblico" )

Tramite il bottone **NUOVO** presente in cima a questa sezione, è possibile definire un nuovo raggruppamento logico indicando il suo identificativo ( Id )
e il titolo da mostrare a video (  )

![Nuovo raggruppamento logico](./immagini/nuova-sezione-utilizzabile.png)

Mentre invece, tramite l'icona **+** presente a fianco ad ogni raggruppamento logico è possibile definire gli elementi che lo compongono ( )

### Nuova configurazione

In fase di nuova configurazione, pagina iniziale è vuota e, attraverso il bottone **NUOVO** si ha la possibilità di iniziare
a definire le sezioni che comporranno il calcolo
