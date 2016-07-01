
			
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
	/// File generato automaticamente dalla tabella I_ATTIVITADYN2MODELLIT per la classe IAttivitaDyn2ModelliT il 05/08/2008 16.49.58
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
	public partial class IAttivitaDyn2ModelliTMgr : BaseManager
	{
		public IAttivitaDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public IAttivitaDyn2ModelliT GetById(string idcomune, int fk_d2mt_id, int fk_ia_id)
		{
			IAttivitaDyn2ModelliT c = new IAttivitaDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.FkD2mtId = fk_d2mt_id;
			c.FkIaId = fk_ia_id;
			
			return (IAttivitaDyn2ModelliT)db.GetClass(c);
		}

		public List<IAttivitaDyn2ModelliT> GetList(IAttivitaDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< IAttivitaDyn2ModelliT>();
		}

		public IAttivitaDyn2ModelliT Insert(IAttivitaDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IAttivitaDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IAttivitaDyn2ModelliT ChildInsert(IAttivitaDyn2ModelliT cls)
		{
			return cls;
		}

		private IAttivitaDyn2ModelliT DataIntegrations(IAttivitaDyn2ModelliT cls)
		{
			return cls;
		}
		

		public IAttivitaDyn2ModelliT Update(IAttivitaDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}



		
		
		private void Validate(IAttivitaDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			