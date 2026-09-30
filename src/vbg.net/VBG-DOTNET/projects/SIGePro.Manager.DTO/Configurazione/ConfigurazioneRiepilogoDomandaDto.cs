using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ConfigurazioneRiepilogoDomandaDto
    {
        [XmlElement(Order = 1)]
        public int FlagIncludiSchede { get; set; }

        public ConfigurazioneRiepilogoDomandaDto()
        {
            this.FlagIncludiSchede = 0;
        }
    }
}
