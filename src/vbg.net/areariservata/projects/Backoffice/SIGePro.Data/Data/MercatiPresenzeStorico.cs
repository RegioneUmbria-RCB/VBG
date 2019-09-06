using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    partial class MercatiPresenzeStorico
    {
        private int? m_id = null;

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        Anagrafe m_anagrafe;
        //[ForeignKey("Idcomune, Codiceanagrafe", "IDCOMUNE, CODICEANAGRAFE")]
        public Anagrafe Anagrafe
        {
            get { return m_anagrafe; }
            set { m_anagrafe = value; }
        }
    }
}
