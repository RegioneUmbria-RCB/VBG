using System;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.Web;
using System.Diagnostics;

namespace Init.SIGePro.Utils
{
	/// <summary>
	/// Responsabile del logging degli eventi 
	/// </summary>
	public class Logger
	{
		protected Logger()
		{
		}

		/// <summary>
		/// Scrive nel log l'evento specificato.
		/// </summary>
		/// <param name="db">database con la connessione corrente</param>
		/// <param name="idComune">Identificativo univoco del comune</param>
		/// <param name="modulo">Modulo in cui si è verificato l'errore (x es: "SmtpMailSender.SendMail")</param>
		/// <param name="errorMessage">Descrizione estesa dell'errore</param>
		/// <param name="codiceErrore">Codice dell'errore se è già stato codificato altrimenti sentire Chiocci ;)</param>
		public static void LogEvent(DataBase db , string idComune, string modulo, string errorMessage, string codiceErrore)
		{
			LogSuap logEntry = new LogSuap();

			logEntry.DATA = DateTime.Now.ToString("yyyyMMdd");
			logEntry.ORA = DateTime.Now.ToString("HH.mm");
			logEntry.IDCOMUNE = idComune;
			logEntry.MESSAGGIO = modulo + ": " + errorMessage;
			logEntry.CODICEERRORE = codiceErrore;

			new LogSuapMgr( db ).Insert(logEntry);
		}

		public static void LogRichiesta(DataBase db, string idComune, string codiceRichiesta, string messaggio)
		{
			LogSuap logEntry = new LogSuap();

			logEntry.DATA = DateTime.Now.ToString("yyyyMMdd");
			logEntry.ORA = DateTime.Now.ToString("HH.mm");
			logEntry.IDCOMUNE = idComune;
			logEntry.MESSAGGIO = messaggio;
			logEntry.CODICERICHIESTA = codiceRichiesta;

			if (HttpContext.Current != null)
			{
				logEntry.IP = HttpContext.Current.Request.UserHostAddress;
			}

			if (!String.IsNullOrEmpty(db.ConnectionDetails.Token))
			{
				AuthenticationInfo authInfo = AuthenticationManager.CheckToken(db.ConnectionDetails.Token);

                if (authInfo != null && authInfo.CodiceResponsabile.GetValueOrDefault(int.MinValue) != int.MinValue)
				{
					Responsabili r = new ResponsabiliMgr(db).GetById(authInfo.CodiceResponsabile.ToString(), idComune);

					if (r != null)
					{
						logEntry.OPERATORE = r.CODICERESPONSABILE + " - " + r.RESPONSABILE;
					}
				}
			}

			new LogSuapMgr(db).Insert(logEntry);
		}

		/// <summary>
		/// Scrive nel log l'evento specificato.
		/// </summary>
		/// <param name="authInfo">Credenziali di accesso</param>
		/// <param name="modulo">Modulo in cui si è verificato l'errore (x es: "SmtpMailSender.SendMail")</param>
		/// <param name="errorMessage">Descrizione estesa dell'errore</param>
		/// <param name="codiceErrore">Codice dell'errore se è già stato codificato altrimenti sentire Chiocci ;)</param>
		public static void LogEvent( AuthenticationInfo authInfo, string modulo, string errorMessage, string codiceErrore)
		{
			LogEvent( authInfo.CreateDatabase() , authInfo.IdComune , modulo , errorMessage , codiceErrore );
		}

        public static void LogEventViewer(string modulo, string errorMessage)
        {
			try
			{
				EventLog.WriteEntry(modulo, errorMessage, EventLogEntryType.Error);
			}
			catch (Exception ex)
			{ 
				
			}
        }
	}
}
