
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_DOCPRO il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Ha le stesse identiche caratteristiche del protocollo DOCAREA, infatti è certificato DOCAREA, ma per errore l'azienda fornitrice (SCAP Sistemi srl) ha chiamato il metodo di Login in maniera non corretta (LoginUser invece che Login) costringendo quindi la creazione di un nuovo componente con conseguente creazione di questa verticalizzazione..
				/// </summary>
				public partial class VerticalizzazioneProtocolloDocpro : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_DOCPRO";

                    public VerticalizzazioneProtocolloDocpro()
                    {

                    }

					public VerticalizzazioneProtocolloDocpro(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// (Obbligatorio) E' l'operatore da utilizzare per protocollare con DOCPRO, deve essere fornito dall'amministratore del protocollo DOCPRO.
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
					/// (Obbligatorio) E' la password per protocollare in DOCPRO, deve essere fornita dall'amministratore del protocollo DOCPRO.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' il codice ente per il quale protocollare, deve essere fornito dall'amministratore del protocollo DOCPRO.
					/// </summary>
					public string Codiceente
					{
						get{ return GetString("CODICEENTE");}
						set{ SetString("CODICEENTE" , value); }
					}
					
					/// <summary>
					/// (Facoltativo) Se valorizzato a 1 va a cercare in automatico, solamente durante la protocollazione da istanza, 
/// gli allegati caricati nel movimento di avvio dell'istanza stessa, se esistono questi vengono inviati al protocollo DOCPRO.
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
/// in alcuni casi (ad esempio Piacenza e Asp Roma) veniva richiesto un valore diverso. Sulle specifiche DOCPRO viene descritto come:  
/// viene inizializzato con un valore che identifica l'ambito dell'applicazione. 
/// Ad esempio questo codice potrebbe essere utilizzato,  
/// per individuare i messaggi provenienti dal portale. 
/// Ovviamente si puo’ scegliere come nel caso a) di usare una codifica del tipo P_X dove X e’ il nome dell’applicazione chiamante. 
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
					/// (Facoltativo) Indica l'ufficio di smistamento del protocollo DOCPRO, questa voce sarà inserita dentro il file segnatura.xml dentro il nodo <ApplicativoProtocollo> valorizzando un nuovo parametro con nome -uo-. E' facoltativo ma il protocollo GS4 di ADS (Piacenza) lo richiede necessariamente.
					/// </summary>
					public string Uo
					{
						get{ return GetString("UO");}
						set{ SetString("UO" , value); }
					}
					
					/// <summary>
					/// Questo parametro è utilizzato per i protocolli in partenza del Protocollo DOCPRO, un protocollo in partenza deve necessariamente scatenare, lato sistema di protocollazione, l'invio di una PEC; quest'evento viene scatenato in base al valore indicato in questo parametro che, ad esempio, al Comune di Empoli è 'e-mail'. Il valore indicato andrà a riempire l'attributo 'nome' dell'elemento 'modalitatramissione' presente dentro al nodo 'ApplicativoProtocollo'
					/// </summary>
					public string ModalitaTrasmissione
					{
						get{ return GetString("MODALITA_TRASMISSIONE");}
						set{ SetString("MODALITA_TRASMISSIONE" , value); }
					}
					
					/// <summary>
					/// Questo parametro è utilizzato per i protocolli in arrivo del Protocollo DOCPRO, a differenza di quelli in partenza, non è necessario scatenare l'invio di una mail, ma è comunque consigliato usare un parametro da passare al protocollo, ad esempio al Comune di Empoli passare il valore PORTALE
					/// </summary>
					public string ModalitaTrasmissioneArrivo
					{
						get{ return GetString("MODALITA_TRASMISSIONE_ARRIVO");}
						set{ SetString("MODALITA_TRASMISSIONE_ARRIVO" , value); }
					}
					
					/// <summary>
					/// Indicare in questo parametro il Codice dell'amministrazione, questo dato andrà a valorizzare gli elementi CODICEAMMINISTRAZIONE della segnatura che viene inviata al metodo protocollazione del web service. Se non valorizzato questo dato verrà preso dal parametro CODICEENTE come succedeva in passato.
					/// </summary>
					public string Codiceamministrazione
					{
						get{ return GetString("CODICEAMMINISTRAZIONE");}
						set{ SetString("CODICEAMMINISTRAZIONE" , value); }
					}
					
					
				}
			}
			