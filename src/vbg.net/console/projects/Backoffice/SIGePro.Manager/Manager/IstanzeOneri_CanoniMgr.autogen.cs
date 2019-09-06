
			
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
	/// File generato automaticamente dalla tabella ISTANZEONERI_CANONI per la classe IstanzeOneri_Canoni il 16/09/2008 18.51.41
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
	public partial class IstanzeOneri_CanoniMgr : BaseManager
	{
		public IstanzeOneri_CanoniMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeOneri_Canoni GetById(string idcomune, int fk_id_istoneri, int fk_idtestata)
		{
			IstanzeOneri_Canoni c = new IstanzeOneri_Canoni();
			
			
			c.Idcomune = idcomune;
			c.FkIdIstoneri = fk_id_istoneri;
			c.FkIdtestata = fk_idtestata;
			
			return (IstanzeOneri_Canoni)db.GetClass(c);
		}

		public List<IstanzeOneri_Canoni> GetList(IstanzeOneri_Canoni filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeOneri_Canoni>();
		}

		public IstanzeOneri_Canoni Insert(IstanzeOneri_Canoni cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeOneri_Canoni)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeOneri_Canoni ChildInsert(IstanzeOneri_Canoni cls)
		{
			return cls;
		}

		private IstanzeOneri_Canoni DataIntegrations(IstanzeOneri_Canoni cls)
		{
			return cls;
		}
		

		public IstanzeOneri_Canoni Update(IstanzeOneri_Canoni cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeOneri_Canoni cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeOneri_Canoni cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IstanzeOneri_Canoni cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(IstanzeOneri_Canoni cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			