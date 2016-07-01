
			
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
	/// File generato automaticamente dalla tabella FO_SOTTOSCRIZIONI per la classe FoSottoscrizioni il 09/11/2009 10.52.22
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
	public partial class FoSottoscrizioniMgr : BaseManager
	{
		public FoSottoscrizioniMgr(DataBase dataBase) : base(dataBase) { }

		public FoSottoscrizioni GetById(string idcomune, string id)
		{
			FoSottoscrizioni c = new FoSottoscrizioni();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (FoSottoscrizioni)db.GetClass(c);
		}

		public List<FoSottoscrizioni> GetList(FoSottoscrizioni filtro)
		{
			return db.GetClassList( filtro ).ToList< FoSottoscrizioni>();
		}

		public FoSottoscrizioni Insert(FoSottoscrizioni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoSottoscrizioni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoSottoscrizioni ChildInsert(FoSottoscrizioni cls)
		{
			return cls;
		}

		private FoSottoscrizioni DataIntegrations(FoSottoscrizioni cls)
		{
			return cls;
		}
		

		public FoSottoscrizioni Update(FoSottoscrizioni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(FoSottoscrizioni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(FoSottoscrizioni cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(FoSottoscrizioni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(FoSottoscrizioni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			