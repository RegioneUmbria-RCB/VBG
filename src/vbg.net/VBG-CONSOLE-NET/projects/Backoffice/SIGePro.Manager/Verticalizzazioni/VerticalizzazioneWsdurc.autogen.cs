
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSDURC il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Modulo per attivare le funzionalità di verifica dei documenti DURC esistenti e la richiesta di nuovi.
				/// </summary>
				public partial class VerticalizzazioneWsdurc : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WSDURC";

                    public VerticalizzazioneWsdurc()
                    {

                    }

					public VerticalizzazioneWsdurc(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' il nome del servizio che fornisce le informazioni del DURC (WSDURC_INFODURC, ...)
					/// </summary>
					public string ServizioAttivo
					{
						get{ return GetString("SERVIZIO_ATTIVO");}
						set{ SetString("SERVIZIO_ATTIVO" , value); }
					}
					
					/// <summary>
					/// E' la tipologia di documento che al quale associare il DURC o le richieste di verifica; viene anche utilizzata per determinare la data di scadenza qualora non presente nelle informazioni restituite.
					/// </summary>
					public string TipodocAnagrafe
					{
						get{ return GetString("TIPODOC_ANAGRAFE");}
						set{ SetString("TIPODOC_ANAGRAFE" , value); }
					}
					
					
				}
			}
			