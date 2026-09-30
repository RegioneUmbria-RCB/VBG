
			
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
	/// File generato automaticamente dalla tabella PERMCDSISTANZE per la classe PermCdsIstanze il 30/07/2008 16.41.17
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
	public partial class PermCdsIstanzeMgr : BaseManager
	{
		public PermCdsIstanzeMgr(DataBase dataBase) : base(dataBase) { }

		public PermCdsIstanze GetById(int codicecds, int codiceatto, int codiceistanza, string idcomune)
		{
			PermCdsIstanze c = new PermCdsIstanze();
			
			
			c.Codicecds = codicecds;
			c.Codiceatto = codiceatto;
			c.Codiceistanza = codiceistanza;
			c.Idcomune = idcomune;
			
			return (PermCdsIstanze)db.GetClass(c);
		}

		public List<PermCdsIstanze> GetList(int codicecds, int codiceatto, int codiceistanza, string idcomune)
		{
			PermCdsIstanze c = new PermCdsIstanze();
			c.Codicecds = codicecds;
			c.Codiceatto = codiceatto;
			c.Codiceistanza = codiceistanza;
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			
		
			return db.GetClassList( c ).ToList< PermCdsIstanze>();
		}

		public List<PermCdsIstanze> GetList(PermCdsIstanze filtro)
		{
			return db.GetClassList( filtro ).ToList< PermCdsIstanze>();
		}

		public PermCdsIstanze Insert(PermCdsIstanze cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (PermCdsIstanze)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private PermCdsIstanze ChildInsert(PermCdsIstanze cls)
		{
			return cls;
		}

		private PermCdsIstanze DataIntegrations(PermCdsIstanze cls)
		{
			return cls;
		}
		

		public PermCdsIstanze Update(PermCdsIstanze cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(PermCdsIstanze cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(PermCdsIstanze cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(PermCdsIstanze cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(PermCdsIstanze cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			