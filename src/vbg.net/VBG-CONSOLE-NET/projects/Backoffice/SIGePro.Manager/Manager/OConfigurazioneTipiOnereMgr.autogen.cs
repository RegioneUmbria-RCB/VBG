
			
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
	/// File generato automaticamente dalla tabella O_CONFIGURAZIONETIPIONERE per la classe OConfigurazioneTipiOnere il 27/06/2008 13.01.35
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
	public partial class OConfigurazioneTipiOnereMgr : BaseManager
	{
		public OConfigurazioneTipiOnereMgr(DataBase dataBase) : base(dataBase) { }

		public OConfigurazioneTipiOnere GetById(string idcomune, string fk_bto_id, string software)
		{
			OConfigurazioneTipiOnere c = new OConfigurazioneTipiOnere();
			
			
			c.Idcomune = idcomune;
			c.FkBtoId = fk_bto_id;
			c.Software = software;
			
			return (OConfigurazioneTipiOnere)db.GetClass(c);
		}

		public List<OConfigurazioneTipiOnere> GetList(string idcomune, string fk_bto_id, int fk_co_id, string software)
		{
			OConfigurazioneTipiOnere c = new OConfigurazioneTipiOnere();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			if(!String.IsNullOrEmpty(fk_bto_id))c.FkBtoId = fk_bto_id;
			c.FkCoId = fk_co_id;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OConfigurazioneTipiOnere>();
		}

		public List<OConfigurazioneTipiOnere> GetList(OConfigurazioneTipiOnere filtro)
		{
			return db.GetClassList(filtro).ToList < OConfigurazioneTipiOnere>();
		}

		public OConfigurazioneTipiOnere Insert(OConfigurazioneTipiOnere cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OConfigurazioneTipiOnere)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OConfigurazioneTipiOnere ChildInsert(OConfigurazioneTipiOnere cls)
		{
			return cls;
		}

		private OConfigurazioneTipiOnere DataIntegrations(OConfigurazioneTipiOnere cls)
		{
			return cls;
		}
		

		public OConfigurazioneTipiOnere Update(OConfigurazioneTipiOnere cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OConfigurazioneTipiOnere cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OConfigurazioneTipiOnere cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OConfigurazioneTipiOnere cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OConfigurazioneTipiOnere cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			