# Build e avvio dell'applicazione

## Avvio con Tomcat normale (consigliato)

Senza usare IntelliJ per il run di Tomcat:

1. **Avvia Tomcat** con `startup.bat` dalla cartella `bin` di Tomcat (es. `C:\Program Files\Apache Software Foundation\Tomcat 10.1\bin\startup.bat`) oppure dal monitor/GUI di Tomcat.
2. **Build e deploy:** doppio clic su **`run-with-maven-war.bat`** nella root del progetto. Lo script compila con Maven e copia il WAR in `webapps` di Tomcat.
3. Se la copia va in errore (es. accesso negato a `Program Files`), esegui il batch **tasto destro → Esegui come amministratore**, oppure imposta la variabile d’ambiente `TOMCAT_WEBAPPS` su una cartella in cui puoi scrivere (es. `C:\tomcat\webapps`) prima di lanciare lo script.
4. Apri **http://localhost:8080/vbg-legacy-dss-webapp/** (porta 8080 = default Tomcat).

Tomcat deploya in automatico i WAR presenti in `webapps` all’avvio; non serve configurare nulla in IntelliJ.

---

## Rinnovo applicazione (Java 17, Spring 6, DSS 6.x, Jakarta)

L’applicazione è stata portata a **Java 17**, **Spring 6**, **Tomcat 10 (Jakarta EE)** e **DSS 6.x**. In sintesi:

- **Perché non usiamo più le lib in `WebContent/WEB-INF/lib`:** contenevano Spring 3, `javax.servlet` e altre JAR legacy. Con Tomcat 10 (solo Jakarta) il deploy falliva con `ClassNotFoundException: javax.servlet.ServletContextListener`. Nel `pom.xml` è stato configurato `warSourceExcludes=WEB-INF/lib/**` così nel WAR entrano **solo** le dipendenze Maven (Spring 6, Jakarta, DSS 6).
- **Perché compaiono errori “No implementation found for X”:** in DSS 6.x diverse funzionalità sono **opzionali** e richiedono di aggiungere esplicitamente un modulo di implementazione. Se manca, a runtime esce “No implementation found for X”. Nel `pom.xml` sono state aggiunte le dipendenze necessarie:
  - **IUtils** → `dss-utils-apache-commons`
  - **IPdfObjFactory** (PAdES/PDF) → `dss-pades-pdfbox`
  - **ICMSUtils** (CAdES/CMS) → `dss-cms-object` (oppure `dss-cms-stream`)

Se in futuro comparisse un altro “No implementation found for Y”, aggiungere il modulo DSS indicato nel messaggio come dipendenza nel `pom.xml` e rifare build/deploy.

**Verifica preventiva:** eseguire `mvn test` prima di deployare. Il test **`DssImplementationsAvailabilityTest`** (in `test/java`) verifica che IUtils, ICMSUtils e IPdfObjFactory siano presenti; se manca un modulo il test fallisce. Lo script `run-with-maven-war.bat` esegue i test durante la build (per saltarli: `set SKIP_TESTS=1` prima di lanciarlo).

---

# Build in IntelliJ IDEA

Il progetto contiene codice legacy (`eu.europa.ec.markt.dss`, `com.lowagie`, `it.gruppoinit.dss.http`) che **non** viene compilato da Maven (esclusioni nel `pom.xml`). IntelliJ invece, di default, compila tutto il contenuto di `src`, generando circa 100 errori.

## Soluzioni

### 1. Esclusioni compilatore (già configurate)

In `.idea/compiler.xml` e in `dss-webapp.iml` sono state aggiunte le stesse esclusioni usate da Maven. Dopo aver riaperto il progetto o fatto **File → Invalidate Caches → Invalidate and Restart**, la build da IntelliJ dovrebbe andare a buon fine.

### 2. Delegare la build a Maven (alternativa sicura)

Se gli errori di compilazione restano:

1. **File → Settings** (o **IntelliJ IDEA → Preferences** su macOS)
2. **Build, Execution, Deployment → Build Tools → Maven → Runner**
3. Attiva **"Delegate IDE build/run actions to Maven"**

Da quel momento IntelliJ userà Maven per compilare (come da `pom.xml`), quindi le esclusioni del compiler plugin verranno rispettate e la build andrà a buon fine.

### Verifica da riga di comando

```bash
mvn compile
```

La compilazione Maven deve sempre riuscire; se fallisce, il problema non è legato a IntelliJ.

---

## Deploy Tomcat e errore "One or more listeners failed to start"

Se il contesto web non si avvia e nei log compare solo *"One or more listeners failed to start"*:

