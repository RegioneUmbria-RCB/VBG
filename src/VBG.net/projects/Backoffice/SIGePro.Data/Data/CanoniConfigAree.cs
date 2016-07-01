using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CanoniConfigAree
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #region Foreign
        CanoniConfigurazione m_configurazione;
        [ForeignKey("Idcomune,Anno", "Idcomune,Anno")]
        public CanoniConfigurazione CanoniConfigurazione
        {
            get { return m_configurazione; }
            set { m_configurazione = value; }
        }

        Aree m_aree;
        [ForeignKey("Idcomune,Codicearea", "IDCOMUNE,CODICEAREA")]
        public Aree Aree
        {
            get { return m_aree; }
            set { m_aree = value; }
        }

        public string AreaDenominazione
        {
            get { return this.ToString(); }
        }

        public override string ToString()
        {
            return ( this.Aree != null ) ? this.Aree.DENOMINAZIONE : String.Empty;
        }

        #endregion
    }
}
