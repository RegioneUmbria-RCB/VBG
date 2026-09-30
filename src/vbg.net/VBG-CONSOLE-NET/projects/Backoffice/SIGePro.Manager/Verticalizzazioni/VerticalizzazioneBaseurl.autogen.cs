
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione BASEURL il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 allora nei parametri può essere specificato l'url dell'applicazione ASPNET ASPNETURL(che verrà utilizzata per invocare le pagine aspx dalle pagine asp) e l'url dell'applicazione asp ASPURL che verrà utlizzato dall'applicazione aspnet per invocare le pagine asp. Quando specificato sostituisce i parametri ASPNET del webconfig e l'application("AspNetBASEURL") dell'applicazione asp.Può essere utilizzato per le installazioni in https.
				/// </summary>
				public partial class VerticalizzazioneBaseurl : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "BASEURL";

                    public VerticalizzazioneBaseurl()
                    {

                    }

					public VerticalizzazioneBaseurl(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Url dell'applicazione aspnet che verrà utilizzata per invocare le pagine aspx dal browser client. Non va specificata la barra finale (/) nell'indirizzo. Es. "http://devel3.init.gruppoinit.it/aspnet"
					/// </summary>
					public string Aspneturl
					{
						get{ return GetString("ASPNETURL");}
						set{ SetString("ASPNETURL" , value); }
					}
					
					/// <summary>
					/// Url dell'applicazione java che verrà utilizzata per invocare le pagine jsp dal browser client. Non va specificata la barra finale (/) nell'indirizzo. Es. "http://demo.gruppoinit.it:8080/sigepro2dev"
					/// </summary>
					public string Javaurl
					{
						get{ return GetString("JAVAURL");}
						set{ SetString("JAVAURL" , value); }
					}
					
					
				}
			}
			