using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per IstanzeAreeMgr.\n	/// </summary>
	public class IstanzeAreeMgr: BaseManager
	{

		public IstanzeAreeMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public IstanzeAree GetById(String pCODICEISTANZA, String pCODICEAREA, String pIDCOMUNE)
		{
			IstanzeAree retVal = new IstanzeAree();
			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.CODICEAREA = pCODICEAREA;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeAree;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeAree> GetList(IstanzeAree p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<IstanzeAree> GetList(IstanzeAree p_class, IstanzeAree p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeAree>();
		}


		public void Delete(IstanzeAree p_class)
		{
			db.Delete( p_class) ;
		}

		public IstanzeAree Insert( IstanzeAree p_class )
		{
			
			p_class = DataIntegrations( p_class );

			Validate( p_class ,AmbitoValidazione.Insert);

			db.Insert( p_class ) ;

			ParentDataIntegrations( p_class );
			
			return p_class;
		}
	
		public IstanzeAree Update( IstanzeAree p_class )
		{
			db.Update( p_class ) ;
			return p_class;
		}


		private IstanzeAree DataIntegrations( IstanzeAree p_class )
		{
			IstanzeAree retVal = ( IstanzeAree ) p_class.Clone();

			if( StringChecker.IsStringEmpty( retVal.PRIMARIO ) )
				retVal.PRIMARIO = "0";

			if( StringChecker.IsStringEmpty( retVal.AUTOINS ) )
				retVal.AUTOINS = "0";

			return retVal;
		}

		private void Validate(IstanzeAree p_class, AmbitoValidazione ambitoValidazione)
		{
			if( p_class.PRIMARIO != "0" && p_class.PRIMARIO != "1" )
				throw new IncongruentDataException( "Impossibile assegnare il valore " + p_class.PRIMARIO + " al campo ISTANZEAREE.PRIMARIO. Valori ammessi: 0,1");

			if( p_class.AUTOINS != "0" && p_class.AUTOINS != "1" )
				throw new IncongruentDataException( "Impossibile assegnare il valore " + p_class.AUTOINS + " al campo ISTANZEAREE.AUTOINS. Valori ammessi: 0,1");

			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate( IstanzeAree p_class )
		{
			#region ISTANZEAREE.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEAREE.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZEAREE.CODICEAREA
			if ( ! IsStringEmpty( p_class.CODICEAREA ) )
			{
				if ( this.recordCount("AREE","CODICEAREA","WHERE CODICEAREA = " + p_class.CODICEAREA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEAREE.CODICEAREA non trovato nella tabella AREE"));
				}
			}
			#endregion
		}

		private void ParentDataIntegrations( IstanzeAree p_class )
		{
			if(p_class.PRIMARIO == "1")
			{
				bool internalOpen = false;

				IDbCommand command = db.CreateCommand();
				
				if ( db.Connection.State == ConnectionState.Closed )
				{
					internalOpen = true;
					db.Connection.Open();
				}

				PersonalLib2.Data.DataProviderFactory dpf = new DataProviderFactory( db.Connection );

				IDbDataParameter pcodicearea = dpf.CreateDataParameter();
				pcodicearea.ParameterName = dpf.Specifics.ParameterName( "CODICEAREA" );
				pcodicearea.Value = p_class.CODICEAREA;
				string s_codicearea = dpf.Specifics.QueryParameterName( "CODICEAREA" );
				command.Parameters.Add( pcodicearea );

				IDbDataParameter pidcomune = dpf.CreateDataParameter();
				pidcomune.ParameterName = dpf.Specifics.ParameterName( "IDCOMUNE" );
				pidcomune.Value = p_class.IDCOMUNE;
				string s_idcomune = dpf.Specifics.QueryParameterName( "IDCOMUNE" );
				command.Parameters.Add( pidcomune );

				IDbDataParameter pcodiceistanza = dpf.CreateDataParameter();
				pcodiceistanza.ParameterName = dpf.Specifics.ParameterName( "CODICEISTANZA" );
				pcodiceistanza.Value = p_class.CODICEISTANZA;
				string s_codiceistanza = dpf.Specifics.QueryParameterName( "CODICEISTANZA" );
				command.Parameters.Add( pcodiceistanza );

				command.CommandText = "UPDATE ISTANZE SET CODICEAREA = " + s_codicearea + " WHERE IDCOMUNE = " + s_idcomune + " AND CODICEISTANZA = " + s_codiceistanza;

				command.ExecuteNonQuery();

				if ( internalOpen )
					db.Connection.Close();
			}
		}

		#endregion
	}
}