
			
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
	/// File generato automaticamente dalla tabella CC_ITABELLA3 per la classe CCITabella3 il 27/06/2008 13.01.39
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
	public partial class CCITabella3Mgr : BaseManager
	{
		public CCITabella3Mgr(DataBase dataBase) : base(dataBase) { }

		public CCITabella3 GetById(string idcomune, int id)
		{
			CCITabella3 c = new CCITabella3();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCITabella3)db.GetClass(c);
		}

		public List<CCITabella3> GetList(string idcomune, int id, int codiceistanza, int fk_ccic_id, int fk_cct3_id, int ipotesichericorre, float incremento)
		{
			CCITabella3 c = new CCITabella3();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCcicId = fk_ccic_id;
			c.FkCct3Id = fk_cct3_id;
			c.Ipotesichericorre = ipotesichericorre;
			c.Incremento = incremento;


			return db.GetClassList(c).ToList < CCITabella3>();
		}

		public List<CCITabella3> GetList(CCITabella3 filtro)
		{
			return db.GetClassList(filtro).ToList < CCITabella3>();
		}

		public CCITabella3 Insert(CCITabella3 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCITabella3)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCITabella3 ChildInsert(CCITabella3 cls)
		{
			return cls;
		}

		private CCITabella3 DataIntegrations(CCITabella3 cls)
		{
			return cls;
		}
		

		public CCITabella3 Update(CCITabella3 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCITabella3 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCITabella3 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCITabella3 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCITabella3 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			