
			
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
	/// File generato automaticamente dalla tabella ISTANZEDYN2DATI per la classe IstanzeDyn2Dati il 05/08/2008 16.49.58
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
	public partial class IstanzeDyn2DatiMgr : BaseManager
	{
		public IstanzeDyn2DatiMgr(DataBase dataBase) : base(dataBase) { }

		public IstanzeDyn2Dati GetById(string idcomune, int codiceistanza, int fk_d2c_id, int indice, int indiceMolteplicita)
		{
			IstanzeDyn2Dati c = new IstanzeDyn2Dati();

			c.Idcomune = idcomune;
			c.Codiceistanza = codiceistanza;
			c.FkD2cId = fk_d2c_id;
			c.Indice = indice;
			c.IndiceMolteplicita = indiceMolteplicita;

			return (IstanzeDyn2Dati)db.GetClass(c);
		}

	

		public List<IstanzeDyn2Dati> GetList(IstanzeDyn2Dati filtro)
		{
			return db.GetClassList( filtro ).ToList< IstanzeDyn2Dati>();
		}

		public IstanzeDyn2Dati Insert(IstanzeDyn2Dati cls)
		{
			cls = DataIntegrations(cls);

			Validate(cls, AmbitoValidazione.Insert);
		
			db.Insert(cls);
			
			cls = (IstanzeDyn2Dati)ChildDataIntegrations(cls);

			ChildInsert(cls);

			return cls;
		}
		
		public override DataClass ChildDataIntegrations(DataClass cls)
		{
			return cls;
		}

		private IstanzeDyn2Dati ChildInsert(IstanzeDyn2Dati cls)
		{
			return cls;
		}

		private IstanzeDyn2Dati DataIntegrations(IstanzeDyn2Dati cls)
		{
			return cls;
		}
		

		public IstanzeDyn2Dati Update(IstanzeDyn2Dati cls)
		{
			Validate( cls , AmbitoValidazione.Update );
		
			db.Update(cls);

			return cls;
		}

		public void Delete(IstanzeDyn2Dati cls)
		{
			VerificaRecordCollegati( cls );
			
			EffettuaCancellazioneACascata( cls );
		
			db.Delete(cls);
		}
		

			



		private void VerificaRecordCollegati(IstanzeDyn2Dati cls)
		{
			// Inserire la logica di verifica di integrità referenziale
			// Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
		}
	}
}
			
			
			