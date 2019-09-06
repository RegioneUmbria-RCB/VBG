
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione MOSTRA_AUTOR_ONERI_LISTA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva, nella lista degli oneri di un'istanza, sotto il richiedente compaiono le informazioni relative al registro specificato nei parametri della verticalizzazione
				/// </summary>
				public partial class VerticalizzazioneMostraAutorOneriLista : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "MOSTRA_AUTOR_ONERI_LISTA";

                    public VerticalizzazioneMostraAutorOneriLista()
                    {

                    }

					public VerticalizzazioneMostraAutorOneriLista(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Label che descrive l'informazione
					/// </summary>
					public string Label
					{
						get{ return GetString("LABEL");}
						set{ SetString("LABEL" , value); }
					}
					
					/// <summary>
					/// Codice del registro da far comparire
					/// </summary>
					public string Registro
					{
						get{ return GetString("REGISTRO");}
						set{ SetString("REGISTRO" , value); }
					}
					
					
				}
			}
			