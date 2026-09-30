using Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani;
using Init.SIGePro.Sit.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Modena.ElencoMappaliUrbani
{
    public class RichiestaRicercaMappaliUrbanoAdapter
    {
        public string NomeServizio { get { return "RicercaMappaleUrbanoService"; } }

        public RichiestaRicercaMappaliUrbanoAdapter()
        {

        }

        public string Adatta(string codiceEnte, string foglio, string mappale)
        {
            var request = new RichiestaRicercaMappaleUrbanoType
            {
                IdEnte = codiceEnte,
                IdentificativoParzialeUIU = new IdentificativoParzialeUIUType
                {
                    Foglio = foglio,
                    Mappale = mappale
                }
            };

            return SerializationExtensions.XmlSerializeToString(request);
        }
    }
}
