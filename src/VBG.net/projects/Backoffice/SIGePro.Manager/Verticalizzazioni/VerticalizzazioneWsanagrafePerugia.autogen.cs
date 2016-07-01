
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSANAGRAFE_PERUGIA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Parametri di configurazione dell'oggetto responsabile per le ricerche anagrafiche del comune di PERUGIA
				/// </summary>
				public partial class VerticalizzazioneWsanagrafePerugia : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE_PERUGIA";

                    public VerticalizzazioneWsanagrafePerugia()
                    {

                    }

					public VerticalizzazioneWsanagrafePerugia(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' la ulr per accedere ai servizi PARIX. Es. https://servizicner.regione.emilia-romagna.it/parixgate/services/gate?wsdl
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					
				}
			}
			