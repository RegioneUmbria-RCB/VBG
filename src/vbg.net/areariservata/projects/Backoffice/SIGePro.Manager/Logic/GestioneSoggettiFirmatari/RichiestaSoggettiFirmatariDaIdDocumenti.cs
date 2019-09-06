using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    [Serializable]
    public class RichiestaSoggettiFirmatariDaIdDocumenti
    {
        [XmlElement(Order=0)]
        public int[] IdDocumentiIntervento { get; set; }

        [XmlElement(Order = 1)]
        public int[] IdDocumentiEndo { get; set; }
    }
}
