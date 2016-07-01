
			
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
	/// File generato automaticamente dalla tabella CITTADINANZA per la classe Cittadinanza il 15/09/2010 12.14.36
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
	public partial class CittadinanzaMgr : BaseManager
	{
		public CittadinanzaMgr(DataBase dataBase) : base(dataBase) { }

		public Cittadinanza GetById(int? codice)
		{
			Cittadinanza c = new Cittadinanza();
			
			
			c.Codice = codice;
			
			return (Cittadinanza)db.GetClass(c);
		}

		public List<Cittadinanza> GetList(Cittadinanza filtro)
		{
			return db.GetClassList( filtro ).ToList< Cittadinanza>();
		}

		public Cittadinanza Insert(Cittadinanza cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Cittadinanza)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Cittadinanza ChildInsert(Cittadinanza cls)
		{
			return cls;
		}

		private Cittadinanza DataIntegrations(Cittadinanza cls)
		{
			return cls;
		}
		

		public Cittadinanza Update(Cittadinanza cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Cittadinanza cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Cittadinanza cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Cittadinanza cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Cittadinanza cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			