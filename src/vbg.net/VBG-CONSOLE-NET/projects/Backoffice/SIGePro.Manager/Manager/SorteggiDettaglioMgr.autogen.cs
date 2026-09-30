
			
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
	/// File generato automaticamente dalla tabella SORTEGGIDETTAGLIO per la classe SorteggiDettaglio il 27/01/2009 8.43.48
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
	public partial class SorteggiDettaglioMgr : BaseManager
	{
		public SorteggiDettaglioMgr(DataBase dataBase) : base(dataBase) { }

		public SorteggiDettaglio GetById(int sd_id, string idcomune)
		{
			SorteggiDettaglio c = new SorteggiDettaglio();
			
			
			c.SdId = sd_id;
			c.Idcomune = idcomune;
			
			return (SorteggiDettaglio)db.GetClass(c);
		}

		public List<SorteggiDettaglio> GetList(SorteggiDettaglio filtro)
		{
			return db.GetClassList( filtro ).ToList< SorteggiDettaglio>();
		}

		public SorteggiDettaglio Insert(SorteggiDettaglio cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (SorteggiDettaglio)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private SorteggiDettaglio ChildInsert(SorteggiDettaglio cls)
		{
			return cls;
		}

		private SorteggiDettaglio DataIntegrations(SorteggiDettaglio cls)
		{
			return cls;
		}
		

		public SorteggiDettaglio Update(SorteggiDettaglio cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(SorteggiDettaglio cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(SorteggiDettaglio cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(SorteggiDettaglio cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(SorteggiDettaglio cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			