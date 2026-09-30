
			
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
	/// File generato automaticamente dalla tabella COLLAUDOVERIFICHE per la classe CollaudoVerifiche il 30/07/2008 16.38.09
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
	public partial class CollaudoVerificheMgr : BaseManager
	{
		public CollaudoVerificheMgr(DataBase dataBase) : base(dataBase) { }

		public CollaudoVerifiche GetById(int codiceistanza, int codiceamministrazione, string idcomune)
		{
			CollaudoVerifiche c = new CollaudoVerifiche();
			
			
			c.Codiceistanza = codiceistanza;
			c.Codiceamministrazione = codiceamministrazione;
			c.Idcomune = idcomune;
			
			return (CollaudoVerifiche)db.GetClass(c);
		}

		public List<CollaudoVerifiche> GetList(int codiceistanza, int codiceamministrazione, DateTime data, string parere, int esito, string note, int proprio, string fileverbale, int codiceoggetto, string idcomune)
		{
			CollaudoVerifiche c = new CollaudoVerifiche();
			c.Codiceistanza = codiceistanza;
			c.Codiceamministrazione = codiceamministrazione;
			c.Data = data;
			if(!String.IsNullOrEmpty(parere))c.Parere = parere;
			c.Esito = esito;
			if(!String.IsNullOrEmpty(note))c.Note = note;
			c.Proprio = proprio;
			if(!String.IsNullOrEmpty(fileverbale))c.Fileverbale = fileverbale;
			c.Codiceoggetto = codiceoggetto;
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			
		
			return db.GetClassList( c ).ToList< CollaudoVerifiche>();
		}

		public List<CollaudoVerifiche> GetList(CollaudoVerifiche filtro)
		{
			return db.GetClassList( filtro ).ToList< CollaudoVerifiche>();
		}

		public CollaudoVerifiche Insert(CollaudoVerifiche cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CollaudoVerifiche)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CollaudoVerifiche ChildInsert(CollaudoVerifiche cls)
		{
			return cls;
		}

		private CollaudoVerifiche DataIntegrations(CollaudoVerifiche cls)
		{
			return cls;
		}
		

		public CollaudoVerifiche Update(CollaudoVerifiche cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CollaudoVerifiche cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CollaudoVerifiche cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CollaudoVerifiche cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CollaudoVerifiche cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			