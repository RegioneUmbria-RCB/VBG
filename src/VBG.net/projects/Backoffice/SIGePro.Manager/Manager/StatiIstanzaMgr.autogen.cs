
			
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
	/// File generato automaticamente dalla tabella STATIISTANZA per la classe StatiIstanza il 31/10/2008 14.59.11
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
	public partial class StatiIstanzaMgr : BaseManager
	{
		public StatiIstanzaMgr(DataBase dataBase) : base(dataBase) { }

		public StatiIstanza GetById(string idcomune, string software, string codicestato)
		{
			StatiIstanza c = new StatiIstanza();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			c.Codicestato = codicestato;
			
			return (StatiIstanza)db.GetClass(c);
		}

		public List<StatiIstanza> GetList(StatiIstanza filtro)
		{
			return db.GetClassList( filtro ).ToList< StatiIstanza>();
		}

		public StatiIstanza Insert(StatiIstanza cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (StatiIstanza)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private StatiIstanza ChildInsert(StatiIstanza cls)
		{
			return cls;
		}

		private StatiIstanza DataIntegrations(StatiIstanza cls)
		{
			return cls;
		}
		

		public StatiIstanza Update(StatiIstanza cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(StatiIstanza cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(StatiIstanza cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(StatiIstanza cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(StatiIstanza cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			