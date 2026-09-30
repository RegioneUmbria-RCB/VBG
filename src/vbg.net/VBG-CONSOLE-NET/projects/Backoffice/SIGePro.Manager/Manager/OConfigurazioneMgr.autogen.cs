
			
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
	/// File generato automaticamente dalla tabella O_CONFIGURAZIONE per la classe OConfigurazione il 27/06/2008 13.01.35
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
	public partial class OConfigurazioneMgr : BaseManager
	{
		public OConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

		public OConfigurazione GetById(string idcomune, string software)
		{
			OConfigurazione c = new OConfigurazione();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			
			return (OConfigurazione)db.GetClass(c);
		}

		public List<OConfigurazione> GetList(string idcomune, int fk_tipiaree_codice_zto, int fk_tipiaree_codice_prg, int fk_tum_umid_mq, int fk_tum_umid_mc, string software)
		{
			OConfigurazione c = new OConfigurazione();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.FkTipiareeCodiceZto = fk_tipiaree_codice_zto;
			c.FkTipiareeCodicePrg = fk_tipiaree_codice_prg;
			c.FkTumUmidMq = fk_tum_umid_mq;
			c.FkTumUmidMc = fk_tum_umid_mc;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OConfigurazione>();
		}

		public List<OConfigurazione> GetList(OConfigurazione filtro)
		{
			return db.GetClassList(filtro).ToList < OConfigurazione>();
		}

		public OConfigurazione Insert(OConfigurazione cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OConfigurazione)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OConfigurazione ChildInsert(OConfigurazione cls)
		{
			return cls;
		}

		private OConfigurazione DataIntegrations(OConfigurazione cls)
		{
			return cls;
		}
		

		public OConfigurazione Update(OConfigurazione cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OConfigurazione cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OConfigurazione cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OConfigurazione cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OConfigurazione cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			