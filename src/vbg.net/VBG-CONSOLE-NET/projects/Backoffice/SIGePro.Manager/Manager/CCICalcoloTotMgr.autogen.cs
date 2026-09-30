
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLOTOT per la classe CCICalcoloTot il 27/06/2008 13.01.38
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
	public partial class CCICalcoloTotMgr : BaseManager
	{
		public CCICalcoloTotMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoloTot GetById(string idcomune, int id)
		{
			CCICalcoloTot c = new CCICalcoloTot();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoloTot)db.GetClass(c);
		}

		public List<CCICalcoloTot> GetList(string idcomune, int id, int codiceistanza, DateTime data, int fk_ccvc_id, string fk_occbti_id, string fk_occbde_id, string fk_bcctc_id, string descrizione, float quotacontrib_totale)
		{
			CCICalcoloTot c = new CCICalcoloTot();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Data = data;
			c.FkCcvcId = fk_ccvc_id;
			if(!String.IsNullOrEmpty(fk_occbti_id))c.FkOccbtiId = fk_occbti_id;
			if(!String.IsNullOrEmpty(fk_occbde_id))c.FkOccbdeId = fk_occbde_id;
			if(!String.IsNullOrEmpty(fk_bcctc_id))c.FkBcctcId = fk_bcctc_id;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;
			c.QuotacontribTotale = quotacontrib_totale;


			return db.GetClassList(c).ToList < CCICalcoloTot>();
		}

		public List<CCICalcoloTot> GetList(CCICalcoloTot filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoloTot>();
		}


		

		public CCICalcoloTot Insert(CCICalcoloTot cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);

			db.Insert(cls);

			cls = (CCICalcoloTot)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		

		public CCICalcoloTot Update(CCICalcoloTot cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}


		
		private void VerificaRecordCollegati(CCICalcoloTot cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}	
		
		private void Validate(CCICalcoloTot cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			