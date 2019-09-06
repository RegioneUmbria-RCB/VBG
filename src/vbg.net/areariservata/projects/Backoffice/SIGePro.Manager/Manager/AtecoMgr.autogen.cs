
			
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
	/// File generato automaticamente dalla tabella ATECO per la classe Ateco il 06/12/2010 12.13.37
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
	public partial class AtecoMgr : BaseManager
	{
		public AtecoMgr(DataBase dataBase) : base(dataBase) { }



		public List<Ateco> GetList(Ateco filtro)
		{
			return db.GetClassList( filtro ).ToList< Ateco>();
		}

		public Ateco Insert(Ateco cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (Ateco)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private Ateco ChildInsert(Ateco cls)
		{
			return cls;
		}

		private Ateco DataIntegrations(Ateco cls)
		{
			return cls;
		}
		

		public Ateco Update(Ateco cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(Ateco cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(Ateco cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(Ateco cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(Ateco cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			