using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    [DataContract]
    public class ParametriAccessoAgliAtti
    {
        [DataMember]
        public bool MostraDatiMovimenti { get; set; } = false;
    }
}
