
			
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
	/// File generato automaticamente dalla tabella CDSINVITATI2 per la classe CdsInvitati2 il 30/07/2008 16.22.49
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
	public partial class CdsInvitati2Mgr : BaseManager
	{
		public CdsInvitati2Mgr(DataBase dataBase) : base(dataBase) { }

		public CdsInvitati2 GetById(int codiceinvitato, int codiceistanza, string idcomune, int fkidtestata)
		{
			CdsInvitati2 c = new CdsInvitati2();
			
			
			c.Codiceinvitato = codiceinvitato;
			c.Codiceistanza = codiceistanza;
			c.Idcomune = idcomune;
			c.Fkidtestata = fkidtestata;
			
			return (CdsInvitati2)db.GetClass(c);
		}

		public List<CdsInvitati2> GetList(int codiceinvitato, int codiceistanza, int codiceatto, int codiceanagrafe, string note, string idcomune, int fkidtestata)
		{
			CdsInvitati2 c = new CdsInvitati2();
			c.Codiceinvitato = codiceinvitato;
			c.Codiceistanza = codiceistanza;
			c.Codiceatto = codiceatto;
			c.Codiceanagrafe = codiceanagrafe;
			if(!String.IsNullOrEmpty(note))c.Note = note;
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Fkidtestata = fkidtestata;
			
		
			return db.GetClassList( c ).ToList< CdsInvitati2>();
		}

		public List<CdsInvitati2> GetList(CdsInvitati2 filtro)
		{
			return db.GetClassList( filtro ).ToList< CdsInvitati2>();
		}

		public CdsInvitati2 Insert(CdsInvitati2 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CdsInvitati2)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CdsInvitati2 ChildInsert(CdsInvitati2 cls)
		{
			return cls;
		}

		private CdsInvitati2 DataIntegrations(CdsInvitati2 cls)
		{
			return cls;
		}
		

		public CdsInvitati2 Update(CdsInvitati2 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CdsInvitati2 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CdsInvitati2 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CdsInvitati2 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CdsInvitati2 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			