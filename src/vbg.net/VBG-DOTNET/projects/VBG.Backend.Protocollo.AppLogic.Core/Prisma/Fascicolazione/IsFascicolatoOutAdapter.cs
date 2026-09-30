using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Fascicolazione
{
    public class IsFascicolatoOutAdapter
    {
        public IsFascicolatoOutAdapter()
        {

        }

        public DatiProtocolloFascicolatoResponseType Adatta(FascicoloOutXml response)
        {
            if (response == null)
            {
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };
            }

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = response.AnnoFascicolo,
                Classifica = response.CodiceClassifica,
                NumeroFascicolo = response.NumeroFascicolo,
                Oggetto = response.OggettoFascicolo,
                DataFascicolo = response.DataApertura.HasValue ? response.DataApertura.Value.ToString("dd/MM/yyyy") : "",
                NoteFascicolo = response.Note,
                Fascicolato = EnumFascicolatoType.si
            };
        }
    }
}
