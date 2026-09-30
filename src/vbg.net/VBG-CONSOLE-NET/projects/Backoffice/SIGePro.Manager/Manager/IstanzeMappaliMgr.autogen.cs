
			
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
	/// File generato automaticamente dalla tabella ISTANZEMAPPALI per la classe IstanzeMappali il 05/11/2008 10.43.50
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
	public partial class IstanzeMappaliMgr : BaseManager
	{
		public IstanzeMappaliMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeMappali GetById(int idmappale, string idcomune)
		{
			IstanzeMappali c = new IstanzeMappali();
			
			
			c.Idmappale = idmappale;
			c.Idcomune = idcomune;
			
			return (IstanzeMappali)db.GetClass(c);
		}

		public List<IstanzeMappali> GetList(IstanzeMappali filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeMappali>();
		}

		public IstanzeMappali Insert(IstanzeMappali cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeMappali)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		

		private IstanzeMappali ChildInsert(IstanzeMappali cls)
		{
			return cls;
		}

		
		

		public IstanzeMappali Update(IstanzeMappali cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeMappali cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeMappali cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IstanzeMappali cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
	}
}
			
			
			