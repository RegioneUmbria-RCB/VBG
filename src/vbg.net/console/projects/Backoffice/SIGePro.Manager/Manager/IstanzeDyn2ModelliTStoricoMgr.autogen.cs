
			
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
	/// File generato automaticamente dalla tabella ISTANZEDYN2MODELLIT_STORICO per la classe IstanzeDyn2ModelliTStorico il 17/02/2010 10.51.26
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
	public partial class IstanzeDyn2ModelliTStoricoMgr : BaseManager
	{
		public IstanzeDyn2ModelliTStoricoMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeDyn2ModelliTStorico GetById(string idcomune, int? idversione, int? codiceistanza, int? fk_d2mt_id)
		{
			IstanzeDyn2ModelliTStorico c = new IstanzeDyn2ModelliTStorico();
			
			
			c.Idcomune = idcomune;
			c.Idversione = idversione;
			c.Codiceistanza = codiceistanza;
			c.FkD2mtId = fk_d2mt_id;
			
			return (IstanzeDyn2ModelliTStorico)db.GetClass(c);
		}

		public List<IstanzeDyn2ModelliTStorico> GetList(IstanzeDyn2ModelliTStorico filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeDyn2ModelliTStorico>();
		}

		public IstanzeDyn2ModelliTStorico Insert(IstanzeDyn2ModelliTStorico cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeDyn2ModelliTStorico)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeDyn2ModelliTStorico ChildInsert(IstanzeDyn2ModelliTStorico cls)
		{
			return cls;
		}


		

		public IstanzeDyn2ModelliTStorico Update(IstanzeDyn2ModelliTStorico cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeDyn2ModelliTStorico cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeDyn2ModelliTStorico cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			

		
		
		private void Validate(IstanzeDyn2ModelliTStorico cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			