
			
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
	/// File generato automaticamente dalla tabella ANAGRAFEDYN2MODELLIT per la classe AnagrafeDyn2ModelliT il 05/08/2008 16.49.58
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
	public partial class AnagrafeDyn2ModelliTMgr : BaseManager
	{
		public AnagrafeDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public AnagrafeDyn2ModelliT GetById(string idcomune, int codiceanagrafe, int fk_d2mt_id)
		{
			AnagrafeDyn2ModelliT c = new AnagrafeDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.Codiceanagrafe = codiceanagrafe;
			c.FkD2mtId = fk_d2mt_id;
			
			return (AnagrafeDyn2ModelliT)db.GetClass(c);
		}

		public List<AnagrafeDyn2ModelliT> GetList(AnagrafeDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< AnagrafeDyn2ModelliT>();
		}

		public AnagrafeDyn2ModelliT Insert(AnagrafeDyn2ModelliT cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AnagrafeDyn2ModelliT)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AnagrafeDyn2ModelliT ChildInsert(AnagrafeDyn2ModelliT cls)
		{
			return cls;
		}

		private AnagrafeDyn2ModelliT DataIntegrations(AnagrafeDyn2ModelliT cls)
		{
			return cls;
		}
		

		public AnagrafeDyn2ModelliT Update(AnagrafeDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AnagrafeDyn2ModelliT cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AnagrafeDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		private void Validate(AnagrafeDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}




	}
}
			
			
			