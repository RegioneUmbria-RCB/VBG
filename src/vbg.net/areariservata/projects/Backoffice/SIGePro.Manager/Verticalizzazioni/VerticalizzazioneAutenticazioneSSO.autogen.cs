
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione AUTENTICAZIONE_SSO il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva, l'accesso a SIGePro sarà gestito tramite SSO. 
/// Ogni utente per accedere deve essersi autenticato tramite SSO e da li viene direttamente re-indirizzato in SIGePro.
/// Se l'utente non esiste in SIGePro verrà inserito e abilitato solamente ai menù di logout.
				/// </summary>
				public partial class VerticalizzazioneAutenticazioneSso : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "AUTENTICAZIONE_SSO";

                    public VerticalizzazioneAutenticazioneSso()
                    {

                    }

					public VerticalizzazioneAutenticazioneSso(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Identifica per quale applicazione deve essere effettuata l'autenticazione sso, accetta i valori: BACK (valido solo per il Backoffice); FRONT (valido solo per il Frontoffice); TUTTI (valido per entrambi).<br>NB: qualore il valore indicato sia nullo il sistema si comporterà come se fosse impostato il valore TUTTI.
					/// </summary>
					public string TipoAutenticazione
					{
						get{ return GetString("TIPO_AUTENTICAZIONE");}
						set{ SetString("TIPO_AUTENTICAZIONE" , value); }
					}
					
					
				}
			}
			