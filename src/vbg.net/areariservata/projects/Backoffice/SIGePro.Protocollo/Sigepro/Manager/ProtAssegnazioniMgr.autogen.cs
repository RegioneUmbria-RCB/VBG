
			
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
	/// File generato automaticamente dalla tabella PROT_ASSEGNAZIONI per la classe ProtAssegnazioni il 17/03/2009 14.24.21
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
	public partial class ProtAssegnazioniMgr : BaseManager
	{
		public ProtAssegnazioniMgr(DataBase dataBase) : base(dataBase) { }

		public ProtAssegnazioni GetById(int as_id, string idcomune)
		{
			ProtAssegnazioni c = new ProtAssegnazioni();
			
			
			c.As_Id = as_id;
			c.Idcomune = idcomune;
			
			return (ProtAssegnazioni)db.GetClass(c);
		}

		public List<ProtAssegnazioni> GetList(ProtAssegnazioni filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtAssegnazioni>();
		}

		public ProtAssegnazioni Insert(ProtAssegnazioni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtAssegnazioni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtAssegnazioni ChildInsert(ProtAssegnazioni cls)
		{
			return cls;
		}

		private ProtAssegnazioni DataIntegrations(ProtAssegnazioni cls)
		{
			return cls;
		}
		

		public ProtAssegnazioni Update(ProtAssegnazioni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtAssegnazioni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtAssegnazioni cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtAssegnazioni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtAssegnazioni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			