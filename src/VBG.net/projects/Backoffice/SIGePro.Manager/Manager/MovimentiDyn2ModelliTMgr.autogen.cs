
			
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
	/// File generato automaticamente dalla tabella MOVIMENTIDYN2MODELLIT per la classe MovimentiDyn2ModelliT il 08/09/2008 10.32.28
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
	public partial class MovimentiDyn2ModelliTMgr : BaseManager
	{
		public MovimentiDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public MovimentiDyn2ModelliT GetById(string idcomune, int fk_d2mt_id, int codicemovimento)
		{
			MovimentiDyn2ModelliT c = new MovimentiDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.FkD2mtId = fk_d2mt_id;
			c.Codicemovimento = codicemovimento;
			
			return (MovimentiDyn2ModelliT)db.GetClass(c);
		}

		public List<MovimentiDyn2ModelliT> GetList(MovimentiDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< MovimentiDyn2ModelliT>();
		}

		public MovimentiDyn2ModelliT Insert(MovimentiDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (MovimentiDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private MovimentiDyn2ModelliT ChildInsert(MovimentiDyn2ModelliT cls)
		{
			return cls;
		}

		private MovimentiDyn2ModelliT DataIntegrations(MovimentiDyn2ModelliT cls)
		{
			return cls;
		}
		

		public MovimentiDyn2ModelliT Update(MovimentiDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(MovimentiDyn2ModelliT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(MovimentiDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(MovimentiDyn2ModelliT cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(MovimentiDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			