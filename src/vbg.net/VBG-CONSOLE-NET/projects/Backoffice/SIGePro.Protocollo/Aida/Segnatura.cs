using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Aida
{

    #region Segnature

    [XmlRoot(Namespace = "", ElementName = "protocollo", IsNullable = false)]
    [Serializable()]
    public class Segnatura
    {
        [XmlElement()]
        public string utente;

        [XmlElement()]
        public string password;

        [XmlElement()]
        public string tipo;

        [XmlElement()]
        public ProtocolloCorrispondenti corrispondenti;

        [XmlElement()]
        public string oggetto;

        [XmlElement()]
        public ProtocolloAssegnatari assegnatari;

        public class ProtocolloCorrispondenti
        {
            [XmlElement()]
            public List<ProtocolloCorrispondentiCorrispondente> corrispondente;
        }

        public class ProtocolloCorrispondentiCorrispondente
        {
            [XmlElement()]
            public string nome;
        }

        public class ProtocolloAssegnatari
        {
            [XmlElement()]
            public List<ProtocolloAssegnatariAssegnatario> assegnatario;
        }

        public class ProtocolloAssegnatariAssegnatario
        {
            [XmlElement()]
            public string livello;

            [XmlElement()]
            public string possesso;
        }

    }

    [XmlRoot(Namespace = "", ElementName = "protocollo", IsNullable = false)]
    [Serializable()]
    public class SegnaturaAllegati
    {
        [XmlElement()]
        public string utente;

        [XmlElement()]
        public string password;

        [XmlElement()]
        public string anno;

        [XmlElement()]
        public string numero;

        [XmlElement()]
        public string tipo;

        [XmlElement()]
        public ProtocolloDocumenti documenti;

        public class ProtocolloDocumenti
        {
            [XmlElement()]
            public List<ProtocolloDocumentiDoc> doc;
        }

        public class ProtocolloDocumentiDoc
        {
            [XmlElement()]
            public string url;
        }

    }

    #endregion

    #region Response

    [XmlRoot(Namespace = "", ElementName = "protocollo", IsNullable = false)]
    [Serializable()]
    public class SegnaturaResponse
    {
        [XmlElement()]
        public string errnum;

        [XmlElement()]
        public string anno;

        [XmlElement()]
        public string numero;

        [XmlElement()]
        public string data;
    }

    [XmlRoot(Namespace = "", ElementName = "protocollo", IsNullable = false)]
    [Serializable()]
    public class SegnaturaAllegatiResponse
    {
        [XmlElement()]
        public string errnum;
    }

    #endregion

}
