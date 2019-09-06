using Microsoft.Web.Services2.Attachments;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo
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