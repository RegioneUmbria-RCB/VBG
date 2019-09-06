

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
	/// File generato automaticamente dalla tabella MOVIMENTIMAILALLEGATI per la classe MovimentiMailAllegati il 06/07/2010 10.56.53
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
	public partial class MovimentiMailAllegatiMgr : BaseManager
	{
		public MovimentiMailAllegatiMgr(DataBase dataBase) : base(dataBase) { }

		public MovimentiMailAllegati GetById(string idcomune, int? id)
		{
			MovimentiMailAllegati c = new MovimentiMailAllegati();


			c.Idcomune = idcomune;
			c.Id = id;

			return (MovimentiMailAllegati)db.GetClass(c);
		}

		public List<MovimentiMailAllegati> GetList(MovimentiMailAllegati filtro)
		{
			return db.GetClassList(filtro).ToList<MovimentiMailAllegati>();
		}

		public MovimentiMailAllegati Insert(MovimentiMailAllegati cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);

			db.Insert(cls);

			cls = (MovimentiMailAllegati)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}

		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private MovimentiMailAllegati ChildInsert(MovimentiMailAllegati cls)
		{
			return cls;
		}

		private MovimentiMailAllegati DataIntegrations(MovimentiMailAllegati cls)
		{
			return cls;
		}


		public MovimentiMailAllegati Update(MovimentiMailAllegati cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			return cls;
		}

		public void Delete(MovimentiMailAllegati cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);
		}

		private void VerificaRecordCollegati(MovimentiMailAllegati cls)
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}



		private void Validate(MovimentiMailAllegati cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate(cls, ambitoValidazione);
		}
	}
}


