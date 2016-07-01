
			
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
	/// File generato automaticamente dalla tabella DYN2_MODELLIDTESTI per la classe Dyn2ModelliDTesti il 05/08/2008 16.49.58
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
	public partial class Dyn2ModelliDTestiMgr : BaseManager
	{
		public Dyn2ModelliDTestiMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2ModelliDTesti GetById(string idcomune, int id)
		{
			Dyn2ModelliDTesti c = new Dyn2ModelliDTesti();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (Dyn2ModelliDTesti)db.GetClass(c);
		}

		public List<Dyn2ModelliDTesti> GetList(Dyn2ModelliDTesti filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2ModelliDTesti>();
		}

		public Dyn2ModelliDTesti Insert(Dyn2ModelliDTesti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2ModelliDTesti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2ModelliDTesti ChildInsert(Dyn2ModelliDTesti cls)
		{
			return cls;
		}

		private Dyn2ModelliDTesti DataIntegrations(Dyn2ModelliDTesti cls)
		{
			return cls;
		}
		

		public Dyn2ModelliDTesti Update(Dyn2ModelliDTesti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2ModelliDTesti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Dyn2ModelliDTesti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Dyn2ModelliDTesti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Dyn2ModelliDTesti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			