
			
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
	/// File generato automaticamente dalla tabella FO_RICHIESTE per la classe FoRichieste il 11/12/2009 10.32.40
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
	public partial class FoRichiesteMgr : BaseManager
	{
		public FoRichiesteMgr(DataBase dataBase) : base(dataBase) { }

		public FoRichieste GetById(string idcomune, int? id)
		{
			FoRichieste c = new FoRichieste();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (FoRichieste)db.GetClass(c);
		}

		public List<FoRichieste> GetList(FoRichieste filtro)
		{
			return db.GetClassList( filtro ).ToList< FoRichieste>();
		}

		public FoRichieste Insert(FoRichieste cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoRichieste)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoRichieste ChildInsert(FoRichieste cls)
		{
			return cls;
		}

		private FoRichieste DataIntegrations(FoRichieste cls)
		{
			return cls;
		}
		

		public FoRichieste Update(FoRichieste cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(FoRichieste cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(FoRichieste cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(FoRichieste cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(FoRichieste cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			