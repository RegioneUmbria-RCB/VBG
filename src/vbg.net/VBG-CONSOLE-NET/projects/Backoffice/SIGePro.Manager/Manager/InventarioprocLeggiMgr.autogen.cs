
			
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
	/// File generato automaticamente dalla tabella INVENTARIOPROC_LEGGI per la classe InventarioprocLeggi il 10/01/2011 12.18.09
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
	public partial class InventarioprocLeggiMgr : BaseManager
	{
		public InventarioprocLeggiMgr(DataBase dataBase) : base(dataBase) { }

		public InventarioprocLeggi GetById(int? id, string idcomune)
		{
			InventarioprocLeggi c = new InventarioprocLeggi();
			
			
			c.Id = id;
			c.Idcomune = idcomune;
			
			return (InventarioprocLeggi)db.GetClass(c);
		}

		public List<InventarioprocLeggi> GetList(InventarioprocLeggi filtro)
		{
			return db.GetClassList( filtro ).ToList< InventarioprocLeggi>();
		}

		public InventarioprocLeggi Insert(InventarioprocLeggi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (InventarioprocLeggi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private InventarioprocLeggi ChildInsert(InventarioprocLeggi cls)
		{
			return cls;
		}

		private InventarioprocLeggi DataIntegrations(InventarioprocLeggi cls)
		{
			return cls;
		}
		

		public InventarioprocLeggi Update(InventarioprocLeggi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(InventarioprocLeggi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(InventarioprocLeggi cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(InventarioprocLeggi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(InventarioprocLeggi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			