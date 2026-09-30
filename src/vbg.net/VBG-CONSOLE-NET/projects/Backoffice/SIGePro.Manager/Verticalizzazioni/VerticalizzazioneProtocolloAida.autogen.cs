
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_AIDA il 26/08/2014 17.28.00
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Il Protocollo AIDA è il protocollo presente al Comune di Livorno, il componente è stato sviluppato nel 2012, lato protocollo il web service è stato sviluppato in ASP, quindi non esistono classi strutturate ma vengono passate solo stringhe.
				/// </summary>
				public partial class VerticalizzazioneProtocolloAida : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_AIDA";

                    public VerticalizzazioneProtocolloAida()
                    {

                    }

					public VerticalizzazioneProtocolloAida(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// (Obbligatorio) Specifica la password, riferita al parametro UTENTE, con cui effettuare la protocollazione.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Specifica quale valore deve assumere il tag POSSESSO riferito all'assegnatario, di default deve essere impostato a 0.
					/// </summary>
					public string Possesso
					{
						get{ return GetString("POSSESSO");}
						set{ SetString("POSSESSO" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Specifica quale valore deve assumere il tag TIPO durante la valorizzazione dell'xml riferito all'inserimento di un allegato (metodo SETDOCNPROT), di default deve assumere il valore 2.
					/// </summary>
					public string TipoAllegati
					{
						get{ return GetString("TIPO_ALLEGATI");}
						set{ SetString("TIPO_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) E' l'URL per invocare il web service del protocollo.
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// (Obbligatorio) Specifica l'utente con cui effettuare la protocollazione.
					/// </summary>
					public string Utente
					{
						get{ return GetString("UTENTE");}
						set{ SetString("UTENTE" , value); }
					}
					
					
				}
			}
			