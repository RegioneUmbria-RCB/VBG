
			
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
	/// File generato automaticamente dalla tabella O_DESTINAZIONI per la classe ODestinazioni il 27/06/2008 13.01.35
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
	public partial class ODestinazioniMgr : BaseManager
	{
		public ODestinazioniMgr(DataBase dataBase) : base(dataBase) { }

		public ODestinazioni GetById(string idcomune, int id)
		{
			ODestinazioni c = new ODestinazioni();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (ODestinazioni)db.GetClass(c);
		}

		public List<ODestinazioni> GetList(string idcomune, int id, string fk_occbde_id, string destinazione, int fk_tum_umid, string software)
		{
			ODestinazioni c = new ODestinazioni();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			if(!String.IsNullOrEmpty(fk_occbde_id))c.FkOccbdeId = fk_occbde_id;
			if(!String.IsNullOrEmpty(destinazione))c.Destinazione = destinazione;
			c.FkTumUmid = fk_tum_umid;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < ODestinazioni>();
		}

		public List<ODestinazioni> GetList(ODestinazioni filtro)
		{
			return db.GetClassList(filtro).ToList < ODestinazioni>();
		}

		public ODestinazioni Insert(ODestinazioni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ODestinazioni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ODestinazioni ChildInsert(ODestinazioni cls)
		{
			return cls;
		}

		private ODestinazioni DataIntegrations(ODestinazioni cls)
		{
			return cls;
		}
		

		public ODestinazioni Update(ODestinazioni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ODestinazioni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		

			
		private void EffettuaCancellazioneACascata(ODestinazioni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ODestinazioni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			