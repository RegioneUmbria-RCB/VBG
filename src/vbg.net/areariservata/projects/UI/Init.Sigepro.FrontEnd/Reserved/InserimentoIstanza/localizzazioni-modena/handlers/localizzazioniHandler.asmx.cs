using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Services;
using System.Web.Services;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.localizzazioni_modena.hendlers
{
    /// <summary>
    /// Summary description for localizzaizoniHandler
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class localizzaizoniHandler : Ninject.Web.WebServiceBase
    {
        [Inject]
        public ILocalizzazioniService _localizzazioniService { get; set; }

        public class LocalizzazioneModena
        {
            public string codCatastale { get; set; }
            public string foglio { get; set; }
            public string particella { get; set; }
        }

        public class OpzioniDefault
        {
            public int idStradarioDefault { get; set; }
            public string idCatastoDefault { get; set; }
            public string nomeCatastoDefault { get; set; }
        }


        [ScriptMethod(ResponseFormat = ResponseFormat.Json)]
        [WebMethod(EnableSession = true)]
        public object ModificaLocalizzazioni(int idDomanda, OpzioniDefault opzioniDefault, IEnumerable<LocalizzazioneModena> localizzazioni)
        {
            var stradario = this._localizzazioniService.GetById(opzioniDefault.idStradarioDefault);

            if (stradario == null)
            {
                throw new Exception($"Codice stradario {opzioniDefault.idStradarioDefault} non trovato");
            }

            this._localizzazioniService.EliminaLocalizzazioni(idDomanda);

            foreach (var item in localizzazioni)
            {
                var localizzazione = new NuovaLocalizzazione(stradario, "");
                var rifCatastale = new NuovoRiferimentoCatastale(opzioniDefault.idCatastoDefault, opzioniDefault.nomeCatastoDefault, item.foglio, item.particella);

                this._localizzazioniService.AggiungiLocalizzazione(idDomanda, localizzazione ,rifCatastale);
            }

            return new
            {
                result = "OK"
            };
        }
    }
}
