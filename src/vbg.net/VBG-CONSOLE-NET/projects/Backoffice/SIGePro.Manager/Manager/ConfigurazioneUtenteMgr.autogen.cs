
			
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
	/// File generato automaticamente dalla tabella CONFIGURAZIONEUTENTE per la classe ConfigurazioneUtente il 16/12/2008 10.40.00
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
	public partial class ConfigurazioneUtenteMgr : BaseManager
	{
		public ConfigurazioneUtenteMgr(DataBase dataBase) : base(dataBase) { }

		public ConfigurazioneUtente GetById(int codiceresponsabile, string nomeparametro, string idcomune)
		{
			ConfigurazioneUtente c = new ConfigurazioneUtente();
			
			
			c.Codiceresponsabile = codiceresponsabile;
			c.Nomeparametro = nomeparametro;
			c.Idcomune = idcomune;
			
			return (ConfigurazioneUtente)db.GetClass(c);
		}

		public List<ConfigurazioneUtente> GetList(ConfigurazioneUtente filtro)
		{
			return db.GetClassList( filtro ).ToList< ConfigurazioneUtente>();
		}

		public ConfigurazioneUtente Insert(ConfigurazioneUtente cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ConfigurazioneUtente)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ConfigurazioneUtente ChildInsert(ConfigurazioneUtente cls)
		{
			return cls;
		}

		private ConfigurazioneUtente DataIntegrations(ConfigurazioneUtente cls)
		{
			return cls;
		}
		

		public ConfigurazioneUtente Update(ConfigurazioneUtente cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ConfigurazioneUtente cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ConfigurazioneUtente cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ConfigurazioneUtente cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ConfigurazioneUtente cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			