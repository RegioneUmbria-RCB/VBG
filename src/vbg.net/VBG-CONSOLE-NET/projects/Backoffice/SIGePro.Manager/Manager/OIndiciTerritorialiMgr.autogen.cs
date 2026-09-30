
			
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
	/// File generato automaticamente dalla tabella O_INDICITERRITORIALI per la classe OIndiciTerritoriali il 27/06/2008 13.01.36
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
	public partial class OIndiciTerritorialiMgr : BaseManager
	{
		public OIndiciTerritorialiMgr(DataBase dataBase) : base(dataBase) { }

		public OIndiciTerritoriali GetById(string idcomune, int id)
		{
			OIndiciTerritoriali c = new OIndiciTerritoriali();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OIndiciTerritoriali)db.GetClass(c);
		}

		public List<OIndiciTerritoriali> GetList(string idcomune, int id, float dtz, float ift, float iff, string software)
		{
			OIndiciTerritoriali c = new OIndiciTerritoriali();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Dtz = dtz;
			c.Ift = ift;
			c.Iff = iff;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < OIndiciTerritoriali>();
		}

		public List<OIndiciTerritoriali> GetList(OIndiciTerritoriali filtro)
		{
			return db.GetClassList(filtro).ToList < OIndiciTerritoriali>();
		}

		public OIndiciTerritoriali Insert(OIndiciTerritoriali cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OIndiciTerritoriali)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OIndiciTerritoriali ChildInsert(OIndiciTerritoriali cls)
		{
			return cls;
		}

		private OIndiciTerritoriali DataIntegrations(OIndiciTerritoriali cls)
		{
			return cls;
		}
		

		public OIndiciTerritoriali Update(OIndiciTerritoriali cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OIndiciTerritoriali cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void EffettuaCancellazioneACascata(OIndiciTerritoriali cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OIndiciTerritoriali cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			