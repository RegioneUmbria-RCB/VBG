
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_NAUTILUS il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che il sit NAUTILUS è attivo.
				/// </summary>
				public partial class VerticalizzazioneSitNautilus : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_NAUTILUS";

                    public VerticalizzazioneSitNautilus()
                    {

                    }

					public VerticalizzazioneSitNautilus(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
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
					/// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da sezione, foglio e particella
					/// </summary>
					public string Urlcartmappale
					{
						get{ return GetString("URLCARTMAPPALE");}
						set{ SetString("URLCARTMAPPALE" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da codice viario e civico
					/// </summary>
					public string Urlcartstradario
					{
						get{ return GetString("URLCARTSTRADARIO");}
						set{ SetString("URLCARTSTRADARIO" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo POC a partire da sezione,foglio e particella
					/// </summary>
					public string Urlpocmappale
					{
						get{ return GetString("URLPOCMAPPALE");}
						set{ SetString("URLPOCMAPPALE" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo POC a partire da codice viario e civico
					/// </summary>
					public string Urlpocstradario
					{
						get{ return GetString("URLPOCSTRADARIO");}
						set{ SetString("URLPOCSTRADARIO" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo RUE a partire da sezione,foglio e particella
					/// </summary>
					public string Urlruemappale
					{
						get{ return GetString("URLRUEMAPPALE");}
						set{ SetString("URLRUEMAPPALE" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo RUE a partire da codice viario e civico
					/// </summary>
					public string Urlruestradario
					{
						get{ return GetString("URLRUESTRADARIO");}
						set{ SetString("URLRUESTRADARIO" , value); }
					}
					
					
				}
			}
			