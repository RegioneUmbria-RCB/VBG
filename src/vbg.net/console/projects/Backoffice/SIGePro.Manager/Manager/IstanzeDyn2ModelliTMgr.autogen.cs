
			
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
	/// File generato automaticamente dalla tabella ISTANZEDYN2MODELLIT per la classe IstanzeDyn2ModelliT il 05/08/2008 16.49.59
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
	public partial class IstanzeDyn2ModelliTMgr : BaseManager
	{
		public IstanzeDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeDyn2ModelliT GetById(string idcomune, int codiceistanza, int fk_d2mt_id)
		{
			IstanzeDyn2ModelliT c = new IstanzeDyn2ModelliT();
			
			
			c.Idcomune = idcomune;
			c.Codiceistanza = codiceistanza;
			c.FkD2mtId = fk_d2mt_id;
			
			return (IstanzeDyn2ModelliT)db.GetClass(c);
		}

		public List<IstanzeDyn2ModelliT> GetList(IstanzeDyn2ModelliT filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeDyn2ModelliT>();
		}

		private IstanzeDyn2ModelliT DataIntegrations(IstanzeDyn2ModelliT cls)
		{
			return cls;
		}
		

		public IstanzeDyn2ModelliT Update(IstanzeDyn2ModelliT cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}



		
		private void VerificaRecordCollegati(IstanzeDyn2ModelliT cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		
		private void Validate(IstanzeDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			