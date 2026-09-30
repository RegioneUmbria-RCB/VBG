using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Titolario;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.RicercaLivello
{
    public class DettaglioOperatore
    {
        [JsonProperty(PropertyName = "Codice")]
        public string Codice { get; set; }

        [JsonProperty(PropertyName = "CodicePadre")]
        public string CodicePadre { get; set; }

        [JsonProperty(PropertyName = "Nome")]
        public string Nome { get; set; }

        [JsonProperty(PropertyName = "PathLivello")]
        public string PathLivello { get; set; }

        [JsonProperty(PropertyName = "PathLivelloPadre")]
        public string PathLivelloPadre { get; set; }

        [JsonProperty(PropertyName = "AreeOmogeneeOrganizzative")]
        public List<AreaOmogeneaOrganizzativa> AOO { get; set; }

        [JsonProperty(PropertyName = "UnitaOrganizzativa")]
        public UnitaOrganizzativa UO { get; set; }

        [JsonProperty(PropertyName = "Dipendenti")]
        public List<Dipendente> Dipendenti { get; set; }

        [JsonProperty(PropertyName = "Attributi")]
        public List<Attributo> Attributi { get; set; }
        
    }
}
