
			
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
	/// File generato automaticamente dalla tabella CC_ICALCOLI per la classe CCICalcoli il 27/06/2008 13.01.38
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
	public partial class CCICalcoliMgr : BaseManager
	{
		public CCICalcoliMgr(DataBase dataBase) : base(dataBase) { }

		public CCICalcoli GetById(string idcomune, int id)
		{
			CCICalcoli c = new CCICalcoli();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CCICalcoli)db.GetClass(c);
		}

		public List<CCICalcoli> GetList(string idcomune, int id, int codiceistanza, float su, float snr, float sc, float st, float sa, float su_art9, float i1, float i2, float i3, int fk_cctce_id, float maggiorazione, float costocmq, float costocmq_maggiorato)
		{
			CCICalcoli c = new CCICalcoli();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.Su = su;
			c.Snr = snr;
			c.Sc = sc;
			c.St = st;
			c.Sa = sa;
			c.SuArt9 = su_art9;
			c.I1 = i1;
			c.I2 = i2;
			c.I3 = i3;
			c.FkCctceId = fk_cctce_id;
			c.Maggiorazione = maggiorazione;
			c.Costocmq = costocmq;
			c.CostocmqMaggiorato = costocmq_maggiorato;


			return db.GetClassList(c).ToList < CCICalcoli>();
		}

		public List<CCICalcoli> GetList(CCICalcoli filtro)
		{
			return db.GetClassList(filtro).ToList < CCICalcoli>();
		}

		public CCICalcoli Insert(CCICalcoli cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCICalcoli)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCICalcoli DataIntegrations(CCICalcoli cls)
		{



			return cls;
		}

		public CCICalcoli Update(CCICalcoli cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCICalcoli cls)
		{
			if (!cls.Id.HasValue)
				throw new Exception("Si sta cercando di eliminare un calcolo senza utilizzarne l'id");

			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCICalcoli cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
	
		private void Validate(CCICalcoli cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}

	}
}
			
			
			