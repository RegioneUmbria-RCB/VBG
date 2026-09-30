
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ALLINEAMENTO_ANAGRAFE_ATTIVO il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che viene utilizzata un'attività batch per l'allineamento dell'anagrafe
				/// </summary>
				public partial class VerticalizzazioneAllineamentoAnagrafeAttivo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ALLINEAMENTO_ANAGRAFE_ATTIVO";

                    public VerticalizzazioneAllineamentoAnagrafeAttivo()
                    {

                    }

					public VerticalizzazioneAllineamentoAnagrafeAttivo(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' il parametro che permette di stabilire se fare l'allineamento solo relativamente alle modifiche del giorno stesso
					/// </summary>
					public string DataAggiornamento
					{
						get{ return GetString("DATA_AGGIORNAMENTO");}
						set{ SetString("DATA_AGGIORNAMENTO" , value); }
					}
					
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
					/// E' il comune per il quale eseguire l'allineamento dell'anagrafe
					/// </summary>
					public string TipoComune
					{
						get{ return GetString("TIPO_COMUNE");}
						set{ SetString("TIPO_COMUNE" , value); }
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
					/// E' il parametro che stabilisce se fare la verifica dell'indirizzo
					/// </summary>
					public string VerificaIndirizzo
					{
						get{ return GetString("VERIFICA_INDIRIZZO");}
						set{ SetString("VERIFICA_INDIRIZZO" , value); }
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

                    /// <summary>
                    /// Indica se nelle giornate di sabato e domenica deve essere fatto l'aggiornamento delle anagrafiche senza limiti temporali, indipendentemente quindi dal fatto che il parametro DATA_AGGIORNAMENTO sia valorizzato o meno. Valorizzare a 1 se si desidera attivare questo parametro, tutti gli altri valori non lo attiveranno.
                    /// </summary>
                    public string AggiornaTuttoWeekEnd
                    {
                        get { return GetString("AGGIORNA_TUTTO_WEEK_END"); }
                        set { SetString("AGGIORNA_TUTTO_WEEK_END", value); }
                    }

                    /// <summary>
                    /// Indicare il numero di minuti dopo i quali l'aggiornamento deve staccare nuovamente il token, serve per non far scadere il token nei casi di allineamenti molto lenti. Se non valorizzato non sarà fatta alcuna check token
                    /// </summary>
                    public string MinCheckToken
                    {
                        get { return GetString("MIN_CHECK_TOKEN"); }
                        set { SetString("MIN_CHECK_TOKEN", value); }
                    }
				}
			}
			