
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_ESC il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che il sit ESC è attivo.
				/// </summary>
				public partial class VerticalizzazioneSitEsc : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_ESC";

                    public VerticalizzazioneSitEsc()
                    {

                    }

					public VerticalizzazioneSitEsc(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione del db le cui viste vengono interrogate
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Provider  del db le cui viste vengono interrogate
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					/// <summary>
					/// Url del web service da invocare
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					
				}
			}
			