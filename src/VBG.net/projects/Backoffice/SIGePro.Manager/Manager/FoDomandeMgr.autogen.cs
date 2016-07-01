
			
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
	/// File generato automaticamente dalla tabella FO_DOMANDE per la classe FoDomande il 06/11/2009 16.29.10
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
	public partial class FoDomandeMgr : BaseManager
	{
		public FoDomandeMgr(DataBase dataBase) : base(dataBase) { }


		public List<FoDomande> GetList(FoDomande filtro)
		{
			return db.GetClassList( filtro ).ToList< FoDomande>();
		}

		public FoDomande Insert(FoDomande cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (FoDomande)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private FoDomande ChildInsert(FoDomande cls)
		{
			return cls;
		}

		private FoDomande DataIntegrations(FoDomande cls)
		{
			return cls;
		}
		

		public FoDomande Update(FoDomande cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}


		
		private void VerificaRecordCollegati(FoDomande cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		
		
		private void Validate(FoDomande cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}

	
	}
}
			
			
			