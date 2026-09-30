using Init.SIGePro.Manager.Utils.Extensions;
using static Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli.LeggiAnagraficaDaSikuelRichiesta;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Maggioli
{
    public class RequestAdapter
    {
        private static class Constants
        {
            public const string SemanthicSwitch = "semanthic_switch_v2";
            public const string Funzionalita = "an1.sikuel.leggiAnagrafica";
            public const string VersioneRichiesta = "4.0";
            public const string TipoOperazione = "1";
            public const string IndicePagina = "1";
            public const string DimensionePagina = "10";
        }

        public RequestAdapter()
        {

        }

        public string[] Adatta(string codiceFiscale, string alias)
        {
            var request = new LeggiAnagraficaDaSikuelRichiesta
            {
                IdRichiesta = new IDRichiestaType
                {
                    VersioneRichiesta = Constants.VersioneRichiesta,
                    TipoOperazione = Constants.TipoOperazione
                },
                DatiTipoOperazione = new DatiTipoOperazioneType
                {
                    CodiceFiscale = codiceFiscale,
                    Paginazione = new PaginazioneType
                    {
                        IndicePagina = Constants.IndicePagina,
                        DimensionePagina = Constants.DimensionePagina
                    }
                }
            };

            string xml = SerializationExtensions.XmlSerializeToString(request);
            return new string[] { Constants.SemanthicSwitch, alias, Constants.Funzionalita, xml };
        }
    }

}
