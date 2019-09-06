using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    [XmlRoot(Namespace = "", ElementName = "CreaCopieIn", IsNullable = false)]
    public class CreaCopieInXml
    {
        public string IdDocumento { get; set; }
        public string AnnoProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public string FascicolaConOriginale { get; set; }

        [XmlArrayItemAttribute("UODestinataria", IsNullable = false)]
        public UODestinatariaXml[] UODestinatarie { get; set; }

        public string Utente { get; set; }
        public string Ruolo { get; set; }
    }

    public class UODestinatariaXml
    {
        public string Carico { get; set; }
        public string TipoUO { get; set; }
        public string Data { get; set; }
        public string NumeroCopie { get; set; }
        public string TipoAssegnazione { get; set; }
    }
}
