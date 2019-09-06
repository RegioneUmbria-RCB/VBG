
			
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
	/// File generato automaticamente dalla tabella SORTEGGITESTATA per la classe SorteggiTestata il 26/01/2009 16.36.22
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
	public partial class SorteggiTestataMgr : BaseManager
	{
		public SorteggiTestataMgr(DataBase dataBase) : base(dataBase) { }

		public SorteggiTestata GetById(int st_id, string idcomune)
		{
			SorteggiTestata c = new SorteggiTestata();
			
			
			c.StId = st_id;
			c.Idcomune = idcomune;
			
			return (SorteggiTestata)db.GetClass(c);
		}

		public List<SorteggiTestata> GetList(SorteggiTestata filtro)
		{
			return db.GetClassList( filtro ).ToList< SorteggiTestata>();
		}

		public SorteggiTestata Insert(SorteggiTestata cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (SorteggiTestata)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private SorteggiTestata ChildInsert(SorteggiTestata cls)
		{
			return cls;
		}

		private SorteggiTestata DataIntegrations(SorteggiTestata cls)
		{
			return cls;
		}
		

		public SorteggiTestata Update(SorteggiTestata cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(SorteggiTestata cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(SorteggiTestata cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(SorteggiTestata cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(SorteggiTestata cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			