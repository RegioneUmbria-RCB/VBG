

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
	/// File generato automaticamente dalla tabella MERCATI_CONFIGURAZIONE per la classe MercatiConfigurazione il 02/08/2010 13.01.18
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
	public partial class MercatiConfigurazioneMgr : BaseManager
	{
		public MercatiConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

		public MercatiConfigurazione GetById(string software, string idcomune)
		{
			MercatiConfigurazione c = new MercatiConfigurazione();


			c.Software = software;
			c.Idcomune = idcomune;

			return (MercatiConfigurazione)db.GetClass(c);
		}

		public List<MercatiConfigurazione> GetList(MercatiConfigurazione filtro)
		{
			return db.GetClassList(filtro).ToList<MercatiConfigurazione>();
		}


		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private MercatiConfigurazione ChildInsert(MercatiConfigurazione cls)
		{
			return cls;
		}

		private MercatiConfigurazione DataIntegrations(MercatiConfigurazione cls)
		{
			return cls;
		}

		private void VerificaRecordCollegati(MercatiConfigurazione cls)
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}

		private void EffettuaCancellazioneACascata(MercatiConfigurazione cls)
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}


		private void Validate(MercatiConfigurazione cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate(cls, ambitoValidazione);
		}
	}
}


