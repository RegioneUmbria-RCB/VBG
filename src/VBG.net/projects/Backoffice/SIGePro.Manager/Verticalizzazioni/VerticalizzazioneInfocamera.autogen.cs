
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione INFOCAMERA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che è possibile inviare automaticamente il file alla Camera del Commercio
				/// </summary>
				public partial class VerticalizzazioneInfocamera : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "INFOCAMERA";

                    public VerticalizzazioneInfocamera()
                    {

                    }

					public VerticalizzazioneInfocamera(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' l'id dell'esportazione configurata per la Camera del Commercio
					/// </summary>
					public string Idesportazione
					{
						get{ return GetString("IDESPORTAZIONE");}
						set{ SetString("IDESPORTAZIONE" , value); }
					}
					
					/// <summary>
					/// E' la password da utilizzare per autenticarsi al sistema di invio automatico
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// E' l'url del ws utilizzato per l'invio automatico
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// E' l'url del ws di esportazione
					/// </summary>
					public string Urlexport
					{
						get{ return GetString("URLEXPORT");}
						set{ SetString("URLEXPORT" , value); }
					}
					
					/// <summary>
					/// E' l'utente da utilizzare per autenticarsi al sistema di invio automatico
					/// </summary>
					public string User
					{
						get{ return GetString("USER");}
						set{ SetString("USER" , value); }
					}
					
					
				}
			}
			