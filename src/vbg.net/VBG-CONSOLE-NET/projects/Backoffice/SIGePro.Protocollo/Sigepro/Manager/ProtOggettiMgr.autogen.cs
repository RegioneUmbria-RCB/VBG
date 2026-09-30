
			
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
	/// File generato automaticamente dalla tabella PROT_OGGETTI per la classe ProtOggetti il 23/08/2011 11.15.49
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
	public partial class ProtOggettiMgr : BaseManager
	{
		public ProtOggettiMgr(DataBase dataBase) : base(dataBase) { }

		public ProtOggetti GetById(int? codiceoggetto, string idcomune)
		{
			ProtOggetti c = new ProtOggetti();
			
			
			c.Codiceoggetto = codiceoggetto;
			c.Idcomune = idcomune;
			
			return (ProtOggetti)db.GetClass(c);
		}

		public List<ProtOggetti> GetList(ProtOggetti filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtOggetti>();
		}

		public ProtOggetti Insert(ProtOggetti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtOggetti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtOggetti ChildInsert(ProtOggetti cls)
		{
			return cls;
		}

		private ProtOggetti DataIntegrations(ProtOggetti cls)
		{
			return cls;
		}
		

		public ProtOggetti Update(ProtOggetti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtOggetti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtOggetti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtOggetti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtOggetti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			