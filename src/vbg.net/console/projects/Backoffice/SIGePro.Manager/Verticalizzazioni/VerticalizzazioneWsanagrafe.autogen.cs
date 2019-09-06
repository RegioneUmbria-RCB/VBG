
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione WSANAGRAFE il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attiva, nella pagina dell'inserimento dell'anagrafe, sarà visibile un pulsante per la ricerca di informazioni relative a una specifica partita iva o codice fiscale. Le informazioni lette dal WS(specificato nei parametri) saranno riportate nel form di inserimento.
				/// </summary>
				public partial class VerticalizzazioneWsanagrafe : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "WSANAGRAFE";

                    public VerticalizzazioneWsanagrafe()
                    {

                    }

					public VerticalizzazioneWsanagrafe(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Percorso completo da cui caricare gli assembly contenenti le classi searcher. Se lasciato vuoto o non impostato verrà utilizzata la cartella ~/Bin dell'applicazione. Normalmente viene utilizzato solo nell'ambiente di sviluppo interno.
					/// </summary>
					public string AssemblyLoadPath
					{
						get{ return GetString("ASSEMBLY_LOAD_PATH");}
						set{ SetString("ASSEMBLY_LOAD_PATH" , value); }
					}
					
					/// <summary>
					/// Indica se sulla pagina di inserimento di una nuova anagrafica deve essere attiva la funzionalità di ricerca di informazioni relative al codice fiscale passato  per le persone fisiche. I possibili valori sono: 0 disattiva e 1 attiva
					/// </summary>
					public string EscludiRicercaPerPf
					{
						get{ return GetString("ESCLUDI_RICERCA_PER_PF");}
						set{ SetString("ESCLUDI_RICERCA_PER_PF" , value); }
					}
					
					/// <summary>
					/// Nome dell'assembly che contiene il componente di ricerca. L'assembly deve implementare nel namespace di root una classe AnagrafeSearcher che eredita da SIGePro.Net.WebServices.WsSIGeProAnagrafe.AnagrafeSearcherBase. Viene utilizzato solo se il componente di ricerca deve ricercare le anagrafiche su DB esternia SIGePro, Es. a firenze CCIAA.
					/// </summary>
					public string SearchComponent
					{
						get{ return GetString("SEARCH_COMPONENT");}
						set{ SetString("SEARCH_COMPONENT" , value); }
					}
					
					/// <summary>
					/// (OBSOLETO : sostituito dai parametri URL_RICERCA_PF e URL_RICERCA_PG). URL completo del componente Anagrafe Seacher, se presente andrà a sovrascrivere quello di default che punta al compomente dotNet. Es. http://<ip-servert>:<port>/webapp/services/name_services?wsdl (http://devel9:8080/nla-pdd-ri/services/anagrafe?wsdl).
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// URL completo del componente WS Anagrafe per la ricerca di una persona Fisica, se presente andrà a sovrascrivere quello di default che punta al componente doNet. Es. http://<ip-servert>:<port>/webapp/services/name_services?wsdl (http://devel9:8080/nla-pdd-ri/services/anagrafe?wsdl)
					/// </summary>
					public string UrlRicercaPf
					{
						get{ return GetString("URL_RICERCA_PF");}
						set{ SetString("URL_RICERCA_PF" , value); }
					}
					
					/// <summary>
					/// URL completo del componente WS Anagrafe per la ricerca di una persona Giuridica, se presente andrà a sovrascrivere quello di default che punta al componente doNet. Es. http://<ip-servert>:<port>/webapp/services/name_services?wsdl (http://devel9:8080/nla-pdd-ri/services/anagrafe?wsdl)
					/// </summary>
					public string UrlRicercaPg
					{
						get{ return GetString("URL_RICERCA_PG");}
						set{ SetString("URL_RICERCA_PG" , value); }
					}
					
					
				}
			}
			