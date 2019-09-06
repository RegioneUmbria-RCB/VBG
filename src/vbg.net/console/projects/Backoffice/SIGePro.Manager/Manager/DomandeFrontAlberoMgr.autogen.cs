
			
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
	/// File generato automaticamente dalla tabella DOMANDEFRONTALBERO per la classe DomandeFrontAlbero il 09/01/2009 16.49.08
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
	public partial class DomandeFrontAlberoMgr : BaseManager
	{
		public DomandeFrontAlberoMgr(DataBase dataBase) : base(dataBase) { }

		public DomandeFrontAlbero GetById(string idcomune, int id)
		{
			DomandeFrontAlbero c = new DomandeFrontAlbero();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (DomandeFrontAlbero)db.GetClass(c);
		}

		public List<DomandeFrontAlbero> GetList(DomandeFrontAlbero filtro)
		{
			return db.GetClassList( filtro ).ToList< DomandeFrontAlbero>();
		}

		public DomandeFrontAlbero Insert(DomandeFrontAlbero cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (DomandeFrontAlbero)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private DomandeFrontAlbero ChildInsert(DomandeFrontAlbero cls)
		{
			return cls;
		}

		private DomandeFrontAlbero DataIntegrations(DomandeFrontAlbero cls)
		{
			return cls;
		}
		

		public DomandeFrontAlbero Update(DomandeFrontAlbero cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(DomandeFrontAlbero cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(DomandeFrontAlbero cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		
		
		private void Validate(DomandeFrontAlbero cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			