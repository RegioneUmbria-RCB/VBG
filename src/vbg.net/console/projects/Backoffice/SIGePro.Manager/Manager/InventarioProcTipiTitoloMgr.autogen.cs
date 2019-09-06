
			
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
	/// File generato automaticamente dalla tabella INVENTARIOPROC_TIPITITOLO per la classe InventarioProcTipiTitolo il 20/05/2011 10.54.43
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
	public partial class InventarioProcTipiTitoloMgr : BaseManager
	{
		public InventarioProcTipiTitoloMgr(DataBase dataBase) : base(dataBase) { }

		public InventarioProcTipiTitolo GetById(string idcomune, int? id)
		{
			InventarioProcTipiTitolo c = new InventarioProcTipiTitolo();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (InventarioProcTipiTitolo)db.GetClass(c);
		}

		public List<InventarioProcTipiTitolo> GetList(InventarioProcTipiTitolo filtro)
		{
			return db.GetClassList( filtro ).ToList< InventarioProcTipiTitolo>();
		}

		public InventarioProcTipiTitolo Insert(InventarioProcTipiTitolo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (InventarioProcTipiTitolo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private InventarioProcTipiTitolo ChildInsert(InventarioProcTipiTitolo cls)
		{
			return cls;
		}

		private InventarioProcTipiTitolo DataIntegrations(InventarioProcTipiTitolo cls)
		{
			return cls;
		}
		

		public InventarioProcTipiTitolo Update(InventarioProcTipiTitolo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(InventarioProcTipiTitolo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(InventarioProcTipiTitolo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(InventarioProcTipiTitolo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(InventarioProcTipiTitolo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			