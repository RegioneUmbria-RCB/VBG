using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class IsFascicolatoOutAdapter
    {
        public IsFascicolatoOutAdapter()
        {

        }

        public DatiProtocolloFascicolato Adatta(FascicoloOutXml response)
        {
            if (response == null)
            {
                return new DatiProtocolloFascicolato { Fascicolato = EnumFascicolato.no };
            }

            return new DatiProtocolloFascicolato
            {
                AnnoFascicolo = response.AnnoFascicolo,
                Classifica = response.CodiceClassifica,
                NumeroFascicolo = response.NumeroFascicolo,
                Oggetto = response.OggettoFascicolo,
                DataFascicolo = response.DataApertura.HasValue ? response.DataApertura.Value.ToString("dd/MM/yyyy") : "",
                NoteFascicolo = response.Note,
                Fascicolato = EnumFascicolato.si
            };
        }
    }
}
