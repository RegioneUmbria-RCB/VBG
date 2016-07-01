
			
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
	/// File generato automaticamente dalla tabella TIPIMOVIMENTO per la classe TipiMovimento il 05/11/2008 11.33.54
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
	public partial class TipiMovimentoMgr : BaseManager
	{
		public TipiMovimentoMgr(DataBase dataBase) : base(dataBase) { }

		public TipiMovimento GetById(string tipomovimento, string idcomune)
		{
			TipiMovimento c = new TipiMovimento();
			
			
			c.Tipomovimento = tipomovimento;
			c.Idcomune = idcomune;
			
			return (TipiMovimento)db.GetClass(c);
		}

		public List<TipiMovimento> GetList(TipiMovimento filtro)
		{
			return db.GetClassList( filtro ).ToList< TipiMovimento>();
		}

		public TipiMovimento Insert(TipiMovimento cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiMovimento)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiMovimento ChildInsert(TipiMovimento cls)
		{
			return cls;
		}

		

		public TipiMovimento Update(TipiMovimento cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiMovimento cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiMovimento cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiMovimento cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
	}
}
			
			
			