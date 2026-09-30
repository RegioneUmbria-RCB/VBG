
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_DEFAULT il 26/08/2014 17.28.02
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// E' il protocollo utilizzato in ambiente di sviluppo e debug per testare l'applicativo e visualizzare come vengono serializzate le classi del protocollo su .net
				/// </summary>
				public partial class VerticalizzazioneProtocolloDefault : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_DEFAULT";

                    public VerticalizzazioneProtocolloDefault()
                    {

                    }

					public VerticalizzazioneProtocolloDefault(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					
				}
			}
			