
			
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
	/// File generato automaticamente dalla tabella O_VALIDITACOEFFICIENTI per la classe OValiditaCoefficienti il 27/06/2008 13.01.37
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
	public partial class OValiditaCoefficientiMgr : BaseManager
	{
		public OValiditaCoefficientiMgr(DataBase dataBase) : base(dataBase) { }

		public OValiditaCoefficienti GetById(string idcomune, int id)
		{
			OValiditaCoefficienti c = new OValiditaCoefficienti();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OValiditaCoefficienti)db.GetClass(c);
		}

		public List<OValiditaCoefficienti> GetList(string idcomune, int id, string descrizione, DateTime datainiziovalidita, string software)
		{
			OValiditaCoefficienti c = new OValiditaCoefficienti();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;
			c.Datainiziovalidita = datainiziovalidita;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OValiditaCoefficienti>();
		}

		public List<OValiditaCoefficienti> GetList(OValiditaCoefficienti filtro)
		{
			return db.GetClassList(filtro).ToList < OValiditaCoefficienti>();
		}

		public OValiditaCoefficienti Insert(OValiditaCoefficienti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OValiditaCoefficienti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OValiditaCoefficienti ChildInsert(OValiditaCoefficienti cls)
		{
			return cls;
		}

		private OValiditaCoefficienti DataIntegrations(OValiditaCoefficienti cls)
		{
			return cls;
		}
		

		public OValiditaCoefficienti Update(OValiditaCoefficienti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OValiditaCoefficienti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		
		private void EffettuaCancellazioneACascata(OValiditaCoefficienti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OValiditaCoefficienti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			