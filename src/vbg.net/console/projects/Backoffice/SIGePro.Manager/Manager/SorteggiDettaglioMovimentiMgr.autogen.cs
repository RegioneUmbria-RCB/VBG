
			
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
	/// File generato automaticamente dalla tabella SORTEGGIDETTAGLIOMOVIMENTI per la classe SorteggiDettaglioMovimenti il 27/01/2009 8.44.05
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
	public partial class SorteggiDettaglioMovimentiMgr : BaseManager
	{
		public SorteggiDettaglioMovimentiMgr(DataBase dataBase) : base(dataBase) { }

		public SorteggiDettaglioMovimenti GetById(string idcomune, int sdm_fk_sdid, int codicemovimento)
		{
			SorteggiDettaglioMovimenti c = new SorteggiDettaglioMovimenti();
			
			
			c.Idcomune = idcomune;
			c.SdmFkSdid = sdm_fk_sdid;
			c.Codicemovimento = codicemovimento;
			
			return (SorteggiDettaglioMovimenti)db.GetClass(c);
		}

		public List<SorteggiDettaglioMovimenti> GetList(SorteggiDettaglioMovimenti filtro)
		{
			return db.GetClassList( filtro ).ToList< SorteggiDettaglioMovimenti>();
		}

		public SorteggiDettaglioMovimenti Insert(SorteggiDettaglioMovimenti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (SorteggiDettaglioMovimenti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private SorteggiDettaglioMovimenti ChildInsert(SorteggiDettaglioMovimenti cls)
		{
			return cls;
		}

		private SorteggiDettaglioMovimenti DataIntegrations(SorteggiDettaglioMovimenti cls)
		{
			return cls;
		}
		

		public SorteggiDettaglioMovimenti Update(SorteggiDettaglioMovimenti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(SorteggiDettaglioMovimenti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(SorteggiDettaglioMovimenti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(SorteggiDettaglioMovimenti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(SorteggiDettaglioMovimenti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			