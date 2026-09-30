using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
    [Serializable]
    [DataContract]
    [KnownType(typeof(SchedaDinamicaInterventoDto))]
    [KnownType(typeof(SchedaDinamicaEndoprocedimentoDto))]
    public class SchedaDinamicaDto
	{
        [XmlElement(Order=1)]
        [DataMember(Order = 1)]
        public int Id { get; set; }

        [XmlElement(Order = 2)]
        [DataMember(Order = 2)]
        public string CodiceScheda { get; set; }

        [XmlElement(Order = 3)]
        [DataMember(Order = 3)]
        public string Descrizione { get; set; }

        [XmlElement(Order = 4)]
        [DataMember(Order = 4)]
        public TipoFirmaEnum TipoFirma { get; set; }

        [XmlElement(Order=5)]
        [DataMember(Order = 5)]
        public bool Facoltativa { get; set; }

        [XmlElement(Order = 6)]
        [DataMember(Order = 6)]
        public int? Ordine { get; set; }

        [XmlElement(Order = 7)]
        [DataMember(Order = 7)]
        public bool FvgMostraNelBackoffice { get; set; }
        
        [XmlElement(Order = 8)]
        [DataMember(Order = 8)]
        public bool Pubblica { get; set; }

        public SchedaDinamicaDto()
		{
			TipoFirma = TipoFirmaEnum.NessunaFirma;
			Facoltativa = false;
		}
	}
}
