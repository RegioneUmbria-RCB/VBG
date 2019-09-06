
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione C4RETESUAP il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Abilita la gestione del progetto c4 REGIONE TOSCANA, necessita dell'installazione 
/// di un Componente java (progettoC4) in un container tomcat e della creazione di uno schema
/// per gestire i dati e le viste - referente Bocci Riccardo, Mirko Calandrini
				/// </summary>
				public partial class VerticalizzazioneC4retesuap : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "C4RETESUAP";

                    public VerticalizzazioneC4retesuap()
                    {

                    }

					public VerticalizzazioneC4retesuap(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Localizzazione del file WSDL per il recupero dei metodi del web service
					/// </summary>
					public string Nomefilewsdl
					{
						get{ return GetString("NOMEFILEWSDL");}
						set{ SetString("NOMEFILEWSDL" , value); }
					}
					
					/// <summary>
					/// URL di accesso al pannello di controllo
					/// </summary>
					public string Urlwebapp
					{
						get{ return GetString("URLWEBAPP");}
						set{ SetString("URLWEBAPP" , value); }
					}
					
					/// <summary>
					/// Indirizzo del web services d contattare
					/// </summary>
					public string Wsaddress
					{
						get{ return GetString("WSADDRESS");}
						set{ SetString("WSADDRESS" , value); }
					}
					
					/// <summary>
					/// Password di accesso per la cooperazione applicativa
					/// </summary>
					public string Wspwd
					{
						get{ return GetString("WSPWD");}
						set{ SetString("WSPWD" , value); }
					}
					
					/// <summary>
					/// Login di accesso per la cooperazione applicativa
					/// </summary>
					public string Wsuser
					{
						get{ return GetString("WSUSER");}
						set{ SetString("WSUSER" , value); }
					}
					
					
				}
			}
			