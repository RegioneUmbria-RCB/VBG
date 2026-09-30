
			
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
	/// File generato automaticamente dalla tabella CC_CAUSALIRIDUZIONIT per la classe CcCausaliRiduzioniT il 06/03/2009 12.28.12
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
	public partial class CcCausaliRiduzioniTMgr : BaseManager
	{
		public CcCausaliRiduzioniTMgr(DataBase dataBase) : base(dataBase) { }

		public CcCausaliRiduzioniT GetById(string idcomune, int id)
		{
			CcCausaliRiduzioniT c = new CcCausaliRiduzioniT();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CcCausaliRiduzioniT)db.GetClass(c);
		}

		public List<CcCausaliRiduzioniT> GetList(CcCausaliRiduzioniT filtro)
		{
			return db.GetClassList( filtro ).ToList< CcCausaliRiduzioniT>();
		}

		public CcCausaliRiduzioniT Insert(CcCausaliRiduzioniT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CcCausaliRiduzioniT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CcCausaliRiduzioniT ChildInsert(CcCausaliRiduzioniT cls)
		{
			return cls;
		}

		private CcCausaliRiduzioniT DataIntegrations(CcCausaliRiduzioniT cls)
		{
			return cls;
		}
		

		public CcCausaliRiduzioniT Update(CcCausaliRiduzioniT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CcCausaliRiduzioniT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CcCausaliRiduzioniT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		
		
		private void Validate(CcCausaliRiduzioniT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			