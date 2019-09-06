using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiProcedureAvvioMgr.\n	/// </summary>
	public class TipiProcedureAvvioMgr: BaseManager
{

		public TipiProcedureAvvioMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

        public TipiProcedureAvvio GetDefault(String pIDCOMUNE, String pCODICEPROCEDURA)
        {
            TipiProcedureAvvio p_class = new TipiProcedureAvvio();

            p_class.IDCOMUNE = pIDCOMUNE;
            p_class.CODICEPROCEDURA = pCODICEPROCEDURA;
            p_class.DEFAULTSN = "1";

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiProcedureAvvio;

            return null;
        }
        
		public TipiProcedureAvvio GetById(String pCODICEPROCEDURA, String pTIPOMOVIMENTO, String pIDCOMUNE)
		{
			TipiProcedureAvvio retVal = new TipiProcedureAvvio();
			
			retVal.CODICEPROCEDURA = pCODICEPROCEDURA;
			retVal.TIPOMOVIMENTO = pTIPOMOVIMENTO;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiProcedureAvvio;
			
			return null;
		}

		public string TIPOMOVIMENTO( string pCODICEPROCEDURA, string pIDCOMUNE, string pDEFAULTSN )
		{
			string retVal = String.Empty;

			TipiProcedureAvvio p_class = new TipiProcedureAvvio();

			p_class.IDCOMUNE = pIDCOMUNE;
			p_class.DEFAULTSN = pDEFAULTSN;
			p_class.CODICEPROCEDURA = pCODICEPROCEDURA;


			bool closeCnn = false;

			if (db.Connection.State == ConnectionState.Closed)
			{
				closeCnn = true;
				db.Connection.Open();
			}
			try
			{
				using (IDataReader p_reader = db.CreateCommand(p_class).ExecuteReader())
				{
					if (p_reader.Read())
						retVal = (p_reader["TIPOMOVIMENTO"] != DBNull.Value) ? p_reader["TIPOMOVIMENTO"].ToString() : String.Empty;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}


			return retVal;
		}

		public TipiProcedureAvvio Insert( TipiProcedureAvvio p_class )
		{
			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		private void Validate(TipiProcedureAvvio p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiProcedureAvvio p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiProcedureAvvio p_class, TipiProcedureAvvio p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

	#endregion
	}
}
