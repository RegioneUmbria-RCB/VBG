
			
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
	/// File generato automaticamente dalla tabella ANAGRAFEDYN2DATI_STORICO per la classe AnagrafeDyn2DatiStorico il 22/02/2010 12.33.27
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
	public partial class AnagrafeDyn2DatiStoricoMgr : BaseManager
	{
		public AnagrafeDyn2DatiStoricoMgr(DataBase dataBase) : base(dataBase) { }

		public AnagrafeDyn2DatiStorico GetById(string idcomune, int? idversione, int? codiceanagrafe, int? fk_d2mt_id, int? fk_d2c_id, int? indice, int? indice_molteplicita)
		{
			AnagrafeDyn2DatiStorico c = new AnagrafeDyn2DatiStorico();
			
			
			c.Idcomune = idcomune;
			c.Idversione = idversione;
			c.Codiceanagrafe = codiceanagrafe;
			c.FkD2mtId = fk_d2mt_id;
			c.FkD2cId = fk_d2c_id;
			c.Indice = indice;
			c.IndiceMolteplicita = indice_molteplicita;
			
			return (AnagrafeDyn2DatiStorico)db.GetClass(c);
		}

		public List<AnagrafeDyn2DatiStorico> GetList(AnagrafeDyn2DatiStorico filtro)
		{
			return db.GetClassList( filtro ).ToList< AnagrafeDyn2DatiStorico>();
		}

		public AnagrafeDyn2DatiStorico Insert(AnagrafeDyn2DatiStorico cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (AnagrafeDyn2DatiStorico)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private AnagrafeDyn2DatiStorico ChildInsert(AnagrafeDyn2DatiStorico cls)
		{
			return cls;
		}

		private AnagrafeDyn2DatiStorico DataIntegrations(AnagrafeDyn2DatiStorico cls)
		{
			return cls;
		}
		

		public AnagrafeDyn2DatiStorico Update(AnagrafeDyn2DatiStorico cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(AnagrafeDyn2DatiStorico cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(AnagrafeDyn2DatiStorico cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(AnagrafeDyn2DatiStorico cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(AnagrafeDyn2DatiStorico cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			