using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Visura.ProssimiPassi
{
    [DataContract]
    public class ProssimiPassiDto
    {
        [DataMember]
        public DateTime Data { get; set; }
        [DataMember]
        public string Titolo { get; set; } = "";
        [DataMember]
        public string Sottotitolo { get; set; }
    }
}
