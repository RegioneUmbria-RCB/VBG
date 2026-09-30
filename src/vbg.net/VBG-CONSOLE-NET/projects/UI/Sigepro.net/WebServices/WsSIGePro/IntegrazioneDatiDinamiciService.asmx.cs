using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for IntegrazioneDatiDinamiciService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class IntegrazioneDatiDinamiciService : SigeproWebService
	{

		public class RichiestaIntegrazioneDatiDinamici
		{
			public int IdIstanza { get; set; }
			public bool ElaboraScriptCaricamento { get; set; }
			public bool ElaboraScriptModifica { get; set; }
			public bool ElaboraScriptSalvataggio { get; set; }
		}


		public class RispostaIntegrazioneDatiDinamici
		{
			public bool IntegrazioneEffettuata { get; set; }
			public List<string> ListaErrori { get; set; }

			public RispostaIntegrazioneDatiDinamici()
			{
				ListaErrori = new List<string>();
			}
		}



		[WebMethod]
		public RispostaIntegrazioneDatiDinamici EffettuaIntegrazioneDatiDinamici(string token , RichiestaIntegrazioneDatiDinamici options)
		{
            throw new NotImplementedException();
		}

		private Dictionary<string, string> ParseListaValori(string listaValori)
		{
			var rVal = new Dictionary<string, string>();

			var elems = listaValori.Split(';');

			for (int i = 0; i < elems.Length; i++)
			{
				var parts = elems[i].Split('$');

				if (parts.Length > 1)
					rVal.Add(parts[0], parts[1]);
				else
					rVal.Add(elems[i], elems[i]);
			}

			return rVal;

		}
	}
}
