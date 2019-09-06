
			
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
	/// File generato automaticamente dalla tabella SOFTWAREATTIVI per la classe SoftwareAttivi il 09/09/2010 14.57.43
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
	public partial class SoftwareAttiviMgr : BaseManager
	{
		public SoftwareAttiviMgr(DataBase dataBase) : base(dataBase) { }

		public SoftwareAttivi GetById(string idcomune, string fk_software)
		{
			SoftwareAttivi c = new SoftwareAttivi();
			
			
			c.Idcomune = idcomune;
			c.FkSoftware = fk_software;
			
			return (SoftwareAttivi)db.GetClass(c);
		}

		public List<SoftwareAttivi> GetList(SoftwareAttivi filtro)
		{
			return db.GetClassList( filtro ).ToList< SoftwareAttivi>();
		}

		public SoftwareAttivi Insert(SoftwareAttivi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (SoftwareAttivi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private SoftwareAttivi ChildInsert(SoftwareAttivi cls)
		{
			return cls;
		}

		private SoftwareAttivi DataIntegrations(SoftwareAttivi cls)
		{
			return cls;
		}
		

		public SoftwareAttivi Update(SoftwareAttivi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(SoftwareAttivi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(SoftwareAttivi cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(SoftwareAttivi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(SoftwareAttivi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			