
			
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
	/// File generato automaticamente dalla tabella ALBEROPROC_DYN2MODELLIT per la classe AlberoProcDyn2ModelliT il 05/08/2008 16.49.58
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
	public partial class AlberoProcDyn2ModelliTMgr : BaseManager
	{
		public AlberoProcDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public AlberoProcDyn2ModelliT GetById(string idcomune, int fk_sc_id, int fk_d2mt_id)
		{
			AlberoProcDyn2ModelliT c = new AlberoProcDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.FkScId = fk_sc_id;
			c.FkD2mtId = fk_d2mt_id;
			
			return (AlberoProcDyn2ModelliT)db.GetClass(c);
		}

		public List<AlberoProcDyn2ModelliT> GetList(AlberoProcDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< AlberoProcDyn2ModelliT>();
		}

		public AlberoProcDyn2ModelliT Insert(AlberoProcDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AlberoProcDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AlberoProcDyn2ModelliT ChildInsert(AlberoProcDyn2ModelliT cls)
		{
			return cls;
		}

		private AlberoProcDyn2ModelliT DataIntegrations(AlberoProcDyn2ModelliT cls)
		{
			return cls;
		}
		

		public AlberoProcDyn2ModelliT Update(AlberoProcDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AlberoProcDyn2ModelliT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AlberoProcDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(AlberoProcDyn2ModelliT cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(AlberoProcDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			