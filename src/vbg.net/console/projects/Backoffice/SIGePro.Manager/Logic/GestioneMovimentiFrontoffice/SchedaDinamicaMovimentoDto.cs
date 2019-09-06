using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public class SchedaDinamicaMovimentoDto
    {
        [XmlElement(Order = 0)]
        public int Id { get; set; }

        [XmlElement(Order = 1)]
        public string Titolo { get; set; }

        [XmlElement(Order = 2)]
        public List<ValoreDatoDinamicoMovimentoDto> Valori { get; set; }

        [XmlElement(Order = 3)]
        public List<int> IdCampiContenuti { get; set; }

        public SchedaDinamicaMovimentoDto()
        {
            this.Valori = new List<ValoreDatoDinamicoMovimentoDto>();
        }
    }
}
