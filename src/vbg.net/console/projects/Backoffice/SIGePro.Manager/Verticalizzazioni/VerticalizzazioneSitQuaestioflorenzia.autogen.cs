
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione SIT_QUAESTIOFLORENZIA il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se 1 indica che il sit QUAESTIOFLORENZIA è attivo.
				/// </summary>
				public partial class VerticalizzazioneSitQuaestioflorenzia : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "SIT_QUAESTIOFLORENZIA";

                    public VerticalizzazioneSitQuaestioflorenzia() : base()
                    {

                    }

					public VerticalizzazioneSitQuaestioflorenzia(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// contiene il valore dell’id del ruolo che permette all’operatore che ne fa parte di poter eseguire operazioni di editino sul componente cartografico
					/// </summary>
					public string CodRuoloEditing
					{
						get{ return GetString("COD_RUOLO_EDITING");}
						set{ SetString("COD_RUOLO_EDITING" , value); }
					}
					
					/// <summary>
					/// contiene il valore da assegnare ad ogni livello gestito da SIGePro (verrà configurato per ogni software)
					/// </summary>
					public string LayerKey
					{
						get{ return GetString("LAYER_KEY");}
						set{ SetString("LAYER_KEY" , value); }
					}
					
					/// <summary>
					/// contiene il valore da assegnare al pannello che raccoglie tutti i livelli utili a gestire le procedure che vengono trattate tramite SIGePro (verrà configurato una sola volta per il software TT)
					/// </summary>
					public string PanelKey
					{
						get{ return GetString("PANEL_KEY");}
						set{ SetString("PANEL_KEY" , value); }
					}
					
					/// <summary>
					/// contiene il valore da assegnare al modo con cui rappresentare le attività/pratiche attive di ciascun software. (verrà configurato per ogni software)
					/// </summary>
					public string RendererKeyAttivo
					{
						get{ return GetString("RENDERER_KEY_ATTIVO");}
						set{ SetString("RENDERER_KEY_ATTIVO" , value); }
					}
					
					/// <summary>
					/// contiene il valore da assegnare al modo con cui rappresentare le attività/pratiche cessate di ciascun software. (verrà configurato per ogni software)
					/// </summary>
					public string RendererKeyCessato
					{
						get{ return GetString("RENDERER_KEY_CESSATO");}
						set{ SetString("RENDERER_KEY_CESSATO" , value); }
					}
					
					/// <summary>
					/// Url del web service da invocare
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
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
					/// E' l'url da utilizzare per richiamare l'applicativo cartografico a partire da colore e civico
					/// </summary>
					public string Urlcartstradario
					{
						get{ return GetString("URLCARTSTRADARIO");}
						set{ SetString("URLCARTSTRADARIO" , value); }
					}
					
					/// <summary>
					/// Url da utilizzare per evitare problemi di cross-domain con il componente GeoIn
					/// </summary>
					public string UrlProxy
					{
						get{ return GetString("URL_PROXY");}
						set{ SetString("URL_PROXY" , value); }
					}
					
					
				}
			}
			