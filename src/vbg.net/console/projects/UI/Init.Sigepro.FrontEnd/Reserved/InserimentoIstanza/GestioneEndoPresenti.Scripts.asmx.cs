using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Web.Script.Services;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    /// <summary>
    /// Summary description for GestioneAllegatiPresenti.Scripts
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class GestioneEndoPresenti_Scripts : System.Web.Services.WebService {

		[Inject]
		public IEndoprocedimentiService _endoprocedimentiService { get; set; }

		public GestioneEndoPresenti_Scripts()
		{
			FoKernelContainer.Inject(this);
		}

        [WebMethod]
		[ScriptMethod]
		public object GetStringaCampiRichiesti(int idTipoTitolo)
		{

			var tipoTitolo = this._endoprocedimentiService.GetTipoTitoloById(idTipoTitolo);

			var campi = GetCampiRichiesti(tipoTitolo.Flags);

			return new
			{
				idTipoTitolo = idTipoTitolo,
				messaggio = GetStringaCampiRichiesti(campi),
				richiedeData = tipoTitolo.Flags.MostraData,
				richiedeNumero = tipoTitolo.Flags.MostraNumero,
				richiedeRilasciatoDa = tipoTitolo.Flags.MostraRilasciatoDa
			};
        }

		private static object GetStringaCampiRichiesti(IEnumerable<string> campi)
		{
			if (campi.Count() == 0)
				return "";

			if (campi.Count() == 1)
				return $"Il campo \"{campi.First()}\" è obbligatorio";

			if (campi.Count() == 2)
				return $"I campi \"{campi.ElementAt(0)}\" e \"{campi.ElementAt(1)}\" sono obbligatori";

			return $"I campi \"{campi.ElementAt(0)}\", \"{campi.ElementAt(1)}\" e \"{campi.ElementAt(2)}\" sono obbligatori";
		}

		private IEnumerable<string> GetCampiRichiesti(TipiTitoloDtoFlags flags)
		{
			if (flags.MostraData)
				yield return "data";

			if (flags.MostraNumero)
				yield return "numero";

			if (flags.MostraRilasciatoDa)
				yield return "rilasciato da";
		}
    }
}
