using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public class ValoreDatoDinamicoMovimentoDto
    {
        [XmlElement(Order = 0)]
        public int Id { get; set; }
        [XmlElement(Order = 1)]
        public int Indice { get; set; }
        [XmlElement(Order = 2)]
        public string Valore { get; set; }
        [XmlElement(Order = 3)]
        public string ValoreDecodificato { get; set; }
    }
}
