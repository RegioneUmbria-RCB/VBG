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
	/// File generato automaticamente dalla tabella INTERVENTIDYN2MODELLIT per la classe InterventiDyn2ModelliT il 05/08/2008 16.49.58
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
	public partial class InterventiDyn2ModelliTMgr : BaseManager
	{
		public InterventiDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public InterventiDyn2ModelliT GetById(string idcomune, int codiceintervento, int fk_d2mt_id)
		{
			InterventiDyn2ModelliT c = new InterventiDyn2ModelliT();
			
			c.Idcomune = idcomune;
            c.CodiceIntervento = codiceintervento;
			c.FkD2mtId = fk_d2mt_id;
			
			return (InterventiDyn2ModelliT)db.GetClass(c);
		}

		public List<InterventiDyn2ModelliT> GetList(InterventiDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< InterventiDyn2ModelliT>();
		}

		public InterventiDyn2ModelliT Insert(InterventiDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (InterventiDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private InterventiDyn2ModelliT ChildInsert(InterventiDyn2ModelliT cls)
		{
			return cls;
		}

		private InterventiDyn2ModelliT DataIntegrations(InterventiDyn2ModelliT cls)
		{
			return cls;
		}
		

		public InterventiDyn2ModelliT Update(InterventiDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(InterventiDyn2ModelliT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(InterventiDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(InterventiDyn2ModelliT cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(InterventiDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			