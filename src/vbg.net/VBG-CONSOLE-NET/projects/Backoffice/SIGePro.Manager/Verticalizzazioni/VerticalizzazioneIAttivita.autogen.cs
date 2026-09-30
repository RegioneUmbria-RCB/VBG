
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione I_ATTIVITA il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se la verticalizzazione è attiva, permette la gestione delle attività ( intesa come catena di istanze ).
				/// </summary>
				public partial class VerticalizzazioneIAttivita : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "I_ATTIVITA";

                    public VerticalizzazioneIAttivita()
                    {

                    }

					public VerticalizzazioneIAttivita(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Può assumere i seguenti valori: S o N.
/// Se valorizzato con S ad ogni operazione svolta sull'attività di collegamento / scollegamento istanze verrà ricalcolata la denominazione.
/// Se valorizzato con N la denominazione dell'attività rimane quella originaria.
					/// </summary>
					public string Aggiornadenominazione
					{
						get{ return GetString("AGGIORNADENOMINAZIONE");}
						set{ SetString("AGGIORNADENOMINAZIONE" , value); }
					}
					
					/// <summary>
					/// E' possibile indicare la lista dei moduli SOFTWARE che hanno la gestione ATTIVITA' in comune. Ad esempio per attivare la gestione attività condivisa tra CO e PE devo creare due VERTICALIZZAZIONI I_ATTIVITA una con software CO e una con software PE ed aggiungere ad ognuna di esse un PARAMETRO che sia "CO,PE".
					/// </summary>
					public string Grupposoftware
					{
						get{ return GetString("GRUPPOSOFTWARE");}
						set{ SetString("GRUPPOSOFTWARE" , value); }
					}
					
					/// <summary>
					/// Valori possibili:
/// 1 - In fase di collegamento di un'istanza ad un'attività precompila i campi di ricerca INDIRIZZO e CIVICO con l'indirizzo è il civico del'istanza.
/// 0 o vuoto - Non effettua nessuna precompilazione.
					/// </summary>
					public string Precompilaindirizzocivico
					{
						get{ return GetString("PRECOMPILAINDIRIZZOCIVICO");}
						set{ SetString("PRECOMPILAINDIRIZZOCIVICO" , value); }
					}
					
					/// <summary>
					/// Tramite questo parametro è possibile specificare la query che verrà utilizzata per creare / aggiornare la denominazione dell'attività.
/// Nella query è possibile utilizzare le seguenti variabili:
/// 	&IDCOMUNE => Viene valorizzato con l'idcomune dell'istanza
/// 	&CODICEISTANZA => Viene valorizzato con il codiceistanza
/// Nel caso il parametro non sia valorizzato o la query non ritorni record, vale la regola iniziale di sigepro sotto riportata::
///    1. Se il campo "Denominazone Attività" dell'istanza è valorizzato viene utilizzato come denominazione
///    2. Se non è valorizzata la "Denominazione Attività" viene utilizzata la "Ragione Sociale" dell'istanza
///    3. Se non sono valorizzate ne la "Denominazione Attività" ne "Ragione Sociale" viene utilizzato il richiedente. 
/// ATTENZIONE: La query dovrebbe sempre ritornare una sola riga con una sola colonna ( non importa il nome del campo in quanto l¿accesso avviene per indice ), nel caso sia presente una query che ritorna più di una riga viene utilizzato il primo record letto!!!!!
					/// </summary>
					public string Querydenominazione
					{
						get{ return GetString("QUERYDENOMINAZIONE");}
						set{ SetString("QUERYDENOMINAZIONE" , value); }
					}
					
					
				}
			}
			