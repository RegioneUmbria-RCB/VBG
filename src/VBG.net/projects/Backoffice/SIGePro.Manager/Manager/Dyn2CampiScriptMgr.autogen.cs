
			
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
	/// File generato automaticamente dalla tabella DYN2_CAMPI_SCRIPT per la classe Dyn2CampiScript il 22/12/2008 12.25.48
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
	public partial class Dyn2CampiScriptMgr : BaseManager
	{
		public Dyn2CampiScriptMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2CampiScript GetById(string idcomune, int fk_d2c_id, string evento)
		{
			Dyn2CampiScript c = new Dyn2CampiScript();
			
			
			c.Idcomune = idcomune;
			c.FkD2cId = fk_d2c_id;
			c.Evento = evento;
			
			return (Dyn2CampiScript)db.GetClass(c);
		}

		public List<Dyn2CampiScript> GetList(Dyn2CampiScript filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2CampiScript>();
		}

		public Dyn2CampiScript Insert(Dyn2CampiScript cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2CampiScript)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2CampiScript ChildInsert(Dyn2CampiScript cls)
		{
			return cls;
		}

		private Dyn2CampiScript DataIntegrations(Dyn2CampiScript cls)
		{
			return cls;
		}
		

		public Dyn2CampiScript Update(Dyn2CampiScript cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2CampiScript cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Dyn2CampiScript cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Dyn2CampiScript cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Dyn2CampiScript cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			