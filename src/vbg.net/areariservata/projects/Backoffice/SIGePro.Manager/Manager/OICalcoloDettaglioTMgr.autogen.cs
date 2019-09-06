
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLO_DETTAGLIOT per la classe OICalcoloDettaglioT il 27/06/2008 13.01.36
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
	public partial class OICalcoloDettaglioTMgr : BaseManager
	{
		public OICalcoloDettaglioTMgr(DataBase dataBase) : base(dataBase) { }

		public OICalcoloDettaglioT GetById(string idcomune, int id)
		{
			OICalcoloDettaglioT c = new OICalcoloDettaglioT();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloDettaglioT)db.GetClass(c);
		}

		public List<OICalcoloDettaglioT> GetList(string idcomune, int id, int codiceistanza, int fk_oic_id, int ordine, string fk_occbde_id, int fk_ode_id, string descrizione, float totale)
		{
			OICalcoloDettaglioT c = new OICalcoloDettaglioT();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkOicId = fk_oic_id;
			c.Ordine = ordine;
			if(!String.IsNullOrEmpty(fk_occbde_id))c.FkOccbdeId = fk_occbde_id;
			c.FkOdeId = fk_ode_id;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;
			c.Totale = totale;


			return db.GetClassList(c).ToList < OICalcoloDettaglioT>();
		}

		public List<OICalcoloDettaglioT> GetList(OICalcoloDettaglioT filtro)
		{
			return db.GetClassList(filtro).ToList < OICalcoloDettaglioT>();
		}

		public OICalcoloDettaglioT Insert(OICalcoloDettaglioT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OICalcoloDettaglioT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OICalcoloDettaglioT ChildInsert(OICalcoloDettaglioT cls)
		{
			return cls;
		}

		public OICalcoloDettaglioT Update(OICalcoloDettaglioT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OICalcoloDettaglioT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OICalcoloDettaglioT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
		
		private void Validate(OICalcoloDettaglioT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			