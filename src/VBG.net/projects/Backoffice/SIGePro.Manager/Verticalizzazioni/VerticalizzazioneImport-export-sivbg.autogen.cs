
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione IMPORT-EXPORT-SIVBG il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato, sarà possibile effettuare l'import/export degli interventi e dei procedimenti con il Repertorio della regione Umbria
				/// </summary>
				public partial class VerticalizzazioneImportexportsivbg : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "IMPORT-EXPORT-SIVBG";
					
					public VerticalizzazioneImportexportsivbg(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Tale parametro deve contenere la URL del web service che la regione Umbria espone per effettuare le operazioni di import/export dei procedimenti
					/// </summary>
					public string UrlWsRepertorio
					{
						get{ return GetString("URL_WS_REPERTORIO");}
						set{ SetString("URL_WS_REPERTORIO" , value); }
					}
					
					
				}
			}
			