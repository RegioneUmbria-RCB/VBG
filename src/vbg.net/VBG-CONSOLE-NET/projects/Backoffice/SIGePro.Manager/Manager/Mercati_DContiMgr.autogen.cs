
			
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
	/// File generato automaticamente dalla tabella MERCATI_D_CONTI per la classe Mercati_DConti il 27/03/2009 11.58.29
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
    public partial class Mercati_DContiMgr : BaseManager
	{
		public Mercati_DContiMgr(DataBase dataBase) : base(dataBase) { }

		public Mercati_DConti GetById(int id, string idcomune)
		{
			Mercati_DConti c = new Mercati_DConti();
			
			
			c.Id = id;
			c.Idcomune = idcomune;
			
			return (Mercati_DConti)db.GetClass(c);
		}

		public List<Mercati_DConti> GetList(Mercati_DConti filtro)
		{
			return db.GetClassList( filtro ).ToList< Mercati_DConti>();
		}

		public Mercati_DConti Insert(Mercati_DConti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Mercati_DConti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Mercati_DConti ChildInsert(Mercati_DConti cls)
		{
			return cls;
		}

		private Mercati_DConti DataIntegrations(Mercati_DConti cls)
		{
			return cls;
		}
		

		public Mercati_DConti Update(Mercati_DConti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Mercati_DConti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Mercati_DConti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Mercati_DConti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
	}
}
			
			
			