
			
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
	/// File generato automaticamente dalla tabella MESSAGGICFG per la classe MessaggiCfg il 23/11/2009 11.29.44
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
	public partial class MessaggiCfgMgr : BaseManager
	{
		public MessaggiCfgMgr(DataBase dataBase) : base(dataBase) { }

		public MessaggiCfg GetById(string idcomune, string software, string contesto, int id)
		{
			MessaggiCfg c = new MessaggiCfg();
			
			c.Idcomune = idcomune;
			c.Software = software;
			c.Contesto = contesto;
			c.Id = id;
			
			return (MessaggiCfg)db.GetClass(c);
		}

		public List<MessaggiCfg> GetList(MessaggiCfg filtro)
		{
			return db.GetClassList( filtro ).ToList< MessaggiCfg>();
		}

		public MessaggiCfg Insert(MessaggiCfg cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (MessaggiCfg)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private MessaggiCfg ChildInsert(MessaggiCfg cls)
		{
			return cls;
		}

		private MessaggiCfg DataIntegrations(MessaggiCfg cls)
		{
			return cls;
		}
		

		public MessaggiCfg Update(MessaggiCfg cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(MessaggiCfg cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(MessaggiCfg cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(MessaggiCfg cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(MessaggiCfg cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			