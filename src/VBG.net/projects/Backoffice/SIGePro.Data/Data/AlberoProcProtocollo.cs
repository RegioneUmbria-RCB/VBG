
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
    [DataTable("ALBEROPROC_PROTOCOLLO")]
    [Serializable]
    public partial class AlberoProcProtocollo : BaseDataClass
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 10)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [DataField("FKSCID", Type = DbType.Decimal)]
        public int? Fkscid { get; set; }

        [DataField("CODICECOMUNE", Type = DbType.String, CaseSensitive = false, Size = 5)]
        public string CodiceComune { get; set; }

        [DataField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
        public int? Codiceamministrazione { get; set; }

        [DataField("SC_PROTCLASSIFICA", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string ScProtclassifica { get; set; }

        [DataField("SC_PROTTIPODOCUMENTO", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string ScProttipodocumento { get; set; }

        [DataField("SC_PROTCODTESTO", Type = DbType.Decimal)]
        public int? ScProtcodtesto { get; set; }

        [DataField("SC_PROTAUTOMATICA", Type = DbType.Decimal)]
        public int? ScProtautomatica { get; set; }

        [DataField("SC_FASCCLASSIFICA", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string ScFascclassifica { get; set; }

        [DataField("SC_FASCCODTESTO", Type = DbType.Decimal)]
        public int? ScFasccodtesto { get; set; }

        [DataField("SC_FASCAUTOMATICA", Type = DbType.Decimal)]
        public int? ScFascautomatica { get; set; }
    }
}
