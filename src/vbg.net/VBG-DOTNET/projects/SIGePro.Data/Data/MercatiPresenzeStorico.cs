using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    public partial class MercatiPresenzeStorico
    {
        private int? m_id = null;

        [useSequence]
        [DataMember]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return this.m_id; }
            set { this.m_id = value; }
        }

        private Anagrafe m_anagrafe;
        //[ForeignKey("Idcomune, Codiceanagrafe", "IDCOMUNE, CODICEANAGRAFE")]
        [DataMember]
        public Anagrafe Anagrafe
        {
            get { return this.m_anagrafe; }
            set { this.m_anagrafe = value; }
        }
    }
}
