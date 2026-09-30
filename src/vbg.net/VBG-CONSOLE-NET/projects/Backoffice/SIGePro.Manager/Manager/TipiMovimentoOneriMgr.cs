using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using System;
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
    public class TipiMovimentoOneriMgr : BaseManager
    {

        public TipiMovimentoOneriMgr(DataBase dataBase) : base(dataBase) { }


        #region Metodi per l'accesso di base al DB

        public TipiMovimentoOneri GetById(String pIDCOMUNE, String pTIPOMOVIMENTO, String pFK_COID, String pCODICECOMPORTAMENTO)
        {
            TipiMovimentoOneri retVal = new TipiMovimentoOneri();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.TIPOMOVIMENTO = pTIPOMOVIMENTO;
            retVal.FK_COID = pFK_COID;
            retVal.CODICECOMPORTAMENTO = pCODICECOMPORTAMENTO;


            List<TipiMovimentoOneri> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiMovimentoOneri;

            return null;
        }


        private TipiMovimentoOneri DataIntegrations(TipiMovimentoOneri p_class)
        {
            TipiMovimentoOneri retVal = (TipiMovimentoOneri)p_class.Clone();

            return retVal;
        }

        private void Validate(TipiMovimentoOneri p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            //ForeignValidate( p_class );
        }

        private void ForeignValidate(TipiMovimentoOneri p_class)
        {
            #region TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO
            if (!this.IsStringEmpty(p_class.CODICECOMPORTAMENTO))
            {
                if (this.recordCount("ONERICOMPORTAMENTO", "CODICECOMPORTAMENTO", "WHERE CODICECOMPORTAMENTO = " + p_class.CODICECOMPORTAMENTO) == 0)
                {
                    throw (new RecordNotfoundException("TIPIMOVIMENTOONERI.CODICECOMPORTAMENTO (" + p_class.CODICECOMPORTAMENTO + ") non trovato nella tabella ONERICOMPORTAMENTO"));
                }
            }
            #endregion

            #region TIPIMOVIMENTOONERI.FK_COID
            if (!this.IsStringEmpty(p_class.FK_COID))
            {
                if (this.recordCount("TIPICAUSALIONERI", "CO_ID", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CO_ID = " + p_class.FK_COID) == 0)
                {
                    throw (new RecordNotfoundException("TIPIMOVIMENTOONERI.FK_COID (" + p_class.FK_COID + ") non trovato nella tabella TIPICAUSALIONERI"));
                }
            }
            #endregion

            #region TIPIMOVIMENTOONERI.TIPOMOVIMENTO
            if (!this.IsStringEmpty(p_class.TIPOMOVIMENTO))
            {
                if (this.recordCount("TIPIMOVIMENTO", "TIPOMOVIMENTO", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "'") == 0)
                {
                    throw (new RecordNotfoundException("TIPIMOVIMENTOONERI.TIPOMOVIMENTO (" + p_class.TIPOMOVIMENTO + ") non trovato nella tabella TIPIMOVIMENTO"));
                }
            }
            #endregion
        }

        public TipiMovimentoOneri Insert(TipiMovimentoOneri p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        #endregion

        public List<TipiCausaliOneri> GetOneriDaTipoMovimento(string idComune, string tipoMovimento, CodiceComportamentoOneriEnum comportamento)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "idComune", "tipoMovimento", "codiceComportamento");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("tipoMovimento", tipoMovimento));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceComportamento", (int)comportamento));

                    return this.db.GetClassList<TipiCausaliOneri>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }
    }
}
