using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    [XmlRoot(Namespace = "", ElementName = "ProtocolloOut", IsNullable = false)]
    public class ProtocolloOutXml
    {
        public int IdDocumento { get; set; }

        [XmlElement("AnnoProtocollo")]
        public string _annoProtocollo { get; set; }

        [XmlIgnore]
        public short AnnoProtocollo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._annoProtocollo))
                {
                    return Convert.ToInt16(this._annoProtocollo);
                }
                return 0;
            }
        }

        [XmlElement("NumeroProtocollo")]
        public string _numeroProtocollo { get; set; }

        [XmlIgnore()]
        public int NumeroProtocollo
        {
            get
            {
                if (!String.IsNullOrEmpty(this._numeroProtocollo))
                {
                    return Convert.ToInt32(this._numeroProtocollo);
                }
                return 0;
            }
        }
        

        [XmlElement("DataProtocollo")]
        public string _dataProtocollo { get; set; }

        [XmlIgnore()]
        public DateTime? DataProtocollo
        {
            get
            {
                if (!String.IsNullOrEmpty(_dataProtocollo))
                {
                    return DateTime.Parse(this._dataProtocollo);
                }
                return null;
            }
        }

        public string Messaggio { get; set; }

        [XmlArrayItemAttribute("Registro", IsNullable = false)]
        public RegistroOutXml[] Registri { get; set; }

        [XmlArrayItemAttribute("Allegato", IsNullable = false)]
        public AllegatoInseritoOutXml[] Allegati { get; set; }
        public string Errore { get; set; }
    }

    public class RegistroOutXml
    {
        public string TipoRegistro { get; set; }
        public short AnnoRegistro { get; set; }
        public short NumeroRegistro { get; set; }
    }

    public class AllegatoInseritoOutXml
    {
        public long Serial { get; set; }
        public int IDBase { get; set; }
        public short Versione { get; set; }
    }
}
