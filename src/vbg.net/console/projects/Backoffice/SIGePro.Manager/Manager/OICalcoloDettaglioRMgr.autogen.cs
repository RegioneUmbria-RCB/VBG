
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLO_DETTAGLIOR per la classe OICalcoloDettaglioR il 27/06/2008 13.01.36
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
	public partial class OICalcoloDettaglioRMgr : BaseManager
	{
		public OICalcoloDettaglioRMgr(DataBase dataBase) : base(dataBase) { }

		public OICalcoloDettaglioR GetById(string idcomune, int id)
		{
			OICalcoloDettaglioR c = new OICalcoloDettaglioR();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloDettaglioR)db.GetClass(c);
		}

		public List<OICalcoloDettaglioR> GetList(string idcomune, int codiceistanza, int id, int fk_oicdt_id, float qta, float lung, float larg, float alt, float totale)
		{
			OICalcoloDettaglioR c = new OICalcoloDettaglioR();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Codiceistanza = codiceistanza;
			c.Id = id;
			c.FkOicdtId = fk_oicdt_id;
			c.Qta = qta;
			c.Lung = lung;
			c.Larg = larg;
			c.Alt = alt;
			c.Totale = totale;


			return db.GetClassList(c).ToList < OICalcoloDettaglioR>();
		}

		public List<OICalcoloDettaglioR> GetList(OICalcoloDettaglioR filtro)
		{
			return db.GetClassList(filtro).ToList < OICalcoloDettaglioR>();
		}

		public OICalcoloDettaglioR Insert(OICalcoloDettaglioR cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OICalcoloDettaglioR)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		

		private OICalcoloDettaglioR ChildInsert(OICalcoloDettaglioR cls)
		{
			return cls;
		}

		private OICalcoloDettaglioR DataIntegrations(OICalcoloDettaglioR cls)
		{
			return cls;
		}
		


		
		private void VerificaRecordCollegati(OICalcoloDettaglioR cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OICalcoloDettaglioR cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OICalcoloDettaglioR cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			