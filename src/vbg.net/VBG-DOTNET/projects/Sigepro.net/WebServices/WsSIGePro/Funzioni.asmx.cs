using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.ComponentModel;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Descrizione di riepilogo per Funzioni.
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class Funzioni : SigeproWebService
    {
        public Funzioni()
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

        [WebMethod(Description = "Metodo usato per visualizzare le informazioni relative allo stato di una pratica", EnableSession = false)]
        public Init.SIGePro.Data.RetStatoPratica StatoPratica(string sToken, string codiceIstanza, bool retStatoIter)
        {
            DataBase db = null;
            try
            {
                AuthenticationInfo authInfo = this.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                db = authInfo.CreateDatabase();

                Init.SIGePro.Data.Istanze ist = new Init.SIGePro.Data.Istanze();
                ist.IDCOMUNE = authInfo.IdComune;
                ist.CODICEISTANZA = codiceIstanza;

                IstanzeMgr istMgr = new IstanzeMgr(db);
                return istMgr.StatoPratica(ist, retStatoIter);
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                if (db != null && db.Connection != null)
                    db.Connection.Close();
            }
        }
    }
}
