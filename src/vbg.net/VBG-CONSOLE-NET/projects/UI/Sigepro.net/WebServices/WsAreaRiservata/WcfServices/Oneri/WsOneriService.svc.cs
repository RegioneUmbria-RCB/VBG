using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.DTO.Oneri;
using Init.SIGePro.Manager.Logic.ServiziConsole;
using Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri;
using Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri.GestioneConti;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Oneri
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsOneriService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsOneriService.svc or WsOneriService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsOneriService : WcfServiceBase, IWsOneriService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsOneriService));

        public List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo, string codiceComuneAssociato)
        {
            AuthenticationInfo ai = this.CheckToken(token);

            try
            {
                using (DataBase db = ai.CreateDatabase())
                {
                    var consoleService = new ConsoleService(db, ai.Alias);
                    var oneriService = new OneriConsoleService(db, consoleService);

                    return oneriService.GetListaOneriDaIdInterventoECodiciEndo(codiceIntervento, listaIdEndo, codiceComuneAssociato).ToList();
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a GetListaOneriDaIdInterventoECodiciEndo({0}, {1}, {2}): {3}", codiceIntervento, string.Join(", ", listaIdEndo), codiceComuneAssociato, ex);
                throw;
            }
        }

        //public string GetCodiceCausaleOnereTraslazione(string token, int idCausale)
        //{
        //    var ai = this.CheckToken(token);

        //    using (var db = ai.CreateDatabase())
        //    {
        //        var mgr = new TipiCausaliOneriMgr(db);
        //        var causale = mgr.GetById(ai.IdComune, idCausale);
        //        if (causale == null)
        //        {
        //            return "";
        //        }

        //        return causale.MappaturaNodoPagamenti;
        //    }
        //}

        public ContoDto GetContoDaIdCausaleOnere(string token, string software, string codiceComune, int idCausale)
        {
            try
            {
                AuthenticationInfo ai = this.CheckToken(token);

                using (DataBase db = ai.CreateDatabase())
                {
                    var consoleService = new ConsoleService(db, ai.Alias);
                    var contiService = new ContiService(db, consoleService);
                    return contiService.GetContoDaIdCausaleOnere(codiceComune, software, idCausale);
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nella chiamata a GetContoDaIdCausaleOnere({0}, {1}, {2}): {3}", software, codiceComune, idCausale, ex);

                throw;
            }
        }
    }
}
