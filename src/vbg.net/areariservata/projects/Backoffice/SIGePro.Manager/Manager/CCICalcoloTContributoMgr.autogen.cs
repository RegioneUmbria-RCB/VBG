
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLO_TCONTRIBUTO per la classe CCICalcoloTContributo il 27/06/2008 13.01.38
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
	public partial class CCICalcoloTContributoMgr : BaseManager
	{
		public CCICalcoloTContributoMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoloTContributo GetById(string idcomune, int id)
		{
			CCICalcoloTContributo c = new CCICalcoloTContributo();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoloTContributo)db.GetClass(c);
		}

		public List<CCICalcoloTContributo> GetList(string idcomune, int id, int codiceistanza, int fk_ccict_id, string stato, float costoc_edificio, int fk_ccic_id, float coefficiente, int fk_ccde_id)
		{
			CCICalcoloTContributo c = new CCICalcoloTContributo();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCcictId = fk_ccict_id;
			if(!String.IsNullOrEmpty(stato))c.Stato = stato;
			c.CostocEdificio = costoc_edificio;
			c.FkCcicId = fk_ccic_id;
			c.Coefficiente = coefficiente;
			c.FkCcdeId = fk_ccde_id;


			return db.GetClassList(c).ToList < CCICalcoloTContributo>();
		}

		public List<CCICalcoloTContributo> GetList(CCICalcoloTContributo filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoloTContributo>();
		}

		public CCICalcoloTContributo Insert(CCICalcoloTContributo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCICalcoloTContributo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCICalcoloTContributo ChildInsert(CCICalcoloTContributo cls)
		{
			return cls;
		}

		

		
		private void VerificaRecordCollegati(CCICalcoloTContributo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}	
		
		private void Validate(CCICalcoloTContributo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}


	}
}
			
			
			