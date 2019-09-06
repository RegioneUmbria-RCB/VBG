using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.JIride.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "FascicoloIn", IsNullable = false)]
    public class FascicoloInXml
    {
        public string Anno { get; set; }
        public string Numero { get; set; }
        public string Data { get; set; }
        public string Oggetto { get; set; }
        public string Classifica { get; set; }
        public string AltriDati { get; set; }
        public string Utente { get; set; }
        public string Ruolo { get; set; }
        public bool Eterogeneo { get; set; }
        public string DataChiusura { get; set; }
        public string DatiAggiuntivi { get; set; }
        public string Applicazione { get; set; }
        public string Aggiornamento { get; set; }
        public string AnagraficaCf { get; set; }
        public string AnagraficaPiva { get; set; }
    }
}
