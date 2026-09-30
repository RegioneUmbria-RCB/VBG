using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    [DataTable("VERTICALIZZAZIONIPARAMETRI")]
    public class VerticalizzazioniParametri : BaseDataClass
    {
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IdComune { get; set; }

        [KeyField("MODULO", Size = 30, Type = DbType.String, CaseSensitive = true)]
        public string Modulo { get; set; }

        [KeyField("PARAMETRO", Size = 30, Type = DbType.String, CaseSensitive = false)]
        public string Parametro { get; set; }

        [KeyField("SOFTWARE", Size = 2, Type = DbType.String, CaseSensitive = true)]
        public string Software { get; set; }

        [DataField("VALORE", Size = 200, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        public string Valore { get; set; }

        [KeyField("CODICECOMUNE", Size = 5, Type = DbType.String, CaseSensitive = true)]
        public string CodiceComune { get; set; }
    }
}