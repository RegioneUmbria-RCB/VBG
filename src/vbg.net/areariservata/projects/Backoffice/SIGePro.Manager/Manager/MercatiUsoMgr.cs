using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per IstanzeStradarioMgr.
	/// </summary>
	public class Mercati_UsoMgr: BaseManager
	{

		public Mercati_UsoMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Mercati_Uso GetById(String idComune,int IdMercatiUso )
		{
			Mercati_Uso retVal = new Mercati_Uso();
            retVal.Id = IdMercatiUso;
			retVal.IdComune = idComune;
		
			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Mercati_Uso;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public List<Mercati_Uso> GetList(Mercati_Uso p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<Mercati_Uso> GetList(Mercati_Uso p_class, Mercati_Uso p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<Mercati_Uso>();
		}


		public void Delete(Mercati_Uso p_class)
		{
			db.Delete( p_class) ;
		}

		public Mercati_Uso Insert( Mercati_Uso p_class )
		{
			
			p_class = DataIntegrations( p_class );

			Validate( p_class ,AmbitoValidazione.Insert);

			db.Insert( p_class ) ;
			
			return p_class;
		}
	
		public Mercati_Uso Update( Mercati_Uso p_class )
		{
			db.Update( p_class ) ;
			return p_class;
		}


		private Mercati_Uso DataIntegrations( Mercati_Uso p_class )
		{
			//VUOTO PERCHé I DATI DEVONO ESSERE PASSATI INIZIALMENTE
			//IN QUANTO NON E' POSSIBILE FARE INSERIMENTI NELLE TABELLE
			//COLLEGATE PERCHE' PRIVE DI IDCOMUNE 
			//(GIORNISETTIMANA)
			return p_class;
		}

		private void Validate(Mercati_Uso p_class, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate( Mercati_Uso p_class )
		{
			#region MERCATI_USO.FKCODICEMERCATO
            if (p_class.FkCodiceMercato.GetValueOrDefault(int.MinValue) > int.MinValue)
			{
				if ( this.recordCount("MERCATI","CODICEMERCATO","WHERE CODICEMERCATO = " + p_class.FkCodiceMercato.ToString() + " AND IDCOMUNE = '" + p_class.IdComune + "'") == 0 )
				{
                    throw (new RecordNotfoundException("MERCATI_USO.FKCODICEMERCATO " + p_class.FkCodiceMercato.ToString() + " non trovato nella tabella MERCATI"));
				}
			}
			#endregion

			#region MERCATI_USO.FKCODICEUSO
			if ( ! StringChecker.IsStringEmpty( p_class.FkCodiceUso ) )
			{
				if ( this.recordCount("CONCESSIONIUSO","CODICE","WHERE CODICE = " + p_class.FkCodiceUso + " AND IDCOMUNE = '" + p_class.IdComune + "'") == 0 )
				{
					throw( new RecordNotfoundException("MERCATI_USO.FKCODICEUSO non trovato nella tabella CONCESSIONIUSO"));
				}
			}
			#endregion

			#region MERCATI_USO.FKGSID
			if ( ! StringChecker.IsStringEmpty( p_class.FkGsId ) )
			{
				if ( this.recordCount("GIORNISETTIMANA","GS_ID","WHERE GS_ID = " + p_class.FkGsId) == 0 )
				{
					throw( new RecordNotfoundException("MERATI_USO.FKGSID non trovato nella tabella GIORNISETTIMANA"));
				}
			}
			#endregion
		}

		#endregion
	}
}