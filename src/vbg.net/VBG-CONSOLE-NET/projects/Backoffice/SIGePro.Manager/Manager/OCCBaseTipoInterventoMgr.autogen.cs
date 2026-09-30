
			
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
	/// File generato automaticamente dalla tabella OCC_BASETIPOINTERVENTO per la classe OCCBaseTipoIntervento il 27/06/2008 13.01.41
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
	public partial class OCCBaseTipoInterventoMgr : BaseManager
	{
		public OCCBaseTipoInterventoMgr(DataBase dataBase) : base(dataBase) { }

		public OCCBaseTipoIntervento GetById(string id)
		{
			OCCBaseTipoIntervento c = new OCCBaseTipoIntervento();
			
			
			c.Id = id;
			
			return (OCCBaseTipoIntervento)db.GetClass(c);
		}

		public List<OCCBaseTipoIntervento> GetList(string id, string intervento)
		{
			OCCBaseTipoIntervento c = new OCCBaseTipoIntervento();
			if(!String.IsNullOrEmpty(id))c.Id = id;
			if(!String.IsNullOrEmpty(intervento))c.Intervento = intervento;


			return db.GetClassList(c).ToList < OCCBaseTipoIntervento>();
		}

		public List<OCCBaseTipoIntervento> GetList(OCCBaseTipoIntervento filtro)
		{
			return db.GetClassList(filtro).ToList < OCCBaseTipoIntervento>();
		}

		public OCCBaseTipoIntervento Insert(OCCBaseTipoIntervento cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OCCBaseTipoIntervento)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OCCBaseTipoIntervento ChildInsert(OCCBaseTipoIntervento cls)
		{
			return cls;
		}

		private OCCBaseTipoIntervento DataIntegrations(OCCBaseTipoIntervento cls)
		{
			return cls;
		}
		

		public OCCBaseTipoIntervento Update(OCCBaseTipoIntervento cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OCCBaseTipoIntervento cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OCCBaseTipoIntervento cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OCCBaseTipoIntervento cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OCCBaseTipoIntervento cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			