using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneEndoprocedimenti
{
    [Serializable]
    public class EndoprocedimentiConsole
    {
        [XmlElement(Order = 1)]
        public List<FamigliaEndoprocedimentoDto> Principali { get; set; }
        [XmlElement(Order = 2)]
        public List<FamigliaEndoprocedimentoDto> Richiesti { get; set; }
        [XmlElement(Order = 3)]
        public List<FamigliaEndoprocedimentoDto> Ricorrenti { get; set; }
        [XmlElement(Order = 4)]
        public List<FamigliaEndoprocedimentoDto> Altri { get; set; }
    }
}
