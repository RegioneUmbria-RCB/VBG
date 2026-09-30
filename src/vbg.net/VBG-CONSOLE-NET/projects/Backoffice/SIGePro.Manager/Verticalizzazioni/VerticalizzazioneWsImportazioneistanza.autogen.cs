
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WS_IMPORTAZIONEISTANZA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Contiene alcuni parametri che determinano il comportamento di un'istanza importata tramite il Web Service ImportazioneIstanza.asmx.
				/// </summary>
				public partial class VerticalizzazioneWsImportazioneistanza : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WS_IMPORTAZIONEISTANZA";

                    public VerticalizzazioneWsImportazioneistanza()
                    {

                    }

					public VerticalizzazioneWsImportazioneistanza(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Imposta lo stato dell'istanza in base al codice indicato nel parametro. I possibili valori sono quelli contenuti in STATIISTANZA
					/// </summary>
					public string Statoistanza
					{
						get{ return GetString("STATOISTANZA");}
						set{ SetString("STATOISTANZA" , value); }
					}
					
					
				}
			}
			