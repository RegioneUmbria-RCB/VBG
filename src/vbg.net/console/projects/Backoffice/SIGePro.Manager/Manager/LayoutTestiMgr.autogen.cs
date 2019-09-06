
			
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
	/// File generato automaticamente dalla tabella LAYOUTTESTI per la classe LayoutTesti il 02/12/2009 16.47.29
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
	public partial class LayoutTestiMgr : BaseManager
	{
		public LayoutTestiMgr(DataBase dataBase) : base(dataBase) { }

		public LayoutTesti GetById(string idcomune, string software, string codicetesto)
		{
			LayoutTesti c = new LayoutTesti();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			c.Codicetesto = codicetesto;
			
			return (LayoutTesti)db.GetClass(c);
		}

		public List<LayoutTesti> GetList(LayoutTesti filtro)
		{
			return db.GetClassList( filtro ).ToList< LayoutTesti>();
		}

		public LayoutTesti Insert(LayoutTesti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (LayoutTesti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private LayoutTesti ChildInsert(LayoutTesti cls)
		{
			return cls;
		}

		private LayoutTesti DataIntegrations(LayoutTesti cls)
		{
			return cls;
		}
		

		public LayoutTesti Update(LayoutTesti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(LayoutTesti cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(LayoutTesti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(LayoutTesti cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(LayoutTesti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			