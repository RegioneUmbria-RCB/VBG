
			
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
	/// File generato automaticamente dalla tabella TESTIESTESI per la classe TestiEstesi il 30/11/2010 16.36.17
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
	public partial class TestiEstesiMgr : BaseManager
	{
		public TestiEstesiMgr(DataBase dataBase) : base(dataBase) { }

		public TestiEstesi GetById(string idcomune, int? id)
		{
			TestiEstesi c = new TestiEstesi();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (TestiEstesi)db.GetClass(c);
		}

		public List<TestiEstesi> GetList(TestiEstesi filtro)
		{
			return db.GetClassList( filtro ).ToList< TestiEstesi>();
		}

		public TestiEstesi Insert(TestiEstesi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TestiEstesi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TestiEstesi ChildInsert(TestiEstesi cls)
		{
			return cls;
		}

		private TestiEstesi DataIntegrations(TestiEstesi cls)
		{
			return cls;
		}
		

		public TestiEstesi Update(TestiEstesi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TestiEstesi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TestiEstesi cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TestiEstesi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TestiEstesi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			