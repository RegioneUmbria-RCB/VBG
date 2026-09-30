using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ProtocollazioneConfiguration
    {
        public string Flusso { get; private set; }
        public string TipoDoc { get; private set; }
        public ParametriRegoleInfo Vert { get; private set; }
        public ProtocolloLogs Logs { get; private set; }
        public ProtocolloSerializer Serializer { get; private set; }
        public List<IAnagraficaAmministrazione> Anagrafiche { get; private set; }
        public ListaMittDest Mittenti { get; private set; }
        public ListaMittDest Destinatari { get; private set; }
        public string Operatore { get; private set; }
        public string Ruolo { get; private set; }
        public MailData Mail { get; private set; } = new MailData();

        public ProtocollazioneConfiguration(DatiProtocolloIn protoIn, List<IAnagraficaAmministrazione> anagrafiche, ParametriRegoleInfo vert, ProtocolloLogs logs, ProtocolloSerializer serializer, string ruolo, string operatore)
        {
            this.Flusso = protoIn.Flusso;
            this.TipoDoc = protoIn.TipoDocumento;
            this.Vert = vert;
            this.Logs = logs;
            this.Serializer = serializer;
            this.Mittenti = protoIn.Mittenti;
            this.Destinatari = protoIn.Destinatari;
            this.Operatore = operatore;
            this.Ruolo = ruolo;

            this.Mail.Oggetto = protoIn.OggettoMail;
            this.Mail.Corpo = protoIn.CorpoMail;
        }
    }
}
