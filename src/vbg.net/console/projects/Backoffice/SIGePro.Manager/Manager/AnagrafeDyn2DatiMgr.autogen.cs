
			
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
	/// File generato automaticamente dalla tabella ANAGRAFEDYN2DATI per la classe AnagrafeDyn2Dati il 25/11/2009 15.24.22
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
	public partial class AnagrafeDyn2DatiMgr : BaseManager
	{
		public AnagrafeDyn2DatiMgr(DataBase dataBase) : base(dataBase) { }

		public AnagrafeDyn2Dati GetById(string idcomune, int? codiceanagrafe, int? fk_d2c_id, int? indice, int indiceMolteplicita)
		{
			AnagrafeDyn2Dati c = new AnagrafeDyn2Dati();
			
			c.Idcomune = idcomune;
			c.Codiceanagrafe = codiceanagrafe;
			c.FkD2cId = fk_d2c_id;
			c.Indice = indice;
			c.IndiceMolteplicita = indiceMolteplicita;
			
			return (AnagrafeDyn2Dati)db.GetClass(c);
		}

		public List<AnagrafeDyn2Dati> GetList(AnagrafeDyn2Dati filtro)
		{
			return db.GetClassList( filtro ).ToList< AnagrafeDyn2Dati>();
		}

		public AnagrafeDyn2Dati Insert(AnagrafeDyn2Dati cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AnagrafeDyn2Dati)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AnagrafeDyn2Dati ChildInsert(AnagrafeDyn2Dati cls)
		{
			return cls;
		}

		private AnagrafeDyn2Dati DataIntegrations(AnagrafeDyn2Dati cls)
		{
			return cls;
		}
		

		public AnagrafeDyn2Dati Update(AnagrafeDyn2Dati cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AnagrafeDyn2Dati cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AnagrafeDyn2Dati cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(AnagrafeDyn2Dati cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		

	}
}
			
			
			