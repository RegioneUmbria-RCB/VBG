using Init.SIGePro.Sit.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Modena.ValidazioneMappaliUrbani
{
    public class RichiestaValidazioneMappaliUrbanoAdapter
    {
        public string NomeServizio { get { return "ValidazioneMappaliUrbanoService"; } }

        public RichiestaValidazioneMappaliUrbanoAdapter()
        {

        }

        public string Adatta(string codiceEnte, string foglio, string mappale, string subalterno)
        {
            var request = new RichiestaValidazioneMappaliUrbanoType
            {
                IdEnte = codiceEnte,
                IdUIUDaValidare = new IdUIUDaValidareType
                {
                    IdentificativoUIU = new IdentificativoUIUType
                    {
                        IdentificativoComune = new IdentificativoComuneType
                        {
                            CodiceBelfioreComune = codiceEnte
                        },
                        IdentificativoParzialeUIU = new IdentificativoParzialeUIUType
                        {
                            Foglio = foglio,
                            Mappale = mappale,
                            Subalterno = subalterno
                        }
                    }
                }
            };

            return SerializationExtensions.XmlSerializeToString(request);
        }
    }
}
