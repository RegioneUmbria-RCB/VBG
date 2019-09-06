using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Oneri;
using System.Collections.Generic;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
    public partial class AreaRiservataServiceBase
    {
        [WebMethod]
        public List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo)
        {
            var ai = CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var endoMgr = new InventarioProcedimentiMgr(db);
                var intervMgr = new AlberoProcMgr(db);

                var rVal = new List<OnereDto>(intervMgr.GetListaOneriDaIdIntervento(ai.IdComune, codiceIntervento));

                for (int i = 0; i < listaIdEndo.Count; i++)
                {
                    var oneriEndo = endoMgr.GetOneriDaCodiceEndo(ai.IdComune, listaIdEndo[i]);

                    rVal.AddRange(oneriEndo);
                }

                return rVal;

            }
        }

        [WebMethod]
        public string GetCodiceCausaleOnereTraslazione(string token, int idCausale)
        {
            var ai = CheckToken(token);

            using (var db = ai.CreateDatabase())
            {
                var mgr = new TipiCausaliOneriMgr(db);
                var causale = mgr.GetById(ai.IdComune, idCausale);
                if (causale == null)
                {
                    return "";
                }

                return causale.Codicecausalepeople;
            }
        }
    }
}