
			
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella LAYOUTTESTIBASE per la classe LayoutTestiBase il 02/12/2009 16.43.02
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	public partial class LayoutTestiBaseMgr : BaseManager
	{
		public LayoutTestiBaseMgr(DataBase dataBase) : base(dataBase) { }

		public LayoutTestiBase GetById(string codicetesto, string software)
		{
			LayoutTestiBase c = new LayoutTestiBase();
			
			
			c.Codicetesto = codicetesto;
			c.Software = software;
			
			return (LayoutTestiBase)db.GetClass(c);
		}

		public List<LayoutTestiBase> GetList(LayoutTestiBase filtro)
		{
			return db.GetClassList( filtro ).ToList< LayoutTestiBase>();
		}
	}
}
			
			
			