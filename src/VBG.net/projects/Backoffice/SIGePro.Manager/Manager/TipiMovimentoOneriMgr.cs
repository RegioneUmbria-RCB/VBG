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
 {
	public enum CodiceComportamentoOneriEnum
	{
		ImpostaScadenza = 1,
		RichiedePagamento = 2,
		InserisceOnere = 3,
		SpostaImporto1SuImporto2 = 4
	}
	
	
	///<summary>
	/// Descrizione di riepilogo per TipiMovimentoOneriMgr.\n	/// </summary>
	public class TipiMovimentoOneriMgr: BaseManager
{

		public TipiMovimentoOneriMgr( DataBase dataBase ) : base( dataBase ) {}


		#region Metodi per l'accesso di base al DB

		public TipiMovimentoOneri GetById(String pIDCOMUNE, String pTIPOMOVIMENTO, String pFK_COID, String pCODICECOMPORTAMENTO)
		{
			TipiMovimentoOneri retVal = new TipiMovimentoOneri();
						retVal.IDCOMUNE = pIDCOMUNE;
			retVal.TIPOMOVIMENTO = pTIPOMOVIMENTO;
			retVal.FK_COID = pFK_COID;
			retVal.CODICECOMPORTAMENTO = pCODICECOMPORTAMENTO;
		

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiMovimentoOneri;
			
			return null;
		}


		private TipiMovimentoOneri DataIntegrations ( TipiMovimentoOneri p_class )
		{
			TipiMovimentoOneri retVal = ( TipiMovimentoOneri ) p_class.Clone();

			return retVal;
		}

		private void Validate(TipiMovimentoOneri p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			//ForeignValidate( p_class );
		}

		private void ForeignValidate ( TipiMovimentoOneri p_class )
		{
			#region TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO
			if ( ! IsStringEmpty( p_class.CODICECOMPORTAMENTO ) )
			{
				if ( this.recordCount("ONERICOMPORTAMENTO","CODICECOMPORTAMENTO","WHERE CODICECOMPORTAMENTO = " + p_class.CODICECOMPORTAMENTO ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO (" + p_class.CODICECOMPORTAMENTO + ") non trovato nella tabella ONERICOMPORTAMENTO"));
				}
			}
			#endregion

			#region TIPIMOVIMENTOONERI.FK_COID
			if ( ! IsStringEmpty( p_class.FK_COID ) )
			{
				if ( this.recordCount("TIPICAUSALIONERI","CO_ID","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CO_ID = " + p_class.FK_COID ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTOONERI.FK_COID (" + p_class.FK_COID + ") non trovato nella tabella TIPICAUSALIONERI"));
				}
			}
			#endregion

			#region TIPIMOVIMENTOONERI.TIPOMOVIMENTO
			if ( ! IsStringEmpty( p_class.TIPOMOVIMENTO ) )
			{
				if ( this.recordCount("TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "'" ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTOONERI.TIPOMOVIMENTO (" + p_class.TIPOMOVIMENTO + ") non trovato nella tabella TIPIMOVIMENTO"));
				}
			}
			#endregion
		}

		public TipiMovimentoOneri Insert( TipiMovimentoOneri p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiMovimentoOneri p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiMovimentoOneri p_class, TipiMovimentoOneri p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

#endregion

		public List<TipiCausaliOneri> GetOneriDaTipoMovimento(string idComune, string tipoMovimento, CodiceComportamentoOneriEnum comportamento)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT 
									TIPICAUSALIONERI.* 
								FROM 
									TIPIMOVIMENTOONERI, 
									TIPICAUSALIONERI 
								WHERE 
									TIPICAUSALIONERI.IDCOMUNE = TIPIMOVIMENTOONERI.IDCOMUNE AND 
									TIPICAUSALIONERI.CO_ID = TIPIMOVIMENTOONERI.FK_COID AND 
									TIPIMOVIMENTOONERI.IDCOMUNE = {0}  AND 
									TIPIMOVIMENTOONERI.TIPOMOVIMENTO = {1} AND 
									TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO = {2}";

				sql = PreparaQueryParametrica(sql, "idComune", "tipoMovimento", "codiceComportamento");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add( db.CreateParameter( "idComune" , idComune ));
					cmd.Parameters.Add( db.CreateParameter( "tipoMovimento" , tipoMovimento ));
					cmd.Parameters.Add( db.CreateParameter( "codiceComportamento" , (int)comportamento ));

					return db.GetClassList<TipiCausaliOneri>(cmd, false);
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
