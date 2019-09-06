using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [Serializable]
    public class NaturaBaseEndoprocedimentoDto
    {
        public NaturaBaseEndoprocedimentoDto()
        {

        }

        public NaturaBaseEndoprocedimentoDto(string valore)
        {
            this.Valore = valore;
        }

        [XmlElement(Order=0)]
        public string Valore { get; set; }
    }
}
