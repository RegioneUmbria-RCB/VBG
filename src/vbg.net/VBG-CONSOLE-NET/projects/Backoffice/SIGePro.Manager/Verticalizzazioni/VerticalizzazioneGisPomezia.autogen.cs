
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione GIS_POMEZIA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Exportazione Dati, funzionalità specifica (esporta 4 file - istanze,istanzestradario, istanzemappali, istanzedettaglioattività per quelle registrate con dettaglio = TT)
				/// </summary>
				public partial class VerticalizzazioneGisPomezia : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "GIS_POMEZIA";

                    public VerticalizzazioneGisPomezia()
                    {

                    }

					public VerticalizzazioneGisPomezia(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			