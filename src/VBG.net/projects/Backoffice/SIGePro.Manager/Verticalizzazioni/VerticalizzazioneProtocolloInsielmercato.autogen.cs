
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_INSIELMERCATO il 26/08/2014 17.28.00
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se attivato, consente di eseguire la protocollazione con il sistema di INSIEL MERCATO (INSIEL MERCATO è il fornitore del software), deve essere anche inserita la stringa PROTOCOLLO_INSIELMERCATO nel parametro TIPOPROTOCOLLO della verticalizzazione PROTOCOLLO_ATTIVO. L'integrazione con questo protocollo, al momento, è presente al Comune di Fondi. Questo protocollo è simile al protocollo Insiel in quanto un tempo erano la stessa azienda prima della scissione dove sono nate le due aziende, una pubblica (Insiel Mercato) e una privata (Insiel).
				/// </summary>
				public partial class VerticalizzazioneProtocolloInsielmercato : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_INSIELMERCATO";

                    public VerticalizzazioneProtocolloInsielmercato()
                    {

                    }

					public VerticalizzazioneProtocolloInsielmercato(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software, codiceComune){}
					
					
					/// <summary>
					/// Questo parametro indica se deve essere considerata la classifica o meno, indicare 1 se si desidera escludere la classifica, 0 o non presente se si desidera inviare la classifica. Questo parametro è stato inserito perchè il dato non è specificato come obbligatorio dal web service, mentre è obbligatorio per noi, è quindi necessario configurare comunque la classifica a livello di backoffice (può andare bene anche un punto o un trattino) al fine di superare la nostra validazione interna che, come detto, rende obbligatorio questo dato. Successivamente, il componente incaricato di adattare i dati per il web service interrogherà questo parametro inviando la classifica o meno al web service di protocollazione.
					/// </summary>
					public string EscludiClassifica
					{
						get{ return GetString("ESCLUDI_CLASSIFICA");}
						set{ SetString("ESCLUDI_CLASSIFICA" , value); }
					}
					
					/// <summary>
					/// In base a quanto indicato dai fornitori del software (INSIEL MERCATO) valorizzare questo parametro solamente in base al codice proprio della sequenza: 
/// "La sequenza di protocollazione viene identificata da 
/// - codice dell’ufficio “titolare” del registro (es. GEN)
/// - codice del registro (es. GEN)
/// - codice proprio della sequenza (es. BASE, COMPLETA, IOP, ecc..).
/// In funzione di ogni modalita’ operativa prevista (inserimento/aggiornamento/annullamento in partenza ed in arrivo) esiste almeno una sequenza di dati (identificata quindi dai 3 codici suddetti) che possono/devono essere inseriti/modificati in base alla modalita’ prescelta. 
/// In particolare possono esistere molteplici sequenze a livello di un unico Registro (come nel caso appena descritto) personalizzate per l’utente (create ed abilitate quindi a livello di amministratore di sistema) che permettono di differenziare le tipologie di dati da trattare in base alle necessita’ e quindi rendere alcuni campi obbligatori, altri facoltativi, altri ancora inibiti.
					/// </summary>
					public string ModalitaSequenza
					{
						get{ return GetString("MODALITA_SEQUENZA");}
						set{ SetString("MODALITA_SEQUENZA" , value); }
					}
					
					/// <summary>
					/// Valore obbligatorio, fa parte, con il parametro USERNAME, al login al webservice, indica con quale password bisogna collegarsi al web service.
					/// </summary>
					public string Password
					{
						get{ return GetString("PASSWORD");}
						set{ SetString("PASSWORD" , value); }
					}
					
					/// <summary>
					/// Endpoint del web service di INSIEL MERCATO, va inserito l'indirizzo http completo del wsdl. Il dato è ovviamente obbligatorio, per l'ambiente di test si può invocare l'url http://demo.insielmercato.it/protocollo-ws/services/protocolloService?wsdl
					/// </summary>
					public string Url
					{
						get{ return GetString("URL");}
						set{ SetString("URL" , value); }
					}
					
					/// <summary>
					/// Indica se per il recupero delle classifiche, sull'albero degli interventi, debba essere usato il web service messo a disposizione da insiel mercato (valore = 1) oppure no (valore 0, vuoto o non presente). E' stato deciso di inserire questo parametro in quanto recuperare le classifiche da web service potrebbe essere macchinoso rendendo la pagina del dettaglio degli interventi molto lenta se i dati dovessero essere molti come in ambiente di test. Oltre tutto i dati (sempre che siano tanti quanti l'ambiente di test) potrebbero creare una lunga lista difficilmente leggibile dagli utenti. In ultimo, se dovesso essere modificati i codici, questi non sarebbero più direttamente visibili sul campo Classifica nel dettaglio degli interventi in quanto non più presenti nella lista.
					/// </summary>
					public string UsaWsClassifiche
					{
						get{ return GetString("USA_WS_CLASSIFICHE");}
						set{ SetString("USA_WS_CLASSIFICHE" , value); }
					}
					
					/// <summary>
					/// Valore obbligatorio, fa parte, con il parametro PASSWORD, al login al webservice, indica con quale username bisogna collegarsi al web service.
					/// </summary>
					public string Username
					{
						get{ return GetString("USERNAME");}
						set{ SetString("USERNAME" , value); }
					}
					
					
				}
			}
			