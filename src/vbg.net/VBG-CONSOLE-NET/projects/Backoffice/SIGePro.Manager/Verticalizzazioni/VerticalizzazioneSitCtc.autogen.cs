
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_CTC il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che il sit CTC è attivo.
				/// </summary>
				public partial class VerticalizzazioneSitCtc : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_CTC";

                    public VerticalizzazioneSitCtc()
                    {

                    }

					public VerticalizzazioneSitCtc(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Stringa di connessione del db le cui viste vengono interrogate o del db collegato al server in cui sono presenti le viste
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// Db in cui sono presenti le viste
					/// </summary>
					public string Database
					{
						get{ return GetString("DATABASE");}
						set{ SetString("DATABASE" , value); }
					}
					
					/// <summary>
					/// Provider  del db le cui viste vengono interrogate  o del db collegato al server in cui sono presenti le viste
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					/// <summary>
					/// Nome del proprietario delle viste
					/// </summary>
					public string Schema
					{
						get{ return GetString("SCHEMA");}
						set{ SetString("SCHEMA" , value); }
					}
					
					/// <summary>
					/// Server in cui sono presenti le viste
					/// </summary>
					public string Server
					{
						get{ return GetString("SERVER");}
						set{ SetString("SERVER" , value); }
					}
					
					/// <summary>
					/// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da sezione, foglio e particella
					/// </summary>
					public string Urlcartmappale
					{
						get{ return GetString("URLCARTMAPPALE");}
						set{ SetString("URLCARTMAPPALE" , value); }
					}
					
					
				}
			}
			