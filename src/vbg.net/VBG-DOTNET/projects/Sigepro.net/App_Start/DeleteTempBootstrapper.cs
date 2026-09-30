[assembly: WebActivatorEx.PostApplicationStartMethod(typeof(Sigepro.net.App_Start.DeleteTempBootstrapper), "Start")]

namespace Sigepro.net.App_Start
{
    using System;
    using System.Configuration;
    using System.IO;
    using System.Web;

    public class DeleteTempBootstrapper
    {
        public static void Start()
        {
            const int DEFAULT_GG_DELETE_TEMP = 4;

            try
            {
                var folderPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["TempPath"].ToString());

                var GGDeleteTemp = DEFAULT_GG_DELETE_TEMP;

                if (ConfigurationManager.AppSettings["GG_DELETE_TEMP"] != null)
                {
                    var isNumber = int.TryParse(ConfigurationManager.AppSettings["GG_DELETE_TEMP"].ToString(), out GGDeleteTemp);

                    if (!isNumber)
                        GGDeleteTemp = DEFAULT_GG_DELETE_TEMP;
                }

                if (Directory.Exists(folderPath))
                {
                    foreach (var dir in Directory.GetDirectories(folderPath))
                    {
                        var dataCreazione = Directory.GetCreationTime(dir);
                        if (dataCreazione.AddDays(GGDeleteTemp) < DateTime.Now)
                            Directory.Delete(dir, true);
                    }

                    foreach (var files in Directory.GetFiles(folderPath))
                    {
                        // Per evitare di cancellare il file copiami.qui che viene usato per pubblicare la cartella nella distribuzione
                        if (files.ToLowerInvariant().EndsWith("copiami.qui"))
                        {
                            continue;
                        }

                        File.Delete(files);
                    }
                }
            }
            catch
            {
            }
        }
    }
}