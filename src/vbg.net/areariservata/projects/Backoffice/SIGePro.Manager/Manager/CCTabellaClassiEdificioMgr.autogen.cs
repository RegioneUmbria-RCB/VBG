
			
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
	/// File generato automaticamente dalla tabella CC_TABELLA_CLASSIEDIFICIO per la classe CCTabellaClassiEdificio il 27/06/2008 13.01.40
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
    public partial class CCTabellaClassiEdificioMgr : BaseManager
	{
		public CCTabellaClassiEdificioMgr(DataBase dataBase) : base(dataBase) { }

		public CCTabellaClassiEdificio GetById(string idcomune, int id)
		{
			CCTabellaClassiEdificio c = new CCTabellaClassiEdificio();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCTabellaClassiEdificio)db.GetClass(c);
		}

		public List<CCTabellaClassiEdificio> GetList(string idcomune, int id, string descrizione, int da, int a, float maggiorazione, string software)
		{
			CCTabellaClassiEdificio c = new CCTabellaClassiEdificio();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;
			c.Da = da;
			c.A = a;
			c.Maggiorazione = maggiorazione;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < CCTabellaClassiEdificio>();
		}

		public List<CCTabellaClassiEdificio> GetList(CCTabellaClassiEdificio filtro)
		{
			return db.GetClassList(filtro).ToList < CCTabellaClassiEdificio>();
		}

		public CCTabellaClassiEdificio Insert(CCTabellaClassiEdificio cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCTabellaClassiEdificio)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCTabellaClassiEdificio ChildInsert(CCTabellaClassiEdificio cls)
		{
			return cls;
		}

		private CCTabellaClassiEdificio DataIntegrations(CCTabellaClassiEdificio cls)
		{
			return cls;
		}
		

		public CCTabellaClassiEdificio Update(CCTabellaClassiEdificio cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCTabellaClassiEdificio cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
					
		private void EffettuaCancellazioneACascata(CCTabellaClassiEdificio cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCTabellaClassiEdificio cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			