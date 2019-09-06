using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class CambiaFascicoloInAdapter
    {
        public CambiaFascicoloInAdapter()
        {

        }

        public CambiaFascicoloInXML Adatta(FascicolazioneInfo info, FascicolazioneServiceWrapper srv)
        {
            string numeroFascicolo = info.NumeroFascicolo;

            if (String.IsNullOrEmpty(numeroFascicolo))
            {
                var adapter = new CreaFascicoloInAdapter();
                var responseCreaFascicolo = srv.CreaFascicolo(adapter.Adatta(info));
                numeroFascicolo = responseCreaFascicolo.NumeroFascicolo;
            }

            return new CambiaFascicoloInXML
            {
                ProtocolloGruppo = new ProtocolloGruppoInCambiaFascicoloXml
                {
                    Anno = info.AnnoProtocollo,
                    Numero = info.NumeroProtocollo,
                    TipoRegistro = info.TipoRegistro
                },
                FascicoloGruppo = new FascicoloGruppoInCambiaFascicoloXml { Anno = info.AnnoProtocollo, Numero = numeroFascicolo },
                Utente = info.Utente,
                Classifica = info.ClassificaFascicolo,
                Oggetto = info.OggettoFascicolo
            };
        }
    }
}
