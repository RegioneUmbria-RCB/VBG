
			
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
	/// File generato automaticamente dalla tabella CC_ITABELLA4 per la classe CCITabella4 il 27/06/2008 13.01.40
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
	public partial class CCITabella4Mgr : BaseManager
	{
		public CCITabella4Mgr(DataBase dataBase) : base(dataBase) { }

		public CCITabella4 GetById(string idcomune, int id)
		{
			CCITabella4 c = new CCITabella4();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCITabella4)db.GetClass(c);
		}

		public List<CCITabella4> GetList(string idcomune, int id, int codiceistanza, int fk_cctc_id, float incremento, int selezionata, int fk_ccic_id)
		{
			CCITabella4 c = new CCITabella4();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCctcId = fk_cctc_id;
			c.Incremento = incremento;
			c.Selezionata = selezionata;
			c.FkCcicId = fk_ccic_id;


			return db.GetClassList(c).ToList < CCITabella4>();
		}

		public List<CCITabella4> GetList(CCITabella4 filtro)
		{
			return db.GetClassList(filtro).ToList < CCITabella4>();
		}

		public CCITabella4 Insert(CCITabella4 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCITabella4)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCITabella4 ChildInsert(CCITabella4 cls)
		{
			return cls;
		}

		private CCITabella4 DataIntegrations(CCITabella4 cls)
		{
			return cls;
		}
		

		public CCITabella4 Update(CCITabella4 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCITabella4 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCITabella4 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCITabella4 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCITabella4 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			