using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ANAGRAFEDOCUMENTI")]
    [Serializable]
    [DataContract]
    public class AnagrafeDocumenti : BaseDataClass
    {

        #region Key Fields

        private string id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        public string ID
        {
            get { return this.id; }
            set { this.id = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string codiceanagrafe = null;
        [isRequired]
        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        [DataMember]
        public string CODICEANAGRAFE
        {
            get { return this.codiceanagrafe; }
            set { this.codiceanagrafe = value; }
        }

        private string idtipodocumento = null;
        [isRequired]
        [DataField("IDTIPODOCUMENTO", Type = DbType.Decimal)]
        [DataMember]
        public string IDTIPODOCUMENTO
        {
            get { return this.idtipodocumento; }
            set { this.idtipodocumento = value; }
        }

        private DateTime? dataregistrazione = null;
        [isRequired]
        [DataField("DATAREGISTRAZIONE", Type = DbType.DateTime)]
        [DataMember]
        public DateTime? DATAREGISTRAZIONE
        {
            get { return this.dataregistrazione; }
            set { this.dataregistrazione = this.VerificaDataLocale(value); }
        }

        private string codiceistanza = null;
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private string codiceoggetto = null;
        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        public string CODICEOGGETTO
        {
            get { return this.codiceoggetto; }
            set { this.codiceoggetto = value; }
        }

        private DateTime? datainiziovalidita = null;
        [DataField("DATAINIZIOVALIDITA", Type = DbType.DateTime)]
        [DataMember]
        public DateTime? DATAINIZIOVALIDITA
        {
            get { return this.datainiziovalidita; }
            set { this.datainiziovalidita = this.VerificaDataLocale(value); }
        }

        private string rifdocumento = null;
        [DataField("RIFDOCUMENTO", Size = 80, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        public string RIFDOCUMENTO
        {
            get { return this.rifdocumento; }
            set { this.rifdocumento = value; }
        }

        private DateTime? datafinevalidita = null;
        [DataField("DATAFINEVALIDITA", Type = DbType.DateTime)]
        [DataMember]
        public DateTime? DATAFINEVALIDITA
        {
            get { return this.datafinevalidita; }
            set { this.datafinevalidita = this.VerificaDataLocale(value); }
        }

        private string annotazioni = null;
        [DataField("ANNOTAZIONI", Size = 500, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        public string ANNOTAZIONI
        {
            get { return this.annotazioni; }
            set { this.annotazioni = value; }
        }

        [DataMember]
        public AnagrafeDocumentiOggetti Oggetto { get; set; }
    }

    [DataContract]

    public class AnagrafeDocumentiOggetti
    {
        #region Key Fields
        [useSequence]
        [KeyField("CODICEOGGETTO", Type = DbType.Int16)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICEOGGETTO { get; set; } = null;

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE { get; set; } = null;

        #endregion

        [isRequired]
        [DataField("NOMEFILE", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string NOMEFILE { get; set; } = null;

        [ForeignKey("IDCOMUNE,CODICEOGGETTO", "Idcomune,Codiceoggetto")]
        [DataMember]
        [XmlElement(Order = 3)]
        public List<OggettiMetadati> Metadati { get; set; } = new List<OggettiMetadati>();
    }
}