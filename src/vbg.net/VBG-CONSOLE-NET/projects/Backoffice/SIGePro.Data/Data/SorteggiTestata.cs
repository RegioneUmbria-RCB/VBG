using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class SorteggiTestata
    {
        Oggetti m_oggetto = null;
        [ForeignKey("Idcomune,Codiceoggetto", "IDCOMUNE,CODICEOGGETTO")]
        public Oggetti Oggetto
        {
            get { return m_oggetto; }
            set { m_oggetto = value; }
        }
    }
}
