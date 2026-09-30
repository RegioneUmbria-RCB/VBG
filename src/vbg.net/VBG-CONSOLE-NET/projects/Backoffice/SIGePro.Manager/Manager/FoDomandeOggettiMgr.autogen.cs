
			
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
	/// File generato automaticamente dalla tabella FO_DOMANDE_OGGETTI per la classe FoDomandeOggetti il 06/11/2009 16.30.01
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
	public partial class FoDomandeOggettiMgr : BaseManager
	{
		public FoDomandeOggettiMgr(DataBase dataBase) : base(dataBase) { }

		public FoDomandeOggetti GetById(string idcomune, int? iddomanda, int? codiceoggetto)
		{
			FoDomandeOggetti c = new FoDomandeOggetti();
			
			
			c.Idcomune = idcomune;
			c.Iddomanda = iddomanda;
			c.Codiceoggetto = codiceoggetto;
			
			return (FoDomandeOggetti)db.GetClass(c);
		}

		public List<FoDomandeOggetti> GetList(FoDomandeOggetti filtro)
		{
			return db.GetClassList( filtro ).ToList< FoDomandeOggetti>();
		}

		public FoDomandeOggetti Insert(FoDomandeOggetti cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoDomandeOggetti)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoDomandeOggetti ChildInsert(FoDomandeOggetti cls)
		{
			return cls;
		}

		private FoDomandeOggetti DataIntegrations(FoDomandeOggetti cls)
		{
			return cls;
		}
		

		public FoDomandeOggetti Update(FoDomandeOggetti cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		
		private void VerificaRecordCollegati(FoDomandeOggetti cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		
		
		private void Validate(FoDomandeOggetti cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			