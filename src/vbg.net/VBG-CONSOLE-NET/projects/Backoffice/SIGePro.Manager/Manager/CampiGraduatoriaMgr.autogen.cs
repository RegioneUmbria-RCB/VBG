
			
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
	/// File generato automaticamente dalla tabella CAMPIGRADUATORIA per la classe CampiGraduatoria il 01/04/2009 9.49.12
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
	public partial class CampiGraduatoriaMgr : BaseManager
	{
		public CampiGraduatoriaMgr(DataBase dataBase) : base(dataBase) { }

		public CampiGraduatoria GetById(int id, string idcomune)
		{
			CampiGraduatoria c = new CampiGraduatoria();
			
			
			c.Id = id;
			c.Idcomune = idcomune;
			
			return (CampiGraduatoria)db.GetClass(c);
		}

		public List<CampiGraduatoria> GetList(CampiGraduatoria filtro)
		{
			return db.GetClassList( filtro ).ToList< CampiGraduatoria>();
		}

		public CampiGraduatoria Insert(CampiGraduatoria cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CampiGraduatoria)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CampiGraduatoria ChildInsert(CampiGraduatoria cls)
		{
			return cls;
		}

		private CampiGraduatoria DataIntegrations(CampiGraduatoria cls)
		{
			return cls;
		}
		

		public CampiGraduatoria Update(CampiGraduatoria cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CampiGraduatoria cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CampiGraduatoria cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CampiGraduatoria cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CampiGraduatoria cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			