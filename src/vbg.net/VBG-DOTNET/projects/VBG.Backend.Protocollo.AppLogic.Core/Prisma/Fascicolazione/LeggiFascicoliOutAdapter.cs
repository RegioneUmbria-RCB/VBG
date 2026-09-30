
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Fascicolazione
{
    public class LeggiFascicoliOutAdapter
    {
        public LeggiFascicoliOutAdapter()
        {

        }

        public ListaFascicoliResponseType Adatta(LeggiFascicoliOutXML response)
        {
            return new ListaFascicoliResponseType
            {
                Fascicolo = response.Fascicolo.Select(x => new DatiFascType
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
