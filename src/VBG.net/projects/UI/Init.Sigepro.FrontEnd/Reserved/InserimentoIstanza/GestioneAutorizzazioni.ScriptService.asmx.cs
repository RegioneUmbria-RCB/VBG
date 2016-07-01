using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Web.Script.Services;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAutorizzazioniMercati;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    /// <summary>
    /// Summary description for GestioneAutorizzazioni.ScriptService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	[ScriptService]
    public class GestioneAutorizzazioniScriptService : System.Web.Services.WebService {

		[Inject]
		protected AutorizzazioniMercatiService _autorizzazioniMercatiService { get; set; }

        [WebMethod]
		[ScriptMethod]
		public int GetNumeroPresenze(string idComune,int idDomanda,int idAutorizzazione)
		{
			FoKernelContainer.Inject(this);

			return this._autorizzazioniMercatiService.GetAutorizzazione(idDomanda, idAutorizzazione).NumeroPresenze;
        }

		[WebMethod]
		[ScriptMethod]
		public IEnumerable<object> GetElencoEnti(string partialMatch)
		{
			FoKernelContainer.Inject(this);

			var upperPartial = partialMatch.ToUpperInvariant();

			return this._autorizzazioniMercatiService
						.GetEnti()
						.Where( x => x.Descrizione.ToUpperInvariant().IndexOf(upperPartial) >=0)
						.Select(x => new { Codice = x.Codice, Descrizione = x.Descrizione });
		}

    }
}
