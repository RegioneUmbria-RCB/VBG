
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIPRWEB il 26/08/2014 17.28.02
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// SIPRWEB è il protocollo presente al Comune di Bari.
				/// </summary>
				public partial class VerticalizzazioneProtocolloSiprweb : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIPRWEB";

                    public VerticalizzazioneProtocolloSiprweb()
                    {

                    }

					public VerticalizzazioneProtocolloSiprweb(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// E' l'URL per invocare la lettura di un protocollo
					/// </summary>
					public string Urlleggi
					{
						get{ return GetString("URLLEGGI");}
						set{ SetString("URLLEGGI" , value); }
					}
					
					/// <summary>
					/// E' l'URL per invocare la richiesta di un protocollo
					/// </summary>
					public string Urlprotocolla
					{
						get{ return GetString("URLPROTOCOLLA");}
						set{ SetString("URLPROTOCOLLA" , value); }
					}
					
					/// <summary>
					/// E' l'URL per invocare la lista delle classifiche
					/// </summary>
					public string Urlclassifiche
					{
						get{ return GetString("URLCLASSIFICHE");}
						set{ SetString("URLCLASSIFICHE" , value); }
					}
					
					/// <summary>
					/// E' l'URL per invocare la lista dei tipi documento
					/// </summary>
					public string Urltipidocumento
					{
						get{ return GetString("URLTIPIDOCUMENTO");}
						set{ SetString("URLTIPIDOCUMENTO" , value); }
					}
					
					/// <summary>
					/// OBSOLETO, NON USARE. Inizialmente si era deciso di usare questo parametro per individuare il tipo di ambiente, i namespace dei web service di test e produzione però erano diversi quindi bisognava creare due componenti diversi o comunque delle soluzioni lunghe e dispendiose, per questo si è deciso di usare solamente le classi dell'ambiente di produzione, pur essendo presenti anche quelle di test; qui la vecchia descrizione del parametro. Indica il tipo di ambiente di protocollo che si intende utilizzare, TEST o PRODUZIONE, prende come valori T = Ambiente di Test, P = Ambiente di Produzione. Se si intende cambiare ambiente, oltre a questo parametro, vanno comunque modificati anche gli altri parametri (URL) presenti in questo modulo. Si è deciso di introdurre questo parametro in quanto i namespace dei wsdl dei webservice sono diversi in base al tipo di ambiente invocato, creando quindi la necessità di creare due classi proxy (una di Test e una di Produzione appunto) per ogni webservice presente in questo tipo di protocollo, il valore presente indicherà al componente, che si occupa della gestione del protocollo, di invocare la classe giusta in base all'esigenza del momento. Ad esempio il namespace presente sul wsdl di test del webservice dei TipiDocumento è - http://SIPrWeb/WebService.nsf/TipoDocService.wsdl - mentre per quello di produzione è - http://sipr/WebService.nsf/TipoDocService.wsdl - IMPORTANTE: questo parametro è obbligatorio, se non valorizzato sarà sollevata un'eccezione.
					/// </summary>
					public string Ambiente
					{
						get{ return GetString("AMBIENTE");}
						set{ SetString("AMBIENTE" , value); }
					}
					
					/// <summary>
					/// Indicare il nome della cartella condivisa sul server di protocollo, il fornitore o il CED del comune deve fornire il nome (solo il nome e non tutto il path) della cartella di appoggio utilizzata dal sistema di protocollo per recuperare i files allegati durante l'inserimento di un protocollo.
					/// </summary>
					public string NomeDirCondivisa
					{
						get{ return GetString("NOME_DIR_CONDIVISA");}
						set{ SetString("NOME_DIR_CONDIVISA" , value); }
					}
					
					/// <summary>
					/// Indica se per il recupero delle classifiche, sull'albero degli interventi, debba essere usato il metodo messo a disposizione del web service (valore = 1) oppure no (valore 0, vuoto o non presente). E' stato deciso di inserire questo parametro in quanto recuperare le classifiche da web service potrebbe essere macchinoso rendendo la pagina del dettaglio degli interventi molto lenta se i dati dovessero essere molti. Oltre tutto i dati potrebbero creare una lunga lista difficilmente leggibile dagli utenti. In ultimo, se dovesso essere modificati i codici, questi non sarebbero più direttamente visibili sul campo Classifica nel dettaglio degli interventi in quanto non più presenti nella lista.
					/// </summary>
					public string UsaWsClassifiche
					{
						get{ return GetString("USA_WS_CLASSIFICHE");}
						set{ SetString("USA_WS_CLASSIFICHE" , value); }
					}
					
					/// <summary>
					/// Indica se per il recupero delle tipologie di documento, sull'albero degli interventi, debba essere usato il metodo messo a disposizione del web service (valore = 1) oppure no (valore 0, vuoto o non presente). E' stato deciso di inserire questo parametro in quanto recuperare le tipologie di documento da web service potrebbe essere macchinoso rendendo la pagina del dettaglio degli interventi molto lenta se i dati dovessero essere molti. Oltre tutto i dati potrebbero creare una lunga lista difficilmente leggibile dagli utenti. In ultimo, se dovesso essere modificati i codici
					/// </summary>
					public string UsaWsTipidoc
					{
						get{ return GetString("USA_WS_TIPIDOC");}
						set{ SetString("USA_WS_TIPIDOC" , value); }
					}
					
					/// <summary>
					/// Percorso cartella FTP del server dedicato. Lunghezza massima 256 caratteri. Parametro obbligatorio. Inserire qui il path ftp che serve come appoggio per inserire i files allegati ad uno specifico protocollo. Durante la protocollazione infatti, se sono presenti dei files, questi vengono appoggiati sul percorso indicato in questo parametro, per poi essere recuperati dal web service stesso durante l'operazione di inserimento allegato, deve essere quindi il client del protocollo a fare upload dei file su questo percorso
					/// </summary>
					public string FtpPathAllegati
					{
						get{ return GetString("FTP_PATH_ALLEGATI");}
						set{ SetString("FTP_PATH_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// E' l'URL del web service che serve per invocare la funzionalità di inserimento files ad un determinato protocollo. Questa funzionalità viene invocata solo dopo aver protocollato e serve appunto per allegare file al protocollo restituito.
					/// </summary>
					public string Urlallegati
					{
						get{ return GetString("URLALLEGATI");}
						set{ SetString("URLALLEGATI" , value); }
					}
					
					/// <summary>
					/// Indica il codice per conoscenza da utilizzare in fase di protocollazione. Sulla maschera di protocollazione è presente un campo a tendina denominato -Trasmesso per- legato all'anagrafica (Mittente / Destinatario), questo campo viene popolato dai valori presenti nella tabella PROTOCOLLO_MODALITAINVIO, indicando il codice in questo campo, quando l'operatore selezionerà nella tendina -Trasmesso per- il valore con lo stesso codice, l'anagrafica selezionata sarà considerata per conoscenza. Se non valorizzato il destinatario (mittente) del protocollo non sarà mai per conoscenza.
					/// </summary>
					public string CodiceCc
					{
						get{ return GetString("CODICE_CC");}
						set{ SetString("CODICE_CC" , value); }
					}
					
					/// <summary>
					/// Parametro riguardante il salvataggio dei files allegati, il web service infatti costringe il client a salvare files in una cartella condivisa sul server del protocollo, alla quale vanno indicate le credenziali tra le quali il DOMINIO che va indicato in questo parametro
					/// </summary>
					public string Dominio
					{
						get{ return GetString("DOMINIO");}
						set{ SetString("DOMINIO" , value); }
					}
					
					/// <summary>
					/// Parametro riguardante il salvataggio dei files allegati, il web service infatti costringe il client a salvare files in una cartella condivisa sul server del protocollo, alla quale vanno indicate le credenziali tra le quali lo USERNAME che va indicato in questo parametro
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					/// <summary>
					/// Parametro riguardante il salvataggio dei files allegati, il web service infatti costringe il client a salvare files in una cartella condivisa sul server del protocollo, alla quale vanno indicate le credenziali tra le quali la PASSWORD che va indicata in questo parametro
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					
				}
			}
			