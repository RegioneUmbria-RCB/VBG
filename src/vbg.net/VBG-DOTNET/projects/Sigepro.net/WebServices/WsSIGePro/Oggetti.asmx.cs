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
using System.Linq;
using System.Web.Services;

namespace SIGePro.Net.WebServices.WsSIGePro
{
    /// <summary>
    /// Gestisce la lettura di oggetti binari dal database
    /// </summary>
    [WebService(Namespace = "http://init.sigepro.it")]
    public class Oggetti : WebServiceBase
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

        public Oggetti()
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
        /// Legge un oggetto binario dal database.
        /// </summary>
        /// <param name="token">Token ottenuto tramite autenticazione</param>
        /// <param name="codiceoggetto">Id dell'oggetto da leggere</param>
        /// <returns></returns>
        [WebMethod(Description = "Permette di leggere un oggetto binario salvato all'interno di SIGePro")]
        public OggettoBackoffice GetOggetto(string token, string codiceOggetto)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);



            using (var db = authInfo.CreateDatabase())
            {
                var codiceOggettoInt = Convert.ToInt32(codiceOggetto);
                var isStileFrontoffice = this.PuoEssereScaricato(db, authInfo.IdComune, codiceOggettoInt);

                if (!isStileFrontoffice)
                {
                    return null;
                }

                return this.LeggiOggettoBackoffice(db, authInfo.IdComune, Convert.ToInt32(codiceOggetto));
            }
        }

        private bool PuoEssereScaricato(DataBase db, string idComune, int codiceOggettoInt)
        {
            if (this.IsModulisticaInternal(db, idComune, codiceOggettoInt))
            {
                return true;
            }

            return this.IsStileFrontofficeInternal(db, idComune, codiceOggettoInt);
        }

        private bool IsModulisticaInternal(DataBase db, string idComune, int codiceOggettoInt)
        {
            FormattableString sql = $"select count(*) from modelli where idcomune={idComune} and codiceoggetto={codiceOggettoInt}";

            var count = db.ExecuteScalar(sql, 0);

            return count > 0;
        }

        private bool IsStileFrontofficeInternal(DataBase db, string idComune, int codiceOggettoInt)
        {
            FormattableString sql = $"select * from stilifrontoffice where idcomune={idComune}";

            var oggettiFo = db.ExecuteReader(sql, dr =>
            {
                var l = new int[dr.FieldCount];

                for (var i = 0; i < dr.FieldCount; i++)
                {
                    if (String.Compare(dr.GetName(i), "idcomune", true) == 0)
                    {
                        continue;
                    }

                    if (!dr.IsDBNull(i))
                    {
                        l[i] = dr.GetInt32(i);
                    }
                }

                return l;
            }).FirstOrDefault();

            if (oggettiFo == null)
            {
                return false;
            }

            return oggettiFo.Contains(codiceOggettoInt);
        }

        public string GetNomeFile(string token, int codiceOggetto)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            if (authInfo == null)
                throw new InvalidTokenException(token);

            using (var db = authInfo.CreateDatabase())
            {
                var codiceOggettoInt = Convert.ToInt32(codiceOggetto);
                var isStileFrontoffice = this.PuoEssereScaricato(db, authInfo.IdComune, codiceOggettoInt);

                if (!isStileFrontoffice)
                {
                    return null;
                }

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
        /// Carica un oggetto binario nel database
        /// </summary>
        /// <param name="sToken">Token ottenuto tramite autenticazione</param>
        /// <param name="sFileName">Nome del file da caricare</param>
        /// <param name="aFile">Contenuto del file</param>
        /// <returns></returns>
        [WebMethod(Description = "Metodo usato per caricare un oggetto binario nel db", EnableSession = false)]
        public int UploadOggetto(string sToken, string sFileName, byte[] aFile)
        {
            return -1; // Disabilitato per motivi di sicurezza. Se serve riabilitarlo, fare una revisione accurata del codice.

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
        /// Legge un'immagine o un oggetto del frontoffice
        /// </summary>
        /// <param name="token"></param>
        /// <param name="idRisorsaOggetto"></param>
        /// <returns></returns>
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