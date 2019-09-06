using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    [DataTable("ALBEROPROC_DOC_SOGG_FIRMATARI")]
    public class AlberoprocDocSoggFirmatari : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get;
            set;
        }

        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get;
            set;
        }

        [KeyField("FK_TIPO_SOGGETTO", Type = DbType.Decimal)]
        public int? FkTipoSoggetto
        {
            get;
            set;
        }

        [KeyField("FK_ALBEROPROC_DOCUMENTI", Type = DbType.Decimal)]
        public int? FkAlberoprocDocumenti
        {
            get;
            set;
        }
    }
}
