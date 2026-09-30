
			
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
	/// File generato automaticamente dalla tabella OT_ISTANZE per la classe OtIstanze il 31/07/2008 9.24.49
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
	public partial class OtIstanzeMgr : BaseManager
	{
		public OtIstanzeMgr(DataBase dataBase) : base(dataBase) { }

		public OtIstanze GetById(int codiceistanza, string idcomune)
		{
			OtIstanze c = new OtIstanze();
			
			
			c.Codiceistanza = codiceistanza;
			c.Idcomune = idcomune;
			
			return (OtIstanze)db.GetClass(c);
		}

		public List<OtIstanze> GetList(int codiceistanza, string idcomune, int codvia, string descvia, string civico, string bis, string foglio, string numero, string subalterno, string interno, string piano, string scala, string ot_fabb_numogg, string ot_oggc_numogg)
		{
			OtIstanze c = new OtIstanze();
			c.Codiceistanza = codiceistanza;
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Codvia = codvia;
			if(!String.IsNullOrEmpty(descvia))c.Descvia = descvia;
			if(!String.IsNullOrEmpty(civico))c.Civico = civico;
			if(!String.IsNullOrEmpty(bis))c.Bis = bis;
			if(!String.IsNullOrEmpty(foglio))c.Foglio = foglio;
			if(!String.IsNullOrEmpty(numero))c.Numero = numero;
			if(!String.IsNullOrEmpty(subalterno))c.Subalterno = subalterno;
			if(!String.IsNullOrEmpty(interno))c.Interno = interno;
			if(!String.IsNullOrEmpty(piano))c.Piano = piano;
			if(!String.IsNullOrEmpty(scala))c.Scala = scala;
			if(!String.IsNullOrEmpty(ot_fabb_numogg))c.OtFabbNumogg = ot_fabb_numogg;
			if(!String.IsNullOrEmpty(ot_oggc_numogg))c.OtOggcNumogg = ot_oggc_numogg;
			
		
			return db.GetClassList( c ).ToList< OtIstanze>();
		}

		public List<OtIstanze> GetList(OtIstanze filtro)
		{
			return db.GetClassList( filtro ).ToList< OtIstanze>();
		}

		public OtIstanze Insert(OtIstanze cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (OtIstanze)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private OtIstanze ChildInsert(OtIstanze cls)
		{
			return cls;
		}

		private OtIstanze DataIntegrations(OtIstanze cls)
		{
			return cls;
		}
		

		public OtIstanze Update(OtIstanze cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(OtIstanze cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(OtIstanze cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(OtIstanze cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(OtIstanze cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			