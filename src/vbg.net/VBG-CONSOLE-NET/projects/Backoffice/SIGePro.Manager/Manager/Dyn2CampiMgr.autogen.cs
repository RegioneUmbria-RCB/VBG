
			
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
	/// File generato automaticamente dalla tabella DYN2_CAMPI per la classe Dyn2Campi il 05/08/2008 16.49.58
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
	public partial class Dyn2CampiMgr : BaseManager
	{
		public Dyn2CampiMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2Campi GetById(string idcomune, int id)
		{
			Dyn2Campi c = new Dyn2Campi();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (Dyn2Campi)db.GetClass(c);
		}

		public List<Dyn2Campi> GetList(Dyn2Campi filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2Campi>();
		}

		public Dyn2Campi Insert(Dyn2Campi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2Campi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2Campi ChildInsert(Dyn2Campi cls)
		{
			return cls;
		}

		private Dyn2Campi DataIntegrations(Dyn2Campi cls)
		{
			return cls;
		}
		

		public Dyn2Campi Update(Dyn2Campi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2Campi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		

			

		
		
		private void Validate(Dyn2Campi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			