using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Autorizzazioni;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
	public class AutorizzazioniConcessioniMgr : BaseManager
	{
		public AutorizzazioniConcessioniMgr(DataBase dataBase) : base(dataBase) { }

		public List<AutorizzazioniConcessioni> GetList(AutorizzazioniConcessioni p_class)
		{
			return this.GetList(p_class, null);
		}

		public List<AutorizzazioniConcessioni> GetList(AutorizzazioniConcessioni p_class, AutorizzazioniConcessioni p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList<AutorizzazioniConcessioni>();
		}

		public void Delete(AutorizzazioniConcessioni p_class)
		{
			db.Delete(p_class);
		}
	}
}
