
			
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
	/// File generato automaticamente dalla tabella FO_MESSAGGI per la classe FoMessaggi il 19/11/2009 10.30.34
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
	public partial class FoMessaggiMgr : BaseManager
	{
		public FoMessaggiMgr(DataBase dataBase) : base(dataBase) { }

		public FoMessaggi GetById(string idcomune, int? id)
		{
			FoMessaggi c = new FoMessaggi();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (FoMessaggi)db.GetClass(c);
		}

		public List<FoMessaggi> GetList(FoMessaggi filtro)
		{
			return db.GetClassList( filtro ).ToList< FoMessaggi>();
		}

		public FoMessaggi Insert(FoMessaggi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoMessaggi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoMessaggi ChildInsert(FoMessaggi cls)
		{
			return cls;
		}

		private FoMessaggi DataIntegrations(FoMessaggi cls)
		{
			return cls;
		}
		

		public FoMessaggi Update(FoMessaggi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(FoMessaggi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(FoMessaggi cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(FoMessaggi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(FoMessaggi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			