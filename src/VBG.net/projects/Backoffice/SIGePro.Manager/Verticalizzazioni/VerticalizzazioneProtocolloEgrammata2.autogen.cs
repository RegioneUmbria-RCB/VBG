
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_EGRAMMATA2 il 26/08/2014 17.28.00
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Sistema di protocollazione E-Grammata usato al momento della creazione solamente dal Comune di Ferrara, il fornitore è Engineering. Questa versione sostituisce quella facente riferimento alla verticalizzazione PROTOCOLLO_EGRAMMATA in quanto, quella precedente, non supportava l'invio degli allegati.
				/// </summary>
				public partial class VerticalizzazioneProtocolloEgrammata2 : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_EGRAMMATA2";

                    public VerticalizzazioneProtocolloEgrammata2()
                    {

                    }

					public VerticalizzazioneProtocolloEgrammata2(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Codice ente che richiama il WS, si tratta del codice numerico che identifica la AOO (obbligatorio)
					/// </summary>
					public string Codiceente
					{
						get{ return GetString("CODICEENTE");}
						set{ SetString("CODICEENTE" , value); }
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
					/// Password dell’utente con cui si autentica al WS (obbligatorio)
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Password dell’utente con cui si identifica ed autentica all’Applicazione di Protocollo, lo username viene identificato con il parametro USERAPP (obbligatorio)
					/// </summary>
					public string PasswordUserapp
					{
						get{ return GetString("PASSWORD_USERAPP");}
						set{ SetString("PASSWORD_USERAPP" , value); }
					}
					
					/// <summary>
					/// Cinquina che identifica la postazione dell’utente applicativo che si autentica al Protocollo (cinque numeri separati dal carattere '-' Esempio 92-25-0-0-0).
					/// </summary>
					public string Postazione
					{
						get{ return GetString("POSTAZIONE");}
						set{ SetString("POSTAZIONE" , value); }
					}
					
					/// <summary>
					/// Url relativo al web service che consente leggere i protocolli. In particolare consente di recuperare una lista di protocolli a partire da determinati parametri di ricerca.
					/// </summary>
					public string UrlLeggiproto
					{
						get{ return GetString("URL_LEGGIPROTO");}
						set{ SetString("URL_LEGGIPROTO" , value); }
					}
					
					/// <summary>
					/// Url relativo al web service che consente di protocollare (obbligatorio)
					/// </summary>
					public string UrlProtocollo
					{
						get{ return GetString("URL_PROTOCOLLO");}
						set{ SetString("URL_PROTOCOLLO" , value); }
					}
					
					/// <summary>
					/// UserApp dell’utente con cui si identifica ed autentica all’Applicazione di Protocollo (obbligatorio)
					/// </summary>
					public string Userapp
					{
						get{ return GetString("USERAPP");}
						set{ SetString("USERAPP" , value); }
					}
					
					/// <summary>
					/// Username dell’utente con cui si autentica al WS (obbligatorio)
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}

                    /// <summary>
                    /// Indicare qui l'url del web service VBG sviluppato lato java che farà poi la chiamata al web service egrammata di protocollazione. Questo servizio si è reso necessario in quanto i file non possono essere inviati via mime (come richiesto dal web service di egrammata) con componenti .net, quindi viene invocato un web service ponte sviluppato in java che svolge questa funzionalità e invoca il web service di protocollazione.
                    /// </summary>
                    public string UrlProtoallegati
                    {
                        get { return GetString("URL_PROTOALLEGATI"); }
                        set { SetString("URL_PROTOALLEGATI", value); }
                    }
                    
                    /// <summary>
                    /// 'Url del web service e-grammata2 che consente di svolgere operazioni con le anagrafiche.
                    /// </summary>
                    public string UrlLeggiAnagrafiche
                    {
                        get { return GetString("URL_LEGGIANAGRAFICHE"); }
                        set { SetString("URL_LEGGIANAGRAFICHE", value); }
                    }
                }
			}
			