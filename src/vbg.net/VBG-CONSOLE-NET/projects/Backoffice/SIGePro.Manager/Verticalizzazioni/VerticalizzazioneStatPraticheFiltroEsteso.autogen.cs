
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione STAT_PRATICHE_FILTRO_ESTESO il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva permette di abilitare il checkbox (FILTROALMENOUNA) presente nella pagina CL_STATPRATICHESELEZIONE che, se spuntato, permette di filtrare le attività a condizione che almeno una delle istanze della catena appartenga ai filtri di selezione.Se non spuntato le attività selezionate sono quelle che hanno l'istanza della catena con data di efficacia maggiore appartenente ai filtri di selezione.
				/// </summary>
				public partial class VerticalizzazioneStatPraticheFiltroEsteso : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "STAT_PRATICHE_FILTRO_ESTESO";

                    public VerticalizzazioneStatPraticheFiltroEsteso()
                    {

                    }

					public VerticalizzazioneStatPraticheFiltroEsteso(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					
				}
			}
			