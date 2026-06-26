# Integrazione SIT

## Requisiti minimi

Richiede:

- Console versione 3.20
- Installazione comune locale con configurazione SIT attiva

## Descrizione

Solitamente i dati dei SIT esistono soltanto nelle installazioni locali.

Per poter integrare la console con i SIT è stato sviluppato un sistema che redirige le chiamate del SIT della console verso l’installazione locale.

## Configurazione di backend

Nella console di backend **LOCALE** è necessario configurare i parametri:

- **URL_WSSIT**
- **ALIAS_BACKEND_LOCALE**
- **REDIRECT_TO_SIT_PAGE**

della nuova verticalizzazione **SIT_CONSOLE**.

**ALIAS_BACKEND_LOCALE** conterrà il codice del comune scelto (tra quelli della console).

**URL_WSSIT** conterrà l'Url del servizio SIT dell'installazione aspnet di backend per il comune scelto.

**REDIRECT_TO_SIT_PAGE** valore booleano che, se impostato a 1, permette il redirect automatico dallo step GestioniLocalizzazioni allo step GestioniLocalizzazioniSit.
Se non impostato il default è `false`
