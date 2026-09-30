using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Sql;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public class BaseDataClassProtocollo : BaseDataClass
    {
        [DataField("CODICECOMUNE", Type = DbType.String, CaseSensitive = false, Size = 5)]
        public string CodiceComune { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software { get; set; }
    }
}
