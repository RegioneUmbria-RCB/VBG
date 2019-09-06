
			
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;

using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella PROT_ALLEGATIPROTOCOLLO per la classe ProtAllegatiProtocollo il 09/01/2009 12.28.26
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
	public partial class ProtAllegatiProtocolloMgr : BaseManager
	{
		public ProtAllegatiProtocolloMgr(DataBase dataBase) : base(dataBase) { }

		public ProtAllegatiProtocollo GetById(int ad_id, string idcomune)
		{
			ProtAllegatiProtocollo c = new ProtAllegatiProtocollo();
			
			
			c.Ad_Id = ad_id;
			c.Idcomune = idcomune;
			
			return (ProtAllegatiProtocollo)db.GetClass(c);
		}

		public List<ProtAllegatiProtocollo> GetList(ProtAllegatiProtocollo filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtAllegatiProtocollo>();
		}

		public ProtAllegatiProtocollo Insert(ProtAllegatiProtocollo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtAllegatiProtocollo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtAllegatiProtocollo ChildInsert(ProtAllegatiProtocollo cls)
		{
			return cls;
		}

		private ProtAllegatiProtocollo DataIntegrations(ProtAllegatiProtocollo cls)
		{
			return cls;
		}
		

		public ProtAllegatiProtocollo Update(ProtAllegatiProtocollo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtAllegatiProtocollo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtAllegatiProtocollo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtAllegatiProtocollo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtAllegatiProtocollo cls, AmbitoValidazione ambitoValidazione)
		{
            if (string.IsNullOrEmpty(cls.Ad_Descrizione))
                throw new RequiredFieldException("PROT_ALLEGATIPROTOCOLLO.AD_DESCRIZIONE obbligatorio");

            RequiredFieldValidate(cls, ambitoValidazione);

            ForeignValidate(cls);
		}	
	}
}
			
			
			