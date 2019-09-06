
			
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
	/// File generato automaticamente dalla tabella HELP per la classe Help il 03/07/2008 12.58.39
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
	public partial class HelpMgr : BaseManager
	{
		public HelpMgr(DataBase dataBase) : base(dataBase) { }

		public Help GetById(string idcomune, string contenttype, string software)
		{
			Help c = new Help();

            c.Idcomune = idcomune;
			c.Contenttype = contenttype;
			c.Software = software;
			
			return (Help)db.GetClass(c);
		}

		public List<Help> GetList(string idcomune, string contenttype, string helptext, string software)
		{
			Help c = new Help();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			if(!String.IsNullOrEmpty(contenttype))c.Contenttype = contenttype;
			if(!String.IsNullOrEmpty(helptext))c.Helptext = helptext;
			if(!String.IsNullOrEmpty(software))c.Software = software;


			return db.GetClassList(c).ToList < Help>();
		}

		public List<Help> GetList(Help filtro)
		{
			return db.GetClassList(filtro).ToList < Help>();
		}

		public Help Insert(Help cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Help)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Help ChildInsert(Help cls)
		{
			return cls;
		}

		private Help DataIntegrations(Help cls)
		{
			return cls;
		}
		

		public Help Update(Help cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Help cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Help cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Help cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Help cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			