
			
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
	/// File generato automaticamente dalla tabella CC_ITABELLA2 per la classe CCITabella2 il 27/06/2008 13.01.39
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
	public partial class CCITabella2Mgr : BaseManager
	{
		public CCITabella2Mgr(DataBase dataBase) : base(dataBase) { }

		public CCITabella2 GetById(string idcomune, int id)
		{
			CCITabella2 c = new CCITabella2();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCITabella2)db.GetClass(c);
		}

		public List<CCITabella2> GetList(string idcomune, int id, int codiceistanza, int fk_ccic_id, float superficie, int fk_ccds_id)
		{
			CCITabella2 c = new CCITabella2();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCcicId = fk_ccic_id;
			c.Superficie = superficie;
			c.FkCcdsId = fk_ccds_id;


			return db.GetClassList(c).ToList < CCITabella2>();
		}

		public List<CCITabella2> GetList(CCITabella2 filtro)
		{
			return db.GetClassList(filtro).ToList < CCITabella2>();
		}

		public CCITabella2 Insert(CCITabella2 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCITabella2)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCITabella2 ChildInsert(CCITabella2 cls)
		{
			return cls;
		}

		private CCITabella2 DataIntegrations(CCITabella2 cls)
		{
			return cls;
		}
		

		public CCITabella2 Update(CCITabella2 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCITabella2 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCITabella2 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCITabella2 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCITabella2 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			