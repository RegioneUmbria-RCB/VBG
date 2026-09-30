
			
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
	/// File generato automaticamente dalla tabella TIPIGRADUATORIED per la classe TipiGraduatorieD il 01/04/2009 9.28.02
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
	public partial class TipiGraduatorieDMgr : BaseManager
	{
		public TipiGraduatorieDMgr(DataBase dataBase) : base(dataBase) { }

		public TipiGraduatorieD GetById(int id, string idcomune)
		{
			TipiGraduatorieD c = new TipiGraduatorieD();
			
			
			c.Id = id;
			c.Idcomune = idcomune;
			
			return (TipiGraduatorieD)db.GetClass(c);
		}

		public List<TipiGraduatorieD> GetList(TipiGraduatorieD filtro)
		{
			return db.GetClassList( filtro ).ToList< TipiGraduatorieD>();
		}

		public TipiGraduatorieD Insert(TipiGraduatorieD cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiGraduatorieD)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiGraduatorieD ChildInsert(TipiGraduatorieD cls)
		{
			return cls;
		}

		private TipiGraduatorieD DataIntegrations(TipiGraduatorieD cls)
		{
			return cls;
		}
		

		public TipiGraduatorieD Update(TipiGraduatorieD cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiGraduatorieD cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiGraduatorieD cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiGraduatorieD cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TipiGraduatorieD cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			