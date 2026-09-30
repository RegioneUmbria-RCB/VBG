
			
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
	/// File generato automaticamente dalla tabella INVENTARIOPROCDYN2MODELLIT per la classe InventarioProcDyn2ModelliT il 05/08/2008 16.49.58
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
	public partial class InventarioProcDyn2ModelliTMgr : BaseManager
	{
		public InventarioProcDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public InventarioProcDyn2ModelliT GetById(string idcomune, int codiceinventario, int fk_d2mt_id)
		{
			InventarioProcDyn2ModelliT c = new InventarioProcDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.Codiceinventario = codiceinventario;
			c.FkD2mtId = fk_d2mt_id;
			
			return (InventarioProcDyn2ModelliT)db.GetClass(c);
		}

		public List<InventarioProcDyn2ModelliT> GetList(InventarioProcDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< InventarioProcDyn2ModelliT>();
		}

		public InventarioProcDyn2ModelliT Insert(InventarioProcDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (InventarioProcDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private InventarioProcDyn2ModelliT ChildInsert(InventarioProcDyn2ModelliT cls)
		{
			return cls;
		}

		private InventarioProcDyn2ModelliT DataIntegrations(InventarioProcDyn2ModelliT cls)
		{
			return cls;
		}
		

		public InventarioProcDyn2ModelliT Update(InventarioProcDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(InventarioProcDyn2ModelliT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(InventarioProcDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(InventarioProcDyn2ModelliT cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(InventarioProcDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			