using System;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneOggetti.DimensioniAllegatiLiberi
{
    [Serializable]
    public class FormatoAllegatoLibero
    {
        [XmlElement(Order = 1)]
        public int Id { get; internal set; }
        [XmlElement(Order = 2)]
        public string Formato { get; internal set; }
        [XmlElement(Order = 3)]
        public int DimensioneMaxPagina { get; internal set; }
    }
}