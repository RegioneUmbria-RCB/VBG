
			
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
	/// File generato automaticamente dalla tabella ISTANZELAVORI_T per la classe IstanzeLavoriT il 31/07/2008 11.06.39
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
	public partial class IstanzeLavoriTMgr : BaseManager
	{
		public IstanzeLavoriTMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeLavoriT GetById(string idcomune, int id)
		{
			IstanzeLavoriT c = new IstanzeLavoriT();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (IstanzeLavoriT)db.GetClass(c);
		}

		public List<IstanzeLavoriT> GetList(string idcomune, int id, int codiceistanza, int fk_isid, int fk_ltid, string lavoro)
		{
			IstanzeLavoriT c = new IstanzeLavoriT();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.Codiceistanza = codiceistanza;
			c.FkIsid = fk_isid;
			c.FkLtid = fk_ltid;
			if(!String.IsNullOrEmpty(lavoro))c.Lavoro = lavoro;
			
		
			return db.GetClassList( c ).ToList< IstanzeLavoriT>();
		}

		public List<IstanzeLavoriT> GetList(IstanzeLavoriT filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeLavoriT>();
		}

		public IstanzeLavoriT Insert(IstanzeLavoriT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeLavoriT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeLavoriT ChildInsert(IstanzeLavoriT cls)
		{
			return cls;
		}

		private IstanzeLavoriT DataIntegrations(IstanzeLavoriT cls)
		{
			return cls;
		}
		

		public IstanzeLavoriT Update(IstanzeLavoriT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeLavoriT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeLavoriT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
				
		private void Validate(IstanzeLavoriT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			