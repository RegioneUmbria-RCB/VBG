
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLI_DETTAGLIOR per la classe CCICalcoliDettaglioR il 27/06/2008 13.01.38
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
	public partial class CCICalcoliDettaglioRMgr : BaseManager
	{
		public CCICalcoliDettaglioRMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoliDettaglioR GetById(string idcomune, int id)
		{
			CCICalcoliDettaglioR c = new CCICalcoliDettaglioR();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoliDettaglioR)db.GetClass(c);
		}

		public List<CCICalcoliDettaglioR> GetList(string idcomune, int id, int codiceistanza, int qta, float lung, float larg, int fk_ccicdt_id, float su)
		{
			CCICalcoliDettaglioR c = new CCICalcoliDettaglioR();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Qta = qta;
			c.Lung = lung;
			c.Larg = larg;
			c.FkCcicdtId = fk_ccicdt_id;
			c.Su = su;


			return db.GetClassList(c).ToList < CCICalcoliDettaglioR>();
		}

		public List<CCICalcoliDettaglioR> GetList(CCICalcoliDettaglioR filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoliDettaglioR>();
		}

		public CCICalcoliDettaglioR Insert(CCICalcoliDettaglioR cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCICalcoliDettaglioR)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}



		private CCICalcoliDettaglioR DataIntegrations(CCICalcoliDettaglioR cls)
		{
			return cls;
		}
		

		
		private void VerificaRecordCollegati(CCICalcoliDettaglioR cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCICalcoliDettaglioR cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCICalcoliDettaglioR cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			