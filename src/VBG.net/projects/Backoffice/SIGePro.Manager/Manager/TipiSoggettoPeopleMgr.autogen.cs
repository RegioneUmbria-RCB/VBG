
			
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
	/// File generato automaticamente dalla tabella TIPISOGGETTOPEOPLE per la classe TipiSoggettoPeople il 10/07/2008 11.15.56
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
	public partial class TipiSoggettoPeopleMgr : BaseManager
	{
		public TipiSoggettoPeopleMgr(DataBase dataBase) : base(dataBase) { }

		public TipiSoggettoPeople GetById(string idcomune, string software, string tiporapprpeople)
		{
			TipiSoggettoPeople c = new TipiSoggettoPeople();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			c.Tiporapprpeople = tiporapprpeople;
			
			return (TipiSoggettoPeople)db.GetClass(c);
		}

		public List<TipiSoggettoPeople> GetList(string idcomune, string software, string tiporapprpeople, int codicetiposoggetto)
		{
			TipiSoggettoPeople c = new TipiSoggettoPeople();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			if(!String.IsNullOrEmpty(software))c.Software = software;
			if(!String.IsNullOrEmpty(tiporapprpeople))c.Tiporapprpeople = tiporapprpeople;
			c.Codicetiposoggetto = codicetiposoggetto;


			return db.GetClassList(c).ToList < TipiSoggettoPeople>() ;
		}

		public List<TipiSoggettoPeople> GetList(TipiSoggettoPeople filtro)
		{
			return db.GetClassList(filtro).ToList < TipiSoggettoPeople>();
		}

		public TipiSoggettoPeople Insert(TipiSoggettoPeople cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiSoggettoPeople)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiSoggettoPeople ChildInsert(TipiSoggettoPeople cls)
		{
			return cls;
		}

		private TipiSoggettoPeople DataIntegrations(TipiSoggettoPeople cls)
		{
			return cls;
		}
		

		public TipiSoggettoPeople Update(TipiSoggettoPeople cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiSoggettoPeople cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiSoggettoPeople cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiSoggettoPeople cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TipiSoggettoPeople cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			