
			
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
	/// File generato automaticamente dalla tabella COMUNIASSOCIATISOFTWARE per la classe ComuniAssociatiSoftware il 10/03/2011 10.43.11
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
	public partial class ComuniAssociatiSoftwareMgr : BaseManager
	{
		public ComuniAssociatiSoftwareMgr(DataBase dataBase) : base(dataBase) { }

		public ComuniAssociatiSoftware GetById(string idcomune, int? id)
		{
			ComuniAssociatiSoftware c = new ComuniAssociatiSoftware();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (ComuniAssociatiSoftware)db.GetClass(c);
		}

		public List<ComuniAssociatiSoftware> GetList(ComuniAssociatiSoftware filtro)
		{
			return db.GetClassList( filtro ).ToList< ComuniAssociatiSoftware>();
		}

		public ComuniAssociatiSoftware Insert(ComuniAssociatiSoftware cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ComuniAssociatiSoftware)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ComuniAssociatiSoftware ChildInsert(ComuniAssociatiSoftware cls)
		{
			return cls;
		}

		private ComuniAssociatiSoftware DataIntegrations(ComuniAssociatiSoftware cls)
		{
			return cls;
		}
		

		public ComuniAssociatiSoftware Update(ComuniAssociatiSoftware cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ComuniAssociatiSoftware cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ComuniAssociatiSoftware cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ComuniAssociatiSoftware cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ComuniAssociatiSoftware cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			