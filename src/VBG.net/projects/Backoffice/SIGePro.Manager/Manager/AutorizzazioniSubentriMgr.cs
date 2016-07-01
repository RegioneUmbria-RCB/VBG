using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Autorizzazioni;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
	public class AutorizzazioniSubentriMgr : BaseManager
	{
		public AutorizzazioniSubentriMgr(DataBase dataBase) : base(dataBase) { }

		public List<AutorizzazioniSubentri> GetList(AutorizzazioniSubentri p_class)
		{
			return this.GetList(p_class, null);
		}

		public List<AutorizzazioniSubentri> GetList(AutorizzazioniSubentri p_class, AutorizzazioniSubentri p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList<AutorizzazioniSubentri>();
		}

		public void Delete(AutorizzazioniSubentri p_class)
		{
			db.Delete(p_class);
		}
	}
}
