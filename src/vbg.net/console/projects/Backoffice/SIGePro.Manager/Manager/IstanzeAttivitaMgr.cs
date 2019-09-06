using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.IstanzeAttivita;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	

	public class IstanzeAttivitaMgr: BaseManager
	{

		public IstanzeAttivitaMgr( DataBase dataBase ) : base( dataBase ) {}

		public IstanzeAttivita GetById( String pIDCOMUNE, int pID )
		{
			IstanzeAttivita retVal = new IstanzeAttivita();
			
			retVal.Id = pID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeAttivita;
			
			return null;
		}

        public List<IstanzeAttivita> GetList(IstanzeAttivita p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<IstanzeAttivita> GetList(IstanzeAttivita p_class, IstanzeAttivita p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeAttivita>();
		}
		
		public void Delete(IstanzeAttivita p_class)
		{	
			db.Delete( p_class) ;
		}

		public IstanzeAttivita Insert( IstanzeAttivita p_class )
		{

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			ParentDataIntegrations( p_class );

			return p_class;
		}	

		private void ParentDataIntegrations(IstanzeAttivita p_class )
		{
			AttivitaMgr pAttivitaMg = new AttivitaMgr( this.db );
			Attivita pAttivita = pAttivitaMg.GetById( p_class.CODICEATTIVITA, p_class.IDCOMUNE );

			SettoriMgr pSettoreMgr = new SettoriMgr( this.db );
			Settori pSettori = pSettoreMgr.GetById( pAttivita.CODICESETTORE, p_class.IDCOMUNE );

			if ( ! IsStringEmpty( pSettori.FLAG_CONTAMQATTIVITA ) )
			{
				if ( pSettori.FLAG_CONTAMQATTIVITA == "1" )
				{
					string metriQTotali = "0";

					string sql = "Select Sum(METRIQ) as answer from ISTANZEATTIVITA, ATTIVITA, SETTORI Where ISTANZEATTIVITA.IDCOMUNE = '" + p_class.IDCOMUNE + "' AND ISTANZEATTIVITA.codiceistanza = " + p_class.CODICEISTANZA + " AND ATTIVITA.IDCOMUNE = ISTANZEATTIVITA.IDCOMUNE AND ATTIVITA.CODICEISTAT = ISTANZEATTIVITA.CODICEATTIVITA AND SETTORI.IDCOMUNE = ATTIVITA.IDCOMUNE AND SETTORI.CODICESETTORE = ATTIVITA.CODICESETTORE AND SETTORI.FLAG_CONTAMQATTIVITA = 1";
					
					using(IDataReader reader = db.CreateCommand( sql ).ExecuteReader())
					{
						if ( reader.Read() )
							metriQTotali = ( reader["answer"] == DBNull.Value ) ? "0" : reader["answer"].ToString();
					}

					//Commentato per via di un baco della funzione "ExecuteScalar"
					//string MetriQTotali = db.CreateCommand(sql).ExecuteScalar().ToString();
					
					if ( metriQTotali != "0" )
					{
						DataProviderFactory mydp = new DataProviderFactory(db.Connection);
						sql = "UPDATE ISTANZE SET METRIQUADRATI = " + mydp.Specifics.QueryParameterName("METRIQUADRATI") + " where istanze.idcomune = " + mydp.Specifics.QueryParameterName("IDCOMUNE") + " and istanze.codiceistanza = " + mydp.Specifics.QueryParameterName("CODICEISTANZA");
						
						IDbCommand cmd = db.CreateCommand(sql);
						
						IDataParameter param1 = cmd.CreateParameter();
						param1.ParameterName = mydp.Specifics.QueryParameterName("METRIQUADRATI");
						param1.Value = Convert.ToDouble( metriQTotali );

						IDataParameter param2 = cmd.CreateParameter();
						param2.ParameterName = mydp.Specifics.QueryParameterName("IDCOMUNE");
						param2.Value = p_class.IDCOMUNE;

						IDataParameter param3 = cmd.CreateParameter();
						param3.ParameterName = mydp.Specifics.QueryParameterName("CODICEISTANZA");
						param3.Value = p_class.CODICEISTANZA;

						cmd.Parameters.Add( param1 );
						cmd.Parameters.Add( param2 );
						cmd.Parameters.Add( param3 );

						cmd.ExecuteNonQuery();
					}
				}

			}
		}

		private void Validate(IstanzeAttivita p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{	
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );

		}

		private void ForeignValidate ( IstanzeAttivita p_class )
		{
			#region ISTANZEATTIVITA.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEATTIVITA.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZEATTIVITA.CODICEATTIVITA
			if ( ! IsStringEmpty( p_class.CODICEATTIVITA ) )
			{
				if ( this.recordCount("ATTIVITA","CODICEISTAT","WHERE CODICEISTAT = '" + p_class.CODICEATTIVITA + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException( p_class, "ISTANZEATTIVITA.CODICEATTIVITA non trovato nella tabella ATTIVITA"));
				}
			}
			#endregion
		}

        public void DeleteByCodiceSettore(string idcomune, string codiceistanza, string codicesettore)
        {

            if ( String.IsNullOrEmpty( idcomune ) ) 
                throw new Exception("Impossibile richiamare il metodo DeleteBySettore del manager IstanzeAttivitaMgr senza specificare idcomune");
            
            if ( String.IsNullOrEmpty( codiceistanza ) ) 
                throw new Exception("Impossibile richiamare il metodo DeleteBySettore del manager IstanzeAttivitaMgr senza specificare codiceistanza");

            if ( String.IsNullOrEmpty( codicesettore ) ) 
                throw new Exception("Impossibile richiamare il metodo DeleteBySettore del manager IstanzeAttivitaMgr senza specificare codicesettore");

            IstanzeAttivita filtro = new IstanzeAttivita();
            filtro.IDCOMUNE = idcomune;
            filtro.CODICEISTANZA = codiceistanza;
            filtro.OthersTables.Add("ATTIVITA");
            filtro.OthersWhereClause.Add("ATTIVITA.IDCOMUNE = ISTANZEATTIVITA.IDCOMUNE");
            filtro.OthersWhereClause.Add("ATTIVITA.CODICEISTAT = ISTANZEATTIVITA.CODICEATTIVITA");
            filtro.OthersWhereClause.Add("ATTIVITA.CODICESETTORE = '" + codicesettore.Replace("'", "''") + "'");

            
            List<IstanzeAttivita> list = GetList(filtro);
            foreach (IstanzeAttivita ia in list)
            {
                Delete(ia);
            }
        }
	}
}