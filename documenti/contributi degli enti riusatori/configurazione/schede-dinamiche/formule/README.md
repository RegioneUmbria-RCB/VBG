# Formule delle schede dinamiche

Questa documentazione descrive i metodi principali utilizzati per la scrittura di formule personalizzate all'interno delle schede dinamiche.

Ogni metodo include:

- La firma C# del metodo
- Una descrizione funzionale
- L’elenco dei parametri
- Il valore restituito
- Eventuali eccezioni sollevate

&gt; **Nota:** Questa documentazione è pensata per utenti che hanno familiarità con C# e il funzionamento delle schede dinamiche.

## Schede dinamiche

### ModelloCorrente.Salva()

```csharp
ModelloCorrente.Salva();
```

#### Parametri ModelloCorrente.Salva

- nessun parametro

#### Valore di ritorno ModelloCorrente.Salva

- nessun valore

#### Comportamento ModelloCorrente.Salva

- vengono invocate eventuali formule scritte nell'evento di salvataggio
- se il passo precedente non genera errori, viene invocato il salvataggio dei dati presenti nella  scheda dinamica
- non viene ricaricata la scheda dinamica visualizzata a video, se sono stati modificati valori tramite la formula prima di invocare il metodo, tali valori potrebbero non essere visualizzati dall'utente finale. Si consiglia l'utilizzo del metodo **SalvaEAggiornaInterfaccia()**

### ModelloCorrente.SalvaEAggiornaInterfaccia()

```csharp
ModelloCorrente.SalvaEAggiornaInterfaccia();
```

#### Parametri ModelloCorrente.SalvaEAggiornaInterfaccia

- nessun parametro

#### Valore di ritorno ModelloCorrente.SalvaEAggiornaInterfaccia

- nessun valore

#### Comportamento ModelloCorrente.SalvaEAggiornaInterfaccia

- viene invocato **ModelloCorrente.Salva()**
- se non ci sono errori, viene invocato **ModelloCorrente.RichiediReloadInterfaccia()**

### ModelloCorrente.RichiediReloadInterfaccia()

```csharp
ModelloCorrente.RichiediReloadInterfaccia();
```

#### Parametri ModelloCorrente.RichiediReloadInterfaccia

- nessun parametro

#### Valore di ritorno ModelloCorrente.RichiediReloadInterfaccia

- nessun valore

#### Comportamento ModelloCorrente.RichiediReloadInterfaccia

- forza il ricaricamento della scheda dinamica

### GetDecodificheAttive( string tabella )

```csharp
var settori = GetDecodificheAttive("ATECO");
```

#### Parametri GetDecodificheAttive( string tabella )

- **tabella** (string): Identificativo della tabella delle decodifiche (per maggiori informazioni consultare la gestione delle decodifiche)

#### Valore di ritorno GetDecodificheAttive( string tabella )

- `IEnumerable&lt;DecodificaDTO&gt;`: Elenco di oggetti di tipo DecodificaDTO.

    ```csharp
    public class DecodificaDTO
    {
        public string Idcomune { get; set; }
        public string Tabella { get; set; }
        public string Chiave { get; set; }
        public string Valore { get; set; }
        public bool FlgDisabilitato { get; set; }
        public string Raggruppamento { get; set; }
        public int? Ordine { get; set; }
    }
    ```

#### Comportamento GetDecodificheAttive( string tabella )

- Cerca tutte le righe associate alla decodifica passata; se non sono presenti righe torna un IEnumerable empty

### GetNumericFormat()

```csharp
var numFormat = GetNumericFormat();
var totale = 2.23d + 12;

TrovaCampo("TOTALE").ListaValori[0].Valore = totale.ToString(numFormat);
```

#### Parametri GetNumericFormat

- nessun parametro

#### Valore di ritorno GetNumericFormat

- System.Globalization.NumberFormatInfo che torna il formato da utilizzare, ad esempio, per trasformare i numeri in stringa ( virgola come separatore dei decimali e senza separatore delle migliaia )

#### Comportamento GetNumericFormat

