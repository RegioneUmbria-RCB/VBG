
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLO_DCONTRIBUTO per la classe CCICalcoloDContributo il 27/06/2008 13.01.38
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
	public partial class CCICalcoloDContributoMgr : BaseManager
	{
		public CCICalcoloDContributoMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoloDContributo GetById(string idcomune, int id)
		{
			CCICalcoloDContributo c = new CCICalcoloDContributo();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoloDContributo)db.GetClass(c);
		}

		public List<CCICalcoloDContributo> GetList(string idcomune, int id, int codiceistanza, int fk_ccictc_id, int fk_ccti_id, int fk_aree_codicearea, float coefficiente)
		{
			CCICalcoloDContributo c = new CCICalcoloDContributo();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCcictcId = fk_ccictc_id;
			c.FkCctiId = fk_ccti_id;
			c.FkAreeCodicearea = fk_aree_codicearea;
			c.Coefficiente = coefficiente;


			return db.GetClassList(c).ToList < CCICalcoloDContributo>();
		}

		public List<CCICalcoloDContributo> GetList(CCICalcoloDContributo filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoloDContributo>();
		}

		public CCICalcoloDContributo Insert(CCICalcoloDContributo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCICalcoloDContributo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCICalcoloDContributo ChildInsert(CCICalcoloDContributo cls)
		{
			return cls;
		}

		private CCICalcoloDContributo DataIntegrations(CCICalcoloDContributo cls)
		{
			return cls;
		}
		

		public CCICalcoloDContributo Update(CCICalcoloDContributo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCICalcoloDContributo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCICalcoloDContributo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCICalcoloDContributo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCICalcoloDContributo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			