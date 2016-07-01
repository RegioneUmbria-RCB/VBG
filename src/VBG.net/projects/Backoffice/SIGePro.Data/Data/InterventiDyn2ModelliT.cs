
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class InterventiDyn2ModelliT
    {
        private Interventi m_intervento;

        [ForeignKey(/*typeof(Interventi),*/ "Idcomune,CodiceIntervento", "Idcomune,CodiceIntervento")]
        public Interventi Interventi
	    {
            get { return m_intervento; }
            set { m_intervento = value; }
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
				