using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Xml.Serialization;
using System.IO;
using log4net;

namespace Init.Sigepro.FrontEnd.LR13.Utils
{
	public partial class DatiCompilazioneForm
	{

		protected static string GetNomeFileConfigurazione(string idComune, string software, int codiceIntervento)
		{
			var logger = LogManager.GetLogger(typeof(DatiCompilazioneForm));

			logger.DebugFormat("Verifica dell'esistenza di un file di configurazione per l'idcomune {0}, software {1} e codiceIntervento {2}", idComune , software , codiceIntervento);

			string fileName = "~/" + idComune + "_" + software + "_" + codiceIntervento.ToString() + "_FormCompilazione.xml";

			if (File.Exists(HttpContext.Current.Server.MapPath(fileName)))
				return fileName;

			logger.DebugFormat("Il file {0} non è stato trovato" , fileName);

			fileName = "~/" + software + "_" + codiceIntervento.ToString() + "_FormCompilazione.xml";

			if (File.Exists(HttpContext.Current.Server.MapPath(fileName)))
				return fileName;

			logger.DebugFormat("Il file {0} non è stato trovato", fileName);

			fileName = "~/LR13_" + codiceIntervento.ToString() + "_FormCompilazione.xml";

			if (File.Exists(HttpContext.Current.Server.MapPath(fileName)))
				return fileName;

			logger.DebugFormat("Il file {0} non è stato trovato", fileName);

			fileName = String.Empty;
			
			return fileName;
		}

		public static bool VerificaEsistenzaFileConfigurazione(string idComune, string software, int codiceIntervento)
		{
			string fileName = GetNomeFileConfigurazione(idComune, software, codiceIntervento);

			return !String.IsNullOrEmpty(fileName);
		}

		public static DatiCompilazioneForm Load(string idComune, string software, int codiceIntervento)
		{
			var logger = LogManager.GetLogger(typeof(DatiCompilazioneForm));


			string fileName = GetNomeFileConfigurazione(idComune, software, codiceIntervento);

			if (String.IsNullOrEmpty(fileName))
			{
				logger.ErrorFormat("Non è stato trovato un file di configurazione per l'idcomune {0}, software {1} e codiceIntervento {2}", idComune , software , codiceIntervento);

				throw new ConfigurationErrorsException("Impossibile trovare il file di configurazione per il form di compilazione dati LR13");
			}

			DatiCompilazioneForm dcf = null;

			using (FileStream fs = File.OpenRead(HttpContext.Current.Server.MapPath(fileName)))
			{
				XmlSerializer xs = new XmlSerializer(typeof(DatiCompilazioneForm));
				dcf = (DatiCompilazioneForm)xs.Deserialize(fs);
			}

			return dcf;
		}
	}
}
