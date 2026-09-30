
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione FILESYSTEM_CMIS il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata, tutti i documenti dell'applicativo verranno salvati su Sistema di gestione documentale esterno che espone interfacce CMIS (Content Management Interoperability Services) es. <b>ALFRESCO</b> e non più su database. Dopo aver attivato questa funzionalità bisogna trasferire su disco tutti gli oggetti attualmente presenti su DB, utilizzando l'utility  o dalla voce di menù "utilità/Spostamento oggetti su systema CMIS". L'attivazione è valida solo per il software TT. Se attivata, tutti i documenti dell'applicativo verranno salvati su Sistema che espone interfacce CMIS Esterno all'applicativo., La funzionalità non può essere attivata contemporaneamente a quella su FILESYSTEM
				/// </summary>
				public partial class VerticalizzazioneFilesystemCmis : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "FILESYSTEM_CMIS";

                    public VerticalizzazioneFilesystemCmis()
                    {

                    }

					public VerticalizzazioneFilesystemCmis(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Rappresenta il percorso servizio di pubblicazione CMIS es: http://<server>:<port>/alfresco/s/cmis.
					/// </summary>
					public string CmisAtomUrl
					{
						get{ return GetString("CMIS_ATOM_URL");}
						set{ SetString("CMIS_ATOM_URL" , value); }
					}
					
					/// <summary>
					/// Rappresenta la directory a partire dalla quale inserire i file mediante le interfacce del servizio di pubblicazione CMIS
					/// </summary>
					public string CmisDocumentRootFolder
					{
						get{ return GetString("CMIS_DOCUMENT_ROOT_FOLDER");}
						set{ SetString("CMIS_DOCUMENT_ROOT_FOLDER" , value); }
					}
					
					/// <summary>
					/// Rappresenta la password dell' utente abilitato ad operare sulle interfacce del servizio di pubblicazione CMIS
					/// </summary>
					public string CmisPassword
					{
						get{ return GetString("CMIS_PASSWORD");}
						set{ SetString("CMIS_PASSWORD" , value); }
					}
					
					/// <summary>
					/// Rappresenta l'identificativo utente abilitato ad operare sulle interfacce del servizio di pubblicazione CMIS
					/// </summary>
					public string CmisUsername
					{
						get{ return GetString("CMIS_USERNAME");}
						set{ SetString("CMIS_USERNAME" , value); }
					}
					
					
				}
			}
			