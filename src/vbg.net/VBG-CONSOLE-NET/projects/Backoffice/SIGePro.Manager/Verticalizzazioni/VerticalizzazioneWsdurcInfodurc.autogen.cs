
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSDURC_INFODURC il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Servizio di cooperazione applicativa per gestire le funzionalità di verifica e la richiesta dei documenti DURC implementato da NUOVAINFORMATICA per l'Osservatorio Cantieri Cassa Edile
				/// </summary>
				public partial class VerticalizzazioneWsdurcInfodurc : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WSDURC_INFODURC";

                    public VerticalizzazioneWsdurcInfodurc()
                    {

                    }

					public VerticalizzazioneWsdurcInfodurc(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Codice della Cassa Edile che fornisce il servizio (Es PR00); è comunicato dalla Cassa Edile.
					/// </summary>
					public string Institutecode
					{
						get{ return GetString("INSTITUTECODE");}
						set{ SetString("INSTITUTECODE" , value); }
					}
					
					/// <summary>
					/// Identificativo del software richiedente; il codice è rilasciato dal gestore tecnico del servizio Osservatorio (Nuova Informatica)
					/// </summary>
					public string Softwarecodeid
					{
						get{ return GetString("SOFTWARECODEID");}
						set{ SetString("SOFTWARECODEID" , value); }
					}
					
					/// <summary>
					/// Indirizzo del wsdl dei servizi INFODURC ES:(STAGE: http://osservatorio.cassaedileweb.it/ServizioDURCTest/WSDurcServiceCE.asmx?WSDL, PRODUZIONE: https://osservatorio.cassaedileweb.it/ServizioDURC/WSDurcServiceCE.asmx?WSDL). Per la produzione è necessario installare nella jvm i certificati rilasciati da NUOVAINFORMATICA
					/// </summary>
					public string UrlwsServiziodurc
					{
						get{ return GetString("URLWS_SERVIZIODURC");}
						set{ SetString("URLWS_SERVIZIODURC" , value); }
					}
					
					/// <summary>
					/// Codice Utente autorizzato ad utilizzare i servizi ISG dell'Osservatorio Cantieri
					/// </summary>
					public string Userid
					{
						get{ return GetString("USERID");}
						set{ SetString("USERID" , value); }
					}
					
					/// <summary>
					/// Password dell' utente autorizzato ad utilizzare i servizi ISG dell'Osservatorio Cantieri
					/// </summary>
					public string Userpassword
					{
						get{ return GetString("USERPASSWORD");}
						set{ SetString("USERPASSWORD" , value); }
					}
					
					
				}
			}
			