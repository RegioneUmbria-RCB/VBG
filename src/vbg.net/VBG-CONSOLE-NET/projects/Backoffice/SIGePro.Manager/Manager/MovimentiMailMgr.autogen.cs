

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
	/// File generato automaticamente dalla tabella MOVIMENTIMAIL per la classe MovimentiMail il 06/07/2010 10.52.29
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
	public partial class MovimentiMailMgr : BaseManager
	{
		public MovimentiMailMgr(DataBase dataBase) : base(dataBase) { }

		public MovimentiMail GetById(string idcomune, int? id)
		{
			MovimentiMail c = new MovimentiMail();


			c.Idcomune = idcomune;
			c.Id = id;

			return (MovimentiMail)db.GetClass(c);
		}

		public List<MovimentiMail> GetList(MovimentiMail filtro)
		{
			return db.GetClassList(filtro).ToList<MovimentiMail>();
		}

		public MovimentiMail Insert(MovimentiMail cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);

			db.Insert(cls);

			cls = (MovimentiMail)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}

		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private MovimentiMail ChildInsert(MovimentiMail cls)
		{
			return cls;
		}

		private MovimentiMail DataIntegrations(MovimentiMail cls)
		{
			return cls;
		}


		public MovimentiMail Update(MovimentiMail cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			return cls;
		}

		public void Delete(MovimentiMail cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);
		}

		private void VerificaRecordCollegati(MovimentiMail cls)
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}




		private void Validate(MovimentiMail cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate(cls, ambitoValidazione);
		}
	}
}


