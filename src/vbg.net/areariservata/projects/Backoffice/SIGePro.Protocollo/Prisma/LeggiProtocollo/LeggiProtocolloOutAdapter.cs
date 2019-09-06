using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Prisma.Allegati;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    public class LeggiProtocolloOutAdapter
    {
        public LeggiProtocolloOutAdapter()
        {

        }

        public DatiProtocolloLetto Adatta(LeggiProtocolloOutXML response, LeggiPecOutXML responsePec, AllegatiServiceWrapper serviceAllegati, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {

            var allegatiAdapter = new AllegatiIOutAdapter();
            var allegati = allegatiAdapter.Adatta(response.FilePrincipale, response.Allegati, responsePec, serviceAllegati, logs, serializer);
            var mittentiDestinatari = MittentiDestinatariOutFactory.Create(response.Doc.Modalita, response.Rapporti.Rapporto, response.Smistamenti.Smistamento, responsePec);

            var retVal = new DatiProtocolloLetto
            {
                Allegati = allegati,
                AnnoProtocollo = response.Doc.Anno,
                NumeroProtocollo = response.Doc.Numero,
                Classifica = response.Doc.ClassificaCod,
                Classifica_Descrizione = response.Doc.ClassificaCod,
                DataProtocollo = response.Doc.Data.HasValue ? response.Doc.Data.Value.ToString("dd/MM/yyyy") : "",
                IdProtocollo = response.Doc.IdDocumento,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                Origine = mittentiDestinatari.Flusso,
                NumeroPratica = response.Doc.FascicoloNumero,
                AnnoNumeroPratica = $"{response.Doc.FascicoloNumero}/{response.Doc.FascicoloAnno}",
                Oggetto = response.Doc.Oggetto,
                TipoDocumento = response.Doc.TipoDocumento,
                TipoDocumento_Descrizione = response.Doc.TipoDocumento
            };

            return retVal;
        }
    }
}
