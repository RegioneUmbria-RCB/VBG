using System;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Authentication;
using PersonalLib2.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.Logic.ExecuteQuery;
using Init.SIGePro.Utils;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Descrizione di riepilogo per ExecuteQuery.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class CExecuteQuery : System.Web.Services.WebService
    {
        const int ERR_SELECT_FAILED = 58001;
        const int ERR_DEL_INS_UPD_FAILED = 58002;

        public CExecuteQuery()
        {
            //CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
            InitializeComponent();
        }

        #region Codice generato da Progettazione componenti

        //Richiesto da Progettazione servizi Web 
        private IContainer components = null;

        /// <summary>
        /// Metodo necessario per il supporto della finestra di progettazione. Non modificare
        /// il contenuto del metodo con l'editor di codice.
        /// </summary>
        private void InitializeComponent()
        {
        }

        /// <summary>
        /// Pulire le risorse in uso.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing && components != null)
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #endregion


        [WebMethod(Description = "Metodo usato per effettuare da FO select sul database", EnableSession = false)]
        public DataSet ExecuteQuery(string sToken, string sQuery)
        {
            DataSet ds = null;
            CExecuteQueryMgr pExecuteQuery = new CExecuteQueryMgr();
            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = AuthenticationManager.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                DataBase db = authInfo.CreateDatabase();
                pExecuteQuery.Database = db;
                pExecuteQuery.Query = sQuery;
                ds = pExecuteQuery.ExecuteQuery();
            }
            catch (Exception ex)
            {
                if (authInfo != null)
                    Logger.LogEvent(authInfo, "EXECUTE_QUERY", ex.ToString(), ERR_SELECT_FAILED.ToString());

                throw ex;
            }
            finally
            {
                if (pExecuteQuery.Database != null)
                    pExecuteQuery.Database.Dispose();
            }

            return ds;
        }

        [WebMethod(Description = "Metodo usato per effettuare da FO insert/update/delete sul database", EnableSession = false)]
        public int ExecuteNonQuery(string sToken, string sQuery)
        {
            int iResult;
            CExecuteQueryMgr pExecuteQuery = new CExecuteQueryMgr();
            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = AuthenticationManager.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                DataBase db = authInfo.CreateDatabase();
                pExecuteQuery.Database = db;
                pExecuteQuery.Query = sQuery;
                iResult = pExecuteQuery.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                if (authInfo != null)
                    Logger.LogEvent(authInfo, "EXECUTE_NONQUERY", ex.ToString(), ERR_DEL_INS_UPD_FAILED.ToString());

                throw ex;
            }
            finally
            {
                if (pExecuteQuery.Database != null)
                    pExecuteQuery.Database.Dispose();
            }

            return iResult;
        }
    }
}

