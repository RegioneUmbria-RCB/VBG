using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    [XmlRoot(Namespace = "", ElementName = "FASCICOLI", IsNullable = false)]
    public class LeggiFascicoliOutXML
    {
        [XmlElementAttribute("FASCICOLO", IsNullable = false)]
        public FascicoloOutXml[] Fascicolo { get; set; }
    }

    public class FascicoloOutXml
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("CLASS_COD")]
        public string CodiceClassifica { get; set; }

        [XmlElement("FASCICOLO_ANNO")]
        public string AnnoFascicolo { get; set; }

        [XmlElement("FASCICOLO_NUMERO")]
        public string NumeroFascicolo { get; set; }

        [XmlElement("FASCICOLO_OGGETTO")]
        public string OggettoFascicolo { get; set; }

        [XmlElement("NOTE")]
        public string Note { get; set; }

        [XmlElement("DATA_CREAZIONE")]
        public string _dataCreazione { get; set; }

        public DateTime? DataCreazione
        {
            get
            {
                if (String.IsNullOrEmpty(this._dataCreazione))
                {
                    return null;
                }

                DateTime retVal;
                var isParsable = DateTime.TryParse(this._dataCreazione, out retVal);

                if (!isParsable)
                {
                    return null;
                }

                return retVal;
            }
        }

        [XmlElement("DATA_APERTURA")]
        public string _dataApertura { get; set; }

        public DateTime? DataApertura
        {
            get
            {
                if (String.IsNullOrEmpty(this._dataApertura))
                {
                    return null;
                }

                DateTime retVal;
                var isParsable = DateTime.TryParse(this._dataApertura, out retVal);

                if (!isParsable)
                {
                    return null;
                }

                return retVal;
            }
        }

    }
}
