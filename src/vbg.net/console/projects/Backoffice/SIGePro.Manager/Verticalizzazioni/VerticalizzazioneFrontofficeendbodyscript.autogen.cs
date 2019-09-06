
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione FRONTOFFICEENDBODYSCRIPT il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva viene aggiunto il contenuto del parametro SCRIPT in fondo alle pagine DEFAULT.ASP,HOMESUAP.ASP,INFOHOME.ASP e CONSULTA del front office prima del tag </Body>. E' stato richiesto da firenze per fare statistiche con Google Analitycs.
				/// </summary>
				public partial class VerticalizzazioneFrontofficeendbodyscript : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "FRONTOFFICEENDBODYSCRIPT";

                    public VerticalizzazioneFrontofficeendbodyscript()
                    {

                    }

					public VerticalizzazioneFrontofficeendbodyscript(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' lo script javascript da eseguire. Es. <script type="text/javascript">alert("ciao");</script>
					/// </summary>
					public string Script
					{
						get{ return GetString("SCRIPT");}
						set{ SetString("SCRIPT" , value); }
					}
					
					
				}
			}
			