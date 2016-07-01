
			
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
	/// File generato automaticamente dalla tabella ISTANZEEVENTI per la classe IstanzeEventi il 06/11/2009 9.50.32
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
	public partial class IstanzeEventiMgr : BaseManager
	{
		public IstanzeEventiMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeEventi GetById(string idcomune, int? idevento)
		{
			IstanzeEventi c = new IstanzeEventi();
			
			
			c.Idcomune = idcomune;
			c.Idevento = idevento;
			
			return (IstanzeEventi)db.GetClass(c);
		}

		public List<IstanzeEventi> GetList(IstanzeEventi filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeEventi>();
		}

		public IstanzeEventi Insert(IstanzeEventi cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeEventi)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeEventi ChildInsert(IstanzeEventi cls)
		{
			return cls;
		}	

		public IstanzeEventi Update(IstanzeEventi cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeEventi cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeEventi cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IstanzeEventi cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
			
	}
}
			
			
			