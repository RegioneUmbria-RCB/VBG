using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Prisma.Smistamento
{
    [XmlRoot(Namespace = "", ElementName = "ROOT", IsNullable = false)]
    public class AddSmistamentoInXML
    {
        [XmlElement("ID_DOCUMENTO")]
        public string IdDocumento { get; set; }

        [XmlElement("TIPO_SMISTAMENTO")]
        public string TipoSmistamento { get; set; }

        [XmlElement("UNITA_SMISTAMENTO")]
        public string UnitaSmistamento { get; set; }

        [XmlElement("UTENTE_ASSEGNATARIO")]
        public string UtenteAssegnatario { get; set; }

        [XmlElement("UTENTE")]
        public string Utente { get; set; }
    }
}