1. **Vedere l'eccezione reale**  
   Apri il file di log di Tomcat per la data corrente, es.:  
   `%USERPROFILE%\AppData\Local\JetBrains\IntelliJIdea2026.1\tomcat\<id-server>\logs\localhost*.log`  
   (oppure in **CATALINA_BASE** indicato all'avvio: `...\tomcat\...\logs\`).  
   Cerca la prima eccezione Java (es. `BeanCreationException`, `ClassNotFoundException`) subito prima del messaggio sui listener.

2. **Far usare all'artifact i file aggiornati**  
   Dopo aver modificato `src/applicationContext.xml` (o altre risorse), esegui **Build → Rebuild Project** prima di ridistribuire l'artifact **war exploded**. In alternativa: **Run → Edit Configurations** → seleziona la run Tomcat → scheda **Deployment** → verifica che l'artifact punti al modulo corretto e che il modulo usi l'output di build aggiornato.

3. **Verificare con il WAR prodotto da Maven**  
   Da riga di comando: `mvn package`  
   Poi copia `target\vbg-legacy-dss-webapp-2.0.0-dss6.war` nella cartella `webapps` di Tomcat (o usala dalla run configuration come artifact esterno). Se il WAR da Maven si avvia, il problema è nella composizione dell'artifact "war exploded" in IntelliJ.

---

## Errore: ClassNotFoundException javax.servlet.ServletContextListener

Con **Tomcat 10** (Jakarta EE) questo errore indica che nell'artifact sono finite librerie che usano ancora **javax.servlet** (Java EE). Il modulo aveva riferimenti a librerie Eclipse J2EE / Tomcat 6; sono stati rimossi da `dss-webapp.iml`.

**Soluzione rapida (consigliata): usare il WAR di Maven**

1. Doppio clic su **`run-with-maven-war.bat`** nella root del progetto (build Maven + copia WAR nella `webapps` della CATALINA_BASE usata da IntelliJ).
2. Nella run **Tomcat 10 - DSS** la scheda **Deployment** deve essere **vuota** (nessun artifact), così Tomcat deploya solo il WAR da `webapps`.
3. Avvia Tomcat da IntelliJ, poi apri **http://localhost:8091/vbg-legacy-dss-webapp/** (non la root: `http://localhost:8091/` non ha app ROOT e dà 404).

Il WAR prodotto da Maven contiene solo dipendenze Jakarta (nessun javax), quindi il contesto parte correttamente.

**Per sistemare l'artifact in IntelliJ:** **File → Project Structure → Artifacts** → seleziona **vbg-legacy-dss-webapp:war exploded**. In **Output Layout** in WEB-INF/lib devono esserci solo dipendenze Maven (Spring 6, Jakarta), nessuna "Eclipse J2EE" o "Tomcat 6". Se l'artifact non è dal modulo Maven, eliminarlo e ricrearlo con **+ → Web Application: Exploded → From Modules** scegliendo il modulo **vbg-legacy-dss-webapp**. Poi **Maven → Reload All Maven Projects**.

---

## Perché localhost:8091 dà 404? E perché /vbg-legacy-dss-webapp/ dava 404?

1. **`http://localhost:8091/` (root) dà 404**  
   La run Tomcat di IntelliJ usa una **CATALINA_BASE** dedicata (cartella in `AppData\...\JetBrains\...\tomcat\<id>\`). Lì non c’è un’applicazione **ROOT**: la cartella `webapps` contiene solo ciò che copi tu (es. il WAR). Quindi sulla root non c’è nessuna app → 404 è normale. L’app è su **`http://localhost:8091/vbg-legacy-dss-webapp/`**.

2. **`http://localhost:8091/vbg-legacy-dss-webapp/` dava 404**  
   In `conf/server.xml` di quella CATALINA_BASE erano impostati:
   - **`deployOnStartup="false"`** → Tomcat non deployava nulla da `webapps` all’avvio.
   - **`deployIgnore="^(?!(manager)|(tomee)$).*"`** → venivano considerate solo le app `manager` e `tomee`; il nostro WAR veniva ignorato.  
   In più, **`appBase`** puntava alla cartella `webapps` di Tomcat in Program Files, non a quella in AppData, quindi il WAR copiato nello script non veniva mai usato.

**Modifiche applicate (nella tua installazione):**
- In **`conf/server.xml`** della run IntelliJ: `appBase="webapps"` (relativo a CATALINA_BASE), `deployOnStartup="true"`, `deployIgnore=""`.
- Lo script **`run-with-maven-war.bat`** copia il WAR in quella cartella `webapps` (sotto `...\tomcat\<id>\webapps\`).

**Importante:** nella run **Tomcat 10 - DSS** la scheda **Deployment** deve essere **vuota** (nessun artifact “war exploded”). Altrimenti IntelliJ deploya via RMI un artifact con `javax.servlet` e il contesto fallisce; l’unica app che deve partire è il WAR da `webapps`. Controlla: **Run → Edit Configurations** → **Tomcat 10 - DSS** → **Deployment** → rimuovi eventuali voci (es. “vbg-legacy-dss-webapp:war exploded”).
