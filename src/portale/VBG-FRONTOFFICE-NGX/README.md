# Frontoffice NGX

## Prerequisiti

Per poter compilare il progetto è necessario installare Node.js all'ultima versione stabile, questo permette di avere npm disponibile nel sistema.
I pacchetti npm globali utilizzati dal progetto sono:

- [Angular cli](https://cli.angular.io/): npm install -g @angular/cli
- [Less](http://lesscss.org/): npm install -g less
- [Less Watch Compiler](https://www.npmjs.com/package/less-watch-compiler): npm install -g less-watch-compiler
- impostare il file settings.json in questo modo (si trova nella cartella .vscode, se non esiste può essere creato)

```json
{
    "files.exclude": {
        "**/.git": true,
        "**/.svn": true,
        "**/.hg": true,
        "**/CVS": true,
        "**/.DS_Store": true,
        "**/*.js": {"when": "$(basename).ts"},
        "**/*.css": {"when": "$(basename).css"}
    },
    "search.exclude": {
        "**/dist": true
    },
    // The number of spaces a tab is equal to. This setting is overridden
    // based on the file contents when `editor.detectIndentation` is true.
    "editor.tabSize": 4,
    // Insert spaces when pressing Tab. This setting is overriden
    // based on the file contents when `editor.detectIndentation` is true.
    "editor.insertSpaces": true,
    // When opening a file, `editor.tabSize` and `editor.insertSpaces`
    // will be detected based on the file contents. Set to false to keep
    // the values you've explicitly set, above.
    "editor.detectIndentation": false
}
```

## Primo avvio

Dopo aver scaricato il progetto eseguire i seguenti comandi:

`npm install`

Avviare il progetto in locale con

`ng serve`

Navigare con il browser all'indirizzo <http://localhost:4200/index/E256/SS> (comune di Gubbio, stile default)

## Deploy in server farm

In server farm l'applicazione è deployata sulla macchina nginx (212.104.15.12) sul percorso /var/www/frontoffice-ngx

Eseguire il build con il comando

`ng build --configuration production --base-href /--APPLICATION_HOME--/`

E creare una regola in nginx che effettui la sostituzione del testo /--APPLICATION_HOME--/ con il nome della location configurata alliinterno di nginx

Ed esempio:

```text
 location /campibisenzio/ {
        alias /var/www/html/frontoffice-ngx/;
        try_files $uri $uri/ /campibisenzio/index.html;
        # kill cache
        add_header Last-Modified $date_gmt;
        add_header Cache-Control 'no-store, no-cache, must-revalidate, proxy-revalidate, max-age=0';
        if_modified_since off;
        expires off;
        etag off;
        sub_filter /--APPLICATION_HOME--/ /campibisenzio/;
        sub_filter_once off;
}

```

## Build

Per aggiornare i files css dei temi che non sono tracciati da angular usare il comando

- npm run build-less-once

se si vogliono tracciare (watch) le modifiche ai css usare il comando

- build-less-watch

## Deploy su iis

La guida è disponibile all'indirizzo:

<https://blogs.msdn.microsoft.com/premier_developer/2017/06/14/tips-for-running-an-angular-app-in-iis/>

Fondamentalmente occorre lanciare il comando

`npm run build-prod`

Una volta preparati i files di installazione occorre creare una virtual directory sotto IIS.

### Creazione di una pagina asp per la gestione della root della directory

Occorre creare una pagina **default.asp** da posizionare nella root della cartella in modo che venga servita quando viene richiasta la root della virtual appplication di iis.
La pagina asp deve contenere il seguente testo (sostituire **DEF** e **SS** con i valori giusti per l'installazione)

```asp
    <%@ language=VBScript%>
    <%
        response.redirect "index/DEF/SS/"
        'response.write "default.asp"
    %>
```

### Modifiche al web.config per supportare il deep linking

Per permettere il deep linking ed evitare errori di tipo 404 quando si chiamano direttamente pagine dell'applicazione
senza passare per la home è necessario installare il package "url-rewrite" da <https://www.iis.net/downloads/microsoft/url-rewrite>

Dopo aver configurato l'url-rewrite è necessario creare un nuovo web.config nella root dell'applcazione che
permetta il redirect di url non esistenti. Un esempio di web.config è:

```xml
<configuration>
<system.webServer>
  <rewrite>
    <rules>
      <rule name="Angular Routes" stopProcessing="true">
        <match url=REDACTED
        <conditions logicalGrouping="MatchAll">
            <add input="{REQUEST_FILENAME}" matchType="IsFile" negate="true" />
            <add input="{REQUEST_FILENAME}" matchType="IsDirectory" negate="true" />
        </conditions>
        <action type="Rewrite" url=REDACTED
      </rule>
    </rules>
  </rewrite>
</system.webServer>
</configuration>
```

Per altre informazioni consultare la guida all'indirizzo

[https://blogs.msdn.microsoft.com/premier_developer/2017/06/14/tips-for-running-an-angular-app-in-iis/](https://blogs.msdn.microsoft.com/premier_developer/2017/06/14/tips-for-running-an-angular-app-in-iis/)
