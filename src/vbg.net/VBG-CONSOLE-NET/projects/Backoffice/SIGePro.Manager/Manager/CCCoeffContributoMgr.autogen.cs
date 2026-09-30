
			
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
	/// File generato automaticamente dalla tabella CC_COEFFCONTRIBUTO per la classe CCCoeffContributo il 30/06/2008 11.22.53
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
	public partial class CCCoeffContributoMgr : BaseManager
	{
		public CCCoeffContributoMgr(DataBase dataBase) : base(dataBase) { }

		public CCCoeffContributo GetById(string idcomune, int id)
		{
			CCCoeffContributo c = new CCCoeffContributo();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCCoeffContributo)db.GetClass(c);
		}

		public List<CCCoeffContributo> GetList(string idcomune, int id, int fk_ccvc_id, int fk_ccde_id, int fk_ccti_id, int fk_aree_codicearea, float coefficiente, string software)
		{
			CCCoeffContributo c = new CCCoeffContributo();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.FkCcvcId = fk_ccvc_id;
			c.FkCcdeId = fk_ccde_id;
			c.FkCctiId = fk_ccti_id;
			c.FkAreeCodicearea = fk_aree_codicearea;
			c.Coefficiente = coefficiente;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < CCCoeffContributo>();
		}

		public List<CCCoeffContributo> GetList(CCCoeffContributo filtro)
		{
			return db.GetClassList(filtro).ToList < CCCoeffContributo>();
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCCoeffContributo ChildInsert(CCCoeffContributo cls)
		{
			return cls;
		}
		
		private void VerificaRecordCollegati(CCCoeffContributo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCCoeffContributo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCCoeffContributo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}


	}
}
			
			
			