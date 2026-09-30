
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WS_LOGIN il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivata permette di sovrascrivere la User Id con la quale eseguire la login durante le chiamate via web service
				/// </summary>
				public partial class VerticalizzazioneWsLogin : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WS_LOGIN";

                    public VerticalizzazioneWsLogin()
                    {

                    }

					public VerticalizzazioneWsLogin(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// User Id dell'operatore
					/// </summary>
					public string UseridOperatore
					{
						get{ return GetString("USERID_OPERATORE");}
						set{ SetString("USERID_OPERATORE" , value); }
					}
					
					
				}
			}
			