using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class MercatiPresenzeD
    {
        private int? m_id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        private string m_codiceanagrafe = null;
        [isRequired]
        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        public string Codiceanagrafe
        {
            get { return m_codiceanagrafe; }
            set { m_codiceanagrafe = value; }
        }

        private Anagrafe m_anagrafe;
        [ForeignKey(/*typeof(Anagrafe),*/ "Idcomune,Codiceanagrafe", "IDCOMUNE,CODICEANAGRAFE")]
        public Anagrafe Anagrafe
        {
            get { return m_anagrafe; }
            set { m_anagrafe = value; }
        }

        private Mercati_D m_posteggio;

        [ForeignKey(/*typeof(Mercati_D),*/ "Idcomune,Fkidposteggio", "IDCOMUNE,IDPOSTEGGIO")]
        public Mercati_D Posteggio
        {
            get { return m_posteggio; }
            set { m_posteggio = value; }
        }
    }
}
