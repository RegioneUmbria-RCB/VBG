
			
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
	/// File generato automaticamente dalla tabella STILIFRONTOFFICE per la classe StiliFrontOffice il 24/03/2010 10.11.26
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
	public partial class StiliFrontOfficeMgr : BaseManager
	{
		public StiliFrontOfficeMgr(DataBase dataBase) : base(dataBase) { }

		public StiliFrontOffice GetById(string idcomune)
		{
			StiliFrontOffice c = new StiliFrontOffice();
			
			
			c.Idcomune = idcomune;
			
			return (StiliFrontOffice)db.GetClass(c);
		}

		public List<StiliFrontOffice> GetList(StiliFrontOffice filtro)
		{
			return db.GetClassList( filtro ).ToList< StiliFrontOffice>();
		}

		public StiliFrontOffice Insert(StiliFrontOffice cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (StiliFrontOffice)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private StiliFrontOffice ChildInsert(StiliFrontOffice cls)
		{
			return cls;
		}

		private StiliFrontOffice DataIntegrations(StiliFrontOffice cls)
		{
			return cls;
		}
		

		public StiliFrontOffice Update(StiliFrontOffice cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(StiliFrontOffice cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(StiliFrontOffice cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(StiliFrontOffice cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(StiliFrontOffice cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			