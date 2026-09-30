
			
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
	/// File generato automaticamente dalla tabella CC_CONFIGURAZIONE_SETTORI per la classe CCConfigurazioneSettori il 27/06/2008 13.01.37
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
	public partial class CCConfigurazioneSettoriMgr : BaseManager
	{
		public CCConfigurazioneSettoriMgr(DataBase dataBase) : base(dataBase) { }

		public CCConfigurazioneSettori GetById(string idcomune, string software, string fk_se_codicesettore)
		{
			CCConfigurazioneSettori c = new CCConfigurazioneSettori();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			c.FkSeCodicesettore = fk_se_codicesettore;
			
			return (CCConfigurazioneSettori)db.GetClass(c);
		}

		public List<CCConfigurazioneSettori> GetList(string idcomune, string software, string fk_se_codicesettore)
		{
			CCConfigurazioneSettori c = new CCConfigurazioneSettori();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			if(!String.IsNullOrEmpty(software))c.Software = software;
			if(!String.IsNullOrEmpty(fk_se_codicesettore))c.FkSeCodicesettore = fk_se_codicesettore;


			return db.GetClassList(c).ToList < CCConfigurazioneSettori>();
		}

		public List<CCConfigurazioneSettori> GetList(CCConfigurazioneSettori filtro)
		{
			return db.GetClassList(filtro).ToList < CCConfigurazioneSettori>();
		}

		public CCConfigurazioneSettori Insert(CCConfigurazioneSettori cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCConfigurazioneSettori)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCConfigurazioneSettori ChildInsert(CCConfigurazioneSettori cls)
		{
			return cls;
		}

		private CCConfigurazioneSettori DataIntegrations(CCConfigurazioneSettori cls)
		{
			return cls;
		}
		

		public CCConfigurazioneSettori Update(CCConfigurazioneSettori cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCConfigurazioneSettori cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
			
		private void EffettuaCancellazioneACascata(CCConfigurazioneSettori cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCConfigurazioneSettori cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			