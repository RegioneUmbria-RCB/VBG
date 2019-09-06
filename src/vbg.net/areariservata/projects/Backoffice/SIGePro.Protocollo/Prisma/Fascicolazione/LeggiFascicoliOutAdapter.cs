using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class LeggiFascicoliOutAdapter
    {
        public LeggiFascicoliOutAdapter()
        {

        }

        public ListaFascicoli Adatta(LeggiFascicoliOutXML response)
        {
            return new ListaFascicoli
            {
                Fascicolo = response.Fascicolo.Select(x => new DatiFasc
                {
                    AnnoFascicolo = x.AnnoFascicolo,
                    ClassificaFascicolo = x.CodiceClassifica,
                    DataFascicolo = x.DataCreazione.HasValue ? x.DataCreazione.Value.ToString("dd/MM/yyyy") : "",
                    NumeroFascicolo = x.NumeroFascicolo,
                    OggettoFascicolo = x.OggettoFascicolo
                }).ToArray()
            };
        }
    }
}
