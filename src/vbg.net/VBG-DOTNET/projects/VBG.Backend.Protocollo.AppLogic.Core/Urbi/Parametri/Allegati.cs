using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri
{
    internal class Allegati : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();
        public Allegati(IEnumerable<AllegatoFascicolo> allegati)
        {
            if (!allegati.Any())
            {
                return;
            }

            this.Parametro.Add("PRCORE03_Num_Allegati", allegati.Count().ToString());
            var index = 0;

            allegati
                .ToList()
                .ForEach(x =>
                {
                    this.Parametro.Add(new Allegato(index, x).Parametro);
                    index++;
                });



        }
    }
}
