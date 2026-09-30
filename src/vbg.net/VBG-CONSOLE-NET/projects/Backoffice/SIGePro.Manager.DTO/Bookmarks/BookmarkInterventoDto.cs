using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Bookmarks
{
    [Serializable]
    public class BookmarkInterventoDto
    {
        [Serializable]
        public class NodoDestinazioneParameteriDto
        {
            [XmlElement(Order = 0)]
            public string Nome { get; set; }

            [XmlElement(Order = 1)]
            public string Valore { get; set; }
        }

        [Serializable]
        public class NodoDestinazioneDto
        {
            [XmlElement(Order=0)]
            public int Id { get; set; }

            [XmlElement(Order = 1)]
            public string IdComune { get; set; }

            [XmlElement(Order = 2)]
            public string Descrizione { get; set; }

            [XmlElement(Order = 3)]
            public string IdNodo { get; set; }

            [XmlElement(Order = 4)]
            public string IdEnte { get; set; }

            [XmlElement(Order = 5)]
            public string IdSportello { get; set; }

            [XmlElement(Order = 6)]
            public string Pec { get; set; }

            [XmlElement(Order = 7)]
            public List<NodoDestinazioneParameteriDto> Parametri { get; set; }

            public NodoDestinazioneDto()
            {
                this.Parametri = new List<NodoDestinazioneParameteriDto>();
            }

        }

        [XmlElement(Order = 0)]
        public int Id { get; set; }

        [XmlElement(Order = 1)]
        public string IdComune { get; set; }

        [XmlElement(Order = 2)]
        public string Url { get; set; }

        [XmlElement(Order = 3)]
        public bool Anonimo { get; set; }

        [XmlElement(Order = 4)]
        public int IdIntervento { get; set; }

        [XmlElement(Order = 5)]
        public NodoDestinazioneDto NodoDestinatario { get; set; }
    }
}
