
			
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
	/// File generato automaticamente dalla tabella ISTANZEFRONTOFFICE per la classe IstanzeFrontOffice il 30/01/2009 10.09.09
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
	public partial class IstanzeFrontOfficeMgr : BaseManager
	{
		public IstanzeFrontOfficeMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeFrontOffice GetById(string idcomune, int id)
		{
			IstanzeFrontOffice c = new IstanzeFrontOffice();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (IstanzeFrontOffice)db.GetClass(c);
		}

		public List<IstanzeFrontOffice> GetList(IstanzeFrontOffice filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeFrontOffice>();
		}

		public IstanzeFrontOffice Insert(IstanzeFrontOffice cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeFrontOffice)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeFrontOffice ChildInsert(IstanzeFrontOffice cls)
		{
			return cls;
		}

		private IstanzeFrontOffice DataIntegrations(IstanzeFrontOffice cls)
		{
			return cls;
		}
		

		public IstanzeFrontOffice Update(IstanzeFrontOffice cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeFrontOffice cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeFrontOffice cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IstanzeFrontOffice cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(IstanzeFrontOffice cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			