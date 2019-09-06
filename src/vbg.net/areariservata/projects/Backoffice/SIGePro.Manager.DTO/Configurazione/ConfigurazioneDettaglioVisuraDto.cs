using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ConfigurazioneDettaglioVisuraDto
    {
        [XmlElement(Order = 1)]
        public bool NascondiStatoIstanza { get; set; }
        [XmlElement(Order = 2)]
        public bool NascondiResponsabili { get; set; }
    }
}
