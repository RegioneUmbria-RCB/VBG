using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;
using System.Globalization;

namespace Init.Sigepro.FrontEnd.Public.WebServices
{
	/// <summary>
	/// Summary description for ValidazioneCfService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[System.Web.Script.Services.ScriptService]
	public class ValidazioneCfService : System.Web.Services.WebService
	{

		[WebMethod(EnableSession = true)]
		[ScriptMethod]
		public object ValidaCF(string cognome, string nome, string dataNascita, string sesso, string comuneNascita, string codiceFiscale)
		{
			if (String.IsNullOrEmpty(cognome))
				return new { Errore = true, DescrizioneErrore = "Specificare il cognome" };

			if (String.IsNullOrEmpty(nome))
				return new { Errore = true, DescrizioneErrore = "Specificare il nome" };

			DateTime date = DateTime.MinValue;

			if(!DateTime.TryParseExact( dataNascita , "dd/MM/yyyy", null , DateTimeStyles.None, out date ))
				return new { Errore = true, DescrizioneErrore = "Data di nascita non valida" };

			if (String.IsNullOrEmpty(sesso))
				return new { Errore = true, DescrizioneErrore = "Specificare il sesso" };

			if (String.IsNullOrEmpty(comuneNascita))
				return new { Errore = true, DescrizioneErrore = "Specificare il comune di nascita" };

			var calcolatoreCf = new InIT.CodiceFiscale();
			var cfCalcolato = calcolatoreCf.calcolaCF(cognome, nome, date, sesso, comuneNascita);

			// Dava errore a perugia con i dati provenienti dall'anagrafica comunale
			if (cfCalcolato.Length > 15)
			{
				// Modificata verifica del codice fiscale, prima era basata solo sulle prime 15 cifre, 
				// ora il controllo è stato esteso a tutte le 16 cifre visto che la verifica non è bloccante
				/* ERA:
				 * var cfCalcolatoOrig = cfCalcolato;
				 * cfCalcolato = cfCalcolato.Substring(0, 15);
				 * var cfConfronto = codiceFiscale.Substring(0, 15);
				 */
				var cfCalcolatoOrig = cfCalcolato;
				var cfConfronto = codiceFiscale;

				if (cfCalcolato.ToUpperInvariant().CompareTo(cfConfronto.ToUpperInvariant()) != 0)
				{
					return new { Errore = false, EsitoValidazione = false, MessaggioValidazione = "Il codice fiscale calcolato in base ai dati immessi (" + cfCalcolatoOrig.ToUpper() + ") non coincide con il codice fiscale specificato (" + codiceFiscale.ToUpper() + "). <br//><br//> E' comunque possibile proseguire con l'inserimento dell'anagrafica se si è certi della correttezza dei dati immessi." };
				}
			}

			return new { Errore = false, EsitoValidazione = true };
		}
	}
}
