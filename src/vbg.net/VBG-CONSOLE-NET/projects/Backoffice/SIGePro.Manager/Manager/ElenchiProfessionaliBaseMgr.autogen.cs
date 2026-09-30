
			
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
	/// File generato automaticamente dalla tabella ELENCHIPROFESSIONALIBASE per la classe ElenchiProfessionaliBase il 15/09/2010 12.28.49
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
	public partial class ElenchiProfessionaliBaseMgr : BaseManager
	{
		public ElenchiProfessionaliBaseMgr(DataBase dataBase) : base(dataBase) { }

		public ElenchiProfessionaliBase GetById(int? ep_id)
		{
			ElenchiProfessionaliBase c = new ElenchiProfessionaliBase();
			
			
			c.EpId = ep_id;
			
			return (ElenchiProfessionaliBase)db.GetClass(c);
		}

		public List<ElenchiProfessionaliBase> GetList(ElenchiProfessionaliBase filtro)
		{
			filtro.OrderBy = "ep_descrizione asc";

			return db.GetClassList( filtro ).ToList< ElenchiProfessionaliBase>();
		}

		public ElenchiProfessionaliBase Insert(ElenchiProfessionaliBase cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ElenchiProfessionaliBase)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ElenchiProfessionaliBase ChildInsert(ElenchiProfessionaliBase cls)
		{
			return cls;
		}

		private ElenchiProfessionaliBase DataIntegrations(ElenchiProfessionaliBase cls)
		{
			return cls;
		}
		

		public ElenchiProfessionaliBase Update(ElenchiProfessionaliBase cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ElenchiProfessionaliBase cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ElenchiProfessionaliBase cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ElenchiProfessionaliBase cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ElenchiProfessionaliBase cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			