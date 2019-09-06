
			
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
	/// File generato automaticamente dalla tabella I_ATTIVITADYN2DATI per la classe IAttivitaDyn2Dati il 05/08/2008 16.49.58
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
	public partial class IAttivitaDyn2DatiMgr : BaseManager
	{
		public IAttivitaDyn2DatiMgr(DataBase dataBase) : base(dataBase) { }

		public IAttivitaDyn2Dati GetById(string idcomune, int fk_ia_id, int fk_d2c_id, int indice , int indiceMolteplicita)
		{
			IAttivitaDyn2Dati c = new IAttivitaDyn2Dati();
			
			c.Idcomune = idcomune;
			c.FkIaId = fk_ia_id;
			c.FkD2cId = fk_d2c_id;
			c.Indice = indice;
			c.IndiceMolteplicita = indiceMolteplicita;
			
			return (IAttivitaDyn2Dati)db.GetClass(c);
		}

		public List<IAttivitaDyn2Dati> GetList(IAttivitaDyn2Dati filtro)
		{
			return db.GetClassList( filtro ).ToList< IAttivitaDyn2Dati>();
		}

		public IAttivitaDyn2Dati Insert(IAttivitaDyn2Dati cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IAttivitaDyn2Dati)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IAttivitaDyn2Dati ChildInsert(IAttivitaDyn2Dati cls)
		{
			return cls;
		}

		private IAttivitaDyn2Dati DataIntegrations(IAttivitaDyn2Dati cls)
		{
			return cls;
		}
		

		public IAttivitaDyn2Dati Update(IAttivitaDyn2Dati cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IAttivitaDyn2Dati cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IAttivitaDyn2Dati cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IAttivitaDyn2Dati cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
	}
}
			
			
			