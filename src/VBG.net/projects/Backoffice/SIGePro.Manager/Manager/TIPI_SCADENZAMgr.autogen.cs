
			
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
	/// File generato automaticamente dalla tabella TIPI_SCADENZA per la classe TIPI_SCADENZA il 03/06/2009 11.07.33
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
	public partial class TIPI_SCADENZAMgr : BaseManager
	{
		public TIPI_SCADENZAMgr(DataBase dataBase) : base(dataBase) { }

		public TIPI_SCADENZA GetById(int id)
		{
			TIPI_SCADENZA c = new TIPI_SCADENZA();
			
			
			c.Id = id;
			
			return (TIPI_SCADENZA)db.GetClass(c);
		}

		public List<TIPI_SCADENZA> GetList(TIPI_SCADENZA filtro)
		{
			return db.GetClassList( filtro ).ToList< TIPI_SCADENZA>();
		}

		public TIPI_SCADENZA Insert(TIPI_SCADENZA cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TIPI_SCADENZA)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TIPI_SCADENZA ChildInsert(TIPI_SCADENZA cls)
		{
			return cls;
		}

		private TIPI_SCADENZA DataIntegrations(TIPI_SCADENZA cls)
		{
			return cls;
		}
		

		public TIPI_SCADENZA Update(TIPI_SCADENZA cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TIPI_SCADENZA cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TIPI_SCADENZA cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TIPI_SCADENZA cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TIPI_SCADENZA cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			