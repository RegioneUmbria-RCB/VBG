
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_EGRAMMATA il 26/08/2014 17.28.01
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato consente di eseguire la protocollazione con il protocollo EGRAMMATA, al momento non abbiamo a disposizioni ambienti di test.
				/// </summary>
				public partial class VerticalizzazioneProtocolloEgrammata : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_EGRAMMATA";

                    public VerticalizzazioneProtocolloEgrammata()
                    {

                    }

					public VerticalizzazioneProtocolloEgrammata(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// [Facoltativo] Indica l'identificativo della postazione di lavoro dell'utente nel caso in cui l'utente stesso abbia più postazioni di lavoro, questo parametro è dello stesso tipo del parametro LIVELLI_UNITA, in questo caso è possibile usare indifferentemente uno o entrambi i parametri se richiesti.
					/// </summary>
					public string IdUnita
					{
						get{ return GetString("ID_UNITA");}
						set{ SetString("ID_UNITA" , value); }
					}
					
					/// <summary>
					/// [Facoltativo] Indica il livello della postazione di lavoro dell'utente nel caso in cui l'utente stesso abbia più postazioni di lavoro, questo parametro è dello stesso tipo del parametro ID_UNITA, in questo caso è possibile usare indifferentemente uno o entrambi i parametri se richiesti.
					/// </summary>
					public string LivelliUnita
					{
						get{ return GetString("LIVELLI_UNITA");}
						set{ SetString("LIVELLI_UNITA" , value); }
					}
					
					/// <summary>
					/// Indicare qui la password dell'utente con cui invocare il sistema di protocollazione, le credenziali sono le stesse degli utenti già presenti nel software di protocollo.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Indicare il registro da usare per eseguire l'inserimento dei protocolli. Valori ammessi: PG=Protocollo Generale; PP=Protocollo Particolare; R=Repertorio; N=Corrispondenza non protocollata
					/// </summary>
					public string Registro
					{
						get{ return GetString("REGISTRO");}
						set{ SetString("REGISTRO" , value); }
					}
					
					/// <summary>
					/// Indicare l'url del web service da contattare per eseguire la lettura di un protocollo con il sistema di protocollazione e-grammata
					/// </summary>
					public string UrlLeggi
					{
						get{ return GetString("URL_LEGGI");}
						set{ SetString("URL_LEGGI" , value); }
					}
					
					/// <summary>
					/// Indicare l'url del web service da contattare per eseguire l'inserimento di un protocollo con il sistema di protocollazione e-grammata
					/// </summary>
					public string UrlProto
					{
						get{ return GetString("URL_PROTO");}
						set{ SetString("URL_PROTO" , value); }
					}
					
					/// <summary>
					/// Indicare qui l'userid dell'utente con cui invocare il sistema di protocollazione, le credenziali sono le stesse degli utenti già presenti nel software di protocollo.
					/// </summary>
					public string UserId
					{
						get{ return GetString("USER_ID");}
						set{ SetString("USER_ID" , value); }
					}
					
					
				}
			}
			