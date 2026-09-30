using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Commissioni
{
    [DataContract]
    public class AllegatoCommissioneDao
    {
        [DataMember]
        public int Id { get; set; }

        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public int CodiceOggetto { get; set; }

        [DataMember]
        public string Nomefile { get; set; }

        [DataMember]
        public DateTime? DataRegistrazione { get; set; }

        [DataMember]
        public string Sha256 { get; set; }

        [DataMember]
        public DateTime? DataApprovazione { get; set; }

        [DataMember]
        public string Sha256Controllo { get; set; }

        public bool Approvato => DataApprovazione.HasValue && Sha256 == Sha256Controllo;
    }
}
