
			
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
	/// File generato automaticamente dalla tabella CC_BASETIPOCALCOLO per la classe CCBaseTipoCalcolo il 27/06/2008 16.48.53
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
	public partial class CCBaseTipoCalcoloMgr : BaseManager
	{
		public CCBaseTipoCalcoloMgr(DataBase dataBase) : base(dataBase) { }

		public CCBaseTipoCalcolo GetById(string id)
		{
			CCBaseTipoCalcolo c = new CCBaseTipoCalcolo();
			
			
			c.Id = id;
			
			return (CCBaseTipoCalcolo)db.GetClass(c);
		}

		public List<CCBaseTipoCalcolo> GetList(string id, string tipocalcolo)
		{
			CCBaseTipoCalcolo c = new CCBaseTipoCalcolo();
			if(!String.IsNullOrEmpty(id))c.Id = id;
			if(!String.IsNullOrEmpty(tipocalcolo))c.Tipocalcolo = tipocalcolo;


			return db.GetClassList(c).ToList < CCBaseTipoCalcolo>();
		}

		public List<CCBaseTipoCalcolo> GetList(CCBaseTipoCalcolo filtro)
		{
			return db.GetClassList(filtro).ToList < CCBaseTipoCalcolo>();
		}

		public CCBaseTipoCalcolo Insert(CCBaseTipoCalcolo cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (CCBaseTipoCalcolo)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private CCBaseTipoCalcolo ChildInsert(CCBaseTipoCalcolo cls)
		{
			return cls;
		}

		private CCBaseTipoCalcolo DataIntegrations(CCBaseTipoCalcolo cls)
		{
			return cls;
		}
		

		public CCBaseTipoCalcolo Update(CCBaseTipoCalcolo cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(CCBaseTipoCalcolo cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(CCBaseTipoCalcolo cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(CCBaseTipoCalcolo cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(CCBaseTipoCalcolo cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			