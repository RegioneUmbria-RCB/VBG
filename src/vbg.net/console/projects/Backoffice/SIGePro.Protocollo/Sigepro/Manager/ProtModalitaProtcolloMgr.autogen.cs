
			
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
	/// File generato automaticamente dalla tabella PROT_MODALITAPROTOCOLLO per la classe ProtModalitaProtcollo il 19/01/2009 10.47.52
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
	public partial class ProtModalitaProtcolloMgr : BaseManager
	{
		public ProtModalitaProtcolloMgr(DataBase dataBase) : base(dataBase) { }

		public ProtModalitaProtcollo GetById(int mp_id)
		{
			ProtModalitaProtcollo c = new ProtModalitaProtcollo();
			
			
			c.Mp_Id = mp_id;
			
			return (ProtModalitaProtcollo)db.GetClass(c);
		}

		public List<ProtModalitaProtcollo> GetList(ProtModalitaProtcollo filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtModalitaProtcollo>();
		}

		public ProtModalitaProtcollo Insert(ProtModalitaProtcollo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtModalitaProtcollo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtModalitaProtcollo ChildInsert(ProtModalitaProtcollo cls)
		{
			return cls;
		}

		private ProtModalitaProtcollo DataIntegrations(ProtModalitaProtcollo cls)
		{
			return cls;
		}
		

		public ProtModalitaProtcollo Update(ProtModalitaProtcollo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtModalitaProtcollo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtModalitaProtcollo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtModalitaProtcollo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtModalitaProtcollo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			