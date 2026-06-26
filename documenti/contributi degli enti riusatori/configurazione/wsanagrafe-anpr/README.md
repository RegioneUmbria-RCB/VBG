# Raccolta Dati di Configurazione ANPR – Servizi C020 e C030

## 1. Obiettivo

Raccogliere dal cliente tutti i parametri PDND necessari per configurare l'integrazione con i servizi ANPR **C020** (consultazione residenza) e **C030** (consultazione ID univoco nazionale), entrambi con Pattern Model applicato.

Il cliente ha già completato la configurazione lato PDND (registrazione, creazione client, caricamento chiavi pubbliche, approvazione finalità). Non serve fornirgli supporto su quella parte: dobbiamo solo farci dare i valori.

---

## 2. Prima della chiamata — Checklist preparatoria

Prima di contattare il cliente, verifica di avere chiaro:

- **Ambiente target**: il cliente va in collaudo o in produzione? Molti parametri cambiano di conseguenza (vedi §5).
- **Canale sicuro per la chiave privata**: hai già concordato un canale cifrato (SFTP, share protetto, PEC con allegato cifrato, ecc.)? Non accettare chiavi via email in chiaro, nemmeno "solo per un test".
- **Client PDND unico o separato**: il cliente potrebbe usare un unico client PDND per entrambi i servizi, oppure due distinti. Chiedilo subito — cambia il numero di parametri da raccogliere.

---

## 3. Sicurezza — Gestione della chiave privata

> **Regola assoluta**: la chiave privata non transita mai su canali in chiaro.

Chiedi al cliente di fornirti il file `.pem` della chiave privata RSA in chiaro (non codificato in Base64 — la conversione la facciamo noi). Il file deve avere questa struttura:

```
-----BEGIN RSA PRIVATE KEY-----
MIIEowIBAAKCAQEA...
(contenuto della chiave)
...
-----END RSA PRIVATE KEY-----
```

**Procedura di consegna:**

1. Concorda con il cliente un canale sicuro prima della chiamata.
2. Ricevi il file `.pem` esclusivamente tramite quel canale.
3. Conferma la ricezione al cliente e verifica che il file sia leggibile.
4. Non conservare copie locali dopo averlo passato al team tecnico.

Se il cliente chiede perché non in Base64: spiegagli che la conversione è a carico nostro, ed è più semplice e meno soggetta a errori partire dal formato originale.

---

## 4. Parametri da raccogliere

I parametri sono identici nella struttura per C020 e C030. Per ogni servizio servono **8 valori**. Di seguito sono raggruppati per tipologia, con le istruzioni su dove il cliente li trova.

### 4.1 Identificativi del client PDND

| Parametro | Dove lo trova il cliente |
|---|---|
| `CLIENTIDPDND` | Portale PDND → sezione di dettaglio del client. È un UUID (es. `a1b2c3d4-e5f6-7890-abcd-ef1234567890`). |
| `KIDPDND` | Portale PDND → dettaglio client → sezione "Chiavi". È il Key ID associato alla chiave pubblica caricata. |
| `PURPOSEIDPDND` | Portale PDND → sezione "Finalità". È un UUID associato alla finalità approvata per il servizio specifico. |
| `CHIAVE_PRIVATA` | File `.pem` della chiave privata RSA generata dal cliente. Vedi §3 per la consegna. |

**Domanda chiave da fare al cliente**: *"Avete creato un client PDND separato per C020 e C030, oppure ne usate uno solo per entrambi?"*

- Se **separati**: raccogli tutti e 4 i valori per ciascun servizio.
- Se **unico**: i valori `CLIENTIDPDND`, `KIDPDND` e `CHIAVE_PRIVATA` saranno probabilmente identici, ma `PURPOSEIDPDND` sarà comunque diverso (una finalità per servizio). Fatti confermare tutto esplicitamente — non dare per scontato nulla.

### 4.2 URL ed endpoint

Questi parametri dipendono dall'ambiente (collaudo vs produzione). Nella tabella sotto trovi i valori attesi per ciascun ambiente. Fatti sempre confermare il valore dal cliente — non inserire mai un URL basandoti solo sulla tua ipotesi dell'ambiente.

| Parametro | Collaudo | Produzione |
|---|---|---|
| `AUDPDND` | `https://auth.uat.interop.pagopa.it/client-assertion` | `https://auth.interop.pagopa.it/client-assertion` |
| `URLTOKENPDND` | `https://auth.uat.interop.pagopa.it/as/token.oauth2` | `https://auth.interop.pagopa.it/as/token.oauth2` |
| `BASEURLAPI` | *(varia per C020/C030 — confermare con il cliente)* | *(varia per C020/C030 — confermare con il cliente)* |

> **Nota su `AUDPDND` e `URLTOKENPDND`**: se il cliente usa lo stesso ambiente per C020 e C030, questi valori saranno identici. Raccoglili comunque separatamente nella tabella.

### 4.3 Audience per il token AGID-JWT-Signature

| Parametro | Dove lo trova il cliente |
|---|---|
| `AUDTOKENAGIDJWTSIGNATURE` | Documentazione tecnica del servizio ANPR oppure nella configurazione del proprio e-service PDND. |

Chiedi al cliente se il valore è lo stesso per C020 e C030 o se differisce — dipende da come hanno configurato i loro e-service.

---

## 5. Riepilogo valori attesi per ambiente

Usa questa tabella come riferimento durante la raccolta. I valori segnati con "—" sono specifici del cliente e non prevedibili.

