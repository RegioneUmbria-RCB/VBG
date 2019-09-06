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
	/// File generato automaticamente dalla tabella CANONI_TIPISUPERFICI per la classe CanoniTipiSuperfici il 11/11/2008 9.19.13
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
	public partial class CanoniTipiSuperficiMgr : BaseManager
	{
		public CanoniTipiSuperficiMgr(DataBase dataBase) : base(dataBase) { }

		public CanoniTipiSuperfici GetById(string idcomune, int id)
		{
			CanoniTipiSuperfici c = new CanoniTipiSuperfici();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (CanoniTipiSuperfici)db.GetClass(c);
		}

		public List<CanoniTipiSuperfici> GetList(CanoniTipiSuperfici filtro)
		{
			return db.GetClassList( filtro ).ToList< CanoniTipiSuperfici>();
		}

		public CanoniTipiSuperfici Insert(CanoniTipiSuperfici cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CanoniTipiSuperfici)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CanoniTipiSuperfici ChildInsert(CanoniTipiSuperfici cls)
		{
			return cls;
		}

		public CanoniTipiSuperfici Update(CanoniTipiSuperfici cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CanoniTipiSuperfici cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
				
		private void EffettuaCancellazioneACascata(CanoniTipiSuperfici cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
	}
}
			
			
			