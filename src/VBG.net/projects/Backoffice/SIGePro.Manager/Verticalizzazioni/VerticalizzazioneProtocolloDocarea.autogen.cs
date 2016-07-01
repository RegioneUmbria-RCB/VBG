
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_DOCAREA il 26/08/2014 17.28.02
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// DOCAREA è lo standard utilizzato da più software di protocollo per effettuare le integrazioni, si tratta di creare una segnatura (segnatura.xml) da inviare al web service. Gli Standard DOCAREA sono utilizzati ad esempio dal protocollo GS4 di ADS (Comune di Piacenza, Comune di Castel Fiorentino...) e non hanno possibilità di lettura dei dati, l'unica funzionalità è quella di inserimento protocollo.
				/// </summary>
				public partial class VerticalizzazioneProtocolloDocarea : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_DOCAREA";
                    public VerticalizzazioneProtocolloDocarea()
                    {

                    }
					public VerticalizzazioneProtocolloDocarea(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// (Obbligatorio) E' l'operatore da utilizzare per protocollare con DOCAREA, deve essere fornito dall'amministratore del protocollo DOCAREA.
					/// </summary>
					public string Operatore
					{
						get{ return GetString("OPERATORE");}
						set{ SetString("OPERATORE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' l'URL per invocare il protocollo. Non devono essere specificati i metodi.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' la password per protocollare in DOCAREA, deve essere fornita dall'amministratore del protocollo DOCAREA.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' il codice ente per il quale protocollare, deve essere fornito dall'amministratore del protocollo DOCAREA.
					/// </summary>
					public string Codiceente
					{
						get{ return GetString("CODICEENTE");}
						set{ SetString("CODICEENTE" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Se valorizzato a 1 va a cercare in automatico, solamente durante la protocollazione da istanza, 
/// gli allegati caricati nel movimento di avvio dell'istanza stessa, se esistono questi vengono inviati al protocollo DOCAREA.
/// Se valorizzato con valore diverso da 1 o non valorizzato la funzionalità appena descritta non sarà svolta.
					/// </summary>
					public string InviaAllMovAvvio
					{
						get{ return GetString("INVIA_ALL_MOV_AVVIO");}
						set{ SetString("INVIA_ALL_MOV_AVVIO" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Se valorizzato a 1 consente, in fase di protocollazione istanza o movimento da backoffice, di inviare il file segnatura.xml che sarebbe il file che viene inviato al web service per eseguire la protocollazione; se valorizzato a 0 o non valorizzato non esegue nessuna funzionalità.
					/// </summary>
					public string InviaSegnatura
					{
						get{ return GetString("INVIA_SEGNATURA");}
						set{ SetString("INVIA_SEGNATURA" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) E' il codice dell'Area Organizzativa Omogenea, va fornito dagli amministratori del protocollo. 
/// In precedenza veniva passato un parametro fisso con valore = 'AOO' è stato modificato in quanto 
/// in alcuni casi (ad esempio Piacenza e Asp Roma) veniva richiesto un valore diverso. Sulle specifiche DOCAREA viene descritto come: 
/// viene inizializzato con un valore che identifica l'ambito dell'applicazione.
/// Ad esempio questo codice potrebbe essere utilizzato,
/// per individuare i messaggi provenienti dal portale.
/// Ovviamente si puo’ scegliere come nel caso a) di usare una codifica del tipo P_X dove X e’ il nome dell’applicazione chiamante
/// .
/// Questo valore viene inserito nel file segnatura.xml dentro 
/// Intestazione-->Identificatore-->CodiceAOO
///  e dentro 
/// Intestazione -->Classifica -->CodiceAOO
/// Se non valorizzato il parametro prenderà il valore di AOO
					/// </summary>
					public string CodiceAoo
					{
						get{ return GetString("CODICE_AOO");}
						set{ SetString("CODICE_AOO" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Specificare il valore del tipo documento allegato ossia quei documenti allegati al protocollo escluso quello principale.
/// Se non valorizzato prenderà il valore = 'Allegato' che era il valore fisso che veniva passato in precedenza. 
/// Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento successivamente a quello principale.
					/// </summary>
					public string TipoDocumentoAllegato
					{
						get{ return GetString("TIPO_DOCUMENTO_ALLEGATO");}
						set{ SetString("TIPO_DOCUMENTO_ALLEGATO" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Specificare il valore del tipo documento principale. ossia quel documento principale o di richiesta del protocollo.
/// Se non valorizzato prenderà il valore = 'Principale' che era il valore fisso che veniva passato in precedenza. 
/// E' stato parametrizzato perchè a Piacenza viene richiesto un valore specifico che è 'TRAS'.
/// Viene valorizzato nel file segnatura.xml dentro Intestazione --> Descrizione --> Documento --> TipoDocumento
					/// </summary>
					public string TipoDocumentoPrincipale
					{
						get{ return GetString("TIPO_DOCUMENTO_PRINCIPALE");}
						set{ SetString("TIPO_DOCUMENTO_PRINCIPALE" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Indica il nome dell'applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l'attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE
					/// </summary>
					public string ApplicativoProtocollo
					{
						get{ return GetString("APPLICATIVO_PROTOCOLLO");}
						set{ SetString("APPLICATIVO_PROTOCOLLO" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Indica l'ufficio di smistamento del protocollo DOCAREA, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.
					/// </summary>
					public string Uo
					{
						get{ return GetString("UO");}
						set{ SetString("UO" , value); }
					}
					
					/// <summary>
					/// Indicare in questo parametro il Codice dell'amministrazione, questo dato andrà a valorizzare gli elementi CODICEAMMINISTRAZIONE della segnatura che viene inviata al metodo protocollazione del web service. Se non valorizzato questo dato verrà preso dal parametro CODICEENTE come succedeva in passato.
					/// </summary>
					public string Codiceamministrazione
					{
						get{ return GetString("CODICEAMMINISTRAZIONE");}
						set{ SetString("CODICEAMMINISTRAZIONE" , value); }
					}
					
					/// <summary>
					/// Indica se il nome file degli allegati deve essere inviato come id sull'attachment della richiesta soap, questa esigenza è venuta a crearsi con il protocollo SAGA al Comune di Lucca in quanto il web service non riuscita a leggere il nome del file in quanto non veniva inviato come id dell'attachment, in altri ambiti DOCAREA invece sembra funzionare correttamente, se valorizzato a 1 allora il sistema invia il nome file (vedi anche il parametro NOMEFILE_ORIGINE della verticalizzazione PROTOCOLLO_ATTIVO) qualsiasi altro valore non invia il nome file sull'id dell'attachment
					/// </summary>
					public string InviaNomefileAttach
					{
						get{ return GetString("INVIA_NOMEFILE_ATTACH");}
						set{ SetString("INVIA_NOMEFILE_ATTACH" , value); }
					}
					
					/// <summary>
					/// Questo parametro indica se il componente che crea la segnatura 
/// da inviare come request al web service DOCAREA deve inserire la ragione sociale della persona giuridica sull'elemento 
/// <DENOMINAZIONE> presente dentro al nodo <MITTENTE> o <DESTINATARIO> (valore = a 1), o sull'elemento <COGNOME> 
/// (non presente, non valorizzato o valore diverso da 1); questo parametro si è reso necessario per il protocollo DocArea 
/// in quanto il web service installato da Maggioli (ad esempio Comune di Lucca) ha un limite, sull'elemento <COGNOME> che 
/// è quello che viene valorizzato di default con la Ragione Sociale della persona giuridica, di 40 caratteri, e 
/// può succedere di avere delle ragioni sociali più lunghe, quindi valorizzando a 1 questo parametro, 
/// la ragione sociale stessa verrebbe dirottata sull'elemento <DENOMINAZIONE>, che non ha limiti. 
/// Il web service DocArea di altre aziende (ad esempio Ads) non avendo limiti di lunghezza, non permette questo tipo 
/// di sistema, richiedendo che la ragione sociale passi necessariamente sull'elemento <COGNOME>, in questo caso questo 
/// parametro non va valorizzato (o comunque non va valorizzato a 1)
					/// </summary>
					public string UsaDenominazionePg
					{
						get{ return GetString("USA_DENOMINAZIONE_PG");}
						set{ SetString("USA_DENOMINAZIONE_PG" , value); }
					}
					
					/// <summary>
					/// Questo parametro indica se il componente che crea la segnatura 
/// da inviare come request al web service DOCAREA debba inviare tutte le anagrafiche che arrivano dal backoffice 
/// (valore uguale a 1), oppure solo la prima (non presente, valore vuoto o qualsiasi altro valore diverso da 1); 
/// in teoria, da specifiche DOCAREA non sarebbe possibile inviare più anagrafiche come MITTENTI / DESTINATARI, 
/// usare quindi questo parametro solamente in casi particolari dove il web service fornito dal fornitore di protocollo 
/// non tenga conto di questa regola
					/// </summary>
					public string MultiMittDest
					{
						get{ return GetString("MULTI_MITT_DEST");}
						set{ SetString("MULTI_MITT_DEST" , value); }
					}

                    /// <summary>
                    /// Indicare in questo parametro il nome del fornitore di protocollo, questo dato è un enumeratore e serve, in base a quanto indicato, a gestire la parte variabile relativa all'APPLICATIVOPROTOCOLLO in quanto ogni protocollo lo gestisce a modo suo. Al momento è possibile inserire i seguenti dati: ADS, DATAGRAPH, DATAMANAGEMENT, NON_DEFINITO (default). Se non specificato sarà valorizzato in automatico il valore NON_DEFINITO.
                    /// </summary>
                    public string TipoFornitore
                    {
                        get { return GetString("TIPOFORNITORE"); }
                        set { SetString("TIPOFORNITORE", value); }
                    }
				}
			}
			