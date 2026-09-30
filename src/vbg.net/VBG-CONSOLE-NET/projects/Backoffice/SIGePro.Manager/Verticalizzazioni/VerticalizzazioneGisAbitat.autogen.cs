
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione GIS_ABITAT il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato permette di visualizzare la localizzazione dell'istanza nel SIT di ABITAT
				/// </summary>
				public partial class VerticalizzazioneGisAbitat : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "GIS_ABITAT";

                    public VerticalizzazioneGisAbitat()
                    {

                    }

					public VerticalizzazioneGisAbitat(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Url a cui fare la richiesta per la visualizzazione della localizzazione dell'istanza
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					
				}
			}
			