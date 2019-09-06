using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento
{
    public class ConfigurazioneAlberoprocLDP
    {
        public class ConfigurazioneAlberoprocLDPItem
        {
            public string IdComune { get; set; }
            public int Id { get; set; }
            public string Contesto { get; set; }
            public string Codice { get; set; }
            public string Descrizione { get; set; }
        }

        public ConfigurazioneAlberoprocLDPItem TipologiaOccupazione { get; set; }
        public ConfigurazioneAlberoprocLDPItem TipologiaPeriodo { get; set; }
        public ConfigurazioneAlberoprocLDPItem TipologiaGeometria { get; set; }
        public string LdpDolQString { get; set; }
    }
}
