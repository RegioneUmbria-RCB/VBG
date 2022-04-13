# NLA-PROXY

Il nodo Proxy consente di far comunicare tra loro due installazioni che utilizzano la componente STC localizzate in differenti datacenter.  
Rappresenta un vero e proprio proxy che si preoccupa di comunicare con il proprio dominio STC conoscendo le credenziali e girando le chiamate al sottosistema STC e quindi NLA-DESTINATARIO.

Come caso d'uso ipotizziamo l'inoltro di una pratica da parte del SUAP dell'unione valli e delizie all'ufficio sismica della provincia di Ravenna per una pratica di sua competenza. 
Le installazioni dei rispettivi backend di gestione pratica sono localizzati rispettivamente in due differenti datacenter.
In questo caso il backoffice di valli e delizie invia le richieste al suo STC indicando come destinatario NLA il nodo-proxy installato nel suo datacenter.

Questi conoscendo il nodo proxy dell'installazione della provincia di Ravenna chiamerà il nodo proxy della provincia di Ravenna che girerà le chiamate al suo STC e di conseguenza al nodo di backend della provincia.

Le stesse interazioni sono possibili anche in senso inverso: ad esempio nel caso che il backend sismica di Ravenna intenda richiedere una integrazione documentale al SUAP unione valli e delizie.

```
FERRARA				|	Ravenna
BACKEND --> STC --> NLA-PROXY	|	NLA-PROXY --> STC --> BACKEND
BACKEND <-- STC <-- NLA-PROXY	|	NLA-PROXY <-- STC <-- BACKEND

```

## Casi d'uso
Nella seguente sezione sono descritti i
[casi d'uso](nla-proxy-casi-uso.md) per capire una eventuale interazione tra nodi nla-proxy.


## Come configurare il nodo NLA-PROXY

A livello applicativo (nla-proxy.war) vanno configurati i file **deploy.properties** e **nla-proxy-security.properties**.

### deploy.properties

### nla-proxy-security.properties

**Proprietà generiche**

- **ws.nla.username**: rappresenta la username delle credenziali configurate per proteggere le chiamate in ingresso da altri proxy.
- **ws.nla.password**: rappresenta la password delle credenziali configurate per proteggere le chiamate in ingresso da altri proxy.
- **ws_client.connection.timeout**: il timeout di connessione verso i client STC e NLA_PROXY
- **ws_client.receive.timeout**: il timeout di attesa risposta (read timeout) verso i client STC e NLA_PROXY

**Proprietà di comunicazione con altri nodi**
Rappresentano le proprietà con le quali il nodo proxy dell'ente conosce ed individua il nodo proxy che lo sta chiamando ed i nodi interni che deve invocare.

**Le configurazioni dei nodi proxy remoti hanno la forma**
```
	<IDNODO>.<IDENTE>.<IDSPORTELLO>.<nome_parametro>=<VALORE>
```
- I parametri	che possono essere specificati sono
	- **url_ws**: indirizzo del WSDL del nodo proxy remoto cui girare le chiamate STC dei nodi interni
	- **utente**: nome utente della autenticazione WSS4J del proxy remoto
	- **password**: password della autenticazione WSS4J del proxy remoto

I parametri IDNODO, IDENTE, IDSPORTELLO corrispondono alla tupla configurata nel back remoto sull'amministrazione che identifica lo sportello a cui inviare le comunicazioni.



**Le configurazioni locali hanno la forma**

```
	PROXY.<IDNODO>.<IDENTE>.<IDSPORTELLO>.<nome_parametro>=<VALORE>
```
- I parametri che possono essere specificati sono:
	- **IDNODO**: Rappresenta l'identificativo del nodo di backend o NLA Destinatario della comunicazione che avvien per conto del proxy remoto
	- **IDNODO_PROXY**: rappresenta l'identificativo o IDNODO di questo proxy come configurato su STC locale (potrebbe essere diverso dall'identificativo del proxy remoto.

I parametri IDNODO, IDENTE, IDSPORTELLO corrispondono alla tupla configurata nel back remoto sull'amministrazione che identifica lo sportello a cui inviare le comunicazioni.



## Esempi di configurazione
Riportiamo gli esempi di configurazione dei file didue proxy remoti usati in ambiente di collaudo, per unione Valli e Delizie e per Ufficio Sismica provincia di Ravenna.

### Nodo proxy Unione Valli e Delizie

------

```
## configurazioni NLA-PROXY ARGENTA
444.LUGO.CE.url_ws=http://localhost:8081/nla-proxy-ravenna/services/NlaSoap11Proxy?wsdl
444.LUGO.CE.utente=
444.LUGO.CE.password=

PROXY.444.ARGENTA.SS.IDNODO=450
## IDNODO DEL NODO PROXY POTREBBE ESSERE DIFFERENTE DA INSTALLAZIONE A INSTALLAZIONE
PROXY.444.ARGENTA.SS.IDNODO_PROXY=444

ws.nla.username=
ws.nla.password=

ws_client.connection.timeout = 12000
ws_client.receive.timeout = 480000

```

------

### Nodo proxy Ufficio sismica provincia di Ravenna

```

## configurazioni NLA-PROXY RAVENNA
444.ARGENTA.SS.url_ws=http://10.50.55.42:8080/nla-proxy/services/NlaSoap11Proxy?wsdl
444.ARGENTA.SS.utente=
444.ARGENTA.SS.password=

PROXY.444.LUGO.CE.IDNODO=400
## IDNODO DEL NODO PROXY POTREBBE ESSERE DIFFERENTE DA INSTALLAZIONE A INSTALLAZIONE
PROXY.444.LUGO.CE.IDNODO_PROXY=444

ws.nla.username=
ws.nla.password=

ws_client.connection.timeout = 12000
ws_client.receive.timeout = 480000

```