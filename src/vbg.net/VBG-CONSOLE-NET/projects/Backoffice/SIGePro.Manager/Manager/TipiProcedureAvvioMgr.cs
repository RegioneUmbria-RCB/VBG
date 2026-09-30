using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiProcedureAvvioMgr.\n	/// </summary>
    public class TipiProcedureAvvioMgr : BaseManager
    {

        public TipiProcedureAvvioMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TipiProcedureAvvio GetDefault(String pIDCOMUNE, String pCODICEPROCEDURA)
        {
            TipiProcedureAvvio p_class = new TipiProcedureAvvio();

            p_class.IDCOMUNE = pIDCOMUNE;
            p_class.CODICEPROCEDURA = pCODICEPROCEDURA;
            p_class.DEFAULTSN = "1";

            List<TipiProcedureAvvio> mydc = this.db.GetClassList(p_class, true);
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

            List<TipiProcedureAvvio> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiProcedureAvvio;

            return null;
        }

        public string TIPOMOVIMENTO(string pCODICEPROCEDURA, string pIDCOMUNE, string pDEFAULTSN)
        {
            string retVal = String.Empty;

            TipiProcedureAvvio p_class = new TipiProcedureAvvio();

            p_class.IDCOMUNE = pIDCOMUNE;
            p_class.DEFAULTSN = pDEFAULTSN;
            p_class.CODICEPROCEDURA = pCODICEPROCEDURA;


            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }
            try
            {
                using (IDataReader p_reader = this.db.CreateCommand(p_class).ExecuteReader())
                {
                    if (p_reader.Read())
                        retVal = (p_reader["TIPOMOVIMENTO"] != DBNull.Value) ? p_reader["TIPOMOVIMENTO"].ToString() : String.Empty;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }


            return retVal;
        }

        public TipiProcedureAvvio Insert(TipiProcedureAvvio p_class)
        {
            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private void Validate(TipiProcedureAvvio p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);
        }

        #endregion
    }
}
