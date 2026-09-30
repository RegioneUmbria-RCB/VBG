# Prova in locale (senza Eclipse)

Con **Tomcat 10.1** già installato sul PC.

## 1. Build del WAR

Dal directory del progetto:

```bash
mvn clean package
```

Il WAR viene generato in:  
`target/vbg-legacy-dss-webapp.war`

## 2. Deploy su Tomcat 10.1

- Copia `target/vbg-legacy-dss-webapp.war` nella cartella **webapps** di Tomcat (es. `CATALINA_HOME/webapps/`).
- Avvia Tomcat (se non è già avviato).
- L’applicazione è disponibile al context **/vbg-legacy-dss-webapp** (nome del WAR senza `.war`).

## 3. URL utili

| Cosa | URL (adatta host/porta al tuo Tomcat) |
|------|----------------------------------------|
| Home | http://localhost:8080/vbg-legacy-dss-webapp/ |
| Verifica firma (upload) | http://localhost:8080/vbg-legacy-dss-webapp/validazione |
| WSDL SOAP validazione | http://localhost:8080/vbg-legacy-dss-webapp/wservice/validation?wsdl |

Apri il WSDL nel browser e verifica che ci sia il servizio **ValidationService** nel namespace `http://impl.ws.dss.markt.ec.europa.eu/`.
