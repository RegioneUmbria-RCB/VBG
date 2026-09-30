
			
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
	/// File generato automaticamente dalla tabella CC_DESTINAZIONI per la classe CCDestinazioni il 01/07/2008 16.53.39
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
	public partial class CCDestinazioniMgr : BaseManager
	{
		public CCDestinazioniMgr(DataBase dataBase) : base(dataBase) { }

		public CCDestinazioni GetById(string idcomune, int id)
		{
			CCDestinazioni c = new CCDestinazioni();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCDestinazioni)db.GetClass(c);
		}

		public List<CCDestinazioni> GetList(string idcomune, int id, string destinazione, string software, string fk_occbde_id)
		{
			CCDestinazioni c = new CCDestinazioni();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			if(!String.IsNullOrEmpty(destinazione))c.Destinazione = destinazione;
			if(!String.IsNullOrEmpty(software))c.Software = software;
			if(!String.IsNullOrEmpty(fk_occbde_id))c.FkOccbdeId = fk_occbde_id;


			return db.GetClassList(c).ToList < CCDestinazioni>();
		}

		public List<CCDestinazioni> GetList(CCDestinazioni filtro)
		{
			return db.GetClassList(filtro).ToList < CCDestinazioni>();
		}

		public CCDestinazioni Insert(CCDestinazioni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCDestinazioni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCDestinazioni ChildInsert(CCDestinazioni cls)
		{
			return cls;
		}

		private CCDestinazioni DataIntegrations(CCDestinazioni cls)
		{
			return cls;
		}
		

		public CCDestinazioni Update(CCDestinazioni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCDestinazioni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
			
		private void EffettuaCancellazioneACascata(CCDestinazioni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCDestinazioni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			