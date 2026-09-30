
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_7DBTL il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che il sit 7DBTL è attivo.
				/// </summary>
				public partial class VerticalizzazioneSit7dbtl : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_7DBTL";

                    public VerticalizzazioneSit7dbtl()
                    {

                    }

					public VerticalizzazioneSit7dbtl(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione del db le cui viste vengono interrogate o del db collegato tramite dblink alla base dati che ha le viste
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Nome del proprietario delle viste/tabelle
					/// </summary>
					public string Owner
					{
						get{ return GetString("OWNER");}
						set{ SetString("OWNER" , value); }
					}
					
					/// <summary>
					/// Provider  del db le cui viste vengono interrogate  o del db collegato tramite dblink alla base dati che ha le viste
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



                    public string UrlZoomDaMappale
                    {
                        get { return GetString("URL_ZOOM_DA_MAPPALE"); }
                        set { SetString("URL_ZOOM_DA_MAPPALE", value); }
                    }

                    public string UrlZoomDaCivico
                    {
                        get { return GetString("URL_ZOOM_DA_CIVICO"); }
                        set { SetString("URL_ZOOM_DA_CIVICO", value); }
                    }
                }
			}
			