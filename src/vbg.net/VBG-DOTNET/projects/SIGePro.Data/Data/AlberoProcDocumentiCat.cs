using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class AlberoProcDocumentiCat
    {
        private Oggetti m_oggetto;

        [ForeignKey("Idcomune,Codiceoggetto", "IDCOMUNE,CODICEOGGETTO")]
        public Oggetti Oggetto
        {
            get { return m_oggetto; }
            set { m_oggetto = value; }
        }
    }
}
