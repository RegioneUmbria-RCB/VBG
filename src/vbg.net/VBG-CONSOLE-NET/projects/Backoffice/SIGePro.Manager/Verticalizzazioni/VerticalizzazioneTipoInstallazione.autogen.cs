
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione TIPO_INSTALLAZIONE il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata allora deve essere specificato attraverso il parametro TIPO se l'installazione è di tipo ENTERPRISE o STANDARD. Di default l'installazione è considerata ENTERPRISE
				/// </summary>
				public partial class VerticalizzazioneTipoInstallazione : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "TIPO_INSTALLAZIONE";

                    public VerticalizzazioneTipoInstallazione()
                    {

                    }

					public VerticalizzazioneTipoInstallazione(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Identifica se l'installazione è di tipo STANDARD o ENTERPRISE. Di default se non specificato l'installazione è considerata ENTERPRISE
					/// </summary>
					public string Tipo
					{
						get{ return GetString("TIPO");}
						set{ SetString("TIPO" , value); }
					}
					
					
				}
			}
			