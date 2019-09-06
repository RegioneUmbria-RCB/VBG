
			
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
	/// File generato automaticamente dalla tabella TIPIBANDOCAMPIGRADUAT per la classe TipiBandoCampiGraduat il 01/04/2009 9.43.30
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
	public partial class TipiBandoCampiGraduatMgr : BaseManager
	{
		public TipiBandoCampiGraduatMgr(DataBase dataBase) : base(dataBase) { }

		public TipiBandoCampiGraduat GetById(int id, string idcomune)
		{
			TipiBandoCampiGraduat c = new TipiBandoCampiGraduat();
			
			
			c.Id = id;
			c.Idcomune = idcomune;
			
			return (TipiBandoCampiGraduat)db.GetClass(c);
		}

		public List<TipiBandoCampiGraduat> GetList(TipiBandoCampiGraduat filtro)
		{
			return db.GetClassList( filtro ).ToList< TipiBandoCampiGraduat>();
		}

		public TipiBandoCampiGraduat Insert(TipiBandoCampiGraduat cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiBandoCampiGraduat)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiBandoCampiGraduat ChildInsert(TipiBandoCampiGraduat cls)
		{
			return cls;
		}

		private TipiBandoCampiGraduat DataIntegrations(TipiBandoCampiGraduat cls)
		{
			return cls;
		}
		

		public TipiBandoCampiGraduat Update(TipiBandoCampiGraduat cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiBandoCampiGraduat cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiBandoCampiGraduat cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiBandoCampiGraduat cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TipiBandoCampiGraduat cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			