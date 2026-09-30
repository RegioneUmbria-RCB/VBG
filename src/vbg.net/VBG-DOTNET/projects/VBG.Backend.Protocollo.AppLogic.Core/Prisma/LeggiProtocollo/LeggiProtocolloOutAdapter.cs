using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Allegati;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public class LeggiProtocolloOutAdapter
    {
        private IProtocolloSerializer _serializer;

        public LeggiProtocolloOutAdapter(IProtocolloSerializer serializer)
        {
            this._serializer = serializer;
        }

        public DatiProtocolloLettoResponseType Adatta(LeggiProtocolloOutXML response, LeggiPecOutXML responsePec, AllegatiServiceWrapper serviceAllegati, ProtocolloLogs logs)
        {
            logs.Debug("Inizio adapter dei dati letti dal protocollo");
            var allegatiAdapter = new AllegatiIOutAdapter(this._serializer);
            var allegati = allegatiAdapter.Adatta(response.FilePrincipale, response.Allegati, responsePec, serviceAllegati, logs);
            logs.Debug("Inizio creazione della struttura dei mittenti/destinatari letti dal protocollo");
            logs.Debug($"Dati in input: modalita {response.Doc.Modalita}, rapporto {response.Rapporti?.Rapporto?.Count()}, smistamento {response.Smistamenti?.Smistamento?.Count()}, responsePec {responsePec}");
            var mittentiDestinatari = MittentiDestinatariOutFactory.Create(response.Doc.Modalita, response.Rapporti.Rapporto, response.Smistamenti?.Smistamento, responsePec);
            logs.Debug("Fine creazione della struttura dei mittenti/destinatari letti dal protocollo");
            var retVal = new DatiProtocolloLettoResponseType
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
            logs.Debug("Fine adapter dei dati letti dal protocollo");
            return retVal;
        }
    }
}
