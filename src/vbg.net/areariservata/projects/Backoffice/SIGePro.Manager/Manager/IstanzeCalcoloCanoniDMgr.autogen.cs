
			
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
	/// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_D per la classe IstanzeCalcoloCanoniD il 11/11/2008 9.19.52
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
	public partial class IstanzeCalcoloCanoniDMgr : BaseManager
	{
		public IstanzeCalcoloCanoniDMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeCalcoloCanoniD GetById(string idcomune, int id)
		{
			IstanzeCalcoloCanoniD c = new IstanzeCalcoloCanoniD();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (IstanzeCalcoloCanoniD)db.GetClass(c);
		}

		public List<IstanzeCalcoloCanoniD> GetList(IstanzeCalcoloCanoniD filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeCalcoloCanoniD>();
		}

		public IstanzeCalcoloCanoniD Insert(IstanzeCalcoloCanoniD cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeCalcoloCanoniD)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeCalcoloCanoniD ChildInsert(IstanzeCalcoloCanoniD cls)
		{
			return cls;
		}

		private IstanzeCalcoloCanoniD DataIntegrations(IstanzeCalcoloCanoniD cls)
		{
			return cls;
		}
		

		public IstanzeCalcoloCanoniD Update(IstanzeCalcoloCanoniD cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeCalcoloCanoniD cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeCalcoloCanoniD cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IstanzeCalcoloCanoniD cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(IstanzeCalcoloCanoniD cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			