
			
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
	/// File generato automaticamente dalla tabella PROT_TIPOLOGIAPROTOCOLLO per la classe ProtTipologiaProtocollo il 19/01/2009 10.56.08
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
	public partial class ProtTipologiaProtocolloMgr : BaseManager
	{
		public ProtTipologiaProtocolloMgr(DataBase dataBase) : base(dataBase) { }

		public ProtTipologiaProtocollo GetById(int tp_id, string idcomune)
		{
			ProtTipologiaProtocollo c = new ProtTipologiaProtocollo();
			
			
			c.Tp_Id = tp_id;
			c.Idcomune = idcomune;
			
			return (ProtTipologiaProtocollo)db.GetClass(c);
		}

		public List<ProtTipologiaProtocollo> GetList(ProtTipologiaProtocollo filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtTipologiaProtocollo>();
		}

		public ProtTipologiaProtocollo Insert(ProtTipologiaProtocollo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtTipologiaProtocollo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtTipologiaProtocollo ChildInsert(ProtTipologiaProtocollo cls)
		{
			return cls;
		}

		private ProtTipologiaProtocollo DataIntegrations(ProtTipologiaProtocollo cls)
		{
			return cls;
		}
		

		public ProtTipologiaProtocollo Update(ProtTipologiaProtocollo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtTipologiaProtocollo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtTipologiaProtocollo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtTipologiaProtocollo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtTipologiaProtocollo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			