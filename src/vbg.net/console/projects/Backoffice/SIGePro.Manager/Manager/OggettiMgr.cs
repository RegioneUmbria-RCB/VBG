//#define OLD_OGGETTI
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.OggettiLogic;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using System;
using System.Data;
using System.IO;
using System.Web;
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
            return new OggettiServiceProxy(db).GetById(codiceOggetto);
        }

        /// <summary>
        /// Estrae solo il percorso relativo all'oggetto.
        /// </summary>
        /// <param name="codiceOggetto">Codice dell'oggetto, campo OGGETTI.CODICEOGGETTO</param>
        public string GetPercorsoOggetto(string idComune, int codiceOggetto)
        {
            bool closeCnn = false;

            try
            {
                //int resultParse = int.MinValue;
                //bool isNumberCodiceOggetto = int.TryParse(codiceOggetto, out resultParse);

                //if (!isNumberCodiceOggetto)
                //    throw new Exception("Il parsing del codiceoggetto da string a int non è andato a buon fine, il codiceoggetto non è un numero valido");

                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                string sql = "select PERCORSO from OGGETTI where IDCOMUNE = " + db.Specifics.ParameterName("idcomune") + " and CODICEOGGETTO = " + db.Specifics.ParameterName("codiceoggetto");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("codiceoggetto", codiceOggetto));

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
                    db.Connection.Close();
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
                int resultParse = int.MinValue;
                bool isNumberCodiceOggetto = int.TryParse(codiceOggetto, out resultParse);

                if (!isNumberCodiceOggetto)
                    throw new Exception("Il parsing del codiceoggetto da string a int non è andato a buon fine, il codiceoggetto non è un numero valido");

                string sql = "select NOMEFILE from OGGETTI where IDCOMUNE = " + dataBase.Specifics.ParameterName("idcomune") + " and CODICEOGGETTO = " + dataBase.Specifics.ParameterName("codiceoggetto");

                using (IDbCommand cmd = dataBase.CreateCommand(sql))
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
            new OggettiServiceProxy(db).UpdateOggetto(codiceOggetto, nuovoNomeFile, nuovoCorpoOggetto);
        }


        /// <summary>
        /// Elimina un oggetto dal database
        /// ATTENZIONE! Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce
        /// </summary>
        /// <remarks>Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce</remarks>
        /// <param name="p_class"></param>
        public void EliminaOggetto(string idcomune, int codiceOggetto)
        {
            new OggettiServiceProxy(db).DeleteOggetto(codiceOggetto);
        }

        public Oggetti InsertClass(string idComune, string nomeFile, object classe)
        {
            using (MemoryStream ms = new MemoryStream())
            {
                XmlSerializer xs = new XmlSerializer(classe.GetType());
                xs.Serialize(ms, classe);

                Oggetti ogg = new Oggetti();
                ogg.IDCOMUNE = idComune;
                ogg.NOMEFILE = nomeFile;
                ogg.OGGETTO = StreamUtils.StreamToBytes(ms);

                ogg.CODICEOGGETTO = new OggettiServiceProxy(db).InsertOggetto(ogg.NOMEFILE, null, ogg.OGGETTO).ToString();

                return ogg;
            }

        }

        public Oggetti Insert(string idComune, HttpPostedFile postedFile)
        {
            var mimeType = postedFile.ContentType;
            var fileName = Path.GetFileName(postedFile.FileName);
            var buffer = new byte[postedFile.ContentLength];

            postedFile.InputStream.Read(buffer, 0, buffer.Length);

            var codiceOggetto = new OggettiServiceProxy(db).InsertOggetto(fileName, mimeType, buffer);

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
            cls.CODICEOGGETTO = new OggettiServiceProxy(db).InsertOggetto(cls.NOMEFILE, null, cls.OGGETTO).ToString();

            return cls;
        }

        public string GetContentType(Oggetti oggetto)
        {
            return GetContentType(oggetto.NOMEFILE);
        }

        public string GetContentType(string nomefile)
        {
            string ext = Path.GetExtension(nomefile);
            ext = ext.Replace(".", "");

            ContentTypes ct = new ContentTypes();
            ct.OthersWhereClause.Add("ct_extension like '%;" + ext.ToLower() + ";%'");

            DataClassCollection coll = db.GetClassList(ct);

            if (coll.Count == 0) return "";

            return ((ContentTypes)coll[0]).CT_MIMETYPE;
        }

        [Obsolete("Utilizzare string GetExtension(string idComune, int codiceOggetto)")]
        public string GetExtension(Oggetti oggetto)
        {
            string ext = Path.GetExtension(oggetto.NOMEFILE);

            return ext.Replace(".", "");
        }

        public string GetExtension(string idComune, int codiceOggetto)
        {
            var nomeFile = GetNomeFile(idComune, codiceOggetto);

            return Path.GetExtension(nomeFile);
        }

        public string GetNomeFile(string idComune, int codiceOggetto)
        {
            return new OggettiServiceProxy(db).GetFileName(codiceOggetto);
        }

        #endregion
    }

}
