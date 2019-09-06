
			
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
	/// File generato automaticamente dalla tabella OCC_BASEDESTINAZIONI per la classe OCCBaseDestinazioni il 27/06/2008 13.01.41
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
	public partial class OCCBaseDestinazioniMgr : BaseManager
	{
		public OCCBaseDestinazioniMgr(DataBase dataBase) : base(dataBase) { }

		public OCCBaseDestinazioni GetById(string id)
		{
			OCCBaseDestinazioni c = new OCCBaseDestinazioni();
			
			
			c.Id = id;
			
			return (OCCBaseDestinazioni)db.GetClass(c);
		}

		public List<OCCBaseDestinazioni> GetList(string id, string destinazione)
		{
			OCCBaseDestinazioni c = new OCCBaseDestinazioni();
			if(!String.IsNullOrEmpty(id))c.Id = id;
			if(!String.IsNullOrEmpty(destinazione))c.Destinazione = destinazione;

			c.OrderBy = "Destinazione asc";

			return db.GetClassList(c).ToList < OCCBaseDestinazioni>();
		}

		public List<OCCBaseDestinazioni> GetList(OCCBaseDestinazioni filtro)
		{
			return db.GetClassList(filtro).ToList < OCCBaseDestinazioni>();
		}

		public OCCBaseDestinazioni Insert(OCCBaseDestinazioni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OCCBaseDestinazioni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OCCBaseDestinazioni ChildInsert(OCCBaseDestinazioni cls)
		{
			return cls;
		}

		private OCCBaseDestinazioni DataIntegrations(OCCBaseDestinazioni cls)
		{
			return cls;
		}
		

		public OCCBaseDestinazioni Update(OCCBaseDestinazioni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OCCBaseDestinazioni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OCCBaseDestinazioni cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OCCBaseDestinazioni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OCCBaseDestinazioni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			