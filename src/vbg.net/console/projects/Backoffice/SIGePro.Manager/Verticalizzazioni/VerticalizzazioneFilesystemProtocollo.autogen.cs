
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione FILESYSTEM_PROTOCOLLO il 26/08/2014 17.23.44
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata, tutti i documenti del Protocollo Informatico verranno salvati su disco e non più su database. Dopo aver attivato la verticalizzazione è necessario eseguire l'applicativo ALLINEAOGGETTIPROTOCOLLO.exe che serve per spostare tutti gli oggetti da DB a filesystem o viceversa
				/// </summary>
				public partial class VerticalizzazioneFilesystemProtocollo : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "FILESYSTEM_PROTOCOLLO";

                    public VerticalizzazioneFilesystemProtocollo()
                    {

                    }

					public VerticalizzazioneFilesystemProtocollo(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Rappresenta il percorso alla directory del server dove vengono salvati/letti i file riguardanti il protocollo.
					/// </summary>
					public string DirectoryLocale
					{
						get{ return GetString("DIRECTORY_LOCALE");}
						set{ SetString("DIRECTORY_LOCALE" , value); }
					}
					
					
				}
			}
			