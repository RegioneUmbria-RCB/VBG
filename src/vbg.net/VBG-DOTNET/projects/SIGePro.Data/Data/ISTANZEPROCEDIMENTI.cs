using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEPROCEDIMENTI")]
    [Serializable]
    [DataContract]
    public class IstanzeProcedimenti : BaseDataClass
    {

        #region Key Fields
        [KeyField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICEISTANZA { get; set; } = null;
        [KeyField("CODICEINVENTARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string CODICEINVENTARIO { get; set; } = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string IDCOMUNE { get; set; } = null;

        #endregion
        [DataField("PERPROVVEDIMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string PERPROVVEDIMENTO { get; set; } = null;
        [DataField("ACQUISITO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string ACQUISITO { get; set; } = null;
        [DataField("COSTORICHIESTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 5)]
        public double? COSTORICHIESTO { get; set; } = null;
        [DataField("COSTOPAGATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public double? COSTOPAGATO { get; set; } = null;
        [DataField("PROC_NUM", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 7)]
        public string PROC_NUM { get; set; } = null;
        [DataField("PROT_NUM", Size = 15, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string PROT_NUM { get; set; } = null;

        private DateTime? proc_del = null;
        [DataField("PROC_DEL", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 9)]
        public DateTime? PROC_DEL
        {
            get { return this.proc_del; }
            set { this.proc_del = this.VerificaDataLocale(value); }
        }

        private DateTime? prot_del = null;
        [DataField("PROT_DEL", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 10)]
        public DateTime? PROT_DEL
        {
            get { return this.prot_del; }
            set { this.prot_del = this.VerificaDataLocale(value); }
        }

        private DateTime? dataattivazione = null;
        [isRequired]
        [DataField("DATAATTIVAZIONE", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 11)]
        public DateTime? DATAATTIVAZIONE
        {
            get { return this.dataattivazione; }
            set { this.dataattivazione = this.VerificaDataLocale(value); }
        }

        #region foreign keys
        [ForeignKey("IDCOMUNE,CODICEINVENTARIO", "Idcomune,Codiceinventario")]
        [DataMember]
        [XmlElement(Order = 12)]
        public InventarioProcedimenti Endoprocedimento { get; set; }
        [ForeignKey("IDCOMUNE,CODICEISTANZA,CODICEINVENTARIO", "IDCOMUNE,CODICEISTANZA,CODICEINVENTARIO")]
        [DataMember]
        [XmlElement(ElementName = "IstanzeAllegati", Order = 13)]
        public List<IstanzeAllegati> IstanzeAllegati { get; set; } = new List<IstanzeAllegati>();



        #endregion
        [DataField("NOTE", Size = 1000, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 14)]
        public string Note { get; set; }

        [DataField("TIPO_ATTO", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 15)]
        public string TipoAtto { get; set; }

        [DataField("RILASCIATO_DA", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 16)]
        public string RilasciatoDa { get; set; }
    }
}