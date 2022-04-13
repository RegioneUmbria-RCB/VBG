# Protocollazione

VBG è in grado di integrarsi con una serie di sistemi protocollazione esterni diversi tra di loro. La lista delle attuali integrazioni
sviluppate è disponibile accedendo al backoffice e andando nella sezione Configurazione -> Tutti i backoffice -> Configurazione regole.
A seconda della versione di VBG installata potrebbero essere presenti più o meno integrazioni, nel caso non si trovi l'integrazione verificare che la versione di VBG sia l'ultima rilasciata

Una volta stabilita che l'integrazione è presente, bisogna procedere con una serie di configurazioni generiche e valide per tutti i sistemi di protocollo, e una serie di configurazioni proprietarie del sistema di protocollazione

## Primo passo: configurare la verticalizzazione PROTOCOLLO_ATTIVO
In prima battuta è necessario attivare la verticalizzazione PROTOCOLLO_ATTIVO e procedere alla sua configurazione ( attenzione alcune configurazioni generiche possono essere sovrascritte dallo specifico connettore, in quei casi fare sempre riferimento a quanto indicato dalla specifica integrazione)

[Visualizza la sezione relativa alla verticalizzazione PROTOCOLLO_ATTIVO](./protocollo_attivo.md)

Una volta configurata questa sezione sarà possibile procedere con le sezioni specifiche di ogni singolo connettore. 

Le ultime integrazioni sviluppate sono state documentate e di seguito vengono riportati i link

## Secondo passo: configurare la specifica integrazione

#### Integrazione con protocollo DoQui - Acta tramite i servizi ACARIS 
E' possibile integrarsi con il protocollo DoQui - Acta tramite i servizi ACARIS messi a disposizione da Città Metropolitana di Torino. Per altri fornitori di servizi bisogna verificarne prima la compatibilità
[Visualizza la configurazione dell'integrazione](./acaris.md)

#### Integrazione con protocollo CiviliaNext 
E' possibile integrarsi con il protocollo CiviliaNext di Dedagroup. 
[Visualizza la configurazione dell'integrazione](./civilianext.md)

#### Integrazione con protocollo JIride
E' possibile integrarsi con il protocollo JIride del Gruppo Maggioli. 
[Visualizza la configurazione dell'integrazione](./jiride.md)

## Approfondimenti
Di seguito verranno illustrate più dettagliatamente alcune delle ultime funzionalità sviluppate

#### Possibilità di inoltro alla Pec dei documenti della protocollazione
Viene data da interfaccia la possibilità di specificare quali documenti, che saranno protocollati, possono essere inviati tramite mail (solo per i sistemi di protocollazione che supportano questa feature Es. TRIESTE-INSIEL)

[Visualizza le modifiche](./invio_pec_documenti.md)