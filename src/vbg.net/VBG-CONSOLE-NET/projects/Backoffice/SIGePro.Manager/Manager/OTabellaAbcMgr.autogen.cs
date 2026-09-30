
			
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
	/// File generato automaticamente dalla tabella O_TABELLAABC per la classe OTabellaAbc il 27/06/2008 13.01.36
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
	public partial class OTabellaAbcMgr : BaseManager
	{
		public OTabellaAbcMgr(DataBase dataBase) : base(dataBase) { }

		public OTabellaAbc GetById(string idcomune, int id)
		{
			OTabellaAbc c = new OTabellaAbc();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OTabellaAbc)db.GetClass(c);
		}

		public List<OTabellaAbc> GetList(string idcomune, int id, int fk_ovc_id, int fk_aree_codicearea_zto, int fk_aree_codicearea_prg, int fk_ode_id, int fk_oin_id, int fk_oit_id, int fk_oto_id, float costo, string software)
		{
			OTabellaAbc c = new OTabellaAbc();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.FkOvcId = fk_ovc_id;
			c.FkAreeCodiceareaZto = fk_aree_codicearea_zto;
			c.FkAreeCodiceareaPrg = fk_aree_codicearea_prg;
			c.FkOdeId = fk_ode_id;
			c.FkOinId = fk_oin_id;
			c.FkOitId = fk_oit_id;
			c.FkOtoId = fk_oto_id;
			c.Costo = costo;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OTabellaAbc>();
		}

		public List<OTabellaAbc> GetList(OTabellaAbc filtro)
		{
			return db.GetClassList(filtro).ToList < OTabellaAbc>();
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OTabellaAbc ChildInsert(OTabellaAbc cls)
		{
			return cls;
		}
	
		private void VerificaRecordCollegati(OTabellaAbc cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OTabellaAbc cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OTabellaAbc cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			