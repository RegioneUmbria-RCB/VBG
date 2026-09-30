
			
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
	/// File generato automaticamente dalla tabella FO_VISURA_CAMPI per la classe FoVisuraCampi il 28/07/2011 9.55.36
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
	public partial class FoVisuraCampiMgr : BaseManager
	{
		public FoVisuraCampiMgr(DataBase dataBase) : base(dataBase) { }

		public FoVisuraCampi GetById(string idcomune, string software, string fkidcontesto, string fkidcampo)
		{
			FoVisuraCampi c = new FoVisuraCampi();
			
			
			c.Idcomune = idcomune;
			c.Software = software;
			c.Fkidcontesto = fkidcontesto;
			c.Fkidcampo = fkidcampo;
			
			return (FoVisuraCampi)db.GetClass(c);
		}

		public List<FoVisuraCampi> GetList(FoVisuraCampi filtro)
		{
			return db.GetClassList( filtro ).ToList< FoVisuraCampi>();
		}

		public FoVisuraCampi Insert(FoVisuraCampi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoVisuraCampi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoVisuraCampi ChildInsert(FoVisuraCampi cls)
		{
			return cls;
		}

		private FoVisuraCampi DataIntegrations(FoVisuraCampi cls)
		{
			return cls;
		}
		

		public FoVisuraCampi Update(FoVisuraCampi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(FoVisuraCampi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(FoVisuraCampi cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(FoVisuraCampi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(FoVisuraCampi cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			