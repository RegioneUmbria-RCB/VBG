using Microsoft.Web.Services2.Attachments;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph.LeggiProtocollo
{
    public class RegistrazioneConAllegatiResponse
    {
        public RegistrazioneRet Segnatura { get; private set; }
        public AttachmentCollection Attachments { get; private set; }

        public RegistrazioneConAllegatiResponse(RegistrazioneRet segnatura, AttachmentCollection attachments)
        {
            this.Segnatura = segnatura;
            this.Attachments = attachments;
        }
    }
}