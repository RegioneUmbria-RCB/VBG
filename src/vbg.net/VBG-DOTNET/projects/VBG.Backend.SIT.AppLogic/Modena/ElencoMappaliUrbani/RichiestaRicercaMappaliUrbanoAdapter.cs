using VBG.Backend.SIT.AppLogic.Utils;

namespace VBG.Backend.SIT.AppLogic.Modena.ElencoMappaliUrbani
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
