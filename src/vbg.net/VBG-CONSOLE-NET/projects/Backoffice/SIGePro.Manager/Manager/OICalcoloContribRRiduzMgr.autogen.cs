
			
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
	/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBR_RIDUZ per la classe OICalcoloContribRRiduz il 17/11/2008 17.30.25
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
	
	
	public partial class OICalcoloContribRRiduzMgrValidatingEventArgs
	{
		AmbitoValidazione m_ambitoValidazione;
		public AmbitoValidazione AmbitoValidazione{ get{ return m_ambitoValidazione; } }
		
		OICalcoloContribRRiduz m_classe;
		public OICalcoloContribRRiduz Classe{ get{ return m_classe; } }
		
		public OICalcoloContribRRiduzMgrValidatingEventArgs(AmbitoValidazione ambitoValidazione,OICalcoloContribRRiduz classe)
		{
			m_ambitoValidazione = ambitoValidazione;
			m_classe = classe;
		}
	}
	
	public partial class OICalcoloContribRRiduzMgr : BaseManager
	{
		#region callbacks per gli eventi relativi alle operazioni sul database
		
		// delegate invocato prima dell'inserimento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
		public delegate void InsertingDelegate( OICalcoloContribRRiduz cls );
		public event InsertingDelegate Inserting;
		
		// delegate invocato subito dopo l'inserimento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
		public delegate void InsertedDelegate( OICalcoloContribRRiduz cls );
		public event InsertedDelegate Inserted;
		
		// delegate invocato prima dell'aggiornamento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
		public delegate void UpdatingDelegate( OICalcoloContribRRiduz cls );
		public event UpdatingDelegate Updating;
		
		// delegate invocato subito dopo l'aggiornamento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
		public delegate void UpdatedDelegate( OICalcoloContribRRiduz cls );
		public event UpdatedDelegate Updated;

		// delegate invocato prima della cancellazione di un record dal database. Effettuare qui l'eventuale verifica dell'integrità referenziale 
		//(sollevare un eccezione di tipo ReferentialIntegrityException se il controllo fallisce)  e l'eventuale coancellazione a cascata
		public delegate void DeletingDelegate( OICalcoloContribRRiduz cls );
		public event DeletingDelegate Deleting;
		
		// delegate invocato subito dopo la cancellazione di un record dal db.
		public delegate void DeletedDelegate( OICalcoloContribRRiduz cls );
		public event DeletedDelegate Deleted;

		// Delegate invocato durante la validazione di una classe (subito dopo la RequiredFieldValidate). Effettuare qui la validazione formale dei dati
		public delegate void ValidatingDelegate( OICalcoloContribRRiduzMgrValidatingEventArgs e );
		public event ValidatingDelegate Validating;	
		
		#endregion
	
	
		public OICalcoloContribRRiduzMgr(DataBase dataBase) : base(dataBase) {  }

		public OICalcoloContribRRiduz GetById(string idcomune, int id)
		{
			OICalcoloContribRRiduz c = new OICalcoloContribRRiduz();
			
			
			c.Idcomune = idcomune;
			c.Id = id;
			
			return (OICalcoloContribRRiduz)db.GetClass(c);
		}

		public List<OICalcoloContribRRiduz> GetList(OICalcoloContribRRiduz filtro)
		{
			return db.GetClassList( filtro ).ToList< OICalcoloContribRRiduz>();
		}

		public OICalcoloContribRRiduz Insert(OICalcoloContribRRiduz cls)
		{
			if ( Inserting != null )
				Inserting( cls );
				
			Validate(cls, AmbitoValidazione.Insert);
			
			db.Insert(cls);
			
			if ( Inserted != null )
				Inserted( cls );
			
			return cls;
		}
		

		public OICalcoloContribRRiduz Update(OICalcoloContribRRiduz cls)
		{
			if ( Updating != null )
				Updating( cls );
		
			Validate( cls , AmbitoValidazione.Update );
			
			db.Update(cls);

			if ( Updated != null )
				Updated( cls );
			
			return cls;
		}

		public void Delete(OICalcoloContribRRiduz cls)
		{
			if ( Deleting != null )
				Deleting( cls );

			db.Delete(cls);
			
			if ( Deleted != null )
				Deleted( cls );
		}
		
	
		
		private void Validate(OICalcoloContribRRiduz cls, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( cls , ambitoValidazione );
			
			if ( Validating != null )
				Validating( new OICalcoloContribRRiduzMgrValidatingEventArgs(ambitoValidazione , cls ) );
		}	
	}
}
			
			
			