using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class ConfigurazioneMovimentoDaEffettuareDto
    {
        [DataMember]
        public bool PermetteSostituzioneDocumentale { get; set; }
        [DataMember]
        public bool RichiedeFirmaDocumenti { get; set; }
        [DataMember]
        public bool RichiedeInterazioneConSIT { get; set; }
    }
}
