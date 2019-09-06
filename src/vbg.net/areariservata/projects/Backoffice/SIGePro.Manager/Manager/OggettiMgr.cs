//#define OLD_OGGETTI
using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Manager.Logic.OggettiLogic;
using System.Web;



namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per OggettiMgr.
	/// </summary>
	public class OggettiMgr : BaseManager, IManager
	{
		public OggettiMgr(DataBase dataBase) : base( dataBase ) {}


#if OLD_OGGETTI
	#region Vecchio codice

		public Oggetti GetById(string idComune, int codiceOggetto)
		{

			bool closeCnn = false;
			Oggetti rVal = null;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql = PreparaQueryParametrica("select * from oggetti where idcomune={0} and codiceoggetto={1}", "idComune", "codiceOggetto");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceOggetto", codiceOggetto));

					using (var dr = cmd.ExecuteReader())
					{
						if (!dr.Read())
							return null;

						var objContenutoOggetto = dr["OGGETTO"];

						rVal = new Oggetti
						{
							IDCOMUNE = idComune,
							CODICEOGGETTO = codiceOggetto.ToString(),
							NOMEFILE = dr["nomefile"].ToString(),
							OGGETTO = (byte[])(objContenutoOggetto == DBNull.Value ? null : objContenutoOggetto)
						};
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}


			VerticalizzazioneFilesystem vfs = new VerticalizzazioneFilesystem(db, idComune, "TT");
			if (vfs.Attiva && !String.IsNullOrEmpty(vfs.Sharedpath))
			{
				string filepath = Path.Combine(vfs.Sharedpath, rVal.NOMEFILE);

				if (File.Exists(filepath))
				{
					using (FileStream fs = File.OpenRead(filepath))
					{
						rVal.OGGETTO = new byte[fs.Length];
						fs.Read(rVal.OGGETTO, 0, rVal.OGGETTO.Length);
					}
				}
			}

			return rVal;

			/*
			Oggetti filtro = new Oggetti();
			filtro.IDCOMUNE = idComune;
			filtro.CODICEOGGETTO = codiceOggetto.ToString();

			Oggetti cls = (Oggetti)db.GetClass(filtro);

			if (cls == null)
				return null;

			VerticalizzazioneFilesystem vfs = new VerticalizzazioneFilesystem(db, idComune, "TT");
			if (vfs.Attiva && !String.IsNullOrEmpty( vfs.Sharedpath ) )
			{
				string filepath = Path.Combine(vfs.Sharedpath, cls.NOMEFILE);

				if (File.Exists(filepath))
				{
					using (FileStream fs = File.OpenRead(filepath))
					{
						cls.OGGETTO = new byte[fs.Length];
						fs.Read(cls.OGGETTO, 0, cls.OGGETTO.Length);
					}
				}
			}

			return cls;*/
		}

	
		public ArrayList GetList(Oggetti p_class, Oggetti p_cmpClass )
		{
            return GetListFileSystem(p_class, p_cmpClass, false, false);
		}


        //Questi 2 metodi privati sono stati inseriti in seguito alla gestione degli oggetti tramite file system
        private DataClassCollection GetListFileSystem(Oggetti p_class, Oggetti p_cmpClass, bool singleRowException, bool ignoreSetMode)
        {
            DataClassCollection listaOggetti = db.GetClassList(p_class, p_cmpClass, singleRowException, ignoreSetMode);

            //Verifico se la verticalizzazione FILESYSTEM è attiva 
            VerticalizzazioneFilesystem vertFileSystem = new VerticalizzazioneFilesystem(db, p_class.IDCOMUNE, "");
            if (vertFileSystem.Attiva && !string.IsNullOrEmpty(vertFileSystem.Sharedpath))
            {
                foreach (Oggetti obj in listaOggetti)
                {
                    try
                    {
						string filePath = Path.Combine(vertFileSystem.Sharedpath, obj.NOMEFILE);

                        using (FileStream fs = new FileStream( filePath , FileMode.Open))
                        {
                            obj.OGGETTO = StreamUtils.StreamToBytes(fs);
                        }
                    }
                    catch (FileNotFoundException ex)
                    {
                        continue;
                    }
                }
            }

            return listaOggetti;
        }

        private DataClassCollection GetListFileSystem(Oggetti p_class, bool singleRowException, bool ignoreSetMode)
        {
            return GetListFileSystem(p_class,null,singleRowException,ignoreSetMode);
        }

        public void AggiornaCorpoOggetto(string idComune , int codiceOggetto , string nuovoNomeFile,byte[] nuovoCorpoOggetto)
        {
			Oggetti cls = GetById(idComune, codiceOggetto);

			VerticalizzazioneFilesystem vertFileSystem = new VerticalizzazioneFilesystem(db, cls.IDCOMUNE, "");
			if (vertFileSystem.Attiva && !string.IsNullOrEmpty(vertFileSystem.Sharedpath))
			{
				string filePath = Path.Combine(vertFileSystem.Sharedpath, cls.NOMEFILE);

				// elimino il vecchio file
				File.Delete(filePath);

				filePath = Path.Combine(vertFileSystem.Sharedpath, nuovoNomeFile);

				// creo il nuovo
				using (FileStream fs = new FileStream(filePath, FileMode.Create))
				{
					fs.Write(nuovoCorpoOggetto, 0, nuovoCorpoOggetto.Length);
				}
				cls.OGGETTO = null;
			}
			else
			{
				cls.OGGETTO = nuovoCorpoOggetto;
			}

			cls.NOMEFILE = nuovoNomeFile;

			db.Update(cls);
        }


		/// <summary>
		/// Elimina un oggetto dal database
		/// ATTENZIONE! Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce
		/// </summary>
		/// <remarks>Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce</remarks>
		/// <param name="p_class"></param>
		public void EliminaOggetto(string idcomune , int codiceOggetto)
		{
			Oggetti cls = GetById(idcomune, codiceOggetto);

			if (cls == null)
				return;

			VerificaRecordCollegati(cls);

            //Verifico se la verticalizzazione FILESYSTEM è attiva 
            VerticalizzazioneFilesystem vertFileSystem = new VerticalizzazioneFilesystem(db, cls.IDCOMUNE, "");
            if ( vertFileSystem.Attiva && !string.IsNullOrEmpty(vertFileSystem.Sharedpath) )
            {
				string filePath = Path.Combine( vertFileSystem.Sharedpath , cls.NOMEFILE );
                File.Delete( filePath );
            }

            db.Delete(cls);
		}

		/// <summary>
		/// Utilizza la tabella mapoggetti per verificare se esistono records collegati
		/// all'oggetto
		/// </summary>
		/// <param name="cls"></param>
		private void VerificaRecordCollegati(Oggetti cls)
		{
			// Prende tutte le righe di mapoggetti
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var listaCampi = new List<KeyValuePair<string,string>>();

				string sql = "SELECT nometabella,nomecampo FROM mapoggetti";

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					using (IDataReader dr = cmd.ExecuteReader())
					{
						while (dr.Read())
							listaCampi.Add(new KeyValuePair<string, string>(dr["NOMETABELLA"].ToString(), dr["NOMECAMPO"].ToString()));
					}
				}


				// per ogni riga effettua una count sulla tabella per verificare se ci sono record collegati
				for (int i = 0; i < listaCampi.Count; i++)
				{
					var nomeTabella = listaCampi[i].Key;
					var nomeCampo = listaCampi[i].Value;

					sql = "select count(*) from " + nomeTabella + " where idcomune={0} and " + nomeCampo + "={1}";

					sql = String.Format(sql, db.Specifics.QueryParameterName("IdComune"), db.Specifics.QueryParameterName(nomeCampo));

					using (IDbCommand cmd = db.CreateCommand(sql))
					{
						cmd.Parameters.Add(db.CreateParameter("IdComune", cls.IDCOMUNE));
						cmd.Parameters.Add(db.CreateParameter(nomeCampo, Convert.ToInt32(cls.CODICEOGGETTO)));

						object cnt = cmd.ExecuteScalar();

						// Se vengono trovati record collegati viene sollevata un'eccezione di tipo ReferentialIntegrityException
						if (cnt != null && cnt != DBNull.Value && Convert.ToInt32(cnt) > 0)
							throw new ReferentialIntegrityException(nomeTabella);
					}
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
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
				ogg.OGGETTO =  StreamUtils.StreamToBytes(ms);

				return Insert(ogg);
			}

		}

		/// <summary>
		/// IOnserisce un oggetto nel db. Se la verticalizzazione Filesystem è attiva
		/// salva l'oggetto su filesystem prima di eseguire la insert nel db
		/// </summary>
		/// <param name="cls"></param>
		/// <returns>La classe oggetti così come è stata inserita nel db</returns>
		public Oggetti Insert( Oggetti cls )
		{
			cls = DataIntegrations( cls );

			Validate( cls , AmbitoValidazione.Insert);

			if (cls.OGGETTO != null)
			{
				VerticalizzazioneFilesystem vertFileSystem = new VerticalizzazioneFilesystem(db, cls.IDCOMUNE, "");

				if (vertFileSystem.Attiva && !string.IsNullOrEmpty(vertFileSystem.Sharedpath))
				{
					string nomeFile = cls.CODICEOGGETTO + "-" + cls.NOMEFILE;

					string filePath = Path.Combine(vertFileSystem.Sharedpath, nomeFile);

					using (FileStream fs = new FileStream(filePath, FileMode.Create))
					{
						fs.Write(cls.OGGETTO, 0, cls.OGGETTO.Length);

						cls.NOMEFILE = nomeFile;
						cls.OGGETTO = null;
					}
				}
			}

            db.Insert(cls);

			return cls;
		}

		private Oggetti DataIntegrations ( Oggetti p_class )
		{
			return p_class;
		}

		private void Validate(Oggetti p_class, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione );
		}

		public string GetContentType( Oggetti oggetto )
		{
			string ext = GetExtension( oggetto );

			ContentTypes ct = new ContentTypes();
			ct.OthersWhereClause.Add( "ct_extension like '%;" + ext.ToLower() + ";%'" );

			DataClassCollection coll = db.GetClassList( ct );
			
			if ( coll.Count == 0 ) return "";
            
			return ((ContentTypes)coll[0]).CT_MIMETYPE;
		}

		[Obsolete("Utilizzare string GetExtension(string idComune, int codiceOggetto)")]
		public string GetExtension( Oggetti oggetto )
		{
			string ext = Path.GetExtension(oggetto.NOMEFILE);

			return ext.Replace(".", "");
		}

		public string GetExtension(string idComune, int codiceOggetto)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = PreparaQueryParametrica("select NOMEFILE from oggetti where idcomune = {0} and codiceoggetto = {1}", 
													"idComune", 
													"codiceOggetto");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceOggetto", codiceOggetto));

					string nomeFile = cmd.ExecuteScalar().ToString();

					if (String.IsNullOrEmpty(nomeFile))
						return String.Empty;

					return Path.GetExtension(nomeFile);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

		public string GetNomeFile(string idComune, int codiceOggetto)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = PreparaQueryParametrica("select NOMEFILE from oggetti where idcomune = {0} and codiceoggetto = {1}",
													"idComune",
													"codiceOggetto");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceOggetto", codiceOggetto));

					string nomeFile = cmd.ExecuteScalar().ToString();

					if (String.IsNullOrEmpty(nomeFile))
						return String.Empty;

					return nomeFile;
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}
		}

