using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    public class ConfigurazioneServiziCittadinoDTO
    {
        [XmlElement(Order=0)]
        public string UrlWsModulisticaDrupal { get; set; }
    }
}
