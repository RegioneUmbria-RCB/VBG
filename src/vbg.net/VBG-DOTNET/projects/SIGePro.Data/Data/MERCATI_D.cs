using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    [DataTable("MERCATI_D")]
    [Serializable]
    [DataContract]
    public class Mercati_D : BaseDataClass, IClasseContestoModelloDinamico
    {
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string IdComune { get; set; } = null;

        [useSequence]
        [KeyField("IDPOSTEGGIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 20)]
        public int? IdPosteggio { get; set; } = null;

        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 30)]
        public int? FkCodiceMercato { get; set; } = null;

        [DataField("CODICEPOSTEGGIO", Size = 50, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 40)]
        public string CodicePosteggio { get; set; } = null;

        [DataField("LARGHEZZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 50)]
        public double? Larghezza { get; set; } = null;

        [DataField("LUNGHEZZA", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 60)]
        public double? Lunghezza { get; set; } = null;

        [DataField("SUPERFICIE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 70)]
        public double? Superficie { get; set; } = null;

        [DataField("FKCODICETIPOSPAZIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 80)]
        public string FkCodiceTipoSpazio { get; set; } = null;

        [DataField("DISABILITATO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 90)]
        public string Disabilitato { get; set; } = null;

        [DataField("FKCODICESTRADARIO", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 100)]
        public string FkCodiceStradario { get; set; } = null;

        [DataField("NOTE", Size = 1000, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 110)]
        public string Note { get; set; } = null;

        #region Arraylist per gli inserimenti nelle tabelle collegate
        // [DataMember]
        [XmlElement(Order = 120)]
        public List<Mercati_DAttivitaIstat> AttivitaIstat { get; set; } = new List<Mercati_DAttivitaIstat>();
        // [DataMember]
        [XmlElement(Order = 130)]
        public List<Mercati_DConti> Conti { get; set; } = new List<Mercati_DConti>();
        #endregion
    }
}