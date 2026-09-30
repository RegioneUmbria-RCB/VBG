
			
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
	/// File generato automaticamente dalla tabella TIPIMOVIMENTIDYN2MODELLIT per la classe TipiMovimentiDyn2ModelliT il 05/08/2008 16.49.59
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
	public partial class TipiMovimentiDyn2ModelliTMgr : BaseManager
	{
		public TipiMovimentiDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public TipiMovimentiDyn2ModelliT GetById(string idcomune, string tipomovimento, int fk_d2mt_id)
		{
			TipiMovimentiDyn2ModelliT c = new TipiMovimentiDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.Tipomovimento = tipomovimento;
			c.FkD2mtId = fk_d2mt_id;
			
			return (TipiMovimentiDyn2ModelliT)db.GetClass(c);
		}

		public List<TipiMovimentiDyn2ModelliT> GetList(TipiMovimentiDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< TipiMovimentiDyn2ModelliT>();
		}

		public TipiMovimentiDyn2ModelliT Insert(TipiMovimentiDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiMovimentiDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiMovimentiDyn2ModelliT ChildInsert(TipiMovimentiDyn2ModelliT cls)
		{
			return cls;
		}

		private TipiMovimentiDyn2ModelliT DataIntegrations(TipiMovimentiDyn2ModelliT cls)
		{
			return cls;
		}
		

		public TipiMovimentiDyn2ModelliT Update(TipiMovimentiDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiMovimentiDyn2ModelliT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiMovimentiDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiMovimentiDyn2ModelliT cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TipiMovimentiDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}
    }
}
			
			
			