
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_PINDARO il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// PINDARO è il sistema di protocollazione presente al Comune di Reggio Calabria, gestito da Recasi.
				/// </summary>
				public partial class VerticalizzazioneProtocolloPindaro : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PINDARO";

                    public VerticalizzazioneProtocolloPindaro()
                    {

                    }

					public VerticalizzazioneProtocolloPindaro(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// E' il codice ente per il quale protocollare, deve essere fornito dall'amministratore del protocollo PINDARO.
					/// </summary>
					public string Codiceente
					{
						get{ return GetString("CODICEENTE");}
						set{ SetString("CODICEENTE" , value); }
					}
					
					/// <summary>
					/// E' l'operatore da utilizzare per protocollare con PINDARO, deve essere fornito dall'amministratore del protocollo PINDARO.
					/// </summary>
					public string Operatore
					{
						get{ return GetString("OPERATORE");}
						set{ SetString("OPERATORE" , value); }
					}
					
					/// <summary>
					/// E' la password per protocollare in PINDARO, deve essere fornita dall'amministratore del protocollo PINDARO.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// E' l'URl per invocare il protocollo. Non devono essere specificati i metodi.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					
				}
			}
			