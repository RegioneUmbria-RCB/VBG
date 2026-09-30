using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class IstanzeEventi
    {
        private Anagrafe m_anagrafe;
        [ForeignKey("Idcomune, Codiceanagrafe", "IDCOMUNE, CODICEANAGRAFE")]
        public Anagrafe Anagrafe
        {
            get { return this.m_anagrafe; }
            set { this.m_anagrafe = value; }
        }
    }
}
