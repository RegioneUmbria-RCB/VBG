
			
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
	/// File generato automaticamente dalla tabella DYN2_BASECONTESTI per la classe Dyn2BaseContesti il 05/08/2008 16.49.58
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
	public partial class Dyn2BaseContestiMgr : BaseManager
	{
		public Dyn2BaseContestiMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2BaseContesti GetById(string id)
		{
			Dyn2BaseContesti c = new Dyn2BaseContesti();
			
			
			c.Id = id;
			
			return (Dyn2BaseContesti)db.GetClass(c);
		}

		public List<Dyn2BaseContesti> GetList(Dyn2BaseContesti filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2BaseContesti>();
		}

		public Dyn2BaseContesti Insert(Dyn2BaseContesti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2BaseContesti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2BaseContesti ChildInsert(Dyn2BaseContesti cls)
		{
			return cls;
		}

		private Dyn2BaseContesti DataIntegrations(Dyn2BaseContesti cls)
		{
			return cls;
		}
		

		public Dyn2BaseContesti Update(Dyn2BaseContesti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2BaseContesti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Dyn2BaseContesti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Dyn2BaseContesti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Dyn2BaseContesti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			