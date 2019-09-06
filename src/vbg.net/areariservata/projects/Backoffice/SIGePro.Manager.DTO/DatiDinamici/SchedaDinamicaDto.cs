using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
    [Serializable]
	public class SchedaDinamicaDto
	{
        [XmlElement(Order=1)]
        public int Id { get; set; }

        [XmlElement(Order = 2)]
        public string CodiceScheda { get; set; }

        [XmlElement(Order = 3)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 4)]
        public TipoFirmaEnum TipoFirma { get; set; }

        [XmlElement(Order=5)]
        public bool Facoltativa { get; set; }

        [XmlElement(Order = 6)]
        public int? Ordine { get; set; }

        [XmlElement(Order = 7)]
        public bool FvgMostraNelBackoffice { get; set; }

        public SchedaDinamicaDto():base()
		{
			TipoFirma = TipoFirmaEnum.NessunaFirma;
			Facoltativa = false;
		}
	}
}
