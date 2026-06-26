# Introduzione

Componente per il ricalcolo aree delle istanze

## Configurazione del Servizio di Ricalcolo Aree

### Configurazione URL Backoffice

Per configurare l'URL che il backoffice deve chiamare, è necessario configurare il parametro **WSHOSTURL_RICALCOLOAREE** oppure accedere al database `ibcsecurity` e aggiornare la seguente tabella:

- **Tabella**: `comunisecurityparam`
- **Valori da inserire**:
  - `property`: `WSHOSTURL_RICALCOLOAREE`
  - `url`: `<url>` (sostituire con l'URL del servizio, tipo questo `http://localhost:8082/rest-api`)
  - `descrizione`: inserire una descrizione opportuna e significativa del servizio

Assicurarsi che l'URL indicato sia raggiungibile e corretto per l'invocazione da parte del backoffice.

### Requisiti Obbligatori

- Deve essere configurata una **URL valida** per il web service della **security**.
- **Assicurarsi che l'URL del servizio VBG sia valido**.
- **L'utenza fornita per SIGE deve essere valida e avere i permessi necessari per accedere al servizio**.

## Installazione su Docker

Le seguenti variabili d'ambiente devono essere configurate per l'avvio corretto della componente **Spring Boot**:

```env
RICALCOLOAREE_MANAGEMENT_PORT=<mport>
RICALCOLOAREE_SERVER_PORT=<port>
RICALCOLOAREE_TOKEN_PWD=<pwd>
RICALCOLOAREE_TOKEN_URL=<urlsige>
RICALCOLOAREE_TOKEN_USER=<user>
```

- `<mport>`: porta di management della componente
- `<port>`: porta principale di esposizione del servizio
- `<pwd>`: password dell'utenza SIGE
- `<urlsige>`: URL del web service SIGE
- `<user>`: utenza SIGE

---
