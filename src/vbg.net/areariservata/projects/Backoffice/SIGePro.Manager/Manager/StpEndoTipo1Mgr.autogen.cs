
			
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
	/// File generato automaticamente dalla tabella STP_ENDO_TIPO1 per la classe StpEndoTipo1 il 06/12/2010 10.11.09
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
	public partial class StpEndoTipo1Mgr : BaseManager
	{
		public StpEndoTipo1Mgr(DataBase dataBase) : base(dataBase) { }

		public StpEndoTipo1 GetById(string idcomune, int? id)
		{
			StpEndoTipo1 c = new StpEndoTipo1();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (StpEndoTipo1)db.GetClass(c);
		}

		public List<StpEndoTipo1> GetList(StpEndoTipo1 filtro)
		{
			return db.GetClassList( filtro ).ToList< StpEndoTipo1>();
		}

		public StpEndoTipo1 Insert(StpEndoTipo1 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (StpEndoTipo1)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private StpEndoTipo1 ChildInsert(StpEndoTipo1 cls)
		{
			return cls;
		}

		private StpEndoTipo1 DataIntegrations(StpEndoTipo1 cls)
		{
			return cls;
		}
		

		public StpEndoTipo1 Update(StpEndoTipo1 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(StpEndoTipo1 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(StpEndoTipo1 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(StpEndoTipo1 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(StpEndoTipo1 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			