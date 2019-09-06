//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Web;
//using System.Configuration;
//using System.IO;
//using System.Xml.Serialization;

//namespace Init.Sigepro.FrontEnd.Contenuti
//{
//    public class ConfigurazioneContenuti
//    {
//        const string CONFIG_KEY = "file-configurazione-contenuti";

//        public string TestoFooter { get; set; }
//        public string NomeCss { get; set; }
//        public string LinkRegione { get; set; }
//        public string TestoRegione { get; set; }


//        public ConfigurazioneContenuti()
//        {

//        }

//        public static ConfigurazioneContenuti Get()
//        {
//            if (!HttpContext.Current.Items.Contains(CONFIG_KEY))
//            {
//                var settingsKey = ConfigurationManager.AppSettings["file-configurazione-contenuti"];
//                var basePath = HttpContext.Current.Server.MapPath("~/Contenuti/");

//                var pathComune = Path.Combine(basePath, settingsKey + ".xml");

//                if (!File.Exists(pathComune))
//                    throw new Exception("Impossibile trovare il file di configurazione dell'applicazione " + settingsKey + ".xml");

//                ConfigurazioneContenuti cls = null;

//                using (FileStream fs = File.OpenRead(pathComune))
//                {
//                    XmlSerializer xs = new XmlSerializer(typeof(ConfigurazioneContenuti));
//                    cls = (ConfigurazioneContenuti)xs.Deserialize(fs);
//                }

//                HttpContext.Current.Items.Add(CONFIG_KEY, cls);
//            }

//            return (ConfigurazioneContenuti)HttpContext.Current.Items[CONFIG_KEY];
//        }
		
//    }
//}
