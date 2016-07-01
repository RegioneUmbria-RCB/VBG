
			
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
	/// File generato automaticamente dalla tabella HELPBASE per la classe HelpBase il 03/07/2008 14.25.32
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
	public partial class HelpBaseMgr : BaseManager
	{
		public HelpBaseMgr(DataBase dataBase) : base(dataBase) { }

		public HelpBase GetById(string software, string contenttype)
		{
			HelpBase c = new HelpBase();
			
			
			c.Software = software;
			c.Contenttype = contenttype;
			
			return (HelpBase)db.GetClass(c);
		}

		public List<HelpBase> GetList(string software, string contenttype, string helptext)
		{
			HelpBase c = new HelpBase();
			if(!String.IsNullOrEmpty(software))c.Software = software;
			if(!String.IsNullOrEmpty(contenttype))c.Contenttype = contenttype;
			if(!String.IsNullOrEmpty(helptext))c.Helptext = helptext;


			return db.GetClassList(c).ToList < HelpBase>();
		}

		public List<HelpBase> GetList(HelpBase filtro)
		{
			return db.GetClassList(filtro).ToList < HelpBase>();
		}

		public HelpBase Insert(HelpBase cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (HelpBase)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private HelpBase ChildInsert(HelpBase cls)
		{
			return cls;
		}

		private HelpBase DataIntegrations(HelpBase cls)
		{
			return cls;
		}
		

		public HelpBase Update(HelpBase cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(HelpBase cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(HelpBase cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(HelpBase cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(HelpBase cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			