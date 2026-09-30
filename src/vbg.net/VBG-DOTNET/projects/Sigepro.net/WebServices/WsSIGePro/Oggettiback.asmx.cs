using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using log4net;
using Ninject;
using Ninject.Web;
using PersonalLib2.Data;
using System;
using System.ComponentModel;
using System.IO;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// ATTENZIONE!!! Utilizzato da pagine asp del backoffice. Finché la generazione di lettere tipo
    /// asp non verrà rimossa non è possibile eliminare questo webservice.
    /// 
    /// 
    /// 
    /// Gestisce la lettura di oggetti binari dal database
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class Oggettiback : WebServiceBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(Oggetti));

        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        private const int ERR_UPLOAD_FAILED = 58001;

        public class OggettoBackoffice
        {
            public string MimeType { get; set; }

            public byte[] BinaryData { get; set; }

            public string FileName { get; set; }

            public OggettoBackoffice()
            {
            }

            public OggettoBackoffice(string mimeType, string fileName, byte[] binaryData)
            {
                this.MimeType = mimeType;
                this.FileName = fileName;
                this.BinaryData = binaryData;
            }

        }

        public Oggettiback()
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

        /// <summary>
        /// ATTENZIONE!!! Utilizzato da pagine asp del backoffice. Finché la generazione di lettere tipo
        /// asp non verrà rimossa non è possibile eliminare questo webservice.
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceOggetto"></param>
        /// <returns></returns>
        /// <exception cref="InvalidTokenException"></exception>
        [WebMethod(Description = "Permette di leggere un oggetto binario salvato all'interno di SIGePro")]
        public OggettoBackoffice GetOggetto(string token, string codiceOggetto)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);



            using (var db = authInfo.CreateDatabase())
            {
                var codiceOggettoInt = Convert.ToInt32(codiceOggetto);

                return this.LeggiOggettoBackoffice(db, authInfo.IdComune, Convert.ToInt32(codiceOggetto));
            }
        }

        /// <summary>
        /// ATTENZIONE!!! Utilizzato da pagine asp del backoffice. Finché la generazione di lettere tipo
        /// asp non verrà rimossa non è possibile eliminare questo webservice.
        /// </summary>
        /// <param name="token"></param>
        /// <param name="codiceOggetto"></param>
        /// <returns></returns>
        /// <exception cref="InvalidTokenException"></exception>
        public string GetNomeFile(string token, int codiceOggetto)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            using (var db = authInfo.CreateDatabase())
            {
                var codiceOggettoInt = Convert.ToInt32(codiceOggetto);

                return new OggettiMgr(db).GetNomeFile(authInfo.IdComune, codiceOggetto);
            }
        }

        private OggettoBackoffice LeggiOggettoBackoffice(DataBase db, string idComune, int codiceOggetto)
        {
            var oggettiManager = new OggettiMgr(db);
            var oggetto = oggettiManager.GetById(idComune, codiceOggetto);

            if (oggetto == null)
                throw new ArgumentException("Codice oggetto non valido: " + codiceOggetto);

            var ctType = oggettiManager.GetContentType(oggetto);

            if (String.IsNullOrEmpty(ctType))
                throw new InvalidOperationException("Content type non trovato per il file " + oggetto.NOMEFILE + " (IdOggetto=" + oggetto.CODICEOGGETTO + ", IdComune=" + oggetto.IDCOMUNE + ")");

            return new OggettoBackoffice(ctType, oggetto.NOMEFILE, oggetto.OGGETTO);
        }

        /// <summary>
        /// ATTENZIONE!!! Utilizzato da pagine asp del backoffice. Finché la generazione di lettere tipo
        /// asp non verrà rimossa non è possibile eliminare questo webservice.
        /// </summary>
        /// <param name="sToken"></param>
        /// <param name="sFileName"></param>
        /// <param name="aFile"></param>
        /// <returns></returns>
        [WebMethod(Description = "Metodo usato per caricare un oggetto binario nel db", EnableSession = false)]
        public int UploadOggetto(string sToken, string sFileName, byte[] aFile)
        {
            DataBase db = null;
            AuthenticationInfo authInfo = null;
            try
            {
                authInfo = this._authenticationManager.CheckToken(sToken);

                if (authInfo == null)
                    throw new InvalidTokenException(sToken);

                db = authInfo.CreateDatabase();

                var obj = new Init.SIGePro.Data.Oggetti();
                obj.IDCOMUNE = authInfo.IdComune;
                obj.NOMEFILE = Path.GetFileName(sFileName);
                obj.OGGETTO = aFile;

                var objMgr = new OggettiMgr(db);
                return int.Parse(objMgr.Insert(obj).CODICEOGGETTO);
            }
            catch (Exception ex)
            {
                this._log.Error($"UPLOAD_OGGETTO: {ex}");

                throw;
            }
            finally
            {
                if (db != null && db.Connection != null)
                    db.Connection.Close();
            }
        }


        /// <summary>
        /// ATTENZIONE!!! Utilizzato da pagine asp del backoffice. Finché la generazione di lettere tipo
        /// asp non verrà rimossa non è possibile eliminare questo webservice.
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idRisorsaOggetto"></param>
        /// <returns></returns>
        /// <exception cref="InvalidTokenException"></exception>
        /// <exception cref="ArgumentException"></exception>
        [WebMethod(Description = "Metodo usato per leggere un oggetto o un'immagine del frontoffice", EnableSession = false)]
        public OggettoBackoffice GetRisorsaFrontoffice(string token, string idRisorsaOggetto)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            using (var db = authInfo.CreateDatabase())
            {
                var sfoMgr = new StiliFrontOfficeMgr(db);
                var codiceOggetto = sfoMgr.GetCodiceOggettoRisorsaFrontoffice(authInfo.IdComune, idRisorsaOggetto);

                if (!codiceOggetto.HasValue)
                    throw new ArgumentException("Id risorsa " + idRisorsaOggetto + " non trovato");

                return this.LeggiOggettoBackoffice(db, authInfo.IdComune, codiceOggetto.Value);
            }
        }
    }


}