
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class AlberoProcDyn2ModelliT
    {
        private AlberoProc m_alberoProc;

        [ForeignKey(/*typeof(AlberoProc),*/ "Idcomune,FkScId", "Idcomune,Sc_id")]
        public AlberoProc AlberoProc
	    { 
		    get { return m_alberoProc;}
		    set { m_alberoProc = value;}
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
				