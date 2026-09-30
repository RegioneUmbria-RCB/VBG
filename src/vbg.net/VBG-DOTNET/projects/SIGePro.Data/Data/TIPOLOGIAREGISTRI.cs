using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("TIPOLOGIAREGISTRI")]
    [Serializable]
    [DataContract]
    public class TipologiaRegistri : BaseDataClass
    {

        #region Key Fields

        private string tr_id = null;
        [useSequence]
        [KeyField("TR_ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string TR_ID
        {
            get { return this.tr_id; }
            set { this.tr_id = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string tr_descrizione = null;
        [DataField("TR_DESCRIZIONE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string TR_DESCRIZIONE
        {
            get { return this.tr_descrizione; }
            set { this.tr_descrizione = value; }
        }

        private string software = null;
        [DataField("SOFTWARE", Size = 2, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string SOFTWARE
        {
            get { return this.software; }
            set { this.software = value; }
        }

        private string tr_progressivo = null;
        [DataField("TR_PROGRESSIVO", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string TR_PROGRESSIVO
        {
            get { return this.tr_progressivo; }
            set { this.tr_progressivo = value; }
        }

        private string tr_flagprotocollo = null;
        [DataField("TR_FLAGPROTOCOLLO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string TR_FLAGPROTOCOLLO
        {
            get { return this.tr_flagprotocollo; }
            set { this.tr_flagprotocollo = value; }
        }
    }
}