using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.Data;


namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per TipiMovimento_DisMgr.\n	/// </summary>
	public class TipiMovimento_DisMgr: BaseManager
	{
		public TipiMovimento_DisMgr( DataBase dataBase ) : base( dataBase ) {}

		private TipiMovimento_Dis DataIntegrations ( TipiMovimento_Dis p_class )
		{
			TipiMovimento_Dis retVal = ( TipiMovimento_Dis ) p_class.Clone();

			if ( StringChecker.IsStringEmpty( retVal.CODICEAMMINISTRAZIONE ) )
				retVal.CODICEAMMINISTRAZIONE = "0";

			if ( StringChecker.IsStringEmpty( retVal.CODICEINVENTARIO ) )
				retVal.CODICEINVENTARIO = "0";

			return retVal;
		}

		private void Validate( TipiMovimento_Dis p_class ,  Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{			
			RequiredFieldValidate( p_class , ambitoValidazione);

			//ForeignValidate( p_class );
		}

		private void ForeignValidate ( TipiMovimento_Dis p_class )
		{
			#region TIPIMOVIMENTO_DIS.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEISTANZA = " + p_class.CODICEISTANZA ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO_DIS.CODICEISTANZA (" + p_class.CODICEISTANZA + ") non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region TIPIMOVIMENTO_DIS.TIPOMOVIMENTO
			if ( ! IsStringEmpty( p_class.TIPOMOVIMENTO ) )
			{
				if ( this.recordCount("TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "'" ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO_DIS.TIPOMOVIMENTO (" + p_class.TIPOMOVIMENTO + ") non trovato nella tabella TIPIMOVIMENTO"));
				}
			}
			#endregion

			#region TIPIMOVIMENTO_DIS.CODICEINVENTARIO
			if ( ! IsStringEmpty( p_class.CODICEINVENTARIO ) )
			{
				if ( this.recordCount("INVENTARIOPROCEDIMENTI","CODICEINVENTARIO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEINVENTARIO = " + p_class.CODICEINVENTARIO ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO_DIS.CODICEINVENTARIO (" + p_class.CODICEINVENTARIO + ") non trovato nella tabella INVENTARIOPROCEDIMENTI"));
				}
			}
			#endregion

			#region TIPIMOVIMENTO_DIS.CODICEAMMINISTRAZIONE
			if ( ! IsStringEmpty( p_class.CODICEAMMINISTRAZIONE ) )
			{
				if ( this.recordCount("AMMINISTRAZIONI","CODICEAMMINISTRAZIONE","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEAMMINISTRAZIONE = " + p_class.CODICEAMMINISTRAZIONE ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO_DIS.CODICEAMMINISTRAZIONE (" + p_class.CODICEAMMINISTRAZIONE + ") non trovato nella tabella AMMINISTRAZIONI"));
				}
			}
			#endregion

			#region TIPIMOVIMENTO_DIS.CODAMMRICHIEDENTE
			if ( ! IsStringEmpty( p_class.CODAMMRICHIEDENTE ) )
			{
				if ( this.recordCount("AMMINISTRAZIONI","CODICEAMMINISTRAZIONE","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEAMMINISTRAZIONE = " + p_class.CODAMMRICHIEDENTE ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO_DIS.CODAMMRICHIEDENTE (" + p_class.CODAMMRICHIEDENTE + ") non trovato nella tabella AMMINISTRAZIONI"));
				}
			}
			#endregion
		}

		public TipiMovimento_Dis Insert( TipiMovimento_Dis p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

        public List<TipiMovimento_Dis> GetList(TipiMovimento_Dis p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<TipiMovimento_Dis> GetList(TipiMovimento_Dis p_class, TipiMovimento_Dis p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<TipiMovimento_Dis>();
		}

		public void DeleteDaCodiceIstanza(string idComune, int codiceIstanza)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"DELETE FROM TIPIMOVIMENTO_DIS 
								WHERE 
								  TIPIMOVIMENTO_DIS.IDCOMUNE = {0} AND 
								  CODICEISTANZA = {1}";

				sql = PreparaQueryParametrica(sql, "IdComune", "CodiceIstanza" );

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("CodiceIstanza", codiceIstanza));

					cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public void RimuoviTipoMovimento(string idComune, int codiceIstanza, string tipoMovimento, int codiceInventario, int codiceAmministrazione)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"DELETE FROM TIPIMOVIMENTO_DIS 
								WHERE 
								  TIPIMOVIMENTO_DIS.IDCOMUNE = {0} AND 
								  CODICEISTANZA = {1} AND 
								  TIPOMOVIMENTO = {2} AND 
								  CODICEINVENTARIO = {3} AND 
								  CODICEAMMINISTRAZIONE = {4}";

				sql = PreparaQueryParametrica(sql, "IdComune", "CodiceIstanza", 
												"TipoMovimento", "CodiceInventario", 
												"CodiceAmministrazione");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add( db.CreateParameter("IdComune",idComune) );
					cmd.Parameters.Add( db.CreateParameter("CodiceIstanza",codiceIstanza) );
					cmd.Parameters.Add( db.CreateParameter("TipoMovimento",tipoMovimento) );
					cmd.Parameters.Add( db.CreateParameter("CodiceInventario",codiceInventario) );
					cmd.Parameters.Add( db.CreateParameter("CodiceAmministrazione",codiceAmministrazione) );

					cmd.ExecuteNonQuery();
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}
	}
}
