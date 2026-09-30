using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.OggettiLogic;
using Init.Utils;
using PersonalLib2.Data;
using System;
using System.Data;
using System.IO;
using System.Linq;
using System.Xml.Serialization;



namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per OggettiMgr.
    /// </summary>
    public class OggettiMgr : BaseManager, IManager
    {
        public OggettiMgr(DataBase dataBase) : base(dataBase) { }



        #region nuova gestione degli oggetti	



        public Oggetti GetById(string idComune, int codiceOggetto)
        {
            return new OggettiServiceProxy(this.db).GetById(codiceOggetto);
        }

        /// <summary>
        /// Estrae solo il percorso relativo all'oggetto.
        /// </summary>
        /// <param name="codiceOggetto">Codice dell'oggetto, campo OGGETTI.CODICEOGGETTO</param>
        public string GetPercorsoOggetto(string idComune, int codiceOggetto)
        {
            var closeCnn = false;

            try
            {
                //int resultParse = int.MinValue;
                //bool isNumberCodiceOggetto = int.TryParse(codiceOggetto, out resultParse);

                //if (!isNumberCodiceOggetto)
                //    throw new Exception("Il parsing del codiceoggetto da string a int non è andato a buon fine, il codiceoggetto non è un numero valido");

                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = "select PERCORSO from OGGETTI where IDCOMUNE = " + this.db.Specifics.ParameterName("idcomune") + " and CODICEOGGETTO = " + this.db.Specifics.ParameterName("codiceoggetto");

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceoggetto", codiceOggetto));

                    var res = cmd.ExecuteScalar();
                    return res.ToString();
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la lettura del percorso degli OGGETTI " + ex.ToString());
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        /// <summary>
        /// Estrae il nome file dell'oggetto caricato nella tabella OGGETTI, durante la fase di interrogazione della tabella non viene estratto il campo OGGETTO, 
        /// consentendo una maggiore velocità durante l'operazione
        /// </summary>
        /// <param name="codiceOggetto">Codice dell'oggetto, campo OGGETTI.CODICEOGGETTO</param>
        public static string GetNomeFileOggettoFast(string codiceOggetto, string idComune, DataBase dataBase)
        {
            try
            {
                var resultParse = int.MinValue;
                var isNumberCodiceOggetto = int.TryParse(codiceOggetto, out resultParse);

                if (!isNumberCodiceOggetto)
                    throw new Exception("Il parsing del codiceoggetto da string a int non è andato a buon fine, il codiceoggetto non è un numero valido");

                var sql = "select NOMEFILE from OGGETTI where IDCOMUNE = " + dataBase.Specifics.ParameterName("idcomune") + " and CODICEOGGETTO = " + dataBase.Specifics.ParameterName("codiceoggetto");

                using (var cmd = dataBase.CreateCommand(sql))
                {
                    cmd.Parameters.Add(dataBase.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(dataBase.CreateParameter("codiceoggetto", resultParse));

                    var res = cmd.ExecuteScalar();
                    return res.ToString();
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la lettura del nome file degli OGGETTI " + ex.ToString());
            }
        }

        public void AggiornaCorpoOggetto(string idComune, int codiceOggetto, string nuovoNomeFile, byte[] nuovoCorpoOggetto)
        {
            new OggettiServiceProxy(this.db).UpdateOggetto(codiceOggetto, nuovoNomeFile, nuovoCorpoOggetto);
        }


        /// <summary>
        /// Elimina un oggetto dal database
        /// ATTENZIONE! Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce
        /// </summary>
        /// <remarks>Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce</remarks>
        /// <param name="p_class"></param>
        public void EliminaOggetto(string idcomune, int codiceOggetto)
        {
            new OggettiServiceProxy(this.db).DeleteOggetto(codiceOggetto);
        }

        public Oggetti InsertClass(string idComune, string nomeFile, object classe)
        {
            using (var ms = new MemoryStream())
            {
                var xs = new XmlSerializer(classe.GetType());
                xs.Serialize(ms, classe);

                var ogg = new Oggetti();
                ogg.IDCOMUNE = idComune;
                ogg.NOMEFILE = nomeFile;
                ogg.OGGETTO = StreamUtils.StreamToBytes(ms);

                ogg.CODICEOGGETTO = new OggettiServiceProxy(this.db).InsertOggetto(ogg.NOMEFILE, null, ogg.OGGETTO).ToString();

                return ogg;
            }

        }

        public Oggetti Insert(string idComune, string mimeType, string fileName, byte[] buffer)
        {
            var codiceOggetto = new OggettiServiceProxy(this.db).InsertOggetto(fileName, mimeType, buffer);

            return new Oggetti
            {
                IDCOMUNE = idComune,
                CODICEOGGETTO = codiceOggetto.ToString(),
                NOMEFILE = fileName,
                OGGETTO = null
            };
        }

        /// <summary>
        /// IOnserisce un oggetto nel db. Se la verticalizzazione Filesystem è attiva
        /// salva l'oggetto su filesystem prima di eseguire la insert nel db
        /// </summary>
        /// <param name="cls"></param>
        /// <returns>La classe oggetti così come è stata inserita nel db</returns>
        public Oggetti Insert(Oggetti cls)
        {
            cls.CODICEOGGETTO = new OggettiServiceProxy(this.db).InsertOggetto(cls.NOMEFILE, null, cls.OGGETTO).ToString();

            return cls;
        }

        public string GetContentType(Oggetti oggetto)
        {
            return this.GetContentType(oggetto.NOMEFILE);
        }

        public string GetContentType(string nomefile)
        {
            // TODO: Utilizzare https://github.com/samuelneff/MimeTypeMap per velocizzare l'operazione
            var ext = Path.GetExtension(nomefile).Replace(".", "").ToLower();
            var extLike = $"%;{ext};%";

            FormattableString sql = $"select CT_MIMETYPE from contenttypes where ct_extension like {extLike}";

            return this.db.ExecuteReader(sql, dr => dr.GetString("CT_MIMETYPE")).FirstOrDefault() ?? String.Empty;
        }

        [Obsolete("Utilizzare string GetExtension(string idComune, int codiceOggetto)")]
        public string GetExtension(Oggetti oggetto)
        {
            var ext = Path.GetExtension(oggetto.NOMEFILE);

            return ext.Replace(".", "");
        }

        public string GetExtension(string idComune, int codiceOggetto)
        {
            var nomeFile = this.GetNomeFile(idComune, codiceOggetto);

            return Path.GetExtension(nomeFile);
        }

        public string GetNomeFile(string idComune, int codiceOggetto)
        {
            return new OggettiServiceProxy(this.db).GetFileName(codiceOggetto);
        }

        #endregion

    }

}
