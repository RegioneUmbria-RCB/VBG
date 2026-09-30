using System;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    [DataContract]
    public class ParametriAreaRiservataCore
    {
        [DataMember]
        public bool UsaAreaRiservataCore { get; set; } = false;

        [DataMember]
        public string BaseUrlCore { get; set; } = null;

        [DataMember]
        public string BaseUrlFramework { get; set; } = null;
    }
}
