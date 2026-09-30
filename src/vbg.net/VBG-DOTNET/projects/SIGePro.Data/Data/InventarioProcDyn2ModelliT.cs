
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class InventarioProcDyn2ModelliT
    {
        private InventarioProcedimenti m_inventarioProc;

        [ForeignKey(/*typeof(InventarioProcedimenti),*/ "Idcomune,Codiceinventario", "Idcomune,Codiceinventario")]
        public InventarioProcedimenti InventarioProcedimenti
        {
            get { return m_inventarioProc; }
            set { m_inventarioProc = value; }
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
				