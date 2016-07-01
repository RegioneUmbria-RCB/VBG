using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject.Web;
using Init.Sigepro.FrontEnd.AppLogic.TestErroriDomanda;
using Ninject;

namespace Init.Sigepro.FrontEnd
{
	public partial class TestErrori : PageBase
	{
		[Inject]
		public Bug580_richiedente_tecnico_azienda_compaiono_anche_nei_soggetti_colelgati Bug580_richiedente_tecnico_azienda_compaiono_anche_nei_soggetti_colelgati { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			var idComune = "E256";
			var software = "SS";
			var idDomanda = 438;

			Bug580_richiedente_tecnico_azienda_compaiono_anche_nei_soggetti_colelgati.Test(idComune, software, idDomanda);
		}
	}
}