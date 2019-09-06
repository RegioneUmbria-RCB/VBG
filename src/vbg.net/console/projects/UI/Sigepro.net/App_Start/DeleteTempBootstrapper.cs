[assembly: WebActivatorEx.PostApplicationStartMethod(typeof(Sigepro.net.App_Start.DeleteTempBootstrapper), "Start")]

namespace Sigepro.net.App_Start
{
    using System;
    using System.Collections.Generic;
    using System.Configuration;
    using System.IO;
    using System.Linq;
    using System.Web;

    public class DeleteTempBootstrapper
    {
        public static void Start()
        {
            const int DEFAULT_GG_DELETE_TEMP = 4;

            try
            {
                string folderPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["TempPath"].ToString());

                int GGDeleteTemp = DEFAULT_GG_DELETE_TEMP;

                if (ConfigurationManager.AppSettings["GG_DELETE_TEMP"] != null)
                {
                    bool isNumber = int.TryParse(ConfigurationManager.AppSettings["GG_DELETE_TEMP"].ToString(), out GGDeleteTemp);

                    if (!isNumber)
                        GGDeleteTemp = DEFAULT_GG_DELETE_TEMP;
                }

                if (Directory.Exists(folderPath))
                {
                    foreach (string dir in Directory.GetDirectories(folderPath))
                    {
                        var dataCreazione = Directory.GetCreationTime(dir);
                        if (dataCreazione.AddDays(GGDeleteTemp) < DateTime.Now)
                            Directory.Delete(dir, true);
                    }

                    foreach (string files in Directory.GetFiles(folderPath))
                        File.Delete(files);
                }
            }
            catch
            {
            }
        }
    }
}