using System;
using System.Xml.Serialization;
namespace Init.SIGePro.Protocollo.Data
{
    public partial class Fascicolo
    {
        public int? AnnoFascicolo { get; set; }
        public string DataFascicolo { get; set; }
        public string NumeroFascicolo { get; set; }
        public string Oggetto { get; set; }
        public string Classifica { get; set; }
    }
}