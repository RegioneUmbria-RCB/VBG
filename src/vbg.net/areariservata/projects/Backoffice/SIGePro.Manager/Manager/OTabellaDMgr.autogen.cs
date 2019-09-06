
			
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
	/// File generato automaticamente dalla tabella O_TABELLAD per la classe OTabellaD il 27/06/2008 13.01.37
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
	public partial class OTabellaDMgr : BaseManager
	{
		public OTabellaDMgr(DataBase dataBase) : base(dataBase) { }

		public OTabellaD GetById(string idcomune, int id)
		{
			OTabellaD c = new OTabellaD();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OTabellaD)db.GetClass(c);
		}

		public List<OTabellaD> GetList(string idcomune, int id, int fk_ovc_id, int fk_ode_id, int fk_oca_id, int fk_oto_id, float costo, string software)
		{
			OTabellaD c = new OTabellaD();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.FkOvcId = fk_ovc_id;
			c.FkOdeId = fk_ode_id;
			c.FkOcaId = fk_oca_id;
			c.FkOtoId = fk_oto_id;
			c.Costo = costo;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OTabellaD>();
		}

		public List<OTabellaD> GetList(OTabellaD filtro)
		{
			return db.GetClassList(filtro).ToList < OTabellaD>();
		}
	
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OTabellaD ChildInsert(OTabellaD cls)
		{
			return cls;
		}
	
		private void VerificaRecordCollegati(OTabellaD cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OTabellaD cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		private void Validate(OTabellaD cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			