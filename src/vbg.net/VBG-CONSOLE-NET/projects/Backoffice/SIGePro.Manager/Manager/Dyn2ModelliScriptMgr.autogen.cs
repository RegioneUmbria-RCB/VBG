
			
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
	/// File generato automaticamente dalla tabella DYN2_MODELLI_SCRIPT per la classe Dyn2ModelliScript il 22/12/2008 12.26.33
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
	public partial class Dyn2ModelliScriptMgr : BaseManager
	{
		public Dyn2ModelliScriptMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2ModelliScript GetById(string idcomune, int fk_d2mt_id, string evento)
		{
			Dyn2ModelliScript c = new Dyn2ModelliScript();
			
			
			c.Idcomune = idcomune;
			c.FkD2mtId = fk_d2mt_id;
			c.Evento = evento;
			
			return (Dyn2ModelliScript)db.GetClass(c);
		}

		public List<Dyn2ModelliScript> GetList(Dyn2ModelliScript filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2ModelliScript>();
		}

		public Dyn2ModelliScript Insert(Dyn2ModelliScript cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2ModelliScript)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2ModelliScript ChildInsert(Dyn2ModelliScript cls)
		{
			return cls;
		}

		private Dyn2ModelliScript DataIntegrations(Dyn2ModelliScript cls)
		{
			return cls;
		}
		

		public Dyn2ModelliScript Update(Dyn2ModelliScript cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2ModelliScript cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Dyn2ModelliScript cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Dyn2ModelliScript cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Dyn2ModelliScript cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			