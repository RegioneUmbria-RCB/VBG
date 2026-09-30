
			
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
	/// File generato automaticamente dalla tabella DYN2_MODELLID per la classe Dyn2ModelliD il 05/08/2008 16.49.58
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
	public partial class Dyn2ModelliDMgr : BaseManager
	{
		public Dyn2ModelliDMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2ModelliD GetById(string idcomune, int id)
		{
			Dyn2ModelliD c = new Dyn2ModelliD();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (Dyn2ModelliD)db.GetClass(c);
		}

		public List<Dyn2ModelliD> GetList(Dyn2ModelliD filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2ModelliD>();
		}

		public Dyn2ModelliD Insert(Dyn2ModelliD cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2ModelliD)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2ModelliD ChildInsert(Dyn2ModelliD cls)
		{
			return cls;
		}

		private Dyn2ModelliD DataIntegrations(Dyn2ModelliD cls)
		{
			return cls;
		}
		

		public Dyn2ModelliD Update(Dyn2ModelliD cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}


		

			
		




	}
}
			
			
			