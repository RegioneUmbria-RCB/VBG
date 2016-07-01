
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ARCHIVIAZIONE_DOC_LEGALDOC il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// abilita l'integrazione con il sistema di archiviazione documentale LegalDoc Folders di Infocert
				/// </summary>
				public partial class VerticalizzazioneArchiviazioneDocLegaldoc : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ARCHIVIAZIONE_DOC_LEGALDOC";

                    public VerticalizzazioneArchiviazioneDocLegaldoc()
                    {

                    }

					public VerticalizzazioneArchiviazioneDocLegaldoc(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// percorso della cartella locale di LegalDocs dove archiviare i file
					/// </summary>
					public string DataFolderPath
					{
						get{ return GetString("DATA_FOLDER_PATH");}
						set{ SetString("DATA_FOLDER_PATH" , value); }
					}
					
					/// <summary>
					/// attributo obbligatorio all'interno del file di avvio che identifica il servizio finale al quale sono indirizzati i file da trasferire tramite LegalDoc Connector. Il contenuto di questo campo è case sensitive e deve corrispondere all'identificativo fornito dal gestore per il servizio prescelto
					/// </summary>
					public string Service
					{
						get{ return GetString("SERVICE");}
						set{ SetString("SERVICE" , value); }
					}
					
					/// <summary>
					/// attributo facoltativo all'interno del file di avvio che può essere usato per identificare l'utente finale, di norma rappresentato da un cliente del cliente intermediario (es. Banca1, Banca2...); il contenuto di questo campo è libero e, se presente, viene riportato nei messaggi PEC di segnalazione
					/// </summary>
					public string EndUserId
					{
						get{ return GetString("END_USER_ID");}
						set{ SetString("END_USER_ID" , value); }
					}
					
					/// <summary>
					/// attributo facoltativo all'interno del file di avvio ad uso del Cliente per descrivere la tipologia di file che costituisce il job; il contenuto di questo campo è libero e, se presente, viene riportato nei messaggi PEC di segnalazione
					/// </summary>
					public string Nome
					{
						get{ return GetString("NOME");}
						set{ SetString("NOME" , value); }
					}
					
					/// <summary>
					/// attributo facoltativo all'interno del file di avvio che può essere usato per specificare l'indirizzo di PEC a cui inviare l'eventuale notifica di errore del job; se questo campo non è presente, la notifica viene inviata all'indirizzo PEC specificato in fase di registrazione
					/// </summary>
					public string FeedbackEmail
					{
						get{ return GetString("FEEDBACK_EMAIL");}
						set{ SetString("FEEDBACK_EMAIL" , value); }
					}
					
					/// <summary>
					/// lista di estensioni di file dichiarate in fase contrattuale. (separate dal carattere ';')
					/// </summary>
					public string FileExtensions
					{
						get{ return GetString("FILE_EXTENSIONS");}
						set{ SetString("FILE_EXTENSIONS" , value); }
					}
					
					/// <summary>
					/// parametro obbligatorio fornito da InfoCert in fase contrattuale; indica il codice del Sistema di Gestione Documentale (SGD) per il quale si sta effettuando il trasferimento
					/// </summary>
					public string Sgd
					{
						get{ return GetString("SGD");}
						set{ SetString("SGD" , value); }
					}
					
					/// <summary>
					/// parametro facoltativo per richiedere la conservazione dei dati inviati entro una certa data; la data massima di conservazione richiesta deve essere di almeno tre giorni successiva alla data di invio dei file. La data deve essere espressa nel formato dd-mm-aaaa
					/// </summary>
					public string DataMaxCons
					{
						get{ return GetString("DATA_MAX_CONS");}
						set{ SetString("DATA_MAX_CONS" , value); }
					}
					
					/// <summary>
					/// numero massimo di file per singola archiviazione
					/// </summary>
					public string MaxNumFiles
					{
						get{ return GetString("MAX_NUM_FILES");}
						set{ SetString("MAX_NUM_FILES" , value); }
					}
					
					/// <summary>
					/// numero massimo di record della tabella ISTANZE da recuperare dalla query che individua le istanze da archiviare
					/// </summary>
					public string MaxNumIstanzePerQuery
					{
						get{ return GetString("MAX_NUM_ISTANZE_PER_QUERY");}
						set{ SetString("MAX_NUM_ISTANZE_PER_QUERY" , value); }
					}
					
					/// <summary>
					/// dimensione massima in byte del singolo file
					/// </summary>
					public string MaxFileSize
					{
						get{ return GetString("MAX_FILE_SIZE");}
						set{ SetString("MAX_FILE_SIZE" , value); }
					}
					
					/// <summary>
					/// lunghezza massima in caratteri del nome del singolo file
					/// </summary>
					public string MaxFileNameLength
					{
						get{ return GetString("MAX_FILE_NAME_LENGTH");}
						set{ SetString("MAX_FILE_NAME_LENGTH" , value); }
					}
					
					/// <summary>
					/// filtro opzionale applicato alla colonna DATAFINEEFFETTIVA della tabella ISTANZE_TEMPISTICA
					/// </summary>
					public string DallaData
					{
						get{ return GetString("DALLA_DATA");}
						set{ SetString("DALLA_DATA" , value); }
					}
					
					/// <summary>
					/// filtro opzionale applicato alla colonna DATAFINEEFFETTIVA della tabella ISTANZE_TEMPISTICA
					/// </summary>
					public string AllaData
					{
						get{ return GetString("ALLA_DATA");}
						set{ SetString("ALLA_DATA" , value); }
					}
					
					
				}
			}
			