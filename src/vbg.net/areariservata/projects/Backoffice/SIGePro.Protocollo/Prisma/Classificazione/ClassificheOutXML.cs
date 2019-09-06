using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Classificazione
{
    [XmlRoot(Namespace = "", ElementName = "CLASSIFICHE", IsNullable = false)]
    public partial class ClassificheOutXML
    {
        //[XmlArrayItemAttribute("CLASSIFICA", IsNullable = false)]
        [XmlElementAttribute("CLASSIFICA")]
        public ClassificaOutXml[] Classifica { get; set; }
    }

    public partial class ClassificaOutXml
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("CLASS_COD")]
        public string CodiceClassifica { get; set; }

        [XmlElement("CLASS_DAL")]
        public string ClassificaDal { get; set; }

        [XmlElement("CLASS_AL")]
        public string ClassificaAl { get; set; }

        [XmlElement("DESCRIZIONE")]
        public string Descrizione { get; set; }

        [XmlIgnore()]
        public string DescrizioneTroncata
        {
            get
            {
                if (String.IsNullOrEmpty(this.Descrizione))
                {
                    return "";
                }

                if (this.Descrizione.Length > 100)
                {
                    return $"{this.Descrizione.Substring(1, 100)}...";
                }

                return this.Descrizione;
            }
        }

        [XmlElement("DATA_CREAZIONE")]
        public string _dataCreazione { get; set; }

        [XmlIgnore()]
        public DateTime? DataCreazione
        {
            get
            {
                if (String.IsNullOrEmpty(this._dataCreazione))
                {
                    return null;
                }

                DateTime retVal;
                bool isDate = DateTime.TryParse(this._dataCreazione, out retVal);
                if (!isDate)
                {
                    return null;
                }

                return retVal;
            }
        }

        [XmlElement("CONTENITORE_DOCUMENTI")]
        public string ContenitoreDocumenti { get; set; }
    }
}