```csharp
public static System.Globalization.NumberFormatInfo GetNumericFormat()
{
    var fi = new System.Globalization.NumberFormatInfo();
    fi.NumberDecimalSeparator = ",";
    fi.NumberGroupSeparator = "";

    return fi;
}
```

```csharp
```

## Campi dinamici

### TrovaCampo(string nomeCampo)

```csharp
var campoDinamico = TrovaCampo("MQ_SUP_ALIMENTARE");
```

#### Parametri TrovaCampo

- **nomeCampo** (string): Il nome del campo dinamico da cercare all'interno della scheda dinamica

#### Valore di ritorno TrovaCampo

- Oggetto di tipo **CampoDinamicoBase**

#### Comportamento TrovaCampo

- Se il campo non viene trovato ritorna **null**

### MostraCampoDinamico(string nomeCampo)

```csharp
MostraCampoDinamico("MQ_SUP_ALIMENTARE");
```

#### Parametri MostraCampoDinamico(string nomeCampo)

- **nomeCampo** (string): Il nome del campo dinamico, precedentemente nascosto, da rendere visibile all'interno della scheda dinamica

#### Valore di ritorno MostraCampoDinamico(string nomeCampo)

- nessun valore

#### Comportamento MostraCampoDinamico(string nomeCampo)

- Imposta il campo dinamico, precedentemente nascosto, visibile all'interno della scheda dinamica

### MostraCampoDinamico(string nomeCampo, int indiceMolteplicita)

```csharp
MostraCampoDinamico("MQ_SUP_ALIMENTARE",0);
```

#### Parametri MostraCampoDinamico(string nomeCampo, int indiceMolteplicita)

- **nomeCampo** (string): Il nome del campo dinamico, precedentemente nascosto, da rendere visibile all'interno della scheda dinamica
- **indiceMolteplicita** (int): Indice che identifica il campo dinamico all'interno di un blocco multiplo

#### Valore di ritorno MostraCampoDinamico(string nomeCampo, int indiceMolteplicita)

- nessun valore

#### Comportamento MostraCampoDinamico(string nomeCampo, int indiceMolteplicita)

- Stesso comportamento di **MostraCampoDinamico(string nomeCampo)** solamente che non agisce su tutte le occorrenze del campo ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0 verrà mostrato solamente il campo dinamico riguardante il primo blocco multiplo

### MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

```csharp
var campi = new [] {"MQ_SUP_ALIMENTARE","MQ_SUP_NON_ALIMENTARE"};

MostraCampiDinamici(campi);
```

#### Parametri MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

- **nomiCampi** (IEnumerable&lt;string&gt;): Elenco dei nomi dei campi dinamici, precedentemente nascosti, da rendere visibili all'interno della scheda dinamica

#### Valore di ritorno MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

- nessun valore

#### Comportamento MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

- Stesso comportamento di **MostraCampoDinamico(string nomeCampo)** solo che agisce su una lista di campi dinamici

### MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

```csharp
var campi = new [] {"MQ_SUP_ALIMENTARE","MQ_SUP_NON_ALIMENTARE"};

MostraCampiDinamici(campi, 0);
```

#### Parametri MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

- **nomiCampi** (IEnumerable&lt;string&gt;): Elenco dei nomi dei campi dinamici, precedentemente nascosti, da rendere visibili all'interno della scheda dinamica
- **indiceMolteplicita** (int): Indice che identifica i campi dinamici all'interno di un blocco multiplo

#### Valore di ritorno MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

- nessun valore

#### Comportamento MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

- Stesso comportamento di **MostraCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)** solamente che non agisce su tutte le occorrenze dei campi ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0 verranno mostrati solamente i campi dinamici riguardanti il primo blocco multiplo

### NascondiCampoDinamico(string nomeCampo)

```csharp
NascondiCampoDinamico("MQ_SUP_ALIMENTARE");
```

#### Parametri NascondiCampoDinamico(string nomeCampo)

- **nomeCampo** (string): Il nome del campo dinamico da nascondere all'interno della scheda dinamica

#### Valore di ritorno NascondiCampoDinamico(string nomeCampo)

- nessun valore

#### Comportamento NascondiCampoDinamico(string nomeCampo)

