
			
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
	/// File generato automaticamente dalla tabella CC_ITABELLA1 per la classe CCITabella1 il 27/06/2008 13.01.39
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
	public partial class CCITabella1Mgr : BaseManager
	{
		public CCITabella1Mgr(DataBase dataBase) : base(dataBase) { }

		public CCITabella1 GetById(string idcomune, int id)
		{
			CCITabella1 c = new CCITabella1();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCITabella1)db.GetClass(c);
		}

		public List<CCITabella1> GetList(string idcomune, int id, int codiceistanza, int fk_ccic_id, int fk_cccs_id, int alloggi, float su, float rapporto_su, float incremento, float incrementoxclassi)
		{
			CCITabella1 c = new CCITabella1();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkCcicId = fk_ccic_id;
			c.FkCccsId = fk_cccs_id;
			c.Alloggi = alloggi;
			c.Su = su;
			c.RapportoSu = rapporto_su;
			c.Incremento = incremento;
			c.Incrementoxclassi = incrementoxclassi;


			return db.GetClassList(c).ToList < CCITabella1>();
		}

		public List<CCITabella1> GetList(CCITabella1 filtro)
		{
			return db.GetClassList(filtro).ToList < CCITabella1>();
		}

		public CCITabella1 Insert(CCITabella1 cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCITabella1)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCITabella1 ChildInsert(CCITabella1 cls)
		{
			return cls;
		}

		private CCITabella1 DataIntegrations(CCITabella1 cls)
		{
			return cls;
		}
		

		public CCITabella1 Update(CCITabella1 cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCITabella1 cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCITabella1 cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCITabella1 cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCITabella1 cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			