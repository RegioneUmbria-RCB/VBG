
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione IMPORT_CCIAA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivata abilita la voce di menù Utility / Import dati / CCIAA che permette l'importazione da file .CSV ad un db CCIAA dal quale si accederà per leggere le anagrafiche della camera di commercio normalizzate. In genere sono i metodi del web service WSSIGEPROClient.GetAnagrafe.asmx che leggeranno le informazioni dalle tabelle CCIAA.
				/// </summary>
				public partial class VerticalizzazioneImportCciaa : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "IMPORT_CCIAA";

                    public VerticalizzazioneImportCciaa()
                    {

                    }

					public VerticalizzazioneImportCciaa(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// E' la stringa di connessione al database normalizzato importato dalla camera di commercio.
					/// </summary>
					public string Connectionstring
					{
						get{ return GetString("CONNECTIONSTRING");}
						set{ SetString("CONNECTIONSTRING" , value); }
					}
					
					/// <summary>
					/// E' il provider per la connessione al database della camera di commercio ( utile per istanziare la PersonalLib2). I valori che può assumere a seconda della stringa di connessione, sono: OleDb, OracleClient, PervasiveClient o SqlClient. 
					/// </summary>
					public string Provider
					{
						get{ return GetString("PROVIDER");}
						set{ SetString("PROVIDER" , value); }
					}
					
					
				}
			}
			