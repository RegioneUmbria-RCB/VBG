
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_INITMAPGUIDE il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che il sit INIT_MAPGUIDE è attivo.
				/// </summary>
				public partial class VerticalizzazioneSitInitmapguide : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_INITMAPGUIDE";

                    public VerticalizzazioneSitInitmapguide()
                    {

                    }

					public VerticalizzazioneSitInitmapguide(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione del db le cui tabelle vengono interrogate
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Provider  del db le cui tabelle vengono interrogate
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da codice viario e civico
					/// </summary>
					public string Urlcartstradario
					{
						get{ return GetString("URLCARTSTRADARIO");}
						set{ SetString("URLCARTSTRADARIO" , value); }
					}
					
					
				}
			}
			