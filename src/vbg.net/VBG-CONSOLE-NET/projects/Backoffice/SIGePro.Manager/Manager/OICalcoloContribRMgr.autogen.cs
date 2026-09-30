
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBR per la classe OICalcoloContribR il 08/07/2008 10.13.19
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
	public partial class OICalcoloContribRMgr : BaseManager
	{
		public OICalcoloContribRMgr(DataBase dataBase) : base(dataBase) { }

		public OICalcoloContribR GetById(string idcomune, int id)
		{
			OICalcoloContribR c = new OICalcoloContribR();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloContribR)db.GetClass(c);
		}

		public List<OICalcoloContribR> GetList(string idcomune, int id, int codiceistanza, int fk_oicct_id, int fk_ode_id, int fk_oto_id, double costotot, double costom)
		{
			OICalcoloContribR c = new OICalcoloContribR();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkOicctId = fk_oicct_id;
			c.FkOdeId = fk_ode_id;
			c.FkOtoId = fk_oto_id;
			c.Costotot = costotot;
			c.Costom = costom;


			return db.GetClassList(c).ToList < OICalcoloContribR>();
		}

		public List<OICalcoloContribR> GetList(OICalcoloContribR filtro)
		{
			return db.GetClassList(filtro).ToList < OICalcoloContribR>();
		}

		public OICalcoloContribR Insert(OICalcoloContribR cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OICalcoloContribR)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OICalcoloContribR ChildInsert(OICalcoloContribR cls)
		{
			return cls;
		}

		private OICalcoloContribR DataIntegrations(OICalcoloContribR cls)
		{
			return cls;
		}
		



		public void Delete(OICalcoloContribR cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OICalcoloContribR cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void Validate(OICalcoloContribR cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			