
			
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
	/// File generato automaticamente dalla tabella STP_ENDO_TIPO2 per la classe StpEndoTipo2 il 06/12/2010 10.11.44
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
	public partial class StpEndoTipo2Mgr : BaseManager
	{
		public StpEndoTipo2Mgr(DataBase dataBase) : base(dataBase) { }

		public StpEndoTipo2 GetById(string idcomune, int? id)
		{
			StpEndoTipo2 c = new StpEndoTipo2();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (StpEndoTipo2)db.GetClass(c);
		}

		public List<StpEndoTipo2> GetList(StpEndoTipo2 filtro)
		{
			return db.GetClassList( filtro ).ToList< StpEndoTipo2>();
		}

		public StpEndoTipo2 Insert(StpEndoTipo2 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (StpEndoTipo2)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private StpEndoTipo2 ChildInsert(StpEndoTipo2 cls)
		{
			return cls;
		}

		private StpEndoTipo2 DataIntegrations(StpEndoTipo2 cls)
		{
			return cls;
		}
		

		public StpEndoTipo2 Update(StpEndoTipo2 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(StpEndoTipo2 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(StpEndoTipo2 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(StpEndoTipo2 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(StpEndoTipo2 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			