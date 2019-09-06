
			
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
	/// File generato automaticamente dalla tabella O_INTERVENTI per la classe OInterventi il 27/06/2008 13.01.36
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
	public partial class OInterventiMgr : BaseManager
	{
		public OInterventiMgr(DataBase dataBase) : base(dataBase) { }

		public OInterventi GetById(string idcomune, int id)
		{
			OInterventi c = new OInterventi();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OInterventi)db.GetClass(c);
		}

		public List<OInterventi> GetList(string idcomune, int id, string fk_occbti_id, string intervento, string software)
		{
			OInterventi c = new OInterventi();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			if(!String.IsNullOrEmpty(fk_occbti_id))c.FkOccbtiId = fk_occbti_id;
			if(!String.IsNullOrEmpty(intervento))c.Intervento = intervento;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OInterventi>();
		}

		public List<OInterventi> GetList(OInterventi filtro)
		{
			return db.GetClassList(filtro).ToList < OInterventi>();
		}

		public OInterventi Insert(OInterventi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OInterventi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OInterventi ChildInsert(OInterventi cls)
		{
			return cls;
		}

		private OInterventi DataIntegrations(OInterventi cls)
		{
			return cls;
		}
		

		public OInterventi Update(OInterventi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OInterventi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void EffettuaCancellazioneACascata(OInterventi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OInterventi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			