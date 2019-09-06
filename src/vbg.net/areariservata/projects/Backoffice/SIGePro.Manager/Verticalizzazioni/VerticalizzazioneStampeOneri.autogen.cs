
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione STAMPE_ONERI il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva nelle stampe degli oneri verranno stampati anche il numero di autorizzazione e i movimenti indicati nei parametri di questa verticalizzazione.(con le stampe RTF questa verticalizzzazione non ha ragione di esistere).
				/// </summary>
				public partial class VerticalizzazioneStampeOneri : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "STAMPE_ONERI";

                    public VerticalizzazioneStampeOneri()
                    {

                    }

					public VerticalizzazioneStampeOneri(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// I tipi movimento indicati in questo parametro vengono utilizzati per filtrare la lista dei movimenti da mostrare nelle stampe degli oneri. I possibili codici da elencare sono TIPIMOVIMENTO.TIPOMOVIMENTO. I codice indicati devono essere separati da virgola.
					/// </summary>
					public string Listatipimovimento
					{
						get{ return GetString("LISTATIPIMOVIMENTO");}
						set{ SetString("LISTATIPIMOVIMENTO" , value); }
					}
					
					/// <summary>
					/// Indica la lista dei registri dai quali leggere le autorizzazioni che verranno stampate in alcune stampe degli oneri. La lista deve essere separata da virgole.
					/// </summary>
					public string Tipologiaregistri
					{
						get{ return GetString("TIPOLOGIAREGISTRI");}
						set{ SetString("TIPOLOGIAREGISTRI" , value); }
					}
					
					
				}
			}
			