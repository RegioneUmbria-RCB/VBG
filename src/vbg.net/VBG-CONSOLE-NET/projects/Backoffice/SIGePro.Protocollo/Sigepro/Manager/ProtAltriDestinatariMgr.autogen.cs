
			
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
	/// File generato automaticamente dalla tabella PROT_ALTRIDESTINATARI per la classe ProtAltriDestinatari il 09/01/2009 12.28.56
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
	public partial class ProtAltriDestinatariMgr : BaseManager
	{
		public ProtAltriDestinatariMgr(DataBase dataBase) : base(dataBase) { }

		public ProtAltriDestinatari GetById(int ad_id, string idcomune)
		{
			ProtAltriDestinatari c = new ProtAltriDestinatari();
			
			
			c.Ad_Id = ad_id;
			c.Idcomune = idcomune;
			
			return (ProtAltriDestinatari)db.GetClass(c);
		}

		public List<ProtAltriDestinatari> GetList(ProtAltriDestinatari filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtAltriDestinatari>();
		}

		public ProtAltriDestinatari Insert(ProtAltriDestinatari cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtAltriDestinatari)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtAltriDestinatari ChildInsert(ProtAltriDestinatari cls)
		{
			return cls;
		}

		private ProtAltriDestinatari DataIntegrations(ProtAltriDestinatari cls)
		{
			return cls;
		}
		

		public ProtAltriDestinatari Update(ProtAltriDestinatari cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtAltriDestinatari cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtAltriDestinatari cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtAltriDestinatari cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtAltriDestinatari cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			