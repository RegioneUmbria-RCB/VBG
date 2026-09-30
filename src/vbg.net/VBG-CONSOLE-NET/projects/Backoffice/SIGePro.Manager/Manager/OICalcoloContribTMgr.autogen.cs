
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBT per la classe OICalcoloContribT il 27/06/2008 13.01.36
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
	public partial class OICalcoloContribTMgr : BaseManager
	{
		public OICalcoloContribTMgr(DataBase dataBase) : base(dataBase) { }

		public OICalcoloContribT GetById(string idcomune, int id)
		{
			OICalcoloContribT c = new OICalcoloContribT();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloContribT)db.GetClass(c);
		}

		public List<OICalcoloContribT> GetList(string idcomune, int id, int codiceistanza, string fk_occbde_id, int fk_aree_codicearea_zto, int fk_aree_codicearea_prg, int fk_oit_id, int fk_oin_id, int fk_oict_id, int fk_ocla_id)
		{
			OICalcoloContribT c = new OICalcoloContribT();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			if(!String.IsNullOrEmpty(fk_occbde_id))c.FkOccbdeId = fk_occbde_id;
			c.FkAreeCodiceareaZto = fk_aree_codicearea_zto;
			c.FkAreeCodiceareaPrg = fk_aree_codicearea_prg;
			c.FkOitId = fk_oit_id;
			c.FkOinId = fk_oin_id;
			c.FkOictId = fk_oict_id;
			c.FkOclaId = fk_ocla_id;


			return db.GetClassList(c).ToList < OICalcoloContribT>();
		}

		public List<OICalcoloContribT> GetList(OICalcoloContribT filtro)
		{
			return db.GetClassList(filtro).ToList < OICalcoloContribT>();
		}

		public OICalcoloContribT Insert(OICalcoloContribT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OICalcoloContribT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OICalcoloContribT ChildInsert(OICalcoloContribT cls)
		{
			return cls;
		}

		private OICalcoloContribT DataIntegrations(OICalcoloContribT cls)
		{
			return cls;
		}
		
		public OICalcoloContribT Update(OICalcoloContribT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OICalcoloContribT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OICalcoloContribT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
	
		private void Validate(OICalcoloContribT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			