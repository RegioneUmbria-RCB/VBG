# Integrazione con sistemi di pagamento

VBG è in grado di integrarsi con una serie di sistemi di pagamento esterni diversi tra di loro. 

VBG non parla direttamente con tutti i sistemi dei pagamenti integrati, ma si avvale di una ulteriore componente chiamata NODO-PAGAMENTI che è incaricata di fare da tramite verso tutti i 
sistemi di pagamento ed esporre un'interfaccia unica di comunicazione verso il backoffice di VBG.

La lista delle attuali integrazioni sviluppate è disponibile accedendo al backoffice e andando nella sezione Configurazione -> Tutti i backoffice -> Configurazione regole.
A seconda della versione di VBG installata potrebbero essere presenti più o meno integrazioni, nel caso non si trovi l'integrazione verificare che la versione di VBG sia l'ultima rilasciata

Una volta stabilita che l'integrazione è presente, bisogna procedere con una serie di configurazioni generiche e valide per tutti i sistemi di pagamenti, e una serie di configurazioni proprietarie del sistema di pagamento specifico che si sta integrando

Alcune di queste configurazioni possono essere effettuate dentro l'interfaccia del Backoffice VBG mentre altre vanno necessariamente fatte sul database del componente NODO-PAGAMENTI

## Primo passo: configurazioni preliminari
In prima battuta è necessario configurare la componente NODO-PAGAMENTI affinchè sia in grado di rispondere alle richieste provenienti dal backoffice di VBG ( attenzione alcune configurazioni generiche possono essere sovrascritte dallo specifico connettore, in quei casi fare sempre riferimento a quanto indicato dalla specifica integrazione)
[Visualizza la sezione relativa alla configurazione preliminare](./configurazione-nodo-pagamenti.md)

Una volta configurata questa sezione sarà possibile procedere con le sezioni specifiche di ogni singolo connettore. 

Le ultime integrazioni sviluppate sono state documentate e di seguito vengono riportati i link

## Secondo passo: configurare la specifica integrazione

#### Integrazione con sistema dei pagamenti IRIS 
[Visualizza la configurazione dell'integrazione](./connettore-IRIS.md)

#### Integrazione con sistema dei pagamenti EntraNext
[Visualizza la configurazione dell'integrazione](./EntraNext.md)

#### Integrazione con sistema dei pagamenti GovPay
[Visualizza la configurazione dell'integrazione](./govpay.md)

#### Integrazione con sistema dei pagamenti Nexi
[Visualizza la configurazione dell'integrazione](./NEXI.md)

#### Integrazione con sistema dei pagamenti Pago Umbria
[Visualizza la configurazione dell'integrazione](./pagoumbria.md)

#### Integrazione con sistema dei pagamenti Piemonte Pay
[Visualizza la configurazione dell'integrazione](./piemontepay.md)