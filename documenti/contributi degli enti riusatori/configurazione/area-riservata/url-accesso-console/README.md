# Url accesso console

E' possibile configurare l'indirizzo tramite cui un utente può passare dall'area riservata nei due scenari possibili:

- Comune singolo che accede ad una sola console (o associazione di comuni che accede ad una singola console)
  In questo scenario l'installazione locale è configurata per un singolo comune e l'utente può acedere alla sola console configurata per quel comune

- Installazione multicomune che accede a console multiple  
  In questo scenario si parte da un'installazione multicomune per passare ad una console per ciascun comune dell'associazione (nella console deve essere registrata una riga per ciascun comune dell'associazione in SDEPROXY)

## Configurazione di base dell'area riservata (o associazione di comuni che accede ad una singola console)

Indipendentemente dallo scenario in cui si sta lavorando è necessario aggiungere all'area riservata (tramite menu.xml) le seguenti voci di menù che permettono di richiamare la pagina che gestisce l'accesso alla console (ovviamente i testi vanno adattati in base alle esigenze).

```
		<MenuItem icona-bs="open-file">
          <Titolo>Nuova pratica (CONSOLE)</Titolo>
          <Descrizione><![CDATA[permette la presentazione di Domande, SCIA e Comunicazioni relative a procedimenti la cui modulistica <b>UNIFICATA</b> è stata definita a livello nazionale e regionale]]></Descrizione>
          <Url>~/reserved/vbg-nuova-domanda.aspx</Url>
        </MenuItem>

        <MenuItem icona-bs="time">
          <Titolo>Pratiche in sospeso (CONSOLE)</Titolo>
          <Descrizione>Recupero domande non ancora presentate dalla console</Descrizione>
          <Url>~/Reserved/vbg-istanze-in-sospeso.aspx</Url>
        </MenuItem>
```

## Comune singolo che accede ad una sola console

Nello scenario più semplice è possibile configurare l'url di accesso alla console tramite i seguenti parametri della verticalizzazione **AREA_RISERVATA**

- **ASMART_CROSS_LOGIN_URL**  
  Url da chiamare per effettuare l'operazione di cross login (es. http://devel3.init.gruppoinit.it/ibcauthenticationgateway/crossloginute?idcomunealias=CE256&authlevel=1)
- **ASMART_URL_ISTANZE_IN_SOSPESO**  
  Url per accedere alle istanze in sospeso (es. http://devel3.init.gruppoinit.it/fo-console/reserved/IstanzeInSospeso.aspx?idcomune=CE256&software=SS)
- **ASMART_URL_NUOVA_DOMANDA**  
  Url di da cui iniziare il processo di presentazione di una nuova domanda (es. http://devel3.init.gruppoinit.it/fo-console/reserved/NuovaIstanza.aspx?idcomune=CE256&software=SS)

In questo caso l'utente verrà rediretto alla pagina desiderata della console una volta fatto click sulla corrispondente voce di menu ("Nuova pratica console" o "Pratiche in sospeso console") 

> Attenzione! Questa modalità potrebbe essere deprecata in futuro in favore del metodo seguente



## Installazione multicomune che accede a console multiple 

In questo caso più complesso si parte da un'area riservata multicomune per andare a finire su una console che ha una configurazione per ciascun comune dell'associazione di partenza.

Dopo aver configurato i comuni desiderati nella console occorre agire sulla verticalizzazione **AR_URL_SERVIZI_CONSOLE**. La verticalizzazione va attivata **per ciascun comune** su cui si intende acedere (per i comuni non configurati non verrà consentito l'accesso alla console).

I parametri di configurazione localizzati per comune sono:

- **CROSS_LOGIN_URL**  
  Url da chiamare per effettuare l'operazione di cross login (es. http://devel3.init.gruppoinit.it/ibcauthenticationgateway/crossloginute?idcomunealias=CE256&authlevel=1)
- **URL_ISTANZE_IN_SOSPESO**  
  Url per accedere alle istanze in sospeso (es. http://devel3.init.gruppoinit.it/fo-console/reserved/IstanzeInSospeso.aspx?idcomune=CE256&software=SS)
- **URL_NUOVA_DOMANDA**  
  Url di da cui iniziare il processo di presentazione di una nuova domanda (es. http://devel3.init.gruppoinit.it/fo-console/reserved/NuovaIstanza.aspx?idcomune=CE256&software=SS)

Attivando questa configurazione l'utente visualizzerà una pagina di intermezzo in cui sono elencati tutti i comuni associati con il relativo link per accedere alla console.

Nel caso in cui un comune non abbia la console configurata a fianco del suo nome verrà mostrato il testo "servizio non ancora attivo". 

> Questa modalità di configurazione permette di configurare anche un'installazione monocomune e l'utente verrebbe rediretto alla console senza possibilità di visualizzare la pagina di intermezzo nel caso in cui nella verticazlizzazione venisse trovato un solo comune. Probabilmente questa sarà la soluzione verso cui si andrà a tendere nel futuro anche per installazioni singole