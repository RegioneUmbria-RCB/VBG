
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ALLINEAMENTO_STRADARIO_ATTIVO il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che viene utilizzata un'attività batch per l'allineamento dello stradario
				/// </summary>
				public partial class VerticalizzazioneAllineamentoStradarioAttivo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ALLINEAMENTO_STRADARIO_ATTIVO";

                    public VerticalizzazioneAllineamentoStradarioAttivo()
                    {

                    }

					public VerticalizzazioneAllineamentoStradarioAttivo(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' l'indirizzo mail del destinatario
					/// </summary>
					public string MailDestinatario
					{
						get{ return GetString("MAIL_DESTINATARIO");}
						set{ SetString("MAIL_DESTINATARIO" , value); }
					}
					
					/// <summary>
					/// E' il file in cui vengono salvate tutte le istruzioni eseguite dall'attività di allineamento
					/// </summary>
					public string PathLog
					{
						get{ return GetString("PATH_LOG");}
						set{ SetString("PATH_LOG" , value); }
					}
					
					/// <summary>
					/// E' il file la cui presenza indica che è già stata lanciata un'operazione di allineamento
					/// </summary>
					public string PathStop
					{
						get{ return GetString("PATH_STOP");}
						set{ SetString("PATH_STOP" , value); }
					}
					
					/// <summary>
					/// E' la password per richiedere un token
					/// </summary>
					public string Pwd
					{
						get{ return GetString("PWD");}
						set{ SetString("PWD" , value); }
					}
					
					/// <summary>
					/// E' il comune per il quale eseguire l'allineamento dello stradario
					/// </summary>
					public string TipoStradario
					{
						get{ return GetString("TIPO_STRADARIO");}
						set{ SetString("TIPO_STRADARIO" , value); }
					}
					
					/// <summary>
					/// E' l'utente per richiedere un token
					/// </summary>
					public string User
					{
						get{ return GetString("USER");}
						set{ SetString("USER" , value); }
					}
					
					/// <summary>
					/// E' l'url del ws per richiedere un token
					/// </summary>
					public string WsLogin
					{
						get{ return GetString("WS_LOGIN");}
						set{ SetString("WS_LOGIN" , value); }
					}
					
					/// <summary>
					/// E' l'url del ws per inviare le mail
					/// </summary>
					public string WsMail
					{
						get{ return GetString("WS_MAIL");}
						set{ SetString("WS_MAIL" , value); }
					}
					
					
				}
			}
			