
			
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
	/// File generato automaticamente dalla tabella DYN2_MODELLIT per la classe Dyn2ModelliT il 05/08/2008 16.49.58
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
	public partial class Dyn2ModelliTMgr : BaseManager
	{
		public Dyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2ModelliT GetById(string idcomune, int id)
		{
			Dyn2ModelliT c = new Dyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (Dyn2ModelliT)db.GetClass(c);
		}

		public List<Dyn2ModelliT> GetList(Dyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2ModelliT>();
		}

		public Dyn2ModelliT Insert(Dyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2ModelliT ChildInsert(Dyn2ModelliT cls)
		{
			return cls;
		}

		private Dyn2ModelliT DataIntegrations(Dyn2ModelliT cls)
		{
			return cls;
		}
		

		public Dyn2ModelliT Update(Dyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		
	}
}
			
			
			