#endregion
#else
#region nuova gestione degli oggetti	



		public Oggetti GetById(string idComune, int codiceOggetto)
		{
			return new OggettiServiceProxy( db ).GetById( codiceOggetto );
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

        public void AggiornaCorpoOggetto(string idComune , int codiceOggetto , string nuovoNomeFile,byte[] nuovoCorpoOggetto)
        {
			new OggettiServiceProxy( db ).UpdateOggetto( codiceOggetto , nuovoNomeFile , nuovoCorpoOggetto);
        }


		/// <summary>
		/// Elimina un oggetto dal database
		/// ATTENZIONE! Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce
		/// </summary>
		/// <remarks>Chiamare il metodo solo dopo aver eliminato il record che referenzia l'oggetto altrimenti la verifica su mapoggetti fallisce</remarks>
		/// <param name="p_class"></param>
		public void EliminaOggetto(string idcomune , int codiceOggetto)
		{
			new OggettiServiceProxy( db ).DeleteOggetto( codiceOggetto );
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
				ogg.OGGETTO =  StreamUtils.StreamToBytes(ms);

				ogg.CODICEOGGETTO = new OggettiServiceProxy( db ).InsertOggetto( ogg.NOMEFILE , null , ogg.OGGETTO ).ToString();

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
		public Oggetti Insert( Oggetti cls )
		{
			cls.CODICEOGGETTO = new OggettiServiceProxy(db).InsertOggetto(cls.NOMEFILE, null, cls.OGGETTO).ToString();

			return cls;
		}

		public string GetContentType( Oggetti oggetto )
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
		public string GetExtension( Oggetti oggetto )
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
#endif
	}

}
