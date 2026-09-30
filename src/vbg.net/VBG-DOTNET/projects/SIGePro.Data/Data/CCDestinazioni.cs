using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_DESTINAZIONI")]
    [Serializable]
    public partial class CCDestinazioni : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [isRequired]
        [DataField("FK_OCCBDE_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbdeId { get; set; }

        [isRequired]
        [DataField("DESTINAZIONE", Type = DbType.String, CaseSensitive = false, Size = 200, Compare = "like")]
        public string Destinazione { get; set; }

        [ForeignKey(/*typeof(OCCBaseDestinazioni),*/ "FkOccbdeId", "Id")]
        public OCCBaseDestinazioni? DestinazioneBase { get; set; }
    }
}
