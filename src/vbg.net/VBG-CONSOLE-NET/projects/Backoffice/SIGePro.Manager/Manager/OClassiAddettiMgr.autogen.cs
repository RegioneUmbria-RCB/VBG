
			
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
	/// File generato automaticamente dalla tabella O_CLASSIADDETTI per la classe OClassiAddetti il 27/06/2008 13.01.35
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
	public partial class OClassiAddettiMgr : BaseManager
	{
		public OClassiAddettiMgr(DataBase dataBase) : base(dataBase) { }

		public OClassiAddetti GetById(string idcomune, int id)
		{
			OClassiAddetti c = new OClassiAddetti();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OClassiAddetti)db.GetClass(c);
		}

		public List<OClassiAddetti> GetList(string idcomune, int id, string classe, string software)
		{
			OClassiAddetti c = new OClassiAddetti();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			if(!String.IsNullOrEmpty(classe))c.Classe = classe;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OClassiAddetti>();
		}

		public List<OClassiAddetti> GetList(OClassiAddetti filtro)
		{
			return db.GetClassList(filtro).ToList < OClassiAddetti>();
		}

		public OClassiAddetti Insert(OClassiAddetti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OClassiAddetti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OClassiAddetti ChildInsert(OClassiAddetti cls)
		{
			return cls;
		}

		private OClassiAddetti DataIntegrations(OClassiAddetti cls)
		{
			return cls;
		}
		

		public OClassiAddetti Update(OClassiAddetti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OClassiAddetti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void EffettuaCancellazioneACascata(OClassiAddetti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		private void Validate(OClassiAddetti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			