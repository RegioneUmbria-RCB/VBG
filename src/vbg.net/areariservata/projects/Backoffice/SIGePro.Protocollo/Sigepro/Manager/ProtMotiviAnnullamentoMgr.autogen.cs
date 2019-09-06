
			
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
	/// File generato automaticamente dalla tabella PROT_MOTIVIANNULLAMENTO per la classe ProtMotiviAnnullamento il 03/03/2009 12.09.15
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
	public partial class ProtMotiviAnnullamentoMgr : BaseManager
	{
		public ProtMotiviAnnullamentoMgr(DataBase dataBase) : base(dataBase) { }

		public ProtMotiviAnnullamento GetById(int ma_id, string idcomune)
		{
			ProtMotiviAnnullamento c = new ProtMotiviAnnullamento();
			
			
			c.MaId = ma_id;
			c.Idcomune = idcomune;
			
			return (ProtMotiviAnnullamento)db.GetClass(c);
		}

		public List<ProtMotiviAnnullamento> GetList(ProtMotiviAnnullamento filtro)
		{
			return db.GetClassList( filtro ).ToList< ProtMotiviAnnullamento>();
		}

		public ProtMotiviAnnullamento Insert(ProtMotiviAnnullamento cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (ProtMotiviAnnullamento)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private ProtMotiviAnnullamento ChildInsert(ProtMotiviAnnullamento cls)
		{
			return cls;
		}

		private ProtMotiviAnnullamento DataIntegrations(ProtMotiviAnnullamento cls)
		{
			return cls;
		}
		

		public ProtMotiviAnnullamento Update(ProtMotiviAnnullamento cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(ProtMotiviAnnullamento cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(ProtMotiviAnnullamento cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(ProtMotiviAnnullamento cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(ProtMotiviAnnullamento cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			