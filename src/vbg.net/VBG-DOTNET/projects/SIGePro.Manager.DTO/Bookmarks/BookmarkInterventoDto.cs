using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Bookmarks
{
    [Serializable]
    [DataContract]
    public class BookmarkInterventoDto
    {
        [Serializable]
        [DataContract]
        public class NodoDestinazioneParameteriDto
        {
            [DataMember]
            [XmlElement(Order = 0)]
            public string Nome { get; set; }

            [DataMember]
            [XmlElement(Order = 1)]
            public string Valore { get; set; }
        }

        [Serializable]
        [DataContract]
        public class NodoDestinazioneDto
        {
            [DataMember]
            [XmlElement(Order = 0)]
            public int Id { get; set; }

            [DataMember]
            [XmlElement(Order = 1)]
            public string IdComune { get; set; }

            [DataMember]
            [XmlElement(Order = 2)]
            public string Descrizione { get; set; }

            [DataMember]
            [XmlElement(Order = 3)]
            public string IdNodo { get; set; }

            [DataMember]
            [XmlElement(Order = 4)]
            public string IdEnte { get; set; }

            [DataMember]
            [XmlElement(Order = 5)]
            public string IdSportello { get; set; }

            [DataMember]
            [XmlElement(Order = 6)]
            public string Pec { get; set; }

            [DataMember]
            [XmlElement(Order = 7)]
            public List<NodoDestinazioneParameteriDto> Parametri { get; set; }

            public NodoDestinazioneDto()
            {
                this.Parametri = new List<NodoDestinazioneParameteriDto>();
            }

        }

        [DataMember]
        [XmlElement(Order = 0)]
        public int Id { get; set; }

        [DataMember]
        [XmlElement(Order = 1)]
        public string IdComune { get; set; }

        [DataMember]
        [XmlElement(Order = 2)]
        public string Url { get; set; }

        [DataMember]
        [XmlElement(Order = 3)]
        public bool Anonimo { get; set; }

        [DataMember]
        [XmlElement(Order = 4)]
        public int IdIntervento { get; set; }

        [DataMember]
        [XmlElement(Order = 5)]
        public NodoDestinazioneDto NodoDestinatario { get; set; }
    }
}
