
			
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
	/// File generato automaticamente dalla tabella FO_CONFIGURAZIONEBASE per la classe FoConfigurazioneBase il 14/09/2010 10.15.14
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
	public partial class FoConfigurazioneBaseMgr : BaseManager
	{
		public FoConfigurazioneBaseMgr(DataBase dataBase) : base(dataBase) { }

		public FoConfigurazioneBase GetById(int? codice)
		{
			FoConfigurazioneBase c = new FoConfigurazioneBase();
			
			
			c.Codice = codice;
			
			return (FoConfigurazioneBase)db.GetClass(c);
		}

		public List<FoConfigurazioneBase> GetList(FoConfigurazioneBase filtro)
		{
			return db.GetClassList( filtro ).ToList< FoConfigurazioneBase>();
		}

		public FoConfigurazioneBase Insert(FoConfigurazioneBase cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoConfigurazioneBase)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoConfigurazioneBase ChildInsert(FoConfigurazioneBase cls)
		{
			return cls;
		}

		private FoConfigurazioneBase DataIntegrations(FoConfigurazioneBase cls)
		{
			return cls;
		}
		

		public FoConfigurazioneBase Update(FoConfigurazioneBase cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(FoConfigurazioneBase cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(FoConfigurazioneBase cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(FoConfigurazioneBase cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(FoConfigurazioneBase cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			