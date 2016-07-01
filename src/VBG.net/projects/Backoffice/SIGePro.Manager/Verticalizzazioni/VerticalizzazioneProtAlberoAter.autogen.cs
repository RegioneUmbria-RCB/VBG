
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROT_ALBERO_ATER il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Visualizza l'albero creato per gestire le esigenze dell'Ater, se non flaggato sarà visualizzato l'albero classico.
				/// </summary>
				public partial class VerticalizzazioneProtAlberoAter : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROT_ALBERO_ATER";

                    public VerticalizzazioneProtAlberoAter()
                    {

                    }

					public VerticalizzazioneProtAlberoAter(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			