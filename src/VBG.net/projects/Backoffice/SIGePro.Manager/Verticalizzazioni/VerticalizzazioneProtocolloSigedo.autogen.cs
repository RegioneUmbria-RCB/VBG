
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIGEDO il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato consente di eseguire la protocollazione con l'applicativo SIGEDO, questo tipo di protocollo utilizza in parte gli Standard DocArea, percui molti parametri saranno gli stessi di quel tipo di protocollo, in più però sono state aggiunte alcune funzionalità specifiche per il Comune di Firenze, tra cui il leggi protocollo
				/// </summary>
				public partial class VerticalizzazioneProtocolloSigedo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIGEDO";

                    public VerticalizzazioneProtocolloSigedo()
                    {

                    }

					public VerticalizzazioneProtocolloSigedo(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Specificare il valore del tipo documento dell'allegato principale, questo dato può essere omesso IN quanto non obbligatorio
					/// </summary>
					public string TipoDocumentoPrincipale
					{
						get{ return GetString("TIPO_DOCUMENTO_PRINCIPALE");}
						set{ SetString("TIPO_DOCUMENTO_PRINCIPALE" , value); }
					}
					
					/// <summary>
					/// Indicare l'url del web service utilizzato per recuperare i valori relativi alle unità protocollanti (uffici). La funzionalità di questo web service sarà utilizzata solamente in fase di lettura del protocollo per fare in modo compaia la descrizione dell'ufficio assegnatario sulla lista dei mittenti / destinatari. Da notare che questo servizio restituisce tutta la struttura degli uffici non potendo utilizzare filtri, sarà quindi cura del componente comparare il codice restituito dal servizio di lettura con quelli restituiti da questo web service
					/// </summary>
					public string UrlStrutturasoa
					{
						get{ return GetString("URL_STRUTTURASOA");}
						set{ SetString("URL_STRUTTURASOA" , value); }
					}
					
					/// <summary>
					/// Indica l'ambito per il quale andare a cercare le unità protocollanti (uffici), i valori da impostare sono: TEST per l'ambiente di test e PRODUZIONE per l'ambiente di produzione, questo parametro va di pari passo con il parametro URL_STRUTTURASOA. NB. Se non impostato verrà restituita un'eccezione
					/// </summary>
					public string CanaleStrutturasoa
					{
						get{ return GetString("CANALE_STRUTTURASOA");}
						set{ SetString("CANALE_STRUTTURASOA" , value); }
					}
					
					/// <summary>
					/// 1 = invia, 0 o altro = non invia. Indica se in fase di protocollazione deve essere inviato il codice fiscale come attributo chiave riguardante il mittente / destinatario (valore 1) oppure no (altro valore o null). Se viene inviato il web service di protocollazione verifica se l'anagrafica è già presente, in caso negativo inserisce i dati nel proprio archivio altrimenti no. Nel caso in cui non venga inviato i dati saranno trattati come testo non andando a fare alcuna operazione sugli archivi di Sigedo. NB. Nel caso in cui il valore sia uguale a 1 sarà fatto il controllo se i mittenti / destinatari, indicati per la protocollazione, abbiano il codice fiscale o la partita iva, in caso negativo il sistema restituirà un'eccezione prima della chiamata al web service di protocollo.
					/// </summary>
					public string InviaCf
					{
						get{ return GetString("INVIA_CF");}
						set{ SetString("INVIA_CF" , value); }
					}
					
					/// <summary>
					/// Inserire in questo parametro l'url che indica l'endpoint del web service di recupero dati degli allegati, da questo web service è possibile visualizzare l'oggetto che viene passato direttamente dentro il web service, se non viene valorizzato questo parametro sarà possibile leggere il protocollo, ma, nel momento in cui si cerca di visualizzare gli allegati verrà restituito un errore
					/// </summary>
					public string UrlWsAllegati
					{
						get{ return GetString("URL_WS_ALLEGATI");}
						set{ SetString("URL_WS_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Inserire in questo paramtro lo username necessario a collegarsi al web service di recupero allegati descritto nel parametro URL_WS_ALLEGATI
					/// </summary>
					public string UsernameWsAllegati
					{
						get{ return GetString("USERNAME_WS_ALLEGATI");}
						set{ SetString("USERNAME_WS_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Inserire in questo paramtro la password necessaria a collegarsi al web service di recupero allegati descritto nel parametro URL_WS_ALLEGATI
					/// </summary>
					public string PasswordWsAllegati
					{
						get{ return GetString("PASSWORD_WS_ALLEGATI");}
						set{ SetString("PASSWORD_WS_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Indica il nome dell'applicativo del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro l'attributo -nome- di <ApplicativoProtocollo/>. NB. Se lasciato vuoto o non attivato prenderà il valore inserito dentro il parametro CODICEENTE
					/// </summary>
					public string ApplicativoProtocollo
					{
						get{ return GetString("APPLICATIVO_PROTOCOLLO");}
						set{ SetString("APPLICATIVO_PROTOCOLLO" , value); }
					}
					
					/// <summary>
					/// E' il codice dell'ente per il quale protocollare, questo parametro va usato nell'autenticazione al web service insieme al parametro USERNAME_WS e PASSWORD_WS
					/// </summary>
					public string Codiceente
					{
						get{ return GetString("CODICEENTE");}
						set{ SetString("CODICEENTE" , value); }
					}
					
					/// <summary>
					/// E' il codice dell'Area Organizzativa Omogenea, definisce l'ambito per cui protocollare, in particolare, deve essere fornito dai responsabili di protocollo.
					/// </summary>
					public string CodiceAoo
					{
						get{ return GetString("CODICE_AOO");}
						set{ SetString("CODICE_AOO" , value); }
					}
					
					/// <summary>
					/// Se valorizzato a 1 consente, in fase di protocollazione istanza o movimento da backoffice, di allegare il file di profilazione (segnatura.xml) che sarebbe il file che viene inviato al web service per eseguire la protocollazione. Questa funzionalità viene usata nel momento in cui il cliente decide che nessun file allegato deve essere inviato al web service, che però ne richiede necessariamente almeno uno.; se valorizzato a 0 o non valorizzato non esegue nessuna funzionalità.
					/// </summary>
					public string InviaSegnatura
					{
						get{ return GetString("INVIA_SEGNATURA");}
						set{ SetString("INVIA_SEGNATURA" , value); }
					}
					
					/// <summary>
					/// E' lo username che serve per autenticarsi al web service di protocollazione, va usato insieme al parametro CODICEENTE e PASSWORD_WS.
					/// </summary>
					public string UsernameWs
					{
						get{ return GetString("USERNAME_WS");}
						set{ SetString("USERNAME_WS" , value); }
					}
					
					/// <summary>
					/// E' la password che serve per autenticarsi al web service di protocollazione, va usato insieme al parametro CODICEENTE e USERNAME_WS.
					/// </summary>
					public string PasswordWs
					{
						get{ return GetString("PASSWORD_WS");}
						set{ SetString("PASSWORD_WS" , value); }
					}
					
					/// <summary>
					/// Specificare il valore del tipo documento degli allegati secondari, ossia quei documenti allegati al protocollo escluso quello principale, questo parametro non è obbligatorio.
					/// </summary>
					public string TipoDocumentoAllegato
					{
						get{ return GetString("TIPO_DOCUMENTO_ALLEGATO");}
						set{ SetString("TIPO_DOCUMENTO_ALLEGATO" , value); }
					}
					
					/// <summary>
					/// Indica l'ufficio di smistamento del protocollo, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -UO-. 
					/// </summary>
					public string Uo
					{
						get{ return GetString("UO");}
						set{ SetString("UO" , value); }
					}
					
					/// <summary>
					/// E' l'endpoint utilizzato per invocare il web service di protocollazione.
					/// </summary>
					public string UrlProto
					{
						get{ return GetString("URL_PROTO");}
						set{ SetString("URL_PROTO" , value); }
					}
					
					/// <summary>
					/// E' l'endpoint utilizzato per invocare il web service di ricerche, tramite questo servizio è possibile cercare i protocolli, gli allegati, i mittenti e destinatari, da usare in fase di LeggiProtocollo, questo servizio ha la particolarità che restituisce diversi dati in base ai metadati passati e determinati dai parametri AREA e MODELLO, ad esempio se AREA viene lasciato vuoto e MODELLO valorizzato con @PROTO cerca un protocollo altrimenti con diversi valori potrebbe cercare allegati oppure mittenti e destinatari.
					/// </summary>
					public string UrlQueryservice
					{
						get{ return GetString("URL_QUERYSERVICE");}
						set{ SetString("URL_QUERYSERVICE" , value); }
					}
					
					/// <summary>
					/// Parametro area usato per la ricerca di un protocollo invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. E' una sezione del documentale dedicata ad un particolare compito applicativo con dei modelli atti a tale scopo; ad esempio l’area SEGRETERIA contiene i modelli del protocollo
					/// </summary>
					public string AreaLeggiproto
					{
						get{ return GetString("AREA_LEGGIPROTO");}
						set{ SetString("AREA_LEGGIPROTO" , value); }
					}
					
					/// <summary>
					/// Parametro modello usato per la ricerca di un protocollo invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. Rappresenta la tipologia di modello di riferimento (in relazione all’area scelta); ciascuna tipologia è dotata di una collezione di attributi specifici e/o metadati da valorizzare, ad esempio anno e numero per la ricerca di un protocollo
					/// </summary>
					public string ModelloLeggiproto
					{
						get{ return GetString("MODELLO_LEGGIPROTO");}
						set{ SetString("MODELLO_LEGGIPROTO" , value); }
					}
					
					/// <summary>
					/// Parametro stato usato per invocare i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE, questo parametro è fisso per tutte le ricerche e indica se cercare un protocollo in stato BO (Bozza) oppure CO (Completo)
					/// </summary>
					public string Stato
					{
						get{ return GetString("STATO");}
						set{ SetString("STATO" , value); }
					}
					
					/// <summary>
					/// Parametro area usato per la ricerca degli allegati secondari di uno specifico protocollo, cercato in precedenza, invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. E' una sezione del documentale dedicata ad un particolare compito applicativo con dei modelli atti a tale scopo; ad esempio l’area SEGRETERIA contiene i modelli del protocollo
					/// </summary>
					public string AreaAllegati
					{
						get{ return GetString("AREA_ALLEGATI");}
						set{ SetString("AREA_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Parametro modello usato per la ricerca degli allegati di uno specifico protocollo, cercato in precedenza, invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. Rappresenta la tipologia di modello di riferimento (in relazione all’area scelta); ciascuna tipologia è dotata di una collezione di attributi specifici e/o metadati da valorizzare, ad esempio anno e numero per la ricerca di un protocollo
					/// </summary>
					public string ModelloAllegati
					{
						get{ return GetString("MODELLO_ALLEGATI");}
						set{ SetString("MODELLO_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Parametro area usato per la ricerca soggetti (mittenti/destinatari) di uno specifico protocollo, cercato in precedenza, invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. E' una sezione del documentale dedicata ad un particolare compito applicativo con dei modelli atti a tale scopo; ad esempio l’area SEGRETERIA contiene i modelli del protocollo
					/// </summary>
					public string AreaSoggetti
					{
						get{ return GetString("AREA_SOGGETTI");}
						set{ SetString("AREA_SOGGETTI" , value); }
					}
					
					/// <summary>
					/// Parametro modello usato per la ricerca soggetti (mittenti/destinatari) di uno specifico protocollo, cercato in precedenza, invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. Rappresenta la tipologia di modello di riferimento (in relazione all’area scelta); ciascuna tipologia è dotata di una collezione di attributi specifici e/o metadati da valorizzare, ad esempio anno e numero per la ricerca di un protocollo
					/// </summary>
					public string ModelloSoggetti
					{
						get{ return GetString("MODELLO_SOGGETTI");}
						set{ SetString("MODELLO_SOGGETTI" , value); }
					}
					
					/// <summary>
					/// Parametro operatore usato nei metadati per eseguire tutti i tipi di ricerche, invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. Valore fisso da passare che però non ha niente a che fare ne con lo username del web service ne con gli operatori di protocollo.
					/// </summary>
					public string OperatoreRicerche
					{
						get{ return GetString("OPERATORE_RICERCHE");}
						set{ SetString("OPERATORE_RICERCHE" , value); }
					}
					
					/// <summary>
					/// Username usato per l'autenticazione del web service di ricerche QUERYSERVICE.
					/// </summary>
					public string UsernameQueryservice
					{
						get{ return GetString("USERNAME_QUERYSERVICE");}
						set{ SetString("USERNAME_QUERYSERVICE" , value); }
					}
					
					/// <summary>
					/// Password usata per l'autenticazione del web service di ricerche QUERYSERVICE.
					/// </summary>
					public string PasswordQueryservice
					{
						get{ return GetString("PASSWORD_QUERYSERVICE");}
						set{ SetString("PASSWORD_QUERYSERVICE" , value); }
					}
					
					/// <summary>
					/// Indicare in questo parametro la descrizione dell'ente, ad esempio - COMUNE DI FIRENZE -, questo valore andrà preso in considerazione nel caso in cui, durante una lettura di un protocollo in partenza, non venga individuato l'ufficio (smistamento) mittente del protocollo stesso; in questo caso il mittente prenderà il valore inserito in questo parametro
					/// </summary>
					public string DescrizioneEnte
					{
						get{ return GetString("DESCRIZIONE_ENTE");}
						set{ SetString("DESCRIZIONE_ENTE" , value); }
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
					/// Parametro operatore usato nei metadati per eseguire la ricerca delle classifiche, invocando i servizi presenti sull'endpoint del parametro URL_QUERYSERVICE. Valore fisso da passare che però non ha niente a che fare ne con lo username del web service ne con gli operatori di protocollo.
					/// </summary>
					public string OperatoreRicercheClassifiche
					{
						get{ return GetString("OPERATORE_RICERCHE_CLASSIFICHE");}
						set{ SetString("OPERATORE_RICERCHE_CLASSIFICHE" , value); }
					}
					
					/// <summary>
					/// Se valorizzato indica l'url cgi da chiamare per ottenere l'uo in base all'utente corrente che effettua la protocollazione, questo sistema è stato studiato solamente per il Comune di Firenze perchè si verificava il caso che alcuni utenti, non facenti parte all'uo indicata nel parametro UO, non potevano leggere i protocolli, nemmeno se erano stati loro stessi a crearli. L'applicativo cgi è stato sviluppato da Fabio Lo Giudice del Comune di Firenze. In questo parametro va specificato l'url inserendo la stringa fino al parametro di querystring senza inserirne il valore, ad esempio: http://url_cgi_per_restituire_la_uo?MATRICOLA=
					/// </summary>
					public string UrlCgiUo
					{
						get{ return GetString("URL_CGI_UO");}
						set{ SetString("URL_CGI_UO" , value); }
					}
					
					/// <summary>
					/// Indica se per il recupero delle classifiche, sull'albero degli interventi, debba essere usato il metodo messo a disposizione del web service (valore = 1) oppure no (valore 0, vuoto o non presente). E' stato deciso di inserire questo parametro in quanto recuperare le classifiche da web service potrebbe essere macchinoso rendendo la pagina del dettaglio degli interventi molto lenta se i dati dovessero essere molti. Oltre tutto i dati potrebbero creare una lunga lista difficilmente leggibile dagli utenti. In ultimo, se dovesso essere modificati i codici, questi non sarebbero più direttamente visibili sul campo Classifica nel dettaglio degli interventi in quanto non più presenti nella lista.
					/// </summary>
					public string UsaWsClassifiche
					{
						get{ return GetString("USA_WS_CLASSIFICHE");}
						set{ SetString("USA_WS_CLASSIFICHE" , value); }
					}
					
					
				}
			}
			