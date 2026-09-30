using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Interventi;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Interventi
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsInterventiAteco" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsInterventiAteco.svc or WsInterventiAteco.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsInterventiAteco : WcfServiceBase, IWsInterventiAteco
    {

        public Ateco GetDettagliAteco(string token, int id)
        {
            return new AtecoService().GetDettagliAteco(token, id);
        }


        public List<Ateco> GetNodiFiglioAteco(string token, int? idPadre)
        {
            return new AtecoService().GetNodiFiglioAteco(token, idPadre);
        }


        public List<Ateco> RicercaAteco(string token, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca)
        {
            return new AtecoService().RicercaAteco(token, matchParziale, matchCount, modoRicerca, tipoRicerca);
        }


        public List<int> CaricaListaIdGerarchiaAteco(string token, int id)
        {
            return new AtecoService().CaricaListaIdGerarchiaAteco(token, id);
        }


        public NodoAlberoInterventiDto GetAlberoProcDaIdAteco(string token, int idAteco, AmbitoRicerca ambitoRicerca)
        {
            return new AtecoService().GetAlberoProcDaIdAteco(token, idAteco, ambitoRicerca);
        }


        public bool VerificaEsistenzaNodiCollegatiAIdAteco(string token, string software, int idAteco, AmbitoRicerca ambitoRicerca)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var res = new AtecoMgr(db).CercaNodiConLink(authInfo.IdComune, software, idAteco, ambitoRicerca);

                return res >= 0;
            }
        }
    }
}
