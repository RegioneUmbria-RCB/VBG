
			
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
	/// File generato automaticamente dalla tabella INVENTARIOPROCEDIMENTI per la classe InventarioProcedimenti il 05/11/2008 11.16.35
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
	public partial class InventarioProcedimentiMgr : BaseManager
	{
		public InventarioProcedimentiMgr(DataBase dataBase) : base(dataBase) { }

		public InventarioProcedimenti GetById(string idcomune,int codiceinventario,  useForeignEnum useForeign)
		{
			InventarioProcedimenti c = new InventarioProcedimenti();
			
			
			c.Codiceinventario = codiceinventario;
			c.Idcomune = idcomune;
            c.UseForeign = useForeign;
			
			return (InventarioProcedimenti)db.GetClass(c);
		}

        public InventarioProcedimenti GetById(string idcomune, int codiceinventario)
        {
            return GetById(idcomune,codiceinventario,  useForeignEnum.No);
        }

		public List<InventarioProcedimenti> GetList(InventarioProcedimenti filtro)
		{
			return db.GetClassList( filtro ).ToList< InventarioProcedimenti>();
		}

		public InventarioProcedimenti Insert(InventarioProcedimenti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (InventarioProcedimenti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private InventarioProcedimenti ChildInsert(InventarioProcedimenti cls)
		{
			return cls;
		}

		private InventarioProcedimenti DataIntegrations(InventarioProcedimenti cls)
		{
			return cls;
		}
		

		public InventarioProcedimenti Update(InventarioProcedimenti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(InventarioProcedimenti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(InventarioProcedimenti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(InventarioProcedimenti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(InventarioProcedimenti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}


	}
}
			
			
			