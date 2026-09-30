using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.Modena
{
    public class LocalizzazioniMappaModenaSelezionate
    {
        public class LocalizzazioneMappaModena
        {
            public string CodCatastale { get; set; }
            public string Foglio { get; set; }
            public string Particella { get; set; }
        }

        public LocalizzazioneMappaModena[] ParticelleManuali { get; set; } = new LocalizzazioneMappaModena[0];
        public LocalizzazioneMappaModena[] ParticelleGrafiche { get; set; } = new LocalizzazioneMappaModena[0];

        public IEnumerable<LocalizzazioneMappaModena> ParticelleTotali => this.ParticelleManuali.Union(this.ParticelleGrafiche);
        
        public static LocalizzazioniMappaModenaSelezionate FromJsonString(string jsonString)
        {
            return JsonConvert.DeserializeObject<LocalizzazioniMappaModenaSelezionate>(jsonString);
        }
    }
}
