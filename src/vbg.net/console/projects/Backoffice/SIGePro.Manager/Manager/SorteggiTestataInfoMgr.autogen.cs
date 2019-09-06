
			
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
	/// File generato automaticamente dalla tabella SORTEGGITESTATAINFO per la classe SorteggiTestataInfo il 27/01/2009 8.44.19
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
	public partial class SorteggiTestataInfoMgr : BaseManager
	{
		public SorteggiTestataInfoMgr(DataBase dataBase) : base(dataBase) { }

		public SorteggiTestataInfo GetById(string idcomune, int fk_stid, string nome)
		{
			SorteggiTestataInfo c = new SorteggiTestataInfo();
			
			
			c.Idcomune = idcomune;
			c.FkStid = fk_stid;
			c.Nome = nome;
			
			return (SorteggiTestataInfo)db.GetClass(c);
		}

		public List<SorteggiTestataInfo> GetList(SorteggiTestataInfo filtro)
		{
			return db.GetClassList( filtro ).ToList< SorteggiTestataInfo>();
		}

		public SorteggiTestataInfo Insert(SorteggiTestataInfo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (SorteggiTestataInfo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private SorteggiTestataInfo ChildInsert(SorteggiTestataInfo cls)
		{
			return cls;
		}

		private SorteggiTestataInfo DataIntegrations(SorteggiTestataInfo cls)
		{
			return cls;
		}
		

		public SorteggiTestataInfo Update(SorteggiTestataInfo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(SorteggiTestataInfo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(SorteggiTestataInfo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(SorteggiTestataInfo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(SorteggiTestataInfo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			