using System;
using System.Xml.Serialization;

namespace VBG.Backend.SIT.AppLogic.Data
{


    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    [Serializable]
    public class DettagliVia
    {
        [XmlElement(Order = 0)]
        public string CodiceViario { get; set; }

        [XmlElement(Order = 1)]
        public string Toponimo { get; set; }

        [XmlElement(Order = 2)]
        public string Denominazione { get; set; }

        [XmlElement(Order = 3)]
        public string Localita { get; set; }

        [XmlElement(Order = 4)]
        public string CodiceComune { get; set; }

        [XmlElement(Order = 5)]
        public DateTime? DataFineValidita { get; set; }
    }
}
