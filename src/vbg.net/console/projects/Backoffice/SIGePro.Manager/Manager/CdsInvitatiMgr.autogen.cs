
			
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
	/// File generato automaticamente dalla tabella CDSINVITATI per la classe CdsInvitati il 30/07/2008 16.12.53
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
	public partial class CdsInvitatiMgr : BaseManager
	{
		public CdsInvitatiMgr(DataBase dataBase) : base(dataBase) { }

		public CdsInvitati GetById(int codiceinvitato, int codiceistanza, string idcomune, int fkidtestata)
		{
			CdsInvitati c = new CdsInvitati();
			
			
			c.Codiceinvitato = codiceinvitato;
			c.Codiceistanza = codiceistanza;
			c.Idcomune = idcomune;
			c.Fkidtestata = fkidtestata;
			
			return (CdsInvitati)db.GetClass(c);
		}

		public List<CdsInvitati> GetList(int codiceinvitato, int codiceistanza, int codiceatto, int codiceamministrazione, string note, string idcomune, int fkidtestata)
		{
			CdsInvitati c = new CdsInvitati();
			c.Codiceinvitato = codiceinvitato;
			c.Codiceistanza = codiceistanza;
			c.Codiceatto = codiceatto;
			c.Codiceamministrazione = codiceamministrazione;
			if(!String.IsNullOrEmpty(note))c.Note = note;
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Fkidtestata = fkidtestata;
			
		
			return db.GetClassList( c ).ToList< CdsInvitati>();
		}

		public List<CdsInvitati> GetList(CdsInvitati filtro)
		{
			return db.GetClassList( filtro ).ToList< CdsInvitati>();
		}

		public CdsInvitati Insert(CdsInvitati cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CdsInvitati)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CdsInvitati ChildInsert(CdsInvitati cls)
		{
			return cls;
		}

		private CdsInvitati DataIntegrations(CdsInvitati cls)
		{
			return cls;
		}
		

		public CdsInvitati Update(CdsInvitati cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CdsInvitati cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CdsInvitati cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CdsInvitati cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CdsInvitati cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			