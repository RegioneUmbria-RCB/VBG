using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.Logic.ExecuteQuery;
using log4net;
using Ninject;
using Ninject.Web;
using PersonalLib2.Data;
using System;
using System.ComponentModel;
using System.Data;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Descrizione di riepilogo per ExecuteQuery.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class CExecuteQuery : WebServiceBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CExecuteQuery));

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        private const int ERR_SELECT_FAILED = 58001;
        private const int ERR_DEL_INS_UPD_FAILED = 58002;

        public CExecuteQuery()
        {
            //CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
            this.InitializeComponent();
        }

        #region Codice generato da Progettazione componenti

        //Richiesto da Progettazione servizi Web 
        private readonly IContainer components = null;

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
            if (disposing && this.components != null)
            {
                this.components.Dispose();
            }
            base.Dispose(disposing);
        }

        #endregion


        [WebMethod(Description = "Metodo usato per effettuare da FO select sul database", EnableSession = false)]
        public DataSet ExecuteQuery(string sToken, string sQuery)
        {
            this._log.Debug($"EXECUTE_QUERY ({sToken}): {sQuery}");

            DataSet ds = null;
            CExecuteQueryMgr pExecuteQuery = new CExecuteQueryMgr();
            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = this._authenticationManager.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                DataBase db = authInfo.CreateDatabase();
                pExecuteQuery.Database = db;
                pExecuteQuery.Query = sQuery;
                ds = pExecuteQuery.ExecuteQuery();
            }
            catch (Exception ex)
            {
                this._log.Error($"EXECUTE_QUERY: {ex}");

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
                authInfo = this._authenticationManager.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                DataBase db = authInfo.CreateDatabase();
                pExecuteQuery.Database = db;
                pExecuteQuery.Query = sQuery;
                iResult = pExecuteQuery.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                this._log.Error($"ExecuteNonQuery: {ex}");

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

