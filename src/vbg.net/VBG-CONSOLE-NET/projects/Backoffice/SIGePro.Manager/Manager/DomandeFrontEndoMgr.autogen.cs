
			
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
	/// File generato automaticamente dalla tabella DOMANDEFRONT_ENDO per la classe DomandeFrontEndo il 09/01/2009 16.48.53
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
	public partial class DomandeFrontEndoMgr : BaseManager
	{
		public DomandeFrontEndoMgr(DataBase dataBase) : base(dataBase) { }

		public DomandeFrontEndo GetById(string idcomune, int codicedomanda, int codiceinventario)
		{
			DomandeFrontEndo c = new DomandeFrontEndo();
			
			
			c.Idcomune = idcomune;
			c.Codicedomanda = codicedomanda;
			c.Codiceinventario = codiceinventario;
			
			return (DomandeFrontEndo)db.GetClass(c);
		}

		public List<DomandeFrontEndo> GetList(DomandeFrontEndo filtro)
		{
			return db.GetClassList( filtro ).ToList< DomandeFrontEndo>();
		}

		public DomandeFrontEndo Insert(DomandeFrontEndo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (DomandeFrontEndo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private DomandeFrontEndo ChildInsert(DomandeFrontEndo cls)
		{
			return cls;
		}

		private DomandeFrontEndo DataIntegrations(DomandeFrontEndo cls)
		{
			return cls;
		}
		

		public DomandeFrontEndo Update(DomandeFrontEndo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(DomandeFrontEndo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(DomandeFrontEndo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(DomandeFrontEndo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(DomandeFrontEndo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			