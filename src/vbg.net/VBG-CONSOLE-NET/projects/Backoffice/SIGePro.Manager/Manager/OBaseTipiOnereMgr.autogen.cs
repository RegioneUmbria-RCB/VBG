
			
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
	/// File generato automaticamente dalla tabella O_BASETIPIONERE per la classe OBaseTipiOnere il 27/06/2008 13.01.35
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
	public partial class OBaseTipiOnereMgr : BaseManager
	{
		public OBaseTipiOnereMgr(DataBase dataBase) : base(dataBase) { }

		public OBaseTipiOnere GetById(string id)
		{
			OBaseTipiOnere c = new OBaseTipiOnere();
			
			
			c.Id = id;
			
			return (OBaseTipiOnere)db.GetClass(c);
		}

		public List<OBaseTipiOnere> GetList(string id, string descrizione)
		{
			OBaseTipiOnere c = new OBaseTipiOnere();
			if(!String.IsNullOrEmpty(id))c.Id = id;
			if(!String.IsNullOrEmpty(descrizione))c.Descrizione = descrizione;


			return db.GetClassList(c).ToList < OBaseTipiOnere>();
		}

		public List<OBaseTipiOnere> GetList(OBaseTipiOnere filtro)
		{
			return db.GetClassList(filtro).ToList < OBaseTipiOnere>();
		}

		public OBaseTipiOnere Insert(OBaseTipiOnere cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OBaseTipiOnere)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OBaseTipiOnere ChildInsert(OBaseTipiOnere cls)
		{
			return cls;
		}

		private OBaseTipiOnere DataIntegrations(OBaseTipiOnere cls)
		{
			return cls;
		}
		

		public OBaseTipiOnere Update(OBaseTipiOnere cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OBaseTipiOnere cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OBaseTipiOnere cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OBaseTipiOnere cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OBaseTipiOnere cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			