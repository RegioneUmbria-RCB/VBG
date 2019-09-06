
			
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
	/// File generato automaticamente dalla tabella O_CAUSALIRIDUZIONIR per la classe OCausaliRiduzioniR il 17/11/2008 10.43.06
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
	
	
	public partial class OCausaliRiduzioniRMgrValidatingEventArgs
	{
		AmbitoValidazione m_ambitoValidazione;
		public AmbitoValidazione AmbitoValidazione{ get{ return m_ambitoValidazione; } }
		
		OCausaliRiduzioniR m_classe;
		public OCausaliRiduzioniR Classe{ get{ return m_classe; } }
		
		public OCausaliRiduzioniRMgrValidatingEventArgs(AmbitoValidazione ambitoValidazione,OCausaliRiduzioniR classe)
		{
			m_ambitoValidazione = ambitoValidazione;
			m_classe = classe;
		}
	}
	
	public partial class OCausaliRiduzioniRMgr : BaseManager
	{
		#region callbacks per gli eventi relativi alle operazioni sul database
		
		// delegate invocato prima dell'inserimento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
		public delegate void InsertingDelegate( OCausaliRiduzioniR cls );
		public event InsertingDelegate Inserting;
		
		// delegate invocato subito dopo l'inserimento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
		public delegate void InsertedDelegate( OCausaliRiduzioniR cls );
		public event InsertedDelegate Inserted;
		
		// delegate invocato prima dell'aggiornamento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
		public delegate void UpdatingDelegate( OCausaliRiduzioniR cls );
		public event UpdatingDelegate Updating;
		
		// delegate invocato subito dopo l'aggiornamento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
		public delegate void UpdatedDelegate( OCausaliRiduzioniR cls );
		public event UpdatedDelegate Updated;

		// delegate invocato prima della cancellazione di un record dal database. Effettuare qui l'eventuale verifica dell'integrità referenziale 
		//(sollevare un eccezione di tipo ReferentialIntegrityException se il controllo fallisce)  e l'eventuale coancellazione a cascata
		public delegate void DeletingDelegate( OCausaliRiduzioniR cls );
		public event DeletingDelegate Deleting;
		
		// delegate invocato subito dopo la cancellazione di un record dal db.
		public delegate void DeletedDelegate( OCausaliRiduzioniR cls );
		public event DeletedDelegate Deleted;

		// Delegate invocato durante la validazione di una classe (subito dopo la RequiredFieldValidate). Effettuare qui la validazione formale dei dati
		public delegate void ValidatingDelegate( OCausaliRiduzioniRMgrValidatingEventArgs e );
		public event ValidatingDelegate Validating;	
		
		#endregion
	
	
		public OCausaliRiduzioniRMgr(DataBase dataBase) : base(dataBase) {  }

		public OCausaliRiduzioniR GetById(string idcomune, int id)
		{
			OCausaliRiduzioniR c = new OCausaliRiduzioniR();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OCausaliRiduzioniR)db.GetClass(c);
		}

		public List<OCausaliRiduzioniR> GetList(OCausaliRiduzioniR filtro)
		{
			return db.GetClassList( filtro ).ToList< OCausaliRiduzioniR>();
		}

		public OCausaliRiduzioniR Insert(OCausaliRiduzioniR cls)
		{
			if ( Inserting != null )
				Inserting( cls );
				
			Validate(cls, AmbitoValidazione.Insert);
			
			db.Insert(cls);
			
			if ( Inserted != null )
				Inserted( cls );
			
			return cls;
		}
		

		public OCausaliRiduzioniR Update(OCausaliRiduzioniR cls)
		{
			if ( Updating != null )
				Updating( cls );
		
			Validate( cls , AmbitoValidazione.Update );
			
			db.Update(cls);

			if ( Updated != null )
				Updated( cls );
			
			return cls;
		}

		public void Delete(OCausaliRiduzioniR cls)
		{
			if ( Deleting != null )
				Deleting( cls );

			db.Delete(cls);
			
			if ( Deleted != null )
				Deleted( cls );
		}
		
	
		
		private void Validate(OCausaliRiduzioniR cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
			
			if ( Validating != null )
				Validating( new OCausaliRiduzioniRMgrValidatingEventArgs(ambitoValidazione , cls ) );
		}	
	}
}
			
			
			