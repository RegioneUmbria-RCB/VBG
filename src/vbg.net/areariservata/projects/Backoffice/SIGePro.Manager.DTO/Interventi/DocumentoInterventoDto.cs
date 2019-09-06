using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Interventi
{
    public class DocumentoInterventoDto
    {
        [XmlElement(Order = 1)]
        public int Codice { get; set; }
        [XmlElement(Order = 2)]
        public string Descrizione { get; set; }
        [XmlElement(Order = 3)]
        public int? CodiceOggetto { get; set; }
        [XmlElement(Order = 4)]
        public bool Obbligatorio { get; set; }
        [XmlElement(Order = 5)]
        public bool RichiedeFirma { get; set; }
        [XmlElement(Order = 6)]
        public string TipoDownload { get; set; }
        [XmlElement(Order = 7)]
        public bool DomandaFo { get; set; }
        [XmlElement(Order = 8)]
        public string NomeFile { get; set; }
        [XmlElement(Order = 9)]
        public int? DimensioneMassima { get; set; }
        [XmlElement(Order = 10)]
        public string EstensioniAmmesse { get; set; }
    }
}
