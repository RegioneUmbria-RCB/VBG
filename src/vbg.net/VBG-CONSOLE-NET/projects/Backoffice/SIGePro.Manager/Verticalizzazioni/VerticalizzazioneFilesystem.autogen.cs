
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione FILESYSTEM il 26/08/2014 17.23.46
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Attenzione!!! Questa funzionalità va attivata solo da personale esperto di In.I.T.. Se attivata, tutti i documenti di SIGePro verranno salvati su disco e non più su database. Dopo aver attivato questa funzionalità bisogna trasferire su disco tutti gli oggetti attualmente presenti su DB, utilizzando l'utility "SpostaOggettiSuSharedPath.exe" o dalla voce di menù "utilità/Spostamento oggetti su file system". L'attivazione è valida solo per il software TT.
				/// </summary>
				public partial class VerticalizzazioneFilesystem : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "FILESYSTEM";

                    public VerticalizzazioneFilesystem()
                    {

                    }

					public VerticalizzazioneFilesystem(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Rappresenta il percorso alla directory del server dove vengono salvati/letti i file.
					/// </summary>
					public string DirectoryLocale
					{
						get{ return GetString("DIRECTORY_LOCALE");}
						set{ SetString("DIRECTORY_LOCALE" , value); }
					}
					
					/// <summary>
					/// Accetta valori 0 o 1. Nel caso che sia settato il valore 1 allora solamente i file che hanno percorso valorizzato vengono letti da filesystem ed i nuovi file vengono scritti nella colonna BLOB della tabella oggetti
					/// </summary>
					public string Readonly
					{
						get{ return GetString("READONLY");}
						set{ SetString("READONLY" , value); }
					}
					
					/// <summary>
					/// Percorso condiviso al quale ha accesso sia l'application SIGePro del server che i client, Es. \\fileserver\sigeprodoc.
					/// </summary>
					public string Sharedpath
					{
						get{ return GetString("SHAREDPATH");}
						set{ SetString("SHAREDPATH" , value); }
					}
					
					
				}
			}
			