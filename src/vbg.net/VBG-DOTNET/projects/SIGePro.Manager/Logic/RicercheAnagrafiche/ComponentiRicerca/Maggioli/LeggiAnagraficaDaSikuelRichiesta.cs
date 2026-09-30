using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli
{
    [XmlRoot(Namespace = "http://xml.apache.org/axis/wsdd/", ElementName = "LeggiAnagraficaDaSikuelRichiesta", IsNullable = false)]
    public class LeggiAnagraficaDaSikuelRichiesta
    {
        [XmlElement("IDRichiesta")]
        public IDRichiestaType IdRichiesta { get; set; }

        [XmlElement("DatiTipoOperazione")]
        public DatiTipoOperazioneType DatiTipoOperazione { get; set; }


        public class IDRichiestaType
        {
            public string VersioneRichiesta { get; set; }
            public string TipoOperazione { get; set; }
            public string DataRichiesta { get; set; }
            public string CodiceEnte { get; set; }
            public string VersioneSistema { get; set; }
        }

        public class DatiTipoOperazioneType
        {
            public string CodiceFiscale { get; set; }
            public string Nome { get; set; }
            public string Cognome { get; set; }
            public string DataNascita { get; set; }

            [XmlElement("Paginazione")]
            public PaginazioneType Paginazione { get; set; }
        }

        public class PaginazioneType
        {
            public string IndicePagina { get; set; }
            public string DimensionePagina { get; set; }
        }
    }
}