- Svuota il valore del campo dinamico
- Nasconde il campo dinamico all'interno della scheda dinamica

### NascondiCampoDinamico(string nomeCampo, int indiceMolteplicita)

```csharp
NascondiCampoDinamico("MQ_SUP_ALIMENTARE", 0);
```

#### Parametri NascondiCampoDinamico(string nomeCampo, int indiceMolteplicita)

- **nomeCampo** (string): Il nome del campo dinamico da nascondere all'interno della scheda dinamica
- **indiceMolteplicita** (int): Indice che identifica il campo dinamico all'interno di un blocco multiplo

#### Valore di ritorno NascondiCampoDinamico(string nomeCampo, int indiceMolteplicita)

- nessun valore

#### Comportamento NascondiCampoDinamico(string nomeCampo, int indiceMolteplicita)

- Stesso comportamento di **NascondiCampoDinamico(string nomeCampo)** solamente che non agisce su tutte le occorrenze del campo ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0 verrà nascosto solamente il campo dinamico riguardante il primo blocco multiplo

### NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

```csharp
var campi = new [] {"MQ_SUP_ALIMENTARE","MQ_SUP_NON_ALIMENTARE"};

NascondiCampiDinamici(campi);
```

#### Parametri NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

- **nomiCampi** (IEnumerable&lt;string&gt;): Elenco dei nomi dei campi dinamici da nascondere all'interno della scheda dinamica

#### Valore di ritorno NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

- nessun valore

#### Comportamento NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)

- Stesso comportamento di **NascondiCampoDinamico(string nomeCampo)** solo che agisce su una lista di campi dinamici

### NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

```csharp
var campi = new [] {"MQ_SUP_ALIMENTARE","MQ_SUP_NON_ALIMENTARE"};

NascondiCampiDinamici(campi,0);
```

#### Parametri NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

- **nomiCampi** (IEnumerable&lt;string&gt;): Elenco dei nomi dei campi dinamici da nascondere all'interno della scheda dinamica
- **indiceMolteplicita** (int): Indice che identifica i campi dinamici all'interno di un blocco multiplo

#### Valore di ritorno NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

- nessun valore

#### Comportamento NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi, int indiceMolteplicita)

- Stesso comportamento di **NascondiCampiDinamici(IEnumerable&lt;string&gt; nomiCampi)** solamente che non agisce su tutte le occorrenze dei campi ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0 verranno nascosti solamente i campi dinamici riguardanti il primo blocco multiplo

### MostraCampoTestuale(int idCampo)

```csharp
MostraCampoTestuale(125);
```

#### Parametri MostraCampoTestuale(int idCampo)

- **idCampo** (int): Identificativo della riga che contiene il campo di tipo **testo**

#### Valore di ritorno MostraCampoTestuale(int idCampo)

- nessun valore

#### Comportamento MostraCampoTestuale(int idCampo)

- Mostra la riga contenente il campo di tipo testo

### MostraCampoTestuale(int idCampo, int indiceMolteplicita)

```csharp
MostraCampoTestuale(125,0);
```

#### Parametri MostraCampoTestuale(int idCampo, int indiceMolteplicita)

- **idCampo** (int): Identificativo della riga che contiene il campo di tipo **testo**
- **indiceMolteplicita** (int): Indice che identifica il campo dinamico all'interno di un blocco multiplo

#### Valore di ritorno MostraCampoTestuale(int idCampo, int indiceMolteplicita)

- nessun valore

#### Comportamento MostraCampoTestuale(int idCampo, int indiceMolteplicita)

- Stesso comportamento di **MostraCampoTestuale(int idCampo)** solamente che non agisce su tutte le occorrenze del campo ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0, l'operazione riguarderà solamente gli elementi del primo blocco multiplo

### MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi)

```csharp
var campi = new [] {125,126};

MostraCampiTestuali(campi);
```

#### Parametri MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi)

- idCampi (IEnumerable&lt;int&gt;): Identificativi delle righe che contengono i campi dinamici di tipo testo

#### Valore di ritorno MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi)

- nessun valore

#### Comportamento MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi)

- Stesso comportamento di MostraCampoTestuale(int idCampo) solo che agisce su una lista di identificativi

### MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

```csharp
var campi = new [] {125,126};

MostraCampiTestuali(campi,0);
```

#### Parametri MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

- idCampi (IEnumerable&lt;int&gt;): Identificativi delle righe che contengono i campi dinamici di tipo testo
- **indiceMolteplicita** (int): Indice che identifica il campo dinamico all'interno di un blocco multiplo

#### Valore di ritorno MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

- nessun valore

#### Comportamento MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

- Stesso comportamento di **MostraCampiTestuali(IEnumerable&lt;int&gt; idCampi)** solamente che non agisce su tutte le occorrenze del campo ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0, la visualizzazione riguarderà solamente gli elementi del primo blocco multiplo

### NascondiCampoTestuale(int idCampo)

```csharp
NascondiCampoTestuale(125);
```

#### Parametri NascondiCampoTestuale(int idCampo)

- **idCampo** (int): Identificativo della riga che contiene il campo di tipo **testo**

#### Valore di ritorno NascondiCampoTestuale(int idCampo)

- nessun valore

#### Comportamento NascondiCampoTestuale(int idCampo)

- Nasconde la riga contenente il campo di tipo testo

### NascondiCampoTestuale(int idCampo, int indiceMolteplicita)

```csharp
NascondiCampoTestuale(125, 0);
```

#### Parametri NascondiCampoTestuale(int idCampo, int indiceMolteplicita)

- **idCampo** (int): Identificativo della riga che contiene il campo di tipo **testo**
- **indiceMolteplicita** (int): Indice che identifica il campo dinamico all'interno di un blocco multiplo

#### Valore di ritorno NascondiCampoTestuale(int idCampo, int indiceMolteplicita)

- nessun valore

#### Comportamento NascondiCampoTestuale(int idCampo, int indiceMolteplicita)

- Stesso comportamento di **NascondiCampoTestuale(int idCampo)** solamente che non agisce su tutte le occorrenze del campo ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0, l'operazione riguarderà solamente gli elementi del primo blocco multiplo

### NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi)

```csharp
var campi = new [] {125,126};

NascondiCampiTestuali(campi);
```

#### Parametri NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi)

- idCampi (IEnumerable&lt;int&gt;): Identificativi delle righe che contengono i campi dinamici di tipo testo

#### Valore di ritorno NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi)

- nessun valore

#### Comportamento NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi)

- Stesso comportamento di **NascondiCampoTestuale(int idCampo)** solo che agisce su una lista di identificativi

### NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

```csharp
var campi = new [] {125,126};

NascondiCampiTestuali(campi,0);
```

#### Parametri NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

- idCampi (IEnumerable&lt;int&gt;): Identificativi delle righe che contengono i campi dinamici di tipo testo
- **indiceMolteplicita** (int): Indice che identifica il campo dinamico all'interno di un blocco multiplo

#### Valore di ritorno NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

- nessun valore

#### Comportamento NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi, int indiceMolteplicita)

- Stesso comportamento di **NascondiCampiTestuali(IEnumerable&lt;int&gt; idCampi)** solamente che non agisce su tutte le occorrenze del campo ma solo su quella dell'indice specificato. Ad esempio se è presente un blocco multiplo valorizzato 3 volte, passando come **indiceMolteplicita** il valore 0, l'operazione riguarderà solamente gli elementi del primo blocco multiplo

### ErroreValidazioneCampo(string messaggio, CampoDinamicoBase campo, int indice = 0)

```csharp
```

#### Parametri ErroreValidazioneCampo(string messaggio, CampoDinamicoBase campo, int indice = 0)

#### Valore di ritorno ErroreValidazioneCampo(string messaggio, CampoDinamicoBase campo, int indice = 0)

#### Comportamento ErroreValidazioneCampo(string messaggio, CampoDinamicoBase campo, int indice = 0)

### CampoModificato

```csharp
```

#### Parametri CampoModificato

#### Valore di ritorno CampoModificato

#### Comportamento CampoModificato

## Direttive di compilazione

### if BACKOFFICE

```csharp
```

### if FRONTOFFICE

```csharp
```

### if AREA_PERSONALE

```csharp
```
