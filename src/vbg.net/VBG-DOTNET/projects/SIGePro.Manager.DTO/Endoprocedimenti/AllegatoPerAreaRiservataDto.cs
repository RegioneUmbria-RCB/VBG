using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Endoprocedimenti
{
    [DataContract]
    public class AllegatoPerAreaRiservataDto: AllegatoDto
    {
        [DataMember]
        public bool Richiesto { get; set; }

        [DataMember]
        public bool Richiedefirma { get; set; }

        [DataMember]
        public int Ordine { get; set; }

        [DataMember]
        public string NomeFile { get; set; }

        [DataMember]
        public string NoteFrontend { get; set; }

        [DataMember]
        public int DimensioneMassima { get; set; }

        [DataMember]
        public string EstensioniAmmesse { get; set; }
    }
}
