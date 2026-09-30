
			
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
	/// File generato automaticamente dalla tabella CATEGORIEEVENTIBASE per la classe CategorieEventiBase il 06/11/2009 9.51.56
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
	public partial class CategorieEventiBaseMgr : BaseManager
	{
		public CategorieEventiBaseMgr(DataBase dataBase) : base(dataBase) { }

		public CategorieEventiBase GetById(string id)
		{
			CategorieEventiBase c = new CategorieEventiBase();
			
			
			c.Id = id;
			
			return (CategorieEventiBase)db.GetClass(c);
		}

		public List<CategorieEventiBase> GetList(CategorieEventiBase filtro)
		{
			return db.GetClassList( filtro ).ToList< CategorieEventiBase>();
		}

		public CategorieEventiBase Insert(CategorieEventiBase cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CategorieEventiBase)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CategorieEventiBase ChildInsert(CategorieEventiBase cls)
		{
			return cls;
		}

		private CategorieEventiBase DataIntegrations(CategorieEventiBase cls)
		{
			return cls;
		}
		

		public CategorieEventiBase Update(CategorieEventiBase cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CategorieEventiBase cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CategorieEventiBase cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CategorieEventiBase cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CategorieEventiBase cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			