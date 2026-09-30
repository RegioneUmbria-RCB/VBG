
			
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
	/// File generato automaticamente dalla tabella MESSAGGICFGBASE per la classe MessaggiCfgBase il 23/11/2009 11.30.29
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
	public partial class MessaggiCfgBaseMgr : BaseManager
	{
		public MessaggiCfgBaseMgr(DataBase dataBase) : base(dataBase) { }

		public MessaggiCfgBase GetById(string contesto)
		{
			MessaggiCfgBase c = new MessaggiCfgBase();
			
			
			c.Contesto = contesto;
			
			return (MessaggiCfgBase)db.GetClass(c);
		}

		public List<MessaggiCfgBase> GetList(MessaggiCfgBase filtro)
		{
			return db.GetClassList( filtro ).ToList< MessaggiCfgBase>();
		}

		public MessaggiCfgBase Insert(MessaggiCfgBase cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (MessaggiCfgBase)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private MessaggiCfgBase ChildInsert(MessaggiCfgBase cls)
		{
			return cls;
		}

		private MessaggiCfgBase DataIntegrations(MessaggiCfgBase cls)
		{
			return cls;
		}
		

		public MessaggiCfgBase Update(MessaggiCfgBase cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(MessaggiCfgBase cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(MessaggiCfgBase cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(MessaggiCfgBase cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(MessaggiCfgBase cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			