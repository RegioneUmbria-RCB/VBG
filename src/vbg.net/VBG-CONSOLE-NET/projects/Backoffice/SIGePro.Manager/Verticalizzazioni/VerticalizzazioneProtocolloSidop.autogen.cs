
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIDOP il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// SIDOP è il sistema di protocollazione presente al Comune di Perugia, è l'unico sistema di protocollo che non passa per web service ma vengono fatte delle chiamate direttamente al base dati.
				/// </summary>
				public partial class VerticalizzazioneProtocolloSidop : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIDOP";

                    public VerticalizzazioneProtocolloSidop()
                    {

                    }

					public VerticalizzazioneProtocolloSidop(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Anno del fascicolo per la protocollazione SIDOP
					/// </summary>
					public string Annofasc
					{
						get{ return GetString("ANNOFASC");}
						set{ SetString("ANNOFASC" , value); }
					}
					
					/// <summary>
					/// Stringa di connessione del DB in cui risiede la stored procedure per la protocollazione SIDOP
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Identificativo dell'indice per la protocollazione SIDOP
					/// </summary>
					public string Idind
					{
						get{ return GetString("IDIND");}
						set{ SetString("IDIND" , value); }
					}
					
					/// <summary>
					/// Percorso completo dell'eseguibile utilizzato per la stampa delle etichette
					/// </summary>
					public string Pathprinterexe
					{
						get{ return GetString("PATHPRINTEREXE");}
						set{ SetString("PATHPRINTEREXE" , value); }
					}
					
					/// <summary>
					/// Progressivo del fascicolo per la protocollazione SIDOP
					/// </summary>
					public string Progrfasc
					{
						get{ return GetString("PROGRFASC");}
						set{ SetString("PROGRFASC" , value); }
					}
					
					/// <summary>
					/// Provider del DB in cui risiede la stored procedure per la protocollazione SIDOP
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					/// <summary>
					/// Nome della stored procedure da eseguire per la stampa delle etichette
					/// </summary>
					public string StoredprocedureEtic
					{
						get{ return GetString("STOREDPROCEDURE_ETIC");}
						set{ SetString("STOREDPROCEDURE_ETIC" , value); }
					}
					
					/// <summary>
					/// Nome della stored procedure da eseguire per la protocollazione SIDOP
					/// </summary>
					public string StoredprocedureProt
					{
						get{ return GetString("STOREDPROCEDURE_PROT");}
						set{ SetString("STOREDPROCEDURE_PROT" , value); }
					}
					
					/// <summary>
					/// E' il nome della vista dalla quale si ricavano le informazioni sull'utente che intende protocollare
					/// </summary>
					public string View
					{
						get{ return GetString("VIEW");}
						set{ SetString("VIEW" , value); }
					}
					
					
				}
			}
			