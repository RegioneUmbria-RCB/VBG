
			
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
	/// File generato automaticamente dalla tabella I_ATTIVITADYN2MODELLIT_STORICO per la classe IAttivitaDyn2ModelliTStorico il 26/10/2010 15.11.30
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
	public partial class IAttivitaDyn2ModelliTStoricoMgr : BaseManager
	{
		public IAttivitaDyn2ModelliTStoricoMgr(DataBase dataBase) : base(dataBase) { }

		public IAttivitaDyn2ModelliTStorico GetById(string idcomune, int? idversione, int? fk_ia_id, int? fk_d2mt_id)
		{
			IAttivitaDyn2ModelliTStorico c = new IAttivitaDyn2ModelliTStorico();
			
			
			c.Idcomune = idcomune;
			c.Idversione = idversione;
			c.FkIaId = fk_ia_id;
			c.FkD2mtId = fk_d2mt_id;
			
			return (IAttivitaDyn2ModelliTStorico)db.GetClass(c);
		}

		public List<IAttivitaDyn2ModelliTStorico> GetList(IAttivitaDyn2ModelliTStorico filtro)
		{
			return db.GetClassList( filtro ).ToList< IAttivitaDyn2ModelliTStorico>();
		}

		public IAttivitaDyn2ModelliTStorico Insert(IAttivitaDyn2ModelliTStorico cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IAttivitaDyn2ModelliTStorico)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IAttivitaDyn2ModelliTStorico ChildInsert(IAttivitaDyn2ModelliTStorico cls)
		{
			return cls;
		}

		private IAttivitaDyn2ModelliTStorico DataIntegrations(IAttivitaDyn2ModelliTStorico cls)
		{
			return cls;
		}
		

		public IAttivitaDyn2ModelliTStorico Update(IAttivitaDyn2ModelliTStorico cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IAttivitaDyn2ModelliTStorico cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IAttivitaDyn2ModelliTStorico cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IAttivitaDyn2ModelliTStorico cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(IAttivitaDyn2ModelliTStorico cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			