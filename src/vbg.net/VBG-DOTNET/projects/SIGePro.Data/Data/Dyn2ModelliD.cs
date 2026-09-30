using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    [DataTable("DYN2_MODELLID")]
    [Serializable]
    [DataContract]
    public partial class Dyn2ModelliD : BaseDataClass, IDyn2DettagliModello
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        public int? Id { get; set; }

        [isRequired]
        [DataField("FK_D2MT_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2mtId { get; set; }

        [DataField("FK_D2C_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2cId { get; set; }

        [DataField("FK_D2MDT_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2mdtId { get; set; }

        [isRequired]
        [DataField("POSVERTICALE", Type = DbType.Decimal)]
        [DataMember]
        public int? Posverticale { get; set; }

        [isRequired]
        [DataField("POSORIZZONTALE", Type = DbType.Decimal)]
        [DataMember]
        public int? Posorizzontale { get; set; }

        [DataField("FLG_MULTIPLO", Type = DbType.Decimal)]
        [DataMember]
        public int? FlgMultiplo { get; set; }

        [DataField("FLG_SPEZZA_TABELLA", Type = DbType.Decimal)]
        [DataMember]
        public int? FlgSpezzaTabella { get; set; }

        [ForeignKey(/*typeof(Dyn2ModelliDTesti),*/ "Idcomune, FkD2mdtId", "Idcomune, Id")]
        [IgnoreDataMember]
        public Dyn2ModelliDTesti? CampoTestuale { get; set; }

        [ForeignKey(/*typeof(Dyn2Campi),*/ "Idcomune, FkD2cId", "Idcomune, Id")]
        [IgnoreDataMember]
        public Dyn2Campi? CampoDinamico { get; set; }

        [DataField("FONTE_INTERNA", Type = DbType.String, CaseSensitive = false, Size = 512)]
        [DataMember]
        public string FonteInterna { get; set; } = "";

        [DataField("FONTE_ESTERNA", Type = DbType.String, CaseSensitive = false, Size = 512)]
        [DataMember]
        public string FonteEsterna { get; set; } = "";

        [DataField("TAGS", Type = DbType.String, CaseSensitive = false, Size = 512)]
        [DataMember]
        public string Tags { get; set; } = "";
    }
}
