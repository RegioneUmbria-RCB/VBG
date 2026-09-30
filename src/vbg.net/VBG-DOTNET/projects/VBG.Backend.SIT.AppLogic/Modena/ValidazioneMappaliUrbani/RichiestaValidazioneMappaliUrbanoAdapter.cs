
using System;
using VBG.Backend.SIT.AppLogic.Utils;

namespace VBG.Backend.SIT.AppLogic.Modena.ValidazioneMappaliUrbani
{
    public class RichiestaValidazioneMappaliUrbanoAdapter
    {
        public string NomeServizio { get { return "ValidazioneMappaliUrbanoService"; } }

        public RichiestaValidazioneMappaliUrbanoAdapter()
        {

        }

        public string Adatta(string codiceEnte, string foglio, string mappale, string subalterno)
        {
            if (String.IsNullOrEmpty(codiceEnte))
            {
                throw new Exception("Parametro di verticalizzazione CODICEENTE non impostato. Verificare la configurazione della verticalizzazione");
            }

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

            return request.XmlSerializeToString();
        }
    }
}
