
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione NLA-RICEZIONE-PEC il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Se abilitato, il nodo nla-pec processa le PEC presenti nelle caselle di posta certificata dei rispettivi software (questo modulo va attivato per i singoli software e non per il software TT)
				/// </summary>
				public partial class VerticalizzazioneNlaricezionepec : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "NLA-RICEZIONE-PEC";

                    public VerticalizzazioneNlaricezionepec()
                    {

                    }

					public VerticalizzazioneNlaricezionepec(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// I valori ammessi sono 0,1 o 2. Se posto a 0 o non valorizzato vengono lette tutte le mail non lette presenti nella INBOX (comportamento di default), se posto a 1 legge tutte le mail presenti nella INBOX, se posto a 2 legge tutte le mail presenti nella INBOX arrivate nell’ultima settimana 
					/// </summary>
					public string StrategiaDiLetturaMessaggi
					{
						get{ return GetString("STRATEGIA_DI_LETTURA_MESSAGGI");}
						set{ SetString("STRATEGIA_DI_LETTURA_MESSAGGI" , value); }
					}
					
					/// <summary>
					/// Tale parametro viene utilizzato per configurare richiedente della pratica, deve essere valorizzato con il codice di un richiedente presente nell'archivio dei richiedenti (persona fisica); nel caso tale parametro mancasse, il sistema  cercherà di inserire come richiedente la persona giuridica presente nell'oggetto della mail (in questo caso però il back office deve essere precedentemente configurato in maniera tale da poter accettare come richiedente anche una persona giuridica).
					/// </summary>
					public string RichiedenteDefault
					{
						get{ return GetString("RICHIEDENTE_DEFAULT");}
						set{ SetString("RICHIEDENTE_DEFAULT" , value); }
					}
					
					/// <summary>
					/// I valori ammessi sono 0, 1 o 2. Se posto a 0 non processa i messaggi PEC provenienti dalla Camera di Commercio, se posto a 1 processa le PEC formattate secondo lo standard nazionale della Camera di Commercio, se posto a 2 processa le PEC formattate secondo lo standard della Lombardia 
					/// </summary>
					public string PecComunicaCameracom
					{
						get{ return GetString("PEC_COMUNICA_CAMERACOM");}
						set{ SetString("PEC_COMUNICA_CAMERACOM" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono S o N. Se posto a S processa i messaggi di PEC provenienti dall'Area Riservata, se posto a N non vengono processati questa tipologia di messaggi
					/// </summary>
					public string PecAreariservata
					{
						get{ return GetString("PEC_AREARISERVATA");}
						set{ SetString("PEC_AREARISERVATA" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono S o N. Se posto a S processa i messaggi di PEC inviati direttamente dai Cittadini/Imprese (formattati in base al DPR160), se posto a N non vengono processati questa tipologia di messaggi
					/// </summary>
					public string PecCittadino
					{
						get{ return GetString("PEC_CITTADINO");}
						set{ SetString("PEC_CITTADINO" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono S o N. Se posto a S processa i messaggi provenienti dagli enti terzi (formattati in base al DPR160) ; se posto a N non vengono processati questa tipologia di messaggi
					/// </summary>
					public string PecEntiterzi
					{
						get{ return GetString("PEC_ENTITERZI");}
						set{ SetString("PEC_ENTITERZI" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono S o N. Se posto a S vengono elaborati tutti i messaggi di notifica presenti nella casella PEC ; se posto a N le notifiche non vengono gestite
					/// </summary>
					public string PecRicevuteControllo
					{
						get{ return GetString("PEC_RICEVUTE_CONTROLLO");}
						set{ SetString("PEC_RICEVUTE_CONTROLLO" , value); }
					}
					
					/// <summary>
					/// Valori ammessi sono null o indirizzo email . Se valorizzato con un indirizzo email, alla fine dell'elaborazione  viene mandata una mail di riepilogo delle attività svolte all'indirizzo specificato; se non valorizzato non viene inviata nessuna mail di riepilogo
					/// </summary>
					public string PecMailDestRiepilogo
					{
						get{ return GetString("PEC_MAIL_DEST_RIEPILOGO");}
						set{ SetString("PEC_MAIL_DEST_RIEPILOGO" , value); }
					}
					
					/// <summary>
					/// Se posto a S invia gli allegati al web service di notifica PEC, altrimenti il nodo nla-pec non invierà gli allegati al web service.
					/// </summary>
					public string NotificaConAllegati
					{
						get{ return GetString("NOTIFICA_CON_ALLEGATI");}
						set{ SetString("NOTIFICA_CON_ALLEGATI" , value); }
					}
					
					/// <summary>
					/// Può assumere i valori S o N, se posto a S l'applicativo controlla se l'oggetto della PEC soddisfa l'espressione regolare (indicata nel parametro PEC_NON_FORMATTATA_FILTRO) e nel caso tale controllo abbia esito positivo, si procede alla creazione di una nuova pratica con le informazioni contenute in tale messaggio.
					/// </summary>
					public string PecNonFormattata
					{
						get{ return GetString("PEC_NON_FORMATTATA");}
						set{ SetString("PEC_NON_FORMATTATA" , value); }
					}
					
					/// <summary>
					/// Tale parametro deve contenere l'espressione regolare per individuare quali messaggi processare secondo la logica indicata nel parametro PEC_NON_FORMATTATA.
					/// </summary>
					public string PecNonFormattataFiltro
					{
						get{ return GetString("PEC_NON_FORMATTATA_FILTRO");}
						set{ SetString("PEC_NON_FORMATTATA_FILTRO" , value); }
					}
					
					/// <summary>
					/// Tale parametro deve contenere l’ID di un richiedente già censito nell'applicativo che comparirà come richiedente della pratica secondo la logica indicata nel parametro PEC_NON_FORMATTATA.
					/// </summary>
					public string PecNonFormattataAnagRic
					{
						get{ return GetString("PEC_NON_FORMATTATA_ANAG_RIC");}
						set{ SetString("PEC_NON_FORMATTATA_ANAG_RIC" , value); }
					}
					
					/// <summary>
					/// Dopo ogni processamento se non presente (o = S) viene inserito un evento che riporta in descrizione il numero di messaggi da leggere presenti nella casella PEC alla <data/ore> in cui è stata eseguita la funzionalità di processaMessaggi
					/// </summary>
					public string InviaEventoNMsgNonletti
					{
						get{ return GetString("INVIA_EVENTO_N_MSG_NONLETTI");}
						set{ SetString("INVIA_EVENTO_N_MSG_NONLETTI" , value); }
					}
					
					/// <summary>
					/// I valori ammessi sono S o N. Se posto a S per ogni PEC presente nella INBOX controlla se si tratta di una Reply-to relativa ad una PEC inviata da VBG. 
					/// </summary>
					public string CheckReply
					{
						get{ return GetString("CHECK_REPLY");}
						set{ SetString("CHECK_REPLY" , value); }
					}
					
					
				}
			}
			