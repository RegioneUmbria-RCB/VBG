
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLO_DCONTRIBATTIV per la classe CCICalcoloDContribAttiv il 27/06/2008 13.01.38
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
	public partial class CCICalcoloDContribAttivMgr : BaseManager
	{
		public CCICalcoloDContribAttivMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoloDContribAttiv GetById(string idcomune, int id)
		{
			CCICalcoloDContribAttiv c = new CCICalcoloDContribAttiv();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoloDContribAttiv)db.GetClass(c);
		}

		public List<CCICalcoloDContribAttiv> GetList(string idcomune, int id, int codiceistanza, int fk_ccictc_id, int fk_cccca_id, float coefficiente)
		{
			CCICalcoloDContribAttiv c = new CCICalcoloDContribAttiv();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCcictcId = fk_ccictc_id;
			c.FkCcccaId = fk_cccca_id;
			c.Coefficiente = coefficiente;


			return db.GetClassList(c).ToList < CCICalcoloDContribAttiv>();
		}

		public List<CCICalcoloDContribAttiv> GetList(CCICalcoloDContribAttiv filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoloDContribAttiv>();
		}

		public CCICalcoloDContribAttiv Insert(CCICalcoloDContribAttiv cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCICalcoloDContribAttiv)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCICalcoloDContribAttiv ChildInsert(CCICalcoloDContribAttiv cls)
		{
			return cls;
		}

		private CCICalcoloDContribAttiv DataIntegrations(CCICalcoloDContribAttiv cls)
		{
			return cls;
		}
		

		public CCICalcoloDContribAttiv Update(CCICalcoloDContribAttiv cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCICalcoloDContribAttiv cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCICalcoloDContribAttiv cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCICalcoloDContribAttiv cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCICalcoloDContribAttiv cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			