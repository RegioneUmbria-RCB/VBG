
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class TipiMovimentiDyn2ModelliT
    {
        private TipiMovimento m_tipiMovimento;

        [ForeignKey(/*typeof(TipiMovimento),*/ "Idcomune,Tipomovimento", "Idcomune,Tipomovimento")]
        public TipiMovimento TipiMovimento
        {
            get { return m_tipiMovimento; }
            set { m_tipiMovimento = value; }
        }

        private Dyn2ModelliT m_dyn2ModelliT;

        [ForeignKey(/*typeof(Dyn2ModelliT),*/ "Idcomune,FkD2mtId", "Idcomune,Id")]
        public Dyn2ModelliT Dyn2ModelliT
        {
            get { return m_dyn2ModelliT; }
            set { m_dyn2ModelliT = value; }
        }
	}
}
				