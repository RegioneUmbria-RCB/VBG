
			
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella TIPICAUSALIONERI per la classe TipiCausaliOneri il 23/01/2009 16.03.12
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
	public partial class TipiCausaliOneriMgr : BaseManager
	{
		public TipiCausaliOneriMgr(DataBase dataBase) : base(dataBase) { }

		public TipiCausaliOneri GetById(string idcomune,int co_id)
		{
			TipiCausaliOneri c = new TipiCausaliOneri();
						
			c.CoId = co_id;
			c.Idcomune = idcomune;
			
			return (TipiCausaliOneri)db.GetClass(c);
		}

		public List<TipiCausaliOneri> GetList(TipiCausaliOneri filtro)
		{
			return db.GetClassList( filtro ).ToList< TipiCausaliOneri>();
		}

		public TipiCausaliOneri Insert(TipiCausaliOneri cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (TipiCausaliOneri)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private TipiCausaliOneri ChildInsert(TipiCausaliOneri cls)
		{
			return cls;
		}
	

		public TipiCausaliOneri Update(TipiCausaliOneri cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(TipiCausaliOneri cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(TipiCausaliOneri cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(TipiCausaliOneri cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(TipiCausaliOneri cls, AmbitoValidazione ambitoValidazione)
		{
            if (cls.PagamentiRegulus != 0 && cls.PagamentiRegulus != 1)
                throw new IncongruentDataException("TIPICAUSALIONERI.PAGAMENTIREGULUS = " + cls.PagamentiRegulus.ToString() + ". Valori ammessi 0 e 1");

			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			