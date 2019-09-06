
			
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
	/// File generato automaticamente dalla tabella DYN2_BASETIPITESTO per la classe Dyn2BaseTipiTesto il 05/08/2008 16.49.58
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
	public partial class Dyn2BaseTipiTestoMgr : BaseManager
	{
		public Dyn2BaseTipiTestoMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2BaseTipiTesto GetById(string id)
		{
			Dyn2BaseTipiTesto c = new Dyn2BaseTipiTesto();
			
			
			c.Id = id;
			
			return (Dyn2BaseTipiTesto)db.GetClass(c);
		}

		public List<Dyn2BaseTipiTesto> GetList(Dyn2BaseTipiTesto filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2BaseTipiTesto>();
		}

		public Dyn2BaseTipiTesto Insert(Dyn2BaseTipiTesto cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2BaseTipiTesto)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2BaseTipiTesto ChildInsert(Dyn2BaseTipiTesto cls)
		{
			return cls;
		}

		private Dyn2BaseTipiTesto DataIntegrations(Dyn2BaseTipiTesto cls)
		{
			return cls;
		}
		

		public Dyn2BaseTipiTesto Update(Dyn2BaseTipiTesto cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2BaseTipiTesto cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Dyn2BaseTipiTesto cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Dyn2BaseTipiTesto cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Dyn2BaseTipiTesto cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			