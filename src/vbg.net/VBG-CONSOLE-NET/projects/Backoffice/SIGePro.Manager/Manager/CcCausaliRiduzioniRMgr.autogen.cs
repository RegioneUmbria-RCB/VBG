
			
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
	/// File generato automaticamente dalla tabella CC_CAUSALIRIDUZIONIR per la classe CcCausaliRiduzioniR il 06/03/2009 12.28.44
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
	public partial class CcCausaliRiduzioniRMgr : BaseManager
	{
		public CcCausaliRiduzioniRMgr(DataBase dataBase) : base(dataBase) { }

		public CcCausaliRiduzioniR GetById(string idcomune, int id)
		{
			CcCausaliRiduzioniR c = new CcCausaliRiduzioniR();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CcCausaliRiduzioniR)db.GetClass(c);
		}

		public List<CcCausaliRiduzioniR> GetList(CcCausaliRiduzioniR filtro)
		{
			return db.GetClassList( filtro ).ToList< CcCausaliRiduzioniR>();
		}

		public CcCausaliRiduzioniR Insert(CcCausaliRiduzioniR cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CcCausaliRiduzioniR)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CcCausaliRiduzioniR ChildInsert(CcCausaliRiduzioniR cls)
		{
			return cls;
		}

		private CcCausaliRiduzioniR DataIntegrations(CcCausaliRiduzioniR cls)
		{
			return cls;
		}
		

		public CcCausaliRiduzioniR Update(CcCausaliRiduzioniR cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CcCausaliRiduzioniR cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		

			
		private void EffettuaCancellazioneACascata(CcCausaliRiduzioniR cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CcCausaliRiduzioniR cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			