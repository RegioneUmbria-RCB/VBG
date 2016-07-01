
			
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
	/// File generato automaticamente dalla tabella I_ATTIVITADYN2DATI_STORICO per la classe IAttivitaDyn2DatiStorico il 26/10/2010 12.24.45
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
	public partial class IAttivitaDyn2DatiStoricoMgr : BaseManager
	{
		public IAttivitaDyn2DatiStoricoMgr(DataBase dataBase) : base(dataBase) { }

		public IAttivitaDyn2DatiStorico GetById(string idcomune, int? idversione, int? fk_ia_id, int? fk_d2mt_id, int? fk_d2c_id, int? indice, int? indice_molteplicita)
		{
			IAttivitaDyn2DatiStorico c = new IAttivitaDyn2DatiStorico();
			
			
			c.Idcomune = idcomune;
			c.Idversione = idversione;
			c.FkIaId = fk_ia_id;
			c.FkD2mtId = fk_d2mt_id;
			c.FkD2cId = fk_d2c_id;
			c.Indice = indice;
			c.IndiceMolteplicita = indice_molteplicita;
			
			return (IAttivitaDyn2DatiStorico)db.GetClass(c);
		}

		public List<IAttivitaDyn2DatiStorico> GetList(IAttivitaDyn2DatiStorico filtro)
		{
			return db.GetClassList( filtro ).ToList< IAttivitaDyn2DatiStorico>();
		}

		public IAttivitaDyn2DatiStorico Insert(IAttivitaDyn2DatiStorico cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IAttivitaDyn2DatiStorico)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IAttivitaDyn2DatiStorico ChildInsert(IAttivitaDyn2DatiStorico cls)
		{
			return cls;
		}

		private IAttivitaDyn2DatiStorico DataIntegrations(IAttivitaDyn2DatiStorico cls)
		{
			return cls;
		}
		

		public IAttivitaDyn2DatiStorico Update(IAttivitaDyn2DatiStorico cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IAttivitaDyn2DatiStorico cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IAttivitaDyn2DatiStorico cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IAttivitaDyn2DatiStorico cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(IAttivitaDyn2DatiStorico cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			