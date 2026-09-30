
			
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
	/// File generato automaticamente dalla tabella PROT_CLASSIFICAZIONE per la classe ProtClassificazione il 19/01/2009 10.46.00
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
	public partial class ProtClassificazioneMgr : BaseManager
	{
		public ProtClassificazioneMgr(DataBase dataBase) : base(dataBase) { }

		public ProtClassificazione GetById(int cl_id, string idcomune)
		{
			ProtClassificazione c = new ProtClassificazione();
			
			
			c.Cl_Id = cl_id;
			c.Idcomune = idcomune;
			
			return (ProtClassificazione)db.GetClass(c);
		}

		public List<ProtClassificazione> GetList(ProtClassificazione filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtClassificazione>();
		}

		public ProtClassificazione Insert(ProtClassificazione cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtClassificazione)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtClassificazione ChildInsert(ProtClassificazione cls)
		{
			return cls;
		}

		private ProtClassificazione DataIntegrations(ProtClassificazione cls)
		{
			return cls;
		}
		

		public ProtClassificazione Update(ProtClassificazione cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtClassificazione cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtClassificazione cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtClassificazione cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtClassificazione cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			