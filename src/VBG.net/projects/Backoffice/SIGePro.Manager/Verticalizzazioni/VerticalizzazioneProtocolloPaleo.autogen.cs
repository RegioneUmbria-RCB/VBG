
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_PALEO il 26/08/2014 17.28.02
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// PALEO è il protocollo che avrebbe dovuto essere installato alla Provincia di Pesaro, non è stato portato a termine in quanto il cliente ha rinunciato all'integrazione durante lo sviluppo del componente
				/// </summary>
				public partial class VerticalizzazioneProtocolloPaleo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_PALEO";

                    public VerticalizzazioneProtocolloPaleo()
                    {

                    }

					public VerticalizzazioneProtocolloPaleo(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// (Obbligatorio) E' il codice amministrazione da utilizzare per protocollare con PALEO, deve essere fornito dall'amministratore del protocollo PALEO.
					/// </summary>
					public string CodiceAmministrazione
					{
						get{ return GetString("CODICE_AMMINISTRAZIONE");}
						set{ SetString("CODICE_AMMINISTRAZIONE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Indicare il cognome, la ragione sociale o la denominazione in genere del corrispondente.
					/// </summary>
					public string CognomeOperatore
					{
						get{ return GetString("COGNOME_OPERATORE");}
						set{ SetString("COGNOME_OPERATORE" , value); }
					}
					
					/// <summary>
					/// (Opzionale) Indicare il nome o una denominazione aggiuntiva del corrispondente.
					/// </summary>
					public string NomeOperatore
					{
						get{ return GetString("NOME_OPERATORE");}
						set{ SetString("NOME_OPERATORE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' la password per protocollare in PALEO, deve essere fornita dall'amministratore del protocollo PALEO.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Indica il registro su cui effettuare la protocollazione; indicare un registro valido per la UO del richiedente.
					/// </summary>
					public string Registro
					{
						get{ return GetString("REGISTRO");}
						set{ SetString("REGISTRO" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Indica il ruolo dell'operatore abilitato alla protocollazione (parametri NOME_OPERATORE e COGNOME_OPERATORE), deve essere fornito dall'amministratore del protocollo PALEO.
					/// </summary>
					public string RuoloOperatore
					{
						get{ return GetString("RUOLO_OPERATORE");}
						set{ SetString("RUOLO_OPERATORE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Indica l'unità operativa dell'operatore abilitato alla protocollazione (parametri NOME_OPERATORE e COGNOME_OPERATORE), deve essere fornito dall'amministratore del protocollo PALEO.
					/// </summary>
					public string UoOperatore
					{
						get{ return GetString("UO_OPERATORE");}
						set{ SetString("UO_OPERATORE" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' l'URl per invocare il protocollo. Non devono essere specificati i metodi.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Username da utilizzare per protocollare con PALEO, deve essere fornito dall'amministratore del protocollo PALEO.
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					
				}
			}
			