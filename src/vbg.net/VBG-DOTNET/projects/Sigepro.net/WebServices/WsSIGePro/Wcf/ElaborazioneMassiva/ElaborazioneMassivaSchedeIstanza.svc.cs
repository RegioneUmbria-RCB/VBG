using Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.Text;

namespace Sigepro.net.WebServices.WsSIGePro.Wcf.ElaborazioneMassiva
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ElaborazioneMassivaSchedeIstanza" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ElaborazioneMassivaSchedeIstanza.svc or ElaborazioneMassivaSchedeIstanza.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class ElaborazioneMassivaSchedeIstanza : WcfServiceBase,IElaborazioneMassivaSchedeIstanza
    {
        public EsitoElaborazioneMassivaSchede Elabora(string token, int idElaborazione)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var service = new ElaborazioneMassivaSchedeIstanzaService(db, authInfo.IdComune);

                var risultato = service.Elabora(idElaborazione);

                return new EsitoElaborazioneMassivaSchede
                {
                    Esito = risultato.Esito,
                    IstanzeElaborate = risultato.ElementiElaborati,
                    IstanzeConErrori = risultato.ElementiConErrore
                };
            }

        }
    }
}
