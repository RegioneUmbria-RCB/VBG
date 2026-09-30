using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class IstanzeEventi
    {
        private CategorieEventiBase m_categorieeventibase;
        [ForeignKey("Fkidcategoriaevento", "Id")]
        public CategorieEventiBase CategorieEventiBase
        {
            get { return m_categorieeventibase; }
            set { m_categorieeventibase = value; }
        }

        Anagrafe m_anagrafe;
        [ForeignKey("Idcomune, Codiceanagrafe", "IDCOMUNE, CODICEANAGRAFE")]
        public Anagrafe Anagrafe
        {
            get { return m_anagrafe; }
            set { m_anagrafe = value; }
        }
    }
}
