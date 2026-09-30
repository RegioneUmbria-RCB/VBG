
			
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
	/// File generato automaticamente dalla tabella TIPIFAMIGLIEENDO per la classe TipiFamiglieEndo il 13/01/2009 14.54.57
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
	public partial class TipiFamiglieEndoMgr : BaseManager
	{
		public TipiFamiglieEndoMgr(DataBase dataBase) : base(dataBase) { }

		public TipiFamiglieEndo GetById(int codice, string idcomune)
		{
			TipiFamiglieEndo c = new TipiFamiglieEndo();
			
			
			c.Codice = codice;
			c.Idcomune = idcomune;
			
			return (TipiFamiglieEndo)db.GetClass(c);
		}

		public List<TipiFamiglieEndo> GetList(TipiFamiglieEndo filtro)
		{
			return db.GetClassList( filtro ).ToList< TipiFamiglieEndo>();
		}

		public TipiFamiglieEndo Insert(TipiFamiglieEndo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiFamiglieEndo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiFamiglieEndo ChildInsert(TipiFamiglieEndo cls)
		{
			return cls;
		}

		private TipiFamiglieEndo DataIntegrations(TipiFamiglieEndo cls)
		{
			return cls;
		}
		

		public TipiFamiglieEndo Update(TipiFamiglieEndo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiFamiglieEndo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiFamiglieEndo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiFamiglieEndo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TipiFamiglieEndo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			