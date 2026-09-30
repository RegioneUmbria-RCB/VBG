using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella FORMEGIURIDICHE per la classe FormeGiuridiche il 05/08/2010 16.00.04
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
	public partial class FormeGiuridicheMgr : BaseManager
	{
		public FormeGiuridicheMgr(DataBase dataBase) : base(dataBase) { }

		public FormeGiuridiche GetById(int? codiceformagiuridica, string idcomune)
		{
			FormeGiuridiche c = new FormeGiuridiche();

			c.CODICEFORMAGIURIDICA = codiceformagiuridica.ToString();
			c.IDCOMUNE = idcomune;

			return (FormeGiuridiche)db.GetClass(c);
		}

		public List<FormeGiuridiche> GetList(FormeGiuridiche filtro)
		{
			return db.GetClassList(filtro).ToList<FormeGiuridiche>();
		}

		public FormeGiuridiche Insert(FormeGiuridiche cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);

			db.Insert(cls);

			cls = (FormeGiuridiche)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}

		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FormeGiuridiche ChildInsert(FormeGiuridiche cls)
		{
			return cls;
		}

		private FormeGiuridiche DataIntegrations(FormeGiuridiche cls)
		{
			return cls;
		}


		public FormeGiuridiche Update(FormeGiuridiche cls)
		{
			Validate(cls, AmbitoValidazione.Update);

			db.Update(cls);

			return cls;
		}

		public void Delete(FormeGiuridiche cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);
		}

		private void EffettuaCancellazioneACascata(FormeGiuridiche cls)
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}


		private void Validate(FormeGiuridiche cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate(cls, ambitoValidazione);
		}
	}
}


