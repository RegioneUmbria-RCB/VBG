
			
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
	/// File generato automaticamente dalla tabella ALBEROPROC_ARENDO per la classe AlberoprocAREndo il 29/08/2011 16.45.07
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
	public partial class AlberoprocAREndoMgr : BaseManager
	{
		public AlberoprocAREndoMgr(DataBase dataBase) : base(dataBase) { }

		public AlberoprocAREndo GetById(string idcomune, int? id)
		{
			AlberoprocAREndo c = new AlberoprocAREndo();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (AlberoprocAREndo)db.GetClass(c);
		}

		public List<AlberoprocAREndo> GetList(AlberoprocAREndo filtro)
		{
			return db.GetClassList( filtro ).ToList< AlberoprocAREndo>();
		}

		public AlberoprocAREndo Insert(AlberoprocAREndo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AlberoprocAREndo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AlberoprocAREndo ChildInsert(AlberoprocAREndo cls)
		{
			return cls;
		}

		private AlberoprocAREndo DataIntegrations(AlberoprocAREndo cls)
		{
			return cls;
		}
		

		public AlberoprocAREndo Update(AlberoprocAREndo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AlberoprocAREndo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AlberoprocAREndo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(AlberoprocAREndo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(AlberoprocAREndo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			