| Parametro | Collaudo (UAT) | Produzione |
|---|---|---|
| `AUDPDND` | `https://auth.uat.interop.pagopa.it/client-assertion` | `https://auth.interop.pagopa.it/client-assertion` |
| `URLTOKENPDND` | `https://auth.uat.interop.pagopa.it/as/token.oauth2` | `https://auth.interop.pagopa.it/as/token.oauth2` |
| `BASEURLAPI` | Da confermare con il cliente | Da confermare con il cliente |
| `AUDTOKENAGIDJWTSIGNATURE` | — | — |
| `CLIENTIDPDND` | — | — |
| `KIDPDND` | — | — |
| `PURPOSEIDPDND` | — | — |
| `CHIAVE_PRIVATA` | — | — |

---

## 6. Tabella di raccolta

Tutti i parametri verranno inseriti nella verticalizzazione **WSANAGRAFE_ANPR**. Compila questa tabella durante la chiamata col cliente oppure inviagliela per la compilazione.

### Servizio C020 — Consultazione residenza

| # | Parametro | Valore | Note |
|---|---|---|---|
| 1 | `C020_AUDPDND` | | |
| 2 | `C020_AUDTOKENAGIDJWTSIGNATURE` | | |
| 3 | `C020_BASEURLAPI` | | Collaudo / Produzione |
| 4 | `C020_CHIAVE_PRIVATA` | *(file .pem via canale sicuro)* | Ricevuto: ☐ Sì ☐ No |
| 5 | `C020_CLIENTIDPDND` | | UUID |
| 6 | `C020_KIDPDND` | | |
| 7 | `C020_PURPOSEIDPDND` | | UUID |
| 8 | `C020_URLTOKENPDND` | | |

![](./immagini/vert_es.png)

### Servizio C030 — Consultazione ID univoco nazionale

| # | Parametro | Valore | Note |
|---|---|---|---|
| 1 | `C030_AUDPDND` | | |
| 2 | `C030_AUDTOKENAGIDJWTSIGNATURE` | | |
| 3 | `C030_BASEURLAPI` | | Collaudo / Produzione |
| 4 | `C030_CHIAVE_PRIVATA` | *(file .pem via canale sicuro)* | Ricevuto: ☐ Sì ☐ No |
| 5 | `C030_CLIENTIDPDND` | | UUID |
| 6 | `C030_KIDPDND` | | |
| 7 | `C030_PURPOSEIDPDND` | | UUID |
| 8 | `C030_URLTOKENPDND` | | |

### Parametri condivisi (se confermato dal cliente)

Se il cliente conferma che C020 e C030 condividono uno o più valori, segnalo qui per chiarezza:

| Parametro | Condiviso? | Conferma esplicita ricevuta? |
|---|---|---|
| `CLIENTIDPDND` | ☐ Sì ☐ No | ☐ |
| `KIDPDND` | ☐ Sì ☐ No | ☐ |
| `CHIAVE_PRIVATA` | ☐ Sì ☐ No | ☐ |
| `AUDPDND` | ☐ Sì ☐ No | ☐ |
| `URLTOKENPDND` | ☐ Sì ☐ No | ☐ |
| `AUDTOKENAGIDJWTSIGNATURE` | ☐ Sì ☐ No | ☐ |

---

## 7. Verifica post-raccolta

Prima di passare i dati al team tecnico, controlla:

- **Tutti i 16 parametri sono presenti** (8 per C020 + 8 per C030). Non procedere se ne manca anche solo uno.
- **Gli UUID hanno il formato corretto**: 8-4-4-4-12 caratteri esadecimali (es. `a1b2c3d4-e5f6-7890-abcd-ef1234567890`).
- **Gli URL sono coerenti con l'ambiente dichiarato**: se il cliente ha detto "collaudo", verifica che tutti gli URL contengano `uat`. Se ha detto "produzione", verifica che nessuno contenga `uat`.
- **Le chiavi private sono state ricevute via canale sicuro** e il file si apre correttamente (header `-----BEGIN RSA PRIVATE KEY-----`).
- **I parametri condivisi sono stati confermati esplicitamente**, non assunti.

---

## 8. Configurazione successiva

Una volta raccolti e verificati tutti i dati:

1. **Passa il pacchetto completo al team tecnico** per la configurazione nella verticalizzazione WSANAGRAFE_ANPR.
2. **Configurazione Docker**: consulta la guida dedicata → [WsAnagrafe Anpr](../../immagini-docker/wsanagrafe-anpr.md).
3. **Impostazione URL_RICERCA_PF**: nella verticalizzazione **WSANAGRAFE** sotto il software **TT**, imposta il campo `URL_RICERCA_PF` con:

```
http://HOST:PORTA/wsanagrafe-anpr/services/WsAnagrafe2?wsdl
```

Sostituisci `HOST` e `PORTA` con i valori dell'ambiente del cliente.

---

## 9. Errori comuni da evitare

| Errore | Conseguenza | Come prevenirlo |
|---|---|---|
| Assumere che C020 e C030 condividano la stessa chiave privata senza conferma | Configurazione non funzionante | Chiedere sempre conferma esplicita |
| Mescolare URL di collaudo e produzione | Autenticazione fallita, errori 401/403 | Verificare la coerenza di tutti gli URL con l'ambiente dichiarato |
| Ricevere la chiave privata via email in chiaro | Compromissione della chiave, rischio sicurezza | Concordare il canale sicuro prima della chiamata |
| Inserire la chiave privata già in Base64 | Doppia codifica, errore in fase di configurazione | Chiedere sempre il file `.pem` in chiaro |
| Procedere con parametri mancanti | Blocco in fase di configurazione tecnica | Usare la checklist di verifica al §7 |
| Confondere `PURPOSEIDPDND` tra C020 e C030 | Chiamate API rifiutate dalla PDND | Raccogliere i due `purposeId` separatamente anche se il client è lo stesso |
