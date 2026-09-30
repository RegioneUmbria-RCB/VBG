using Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace Sigepro.net.WebServices.WsSIGePro.Wcf.ElaborazioneMassiva
{
    [DataContract]
    public class EsitoElaborazioneMassivaSchede
    {
        [DataMember]
        public EsitoElaborazioneMassivaSchedeEnum Esito { get; set; }

        [DataMember]
        public int IstanzeElaborate { get; set; } = 0;

        [DataMember]
        public int IstanzeConErrori { get; set; } = 0;
    }
}