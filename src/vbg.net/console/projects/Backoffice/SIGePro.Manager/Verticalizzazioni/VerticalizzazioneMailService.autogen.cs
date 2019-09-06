
			using System;
			using System.IO;
			using Init.SIGePro.Data;
			using PersonalLib2.Data;

			namespace Init.SIGePro.Verticalizzazioni
			{
				/**************************************************************************************************************************************
				*
				* Classe generata automaticamente dalla verticalizzazione MAIL_SERVICE il 26/08/2014 17.23.45
				* NON MODIFICARE DIRETTAMENTE!!!
				*
				***************************************************************************************************************************************/
			
			
				/// <summary>
				/// Regola letta dall'applicazione MailService per personalizzare alcune funzionalita' in fase di invio mail.
				/// </summary>
				public partial class VerticalizzazioneMailService : Verticalizzazione
				{
					private const string NOME_VERTICALIZZAZIONE = "MAIL_SERVICE";
					
					public VerticalizzazioneMailService(string idComuneAlias, string software ) : base(idComuneAlias, NOME_VERTICALIZZAZIONE , software ){}
					
					
					/// <summary>
					/// Parametro per istruire il tipo di ricevuta (breve, sintetica, completa) che il gestore PEC deve generare. I valori ammessi sono: [breve] la ricevuta breve di avvenuta consegna include il testo del messaggio originale, mentre eventuali file allegati sono sostituiti con file di testo contenenti hash crittografici. [sintetica] contiene unicamente i dati di certificazione (mittente, destinatario, oggetto, riferimenti temporali e identificativi del messaggio), in modo analogo alla precedente, ma non riporta né il testo del messaggio originale né i file allegati (permette la verifica dei soli contenuti trasmessi nell'oggetto). [completa] questo tipo di ricevuta consiste in un messaggio di posta elettronica che riporta sia in formato leggibile, sia all'interno di un file xml allegato, i dati di certificazione (mittente, destinatario, oggetto, riferimenti temporali e identificativo del messaggio) e gli allegati originali. Il valore di default se non specificato e' "completa" .
					/// </summary>
					public string TipoRicevuta
					{
						get{ return GetString("TIPO_RICEVUTA");}
						set{ SetString("TIPO_RICEVUTA" , value); }
					}
					
					
				}
			}
			