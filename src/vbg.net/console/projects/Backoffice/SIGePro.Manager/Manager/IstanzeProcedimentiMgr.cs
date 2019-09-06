using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager 
{ 	
	public class IstanzeProcedimentiMgr: BaseManager
	{

		public IstanzeProcedimentiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public IstanzeProcedimenti GetById(String pCODICEISTANZA, String pCODICEINVENTARIO, String pIDCOMUNE)
		{
			IstanzeProcedimenti retVal = new IstanzeProcedimenti();
			
			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.CODICEINVENTARIO = pCODICEINVENTARIO;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeProcedimenti;
			
			return null;
		}

		public List<IstanzeProcedimenti> GetList(IstanzeProcedimenti p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public List<IstanzeProcedimenti> GetList(IstanzeProcedimenti p_class, IstanzeProcedimenti p_cmpClass )
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeProcedimenti>();
		}

		public void Delete(IstanzeProcedimenti cls)
		{
			VerificaRecordCorrelati(cls);
			EffettuaCancellazioneaCascata(cls);
			db.Delete(cls);
		}

		private void EffettuaCancellazioneaCascata(IstanzeProcedimenti cls)
		{
			IstanzeAllegatiMgr istanzeAllegatiMgr = new IstanzeAllegatiMgr(db);
			IstanzeAllegati filtroIstanzeAllegati = new IstanzeAllegati();
			filtroIstanzeAllegati.IDCOMUNE = cls.IDCOMUNE;
			filtroIstanzeAllegati.CODICEISTANZA = cls.CODICEISTANZA;
			filtroIstanzeAllegati.CODICEINVENTARIO = cls.CODICEINVENTARIO;

			List<IstanzeAllegati> lAllegatiA = istanzeAllegatiMgr.GetList(filtroIstanzeAllegati);
			foreach (IstanzeAllegati allegato in lAllegatiA)
			{
				istanzeAllegatiMgr.Delete(allegato);
			}
		}

		private void VerificaRecordCorrelati(IstanzeProcedimenti cls)
		{

		}


		public IstanzeProcedimenti Insert( IstanzeProcedimenti p_class )
		{

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class );

            p_class = ChildDataIntegrations(p_class);

            ChildInsert(p_class);


			return p_class;
		}

        private IstanzeProcedimenti ChildDataIntegrations(IstanzeProcedimenti p_class)
        {
            IstanzeProcedimenti retVal = (IstanzeProcedimenti)p_class.Clone();

            foreach (IstanzeAllegati ist_all in retVal.IstanzeAllegati)
            {
                if ( string.IsNullOrEmpty(ist_all.IDCOMUNE))
                    ist_all.IDCOMUNE = retVal.IDCOMUNE;
                else if (ist_all.IDCOMUNE != ist_all.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("ISTANZEALLEGATI.IDCOMUNE diverso da ISTANZEPROCEDIMENTI.IDCOMUNE");

                if (string.IsNullOrEmpty(ist_all.CODICEISTANZA))
                    ist_all.CODICEISTANZA = retVal.CODICEISTANZA;
                else if (ist_all.CODICEISTANZA != retVal.CODICEISTANZA)
                    throw new Exceptions.IncongruentDataException("ISTANZEALLEGATI.CODICEISTANZA diverso da ISTANZEPROCEDIMENTI.CODICEISTANZA");

                if (string.IsNullOrEmpty(ist_all.CODICEINVENTARIO))
                    ist_all.CODICEINVENTARIO = retVal.CODICEINVENTARIO;
                else if (ist_all.CODICEINVENTARIO != retVal.CODICEINVENTARIO)
                    throw new Exceptions.IncongruentDataException("ISTANZEALLEGATI.CODICEINVENTARIO diverso da ISTANZEPROCEDIMENTI.CODICEINVENTARIO");
            }

            return retVal;
        }

        private void ChildInsert(IstanzeProcedimenti p_class)
        {
            foreach (IstanzeAllegati allegato in p_class.IstanzeAllegati)
            {
                IstanzeAllegatiMgr pManager = new IstanzeAllegatiMgr(this.db);
                pManager.Insert(allegato);
            }
        }
	
		public IstanzeProcedimenti Update( IstanzeProcedimenti p_class )
		{
			
			db.Update( p_class );

			return p_class;
		}

		private void Validate(IstanzeProcedimenti p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{

			if ( IsStringEmpty( p_class.PERPROVVEDIMENTO ) )
				p_class.PERPROVVEDIMENTO = "1";
			
			if(p_class.PERPROVVEDIMENTO != "0" && p_class.PERPROVVEDIMENTO != "1")
			{
				throw( new TypeMismatchException("Impossibile inserire " + p_class.PERPROVVEDIMENTO + " in ISTANZEPROCEDIMENTI.PERPROVVEDIMENTO "));
			}

			if ( IsStringEmpty( p_class.ACQUISITO ) )
				p_class.ACQUISITO = "0";

			RequiredFieldValidate( p_class , ambitoValidazione);
			ForeignValidate( p_class );

		}
		
		private void ForeignValidate ( IstanzeProcedimenti p_class )
		{
			#region ISTANZEPROCEDIMENTI.CODICEISTANZA
			
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = '" + p_class.CODICEISTANZA + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZEPROCEDIMENTI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}

			#endregion

			#region ISTANZEPROCEDIMENTI.CODICEINVENTARIO		
			
			if ( ! IsStringEmpty( p_class.CODICEINVENTARIO ) )
			{
				if (  this.recordCount( "INVENTARIOPROCEDIMENTI","CODICEINVENTARIO","WHERE CODICEINVENTARIO = '" + p_class.CODICEINVENTARIO + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("INVENTARIOPROCEDIMENTI.CODICEINVENTARIO non trovato nella tabella INVENTARIOPROCEDIMENTI"));
				}
			}

			#endregion
		
		}
		
		#endregion

		public List<int> GetListaAmministrazioniCoinvolte(string idComune, int codiceistanza)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT DISTINCT 
									inventarioprocedimenti.amministrazione 
								FROM 
									istanzeprocedimenti,
									inventarioprocedimenti 
								WHERE 
									istanzeprocedimenti.idcomune = {0} AND 
									istanzeprocedimenti.codiceistanza = {1} AND 
									inventarioprocedimenti.idcomune=istanzeprocedimenti.idcomune AND 
									inventarioprocedimenti.codiceinventario <> 0 AND 
									inventarioprocedimenti.codiceinventario = istanzeprocedimenti.codiceinventario AND 
									InventarioProcedimenti.disabilitato = 0";

				sql = PreparaQueryParametrica(sql, "idComune", "codiceistanza");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceistanza", codiceistanza));

					using (IDataReader dr = cmd.ExecuteReader())
					{
						var rVal = new List<int>();

						while (dr.Read())
							rVal.Add( Convert.ToInt32(dr[0]));

						return rVal;
					}
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