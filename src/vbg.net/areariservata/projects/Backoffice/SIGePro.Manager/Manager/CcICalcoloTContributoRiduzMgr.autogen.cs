
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLOTCONTRIBUTO_RIDUZ per la classe CcICalcoloTContributoRiduz il 09/03/2009 12.30.53
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
	public partial class CcICalcoloTContributoRiduzMgr : BaseManager
	{
		public CcICalcoloTContributoRiduzMgr(DataBase dataBase) : base(dataBase) { }

		public CcICalcoloTContributoRiduz GetById(string idcomune, int id)
		{
			CcICalcoloTContributoRiduz c = new CcICalcoloTContributoRiduz();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CcICalcoloTContributoRiduz)db.GetClass(c);
		}

		public List<CcICalcoloTContributoRiduz> GetList(CcICalcoloTContributoRiduz filtro)
		{
			return db.GetClassList( filtro ).ToList< CcICalcoloTContributoRiduz>();
		}


		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CcICalcoloTContributoRiduz ChildInsert(CcICalcoloTContributoRiduz cls)
		{
			return cls;
		}

		private CcICalcoloTContributoRiduz DataIntegrations(CcICalcoloTContributoRiduz cls)
		{
			return cls;
		}
		





		
		private void VerificaRecordCollegati(CcICalcoloTContributoRiduz cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CcICalcoloTContributoRiduz cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CcICalcoloTContributoRiduz cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}


	}
}
			
			
			