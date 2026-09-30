
			
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
	/// File generato automaticamente dalla tabella DYN2_CAMPIPROPRIETA per la classe Dyn2CampiProprieta il 05/08/2008 16.49.58
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
	public partial class Dyn2CampiProprietaMgr : BaseManager
	{
		public Dyn2CampiProprietaMgr(DataBase dataBase) : base(dataBase) { }

		public Dyn2CampiProprieta GetById(string idcomune, int fk_d2c_id, string proprieta)
		{
			Dyn2CampiProprieta c = new Dyn2CampiProprieta();
			
			
			c.Idcomune = idcomune;
			c.FkD2cId = fk_d2c_id;
			c.Proprieta = proprieta;
			
			return (Dyn2CampiProprieta)db.GetClass(c);
		}

		public List<Dyn2CampiProprieta> GetList(Dyn2CampiProprieta filtro)
		{
			return db.GetClassList( filtro ).ToList< Dyn2CampiProprieta>();
		}

		public Dyn2CampiProprieta Insert(Dyn2CampiProprieta cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Dyn2CampiProprieta)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Dyn2CampiProprieta ChildInsert(Dyn2CampiProprieta cls)
		{
			return cls;
		}

		private Dyn2CampiProprieta DataIntegrations(Dyn2CampiProprieta cls)
		{
			return cls;
		}
		

		public Dyn2CampiProprieta Update(Dyn2CampiProprieta cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Dyn2CampiProprieta cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Dyn2CampiProprieta cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Dyn2CampiProprieta cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Dyn2CampiProprieta cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}


	}
}
			
			
			