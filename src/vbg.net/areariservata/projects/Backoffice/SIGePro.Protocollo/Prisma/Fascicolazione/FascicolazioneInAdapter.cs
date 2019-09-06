using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class FascicolazioneInAdapter
    {
        public FascicolazioneInAdapter()
        {

        }

        public FascicolazioneInXML Adatta(FascicolazioneInfo info, FascicolazioneServiceWrapper srv)
        {
            string numeroFascicolo = info.NumeroFascicolo;

            if (String.IsNullOrEmpty(numeroFascicolo))
            {
                var adapter = new CreaFascicoloInAdapter();
                var responseCreaFascicolo = srv.CreaFascicolo(adapter.Adatta(info));
                numeroFascicolo = responseCreaFascicolo.NumeroFascicolo;
            }

            return new FascicolazioneInXML
            {
                AnnoFascicolo = info.AnnoFascicolo,
                CodiceClassifica = info.ClassificaFascicolo,
                NumeroFascicolo = numeroFascicolo,
                Utente = info.Utente,
                ProtocolloGruppo = new ProtocolloGruppoInXml
                {
                    Anno = info.AnnoProtocollo,
                    Numero = info.NumeroProtocollo,
                    TipoRegistro = info.TipoRegistro
                }
            };


        }
    }
}
