using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEPROCURE")]
    [Serializable]
    [DataContract]
    public partial class IstanzeProcure : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string IdComune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public int? Id { get; set; }

        [DataField("CODICEISTANZA", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        [DataMember]
        [XmlElement(Order = 2)]
        public int? CodiceIstanza { get; set; }

        [DataField("FK_ANAG_PROC", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        [DataMember]
        [XmlElement(Order = 3)]
        public int? FkAnagProc { get; set; }

        [DataField("FK_ANAG_PROC_STORICO", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        [DataMember]
        [XmlElement(Order = 4)]
        public int? FkAnagProcStorico { get; set; }

        [DataField("FK_ANAG_RAPP", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        [DataMember]
        [XmlElement(Order = 5)]
        public int? FkAnagRapp { get; set; }

        [DataField("FK_ANAG_RAPP_STORICO", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        [DataMember]
        [XmlElement(Order = 6)]
        public int? FkAnagRappStorico { get; set; }

        [DataField("CODICEOGGETTOPROCURA", Type = DbType.Decimal, CaseSensitive = false, Size = 10)]
        [DataMember]
        [XmlElement(Order = 7)]
        public int? CodiceOggettoProcura { get; set; }

        [DataField("STC_ID_ALLEGATO", Type = DbType.String, CaseSensitive = false, Size = 200)]
        [DataMember]
        [XmlElement(Order = 8)]
        public string StcIdAllegato { get; set; }

        [DataField("STC_ID_DOCUMENTO", Type = DbType.String, CaseSensitive = false, Size = 200)]
        [DataMember]
        [XmlElement(Order = 9)]
        public string StcIdDocumento { get; set; }
        /// <summary>
        /// Risoluzione FK, Procuratore
        /// </summary>
        [ForeignKey("IdComune,FkAnagProc", "IDCOMUNE,CODICEANAGRAFE")]
        [XmlElement(Order = 10)]
        [DataMember]
        public Anagrafe Procuratore { get; set; }

        /// <summary>
        /// Risoluzione FK, Rappresentato
        /// </summary>
        [ForeignKey("IdComune,FkAnagRapp", "IDCOMUNE,CODICEANAGRAFE")]
        [XmlElement(Order = 11)]
        [DataMember]
        public Anagrafe Rappresentato { get; set; }
    }
}
