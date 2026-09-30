
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLOTOT per la classe OICalcoloTot il 27/06/2008 13.01.36
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
	public partial class OICalcoloTotMgr : BaseManager
	{
		public OICalcoloTotMgr(DataBase dataBase) : base(dataBase) { }

		public OICalcoloTot GetById(string idcomune, int id)
		{
			OICalcoloTot c = new OICalcoloTot();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloTot)db.GetClass(c);
		}

		public List<OICalcoloTot> GetList(string idcomune, int id, int codiceistanza, DateTime data, string descrizione, int fk_ovc_id)
		{
			OICalcoloTot c = new OICalcoloTot();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Data = data;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;
			c.FkOvcId = fk_ovc_id;


			return db.GetClassList(c).ToList < OICalcoloTot>();
		}

		public List<OICalcoloTot> GetList(OICalcoloTot filtro)
		{
			return db.GetClassList(filtro).ToList < OICalcoloTot>();
		}

		public OICalcoloTot Insert(OICalcoloTot cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OICalcoloTot)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OICalcoloTot ChildInsert(OICalcoloTot cls)
		{
			return cls;
		}

		private OICalcoloTot DataIntegrations(OICalcoloTot cls)
		{
			return cls;
		}

		public OICalcoloTot Update(OICalcoloTot cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OICalcoloTot cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OICalcoloTot cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
		
		private void Validate(OICalcoloTot cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			