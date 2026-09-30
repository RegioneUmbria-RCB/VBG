
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione DOC_AREA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitata permette di eseguire le funzionalità specifiche per DocArea.
				/// </summary>
				public partial class VerticalizzazioneDocArea : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "DOC_AREA";

                    public VerticalizzazioneDocArea()
                    {

                    }

					public VerticalizzazioneDocArea(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Parametri per la ricerca di un documento DATIDOC in Hummingbird nel formato CHIAVE=VALORE;
					/// </summary>
					public string HbDatidocParams
					{
						get{ return GetString("HB_DATIDOC_PARAMS");}
						set{ SetString("HB_DATIDOC_PARAMS" , value); }
					}
					
					/// <summary>
					/// Utenti per la ricerca di un documento DATIDOC in Hummingbird nel formato UTENTE1,UTENTE2,UTENTEN
					/// </summary>
					public string HbDatidocUsers
					{
						get{ return GetString("HB_DATIDOC_USERS");}
						set{ SetString("HB_DATIDOC_USERS" , value); }
					}
					
					/// <summary>
					/// Utenti per la ricerca di un documento DATIENDO in Hummingbird nel formato UTENTE1,UTENTE2,UTENTEN
					/// </summary>
					public string HbDatiendoUsers
					{
						get{ return GetString("HB_DATIENDO_USERS");}
						set{ SetString("HB_DATIENDO_USERS" , value); }
					}
					
					/// <summary>
					/// Utenti per la ricerca di un documento DATIENDO in Hummingbird nel formato UTENTE1,UTENTE2,UTENTEN
					/// </summary>
					public string HbDatiendoParams
					{
						get{ return GetString("HB_DATIENDO_PARAMS");}
						set{ SetString("HB_DATIENDO_PARAMS" , value); }
					}
					
					/// <summary>
					/// La library da utilizzare in Hummingbird per la ricerca dei documenti
					/// </summary>
					public string HbLibraryname
					{
						get{ return GetString("HB_LIBRARYNAME");}
						set{ SetString("HB_LIBRARYNAME" , value); }
					}
					
					/// <summary>
					/// L'utente da utilizzare in Hummingbird per la ricerca dei documenti
					/// </summary>
					public string HbUsername
					{
						get{ return GetString("HB_USERNAME");}
						set{ SetString("HB_USERNAME" , value); }
					}
					
					/// <summary>
					/// Password dell'utente da utilizzare in Hummingbird per la ricerca dei documenti
					/// </summary>
					public string HbPassword
					{
						get{ return GetString("HB_PASSWORD");}
						set{ SetString("HB_PASSWORD" , value); }
					}
					
					/// <summary>
					/// Il codice movimento da utilizzare di default per il rientro di un parere di un amministrazione
					/// </summary>
					public string TipoMovimentoDefault
					{
						get{ return GetString("TIPO_MOVIMENTO_DEFAULT");}
						set{ SetString("TIPO_MOVIMENTO_DEFAULT" , value); }
					}
					
					/// <summary>
					/// Codice inventario da utilizzare per l'importazione di un parere da amministrazione
					/// </summary>
					public string CodiceInventarioDefault
					{
						get{ return GetString("CODICE_INVENTARIO_DEFAULT");}
						set{ SetString("CODICE_INVENTARIO_DEFAULT" , value); }
					}
					
					/// <summary>
					/// Url del web service pantarei
					/// </summary>
					public string WsPantareiUrl
					{
						get{ return GetString("WS_PANTAREI_URL");}
						set{ SetString("WS_PANTAREI_URL" , value); }
					}
					
					/// <summary>
					/// Cod AOO
					/// </summary>
					public string AooCodiceTipologia
					{
						get{ return GetString("AOO_CODICE_TIPOLOGIA");}
						set{ SetString("AOO_CODICE_TIPOLOGIA" , value); }
					}
					
					/// <summary>
					/// Specifica se includere l'utente correntemente loggato negli utenti utilizzati nella ricerca di un documento. Può assumere una combinazione dei seguenti valori: 0 - No , 1 - Usa utente per ricerche di DATIDOC , 2 - Usa utente per ricerche di DATIENDO 
					/// </summary>
					public string HbIncludeCurrentUser
					{
						get{ return GetString("HB_INCLUDE_CURRENT_USER");}
						set{ SetString("HB_INCLUDE_CURRENT_USER" , value); }
					}
					
					/// <summary>
					/// Gruppi su cui effettuare la ricerca per il file DATIENDO.xml
					/// </summary>
					public string HbDatiendoGroups
					{
						get{ return GetString("HB_DATIENDO_GROUPS");}
						set{ SetString("HB_DATIENDO_GROUPS" , value); }
					}
					
					/// <summary>
					/// Gruppi su cui effettuare la ricerca per il file DATIDOC.xml
					/// </summary>
					public string HbDatidocGroups
					{
						get{ return GetString("HB_DATIDOC_GROUPS");}
						set{ SetString("HB_DATIDOC_GROUPS" , value); }
					}
					
					/// <summary>
					/// Se impostato utilizza username e la password dell utente correntemente loggato per accedere a HB (i parametri di login vengono ignorati)
					/// </summary>
					public string HbDynamicLogin
					{
						get{ return GetString("HB_DYNAMIC_LOGIN");}
						set{ SetString("HB_DYNAMIC_LOGIN" , value); }
					}
					
					/// <summary>
					/// Oggetto del messaggio d mail inviato durante la creazione di una richiesta DOC_AREA
					/// </summary>
					public string SmtpMessageTitle
					{
						get{ return GetString("SMTP_MESSAGE_TITLE");}
						set{ SetString("SMTP_MESSAGE_TITLE" , value); }
					}
					
					
				}
			}
			