
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione ACQUISIZIONE_TWAIN il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Modulo che permette di acquisire i documenti dell istanza tramite una fonte twain
				/// </summary>
				public partial class VerticalizzazioneAcquisizioneTwain : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "ACQUISIZIONE_TWAIN";

                    public VerticalizzazioneAcquisizioneTwain()
                    {

                    }

					public VerticalizzazioneAcquisizioneTwain(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			