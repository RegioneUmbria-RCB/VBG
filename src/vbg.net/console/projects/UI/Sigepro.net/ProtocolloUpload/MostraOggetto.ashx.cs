using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Manager;
using Init.SIGePro.Authentication;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo;
using log4net;
using System.IO;

namespace ProtocolloUpload
{
	public class MostraOggetto : IHttpHandler
	{
        ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_SIGEPRO));
		public void ProcessRequest(HttpContext context)
		{
            try
            {
                var codiceOggetto = Convert.ToInt32(context.Request.QueryString["id"]);
                var token = context.Request.QueryString["token"];
                var asAttechmentStr = context.Request.QueryString["attachment"];
                var asAttechment = true;
                const string SOFTWARE_PR = "PR";

                AuthenticationInfo auth = AuthenticationManager.CheckToken(token);
                
                if (auth == null)
                    throw new Exception("Token non valido");

                string idComune = auth.IdComune;


                if (!String.IsNullOrEmpty(asAttechmentStr))
                    asAttechment = Convert.ToBoolean(asAttechmentStr);

                var protOgg = new ProtOggettiMgr(auth.CreateDatabase()).GetById(codiceOggetto, idComune);

                if (protOgg == null)
                    throw new Exception("Oggetto non trovato: id=" + codiceOggetto + ", idComune=" + idComune);

                OggettiMgr ogg = new OggettiMgr(auth.CreateDatabase());

                byte[] oggetto = protOgg.Oggetto;

                if (oggetto == null)
                {
                    VerticalizzazioneFilesystemProtocollo fs = new VerticalizzazioneFilesystemProtocollo(auth.Alias, SOFTWARE_PR);
                    bool readFromFileSystem = fs.Attiva;
                    if (readFromFileSystem)
                    {
                        if (String.IsNullOrEmpty(fs.DirectoryLocale))
                            throw new Exception("Il Parametro DIRECTORY_LOCALE della verticalizzazione FILESYSTEM_PROTOCOLLO deve essere valorizzato");

                        var format = new PROTOCOLLO_SIGEPRO.FormatFileProtocolloStruct { IdComune = idComune, CodiceOggetto = protOgg.Codiceoggetto.Value.ToString(), Estensione = Path.GetExtension(protOgg.Nomefile) };
                        string fileName = format.FormatFileProtocollo(PROTOCOLLO_SIGEPRO.FormatFileProtocolloEnum.CodiceOggetto_IdComune);
                        _log.Debug("fileName: " + fileName);
                        var protsig = new PROTOCOLLO_SIGEPRO();

                        oggetto = protsig.GetAllegatoFromDisk(protOgg.Percorso, fileName, fs.DirectoryLocale);
                    }
                }
                
                context.Response.ContentType = ogg.GetContentType(protOgg.Nomefile);

                if (asAttechment)
                    context.Response.AddHeader("Content-Disposition", "attachment; filename=" + protOgg.Nomefile);
                else
                    context.Response.AddHeader("filename", protOgg.Nomefile);

                if (oggetto == null)
                    throw new Exception("Oggetto non trovato controllare la verticalizzazione FILESYSTEM_PROTOCOLLO e il parametro DIRECTORY_LOCALE");

                context.Response.BinaryWrite(oggetto);
            }
            catch (Exception)
            {
                throw;
            }
		}

		public bool IsReusable
		{
			get
			{
				return false;
			}
		}
	}
}
