
			
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
	/// File generato automaticamente dalla tabella ALBEROPROC_DOCUMENTICAT per la classe AlberoProcDocumentiCat il 06/11/2009 9.31.24
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
	public partial class AlberoProcDocumentiCatMgr : BaseManager
	{
		public AlberoProcDocumentiCatMgr(DataBase dataBase) : base(dataBase) { }

		public AlberoProcDocumentiCat GetById(string idcomune, int? id)
		{
			AlberoProcDocumentiCat c = new AlberoProcDocumentiCat();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (AlberoProcDocumentiCat)db.GetClass(c);
		}

		public List<AlberoProcDocumentiCat> GetList(AlberoProcDocumentiCat filtro)
		{
			return db.GetClassList( filtro ).ToList< AlberoProcDocumentiCat>();
		}

		public AlberoProcDocumentiCat Insert(AlberoProcDocumentiCat cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AlberoProcDocumentiCat)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AlberoProcDocumentiCat ChildInsert(AlberoProcDocumentiCat cls)
		{
			return cls;
		}

		private AlberoProcDocumentiCat DataIntegrations(AlberoProcDocumentiCat cls)
		{
			return cls;
		}
		

		public AlberoProcDocumentiCat Update(AlberoProcDocumentiCat cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AlberoProcDocumentiCat cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AlberoProcDocumentiCat cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(AlberoProcDocumentiCat cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(AlberoProcDocumentiCat cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			