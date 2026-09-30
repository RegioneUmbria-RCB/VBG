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
            get { return this.m_id; }
            set { this.m_id = value; }
        }

        private string m_codiceanagrafe = null;
        [isRequired]
        [DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
        public string Codiceanagrafe
        {
            get { return this.m_codiceanagrafe; }
            set { this.m_codiceanagrafe = value; }
        }

        private Anagrafe m_anagrafe;
        [ForeignKey(/*typeof(Anagrafe),*/ "Idcomune,Codiceanagrafe", "IDCOMUNE,CODICEANAGRAFE")]
        public Anagrafe Anagrafe
        {
            get { return this.m_anagrafe; }
            set { this.m_anagrafe = value; }
        }

        private Mercati_D m_posteggio;

        [ForeignKey(/*typeof(Mercati_D),*/ "Idcomune,Fkidposteggio", "IdComune,IdPosteggio")]
        public Mercati_D Posteggio
        {
            get { return this.m_posteggio; }
            set { this.m_posteggio = value; }
        }
    }
}
