
			
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
	/// File generato automaticamente dalla tabella ANAGRAFEDYN2MODELLIT_STORICO per la classe AnagrafeDyn2ModelliTStorico il 22/02/2010 12.32.50
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
	public partial class AnagrafeDyn2ModelliTStoricoMgr : BaseManager
	{
		public AnagrafeDyn2ModelliTStoricoMgr(DataBase dataBase) : base(dataBase) { }

		public AnagrafeDyn2ModelliTStorico GetById(string idcomune, int? idversione, int? codiceanagrafe, int? fk_d2mt_id)
		{
			AnagrafeDyn2ModelliTStorico c = new AnagrafeDyn2ModelliTStorico();
			
			
			c.Idcomune = idcomune;
			c.Idversione = idversione;
			c.Codiceanagrafe = codiceanagrafe;
			c.FkD2mtId = fk_d2mt_id;
			
			return (AnagrafeDyn2ModelliTStorico)db.GetClass(c);
		}

		public List<AnagrafeDyn2ModelliTStorico> GetList(AnagrafeDyn2ModelliTStorico filtro)
		{
			return db.GetClassList( filtro ).ToList< AnagrafeDyn2ModelliTStorico>();
		}

		public AnagrafeDyn2ModelliTStorico Insert(AnagrafeDyn2ModelliTStorico cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AnagrafeDyn2ModelliTStorico)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AnagrafeDyn2ModelliTStorico ChildInsert(AnagrafeDyn2ModelliTStorico cls)
		{
			return cls;
		}

		//private AnagrafeDyn2ModelliTStorico DataIntegrations(AnagrafeDyn2ModelliTStorico cls)
		//{
		//    return cls;
		//}
		

		public AnagrafeDyn2ModelliTStorico Update(AnagrafeDyn2ModelliTStorico cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AnagrafeDyn2ModelliTStorico cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AnagrafeDyn2ModelliTStorico cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		//private void EffettuaCancellazioneACascata(AnagrafeDyn2ModelliTStorico cls )
		//{
		//    // Inserire la logica di cancellazione a cascata di dati collegati
		//}
		
		
		private void Validate(AnagrafeDyn2ModelliTStorico cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			