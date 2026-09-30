
			
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
	/// File generato automaticamente dalla tabella ISTANZELAVORI_D per la classe IstanzeLavoriD il 31/07/2008 11.06.54
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
	public partial class IstanzeLavoriDMgr : BaseManager
	{
		public IstanzeLavoriDMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeLavoriD GetById(string idcomune, int id)
		{
			IstanzeLavoriD c = new IstanzeLavoriD();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (IstanzeLavoriD)db.GetClass(c);
		}

		public List<IstanzeLavoriD> GetList(string idcomune, int id, int fk_iltid, int fk_coid, int fk_umid, double costo_unitario_um, double quantita, double totale)
		{
			IstanzeLavoriD c = new IstanzeLavoriD();
			if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
			c.Id = id;
			c.FkIltid = fk_iltid;
			c.FkCoid = fk_coid;
			c.FkUmid = fk_umid;
			c.CostoUnitarioUm = costo_unitario_um;
			c.Quantita = quantita;
			c.Totale = totale;
			
		
			return db.GetClassList( c ).ToList< IstanzeLavoriD>();
		}

		public List<IstanzeLavoriD> GetList(IstanzeLavoriD filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeLavoriD>();
		}

		public IstanzeLavoriD Insert(IstanzeLavoriD cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeLavoriD)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeLavoriD ChildInsert(IstanzeLavoriD cls)
		{
			return cls;
		}

		private IstanzeLavoriD DataIntegrations(IstanzeLavoriD cls)
		{
			return cls;
		}
		

		public IstanzeLavoriD Update(IstanzeLavoriD cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeLavoriD cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		
		private void VerificaRecordCollegati(IstanzeLavoriD cls )
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
			
		private void EffettuaCancellazioneACascata(IstanzeLavoriD cls )
		{
			// Inserire la logica di cancellazione a cascata di dati collegati
		}
		
		
		private void Validate(IstanzeLavoriD cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
		}	
	}
}
			
			
			