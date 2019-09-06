
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBT_BTO per la classe OICalcoloContribTBTO il 08/07/2008 10.12.50
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
	public partial class OICalcoloContribTBTOMgr : BaseManager
	{
		public OICalcoloContribTBTOMgr(DataBase dataBase) : base(dataBase) { }

		public OICalcoloContribTBTO GetById(string idcomune, int id)
		{
			OICalcoloContribTBTO c = new OICalcoloContribTBTO();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloContribTBTO)db.GetClass(c);
		}

		public List<OICalcoloContribTBTO> GetList(string idcomune, int id, int codiceistanza, int fk_oicct_id, string fk_bto_id, double costotot)
		{
			OICalcoloContribTBTO c = new OICalcoloContribTBTO();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkOicctId = fk_oicct_id;
			if(!String.IsNullOrEmpty(fk_bto_id))c.FkBtoId = fk_bto_id;
			c.Costotot = costotot;


			return db.GetClassList(c).ToList < OICalcoloContribTBTO>();
		}

		public List<OICalcoloContribTBTO> GetList(OICalcoloContribTBTO filtro)
		{
			return db.GetClassList(filtro).ToList < OICalcoloContribTBTO>();
		}

		public OICalcoloContribTBTO Insert(OICalcoloContribTBTO cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OICalcoloContribTBTO)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OICalcoloContribTBTO ChildInsert(OICalcoloContribTBTO cls)
		{
			return cls;
		}

		private OICalcoloContribTBTO DataIntegrations(OICalcoloContribTBTO cls)
		{
			return cls;
		}
		

		public OICalcoloContribTBTO Update(OICalcoloContribTBTO cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OICalcoloContribTBTO cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OICalcoloContribTBTO cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OICalcoloContribTBTO cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OICalcoloContribTBTO cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			