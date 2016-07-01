
			
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
	/// File generato automaticamente dalla tabella FO_CONFIGURAZIONE per la classe FoConfigurazione il 14/09/2010 10.14.53
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
	public partial class FoConfigurazioneMgr : BaseManager
	{
		public FoConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

		public FoConfigurazione GetById(string idcomune, int? codice)
		{
			FoConfigurazione c = new FoConfigurazione();
			
			
			c.Idcomune = idcomune;
			c.Codice = codice;
			
			return (FoConfigurazione)db.GetClass(c);
		}

		public List<FoConfigurazione> GetList(FoConfigurazione filtro)
		{
			return db.GetClassList( filtro ).ToList< FoConfigurazione>();
		}

		public FoConfigurazione Insert(FoConfigurazione cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoConfigurazione)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoConfigurazione ChildInsert(FoConfigurazione cls)
		{
			return cls;
		}

		private FoConfigurazione DataIntegrations(FoConfigurazione cls)
		{
			return cls;
		}
		

		public FoConfigurazione Update(FoConfigurazione cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(FoConfigurazione cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(FoConfigurazione cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(FoConfigurazione cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(FoConfigurazione cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			