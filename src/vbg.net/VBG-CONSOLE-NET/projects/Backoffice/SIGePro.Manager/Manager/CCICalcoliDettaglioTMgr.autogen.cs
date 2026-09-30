
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLI_DETTAGLIOT per la classe CCICalcoliDettaglioT il 30/06/2008 11.07.01
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
	public partial class CCICalcoliDettaglioTMgr : BaseManager
	{
		public CCICalcoliDettaglioTMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoliDettaglioT GetById(string idcomune, int id)
		{
			CCICalcoliDettaglioT c = new CCICalcoliDettaglioT();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoliDettaglioT)db.GetClass(c);
		}

		public List<CCICalcoliDettaglioT> GetList(string idcomune, int id, int codiceistanza, int ordine, int fk_ccts_id, int fk_ccds_id, string descrizione, float su, int fk_ccic_id)
		{
			CCICalcoliDettaglioT c = new CCICalcoliDettaglioT();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Ordine = ordine;
			c.FkCctsId = fk_ccts_id;
			c.FkCcdsId = fk_ccds_id;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;
			c.Su = su;
			c.FkCcicId = fk_ccic_id;


			return db.GetClassList(c).ToList < CCICalcoliDettaglioT>();
		}

		public List<CCICalcoliDettaglioT> GetList(CCICalcoliDettaglioT filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoliDettaglioT>();
		}

		public CCICalcoliDettaglioT Insert(CCICalcoliDettaglioT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCICalcoliDettaglioT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		private CCICalcoliDettaglioT ChildInsert(CCICalcoliDettaglioT cls)
		{
			return cls;
		}

		public CCICalcoliDettaglioT Update(CCICalcoliDettaglioT cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			return cls;
		}

		public void Delete(CCICalcoliDettaglioT cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCICalcoliDettaglioT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void Validate(CCICalcoliDettaglioT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			