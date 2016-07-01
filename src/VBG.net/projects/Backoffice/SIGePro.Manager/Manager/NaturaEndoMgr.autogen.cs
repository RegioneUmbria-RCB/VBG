using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{
    public class NaturaEndoMgr : BaseManager
    {
        public NaturaEndoMgr(DataBase dataBase) : base(dataBase) { }

		public NaturaEndo GetById(string idComune, int codiceNatura)
		{
			var filtro = new NaturaEndo
			{
				IDCOMUNE = idComune,
				CODICENATURA = codiceNatura.ToString()
			};

			return (NaturaEndo)db.GetClass(filtro);
		}

        public ArrayList GetList(NaturaEndo p_class)
        {
            return this.GetList(p_class, null);
        }

        public ArrayList GetList(NaturaEndo p_class, NaturaEndo p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false);
        }
    }
}
