using Init.Sigepro.FrontEnd.AppLogic.GestioneInpsInail;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneInpsInail
{
    /// <summary>
    /// Summary description for InpsInailjsService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    [System.Web.Script.Services.ScriptService]
    public class InpsInailjsService : Ninject.Web.WebServiceBase
    {
        [Inject]
        protected IInpsInailService _inpsInailService { get; set; }

        [WebMethod]
        [ScriptMethod]
        public object[] ElencoSediInps(string idComune, string partial)
        {
            return this._inpsInailService
                            .GetSediInps(partial)
                            .Select(x => new { 
                                codice = x.Codice, descrizione = x.Descrizione
                            })
                            .ToArray();
        }

        [WebMethod]
        [ScriptMethod]
        public object[] ElencoSediInail(string idComune, string partial)
        {
            return this._inpsInailService
                            .GetSediInail(partial)
                            .Select(x => new
                            {
                                codice = x.Codice,
                                descrizione = x.Descrizione
                            })
                            .ToArray();
        }
    }
}
