
			
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
	/// File generato automaticamente dalla tabella PROTOCOLLO_CONFIGURAZIONE per la classe ProtocolloConfigurazione il 11/12/2008 11.38.33
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
	public partial class ProtocolloConfigurazioneMgr : BaseManager
	{
		public ProtocolloConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

		public ProtocolloConfigurazione GetById(string idcomune, string software)
		{
			ProtocolloConfigurazione c = new ProtocolloConfigurazione();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			
			return (ProtocolloConfigurazione)db.GetClass(c);
		}

		public List<ProtocolloConfigurazione> GetList(ProtocolloConfigurazione filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtocolloConfigurazione>();
		}

		public ProtocolloConfigurazione Insert(ProtocolloConfigurazione cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtocolloConfigurazione)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtocolloConfigurazione ChildInsert(ProtocolloConfigurazione cls)
		{
			return cls;
		}

		private ProtocolloConfigurazione DataIntegrations(ProtocolloConfigurazione cls)
		{
			return cls;
		}
		

		public ProtocolloConfigurazione Update(ProtocolloConfigurazione cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtocolloConfigurazione cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtocolloConfigurazione cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtocolloConfigurazione cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtocolloConfigurazione cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			