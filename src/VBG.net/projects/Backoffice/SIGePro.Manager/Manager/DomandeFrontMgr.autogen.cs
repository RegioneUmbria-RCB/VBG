
			
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
	/// File generato automaticamente dalla tabella DOMANDEFRONT per la classe DomandeFront il 09/01/2009 16.48.35
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
	public partial class DomandeFrontMgr : BaseManager
	{
		public DomandeFrontMgr(DataBase dataBase) : base(dataBase) { }

		public DomandeFront GetById(string idcomune, int codicedomanda)
		{
			DomandeFront c = new DomandeFront();
			
			
			c.Idcomune = idcomune;
			c.Codicedomanda = codicedomanda;
			
			return (DomandeFront)db.GetClass(c);
		}

		public List<DomandeFront> GetList(DomandeFront filtro)
		{
			return db.GetClassList( filtro ).ToList< DomandeFront>();
		}

		public DomandeFront Insert(DomandeFront cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (DomandeFront)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private DomandeFront ChildInsert(DomandeFront cls)
		{
			return cls;
		}

		private DomandeFront DataIntegrations(DomandeFront cls)
		{
			return cls;
		}
		

		public DomandeFront Update(DomandeFront cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(DomandeFront cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(DomandeFront cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		
		private void Validate(DomandeFront cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			