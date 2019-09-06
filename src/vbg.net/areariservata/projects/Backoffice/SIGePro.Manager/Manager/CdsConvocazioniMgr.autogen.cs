
			
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
	/// File generato automaticamente dalla tabella CDSCONVOCAZIONI per la classe CdsConvocazioni il 30/07/2008 16.24.11
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
	public partial class CdsConvocazioniMgr : BaseManager
	{
		public CdsConvocazioniMgr(DataBase dataBase) : base(dataBase) { }

		public CdsConvocazioni GetById(int id, int codiceistanza, int idtestata, string idcomune)
		{
			CdsConvocazioni c = new CdsConvocazioni();
			
			
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Idtestata = idtestata;
			c.Idcomune = idcomune;
			
			return (CdsConvocazioni)db.GetClass(c);
		}

		public List<CdsConvocazioni> GetList(DateTime dataconvocazione, string oraconvocazione, int id, int codiceistanza, int idtestata, string idcomune)
		{
			CdsConvocazioni c = new CdsConvocazioni();
			c.Dataconvocazione = dataconvocazione;
			if(!String.IsNullOrEmpty(oraconvocazione))c.Oraconvocazione = oraconvocazione;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Idtestata = idtestata;
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			
		
			return db.GetClassList( c ).ToList< CdsConvocazioni>();
		}

		public List<CdsConvocazioni> GetList(CdsConvocazioni filtro)
		{
			return db.GetClassList( filtro ).ToList< CdsConvocazioni>();
		}

		public CdsConvocazioni Insert(CdsConvocazioni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CdsConvocazioni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CdsConvocazioni ChildInsert(CdsConvocazioni cls)
		{
			return cls;
		}

		private CdsConvocazioni DataIntegrations(CdsConvocazioni cls)
		{
			return cls;
		}
		

		public CdsConvocazioni Update(CdsConvocazioni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CdsConvocazioni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CdsConvocazioni cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CdsConvocazioni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CdsConvocazioni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			