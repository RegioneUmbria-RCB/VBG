using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("MOVIMENTIALLEGATI")]
    [Serializable]
    [DataContract]
    public class MovimentiAllegati : BaseDataClass
    {

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string IDCOMUNE { get; set; } = null;

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        [XmlElement(Order = 1)]
        public string Id { get; set; } = null;

        [DataField("IDALLEGATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string IDALLEGATO { get; set; } = null;

        [DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string CODICEMOVIMENTO { get; set; } = null;

        [isRequired]
        [DataField("DESCRIZIONE", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 4)]
        public string DESCRIZIONE { get; set; } = null;

        [DataField("NOTE", Size = 500, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 5)]
        public string NOTE { get; set; } = null;

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 6)]
        public string CODICEOGGETTO { get; set; } = null;

        private DateTime? dataregistrazione = null;
        [DataField("DATAREGISTRAZIONE", Type = DbType.DateTime)]
        [DataMember]
        [XmlElement(Order = 7)]
        public DateTime? DATAREGISTRAZIONE
        {
            get { return this.dataregistrazione; }
            set { this.dataregistrazione = this.VerificaDataLocale(value); }
        }

        [DataField("STC_IDDOCUMENTO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string STC_IDDOCUMENTO { get; set; } = null;

        [DataField("STC_IDALLEGATO", Size = 20, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string STC_IDALLEGATO { get; set; } = null;

        [DataField("FLAG_PUBBLICA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 10)]
        public int? FlagPubblica { get; set; } = null;

        [ForeignKey("IDCOMUNE, CODICEOGGETTO", "IDCOMUNE, CODICEOGGETTO")]
        [DataMember]
        [XmlElement(Order = 11)]
        public MovimentiAllegatiOggetti Oggetto { get; set; }

        [DataField("CONTROLLOOK", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 12)]
        public int? ControlloOK { get; set; }
    }


    [DataTable("OGGETTI")]
    [Serializable]
    [DataContract]
    public class MovimentiAllegatiOggetti : BaseDataClass
    {
        [useSequence]
        [KeyField("CODICEOGGETTO", Type = DbType.Int16)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICEOGGETTO { get; set; } = null;

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE { get; set; } = null;

        [isRequired]
        [DataField("NOMEFILE", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string NOMEFILE { get; set; } = null;

        [ForeignKey("IDCOMUNE,CODICEOGGETTO", "Idcomune,Codiceoggetto")]
        [DataMember]
        [XmlElement(Order = 4)]
        public List<OggettiMetadati> Metadati { get; set; } = new List<OggettiMetadati>();
    }
}