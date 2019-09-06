using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class ApplicationPath
    {
        private static class PathConstants
        {
            public const string ReservedPath = "/RESERVED/";
            public const string DatiDinamiciScriptServicePath = "/HELPER/";
            public const string FirmaAppletPath = "/FIRMADIGITALE/APPLETS/";
            public const string JavascriptFileExtension = ".JS";
            public static string AppletCompilazioneOggetti = "/AREARISERVATA/RESERVED/INSERIMENTOISTANZA/EDITOGGETTI/APPLET/INIT-EDITDOCS-APPLET.JAR";
        }

        readonly string _uri;

        public ApplicationPath(string uri)
        {
            this._uri = uri;
        }

        public bool IsReserved
        {
            get
            {
                var upath = this._uri.ToUpper();

                if (upath.EndsWith(PathConstants.AppletCompilazioneOggetti))
                    return false;

                if (upath.EndsWith(PathConstants.JavascriptFileExtension))
                    return false;

                if (upath.IndexOf(PathConstants.DatiDinamiciScriptServicePath) > -1)
                    return false;

                if (upath.IndexOf(PathConstants.FirmaAppletPath) > -1)
                    return false;

                return (upath.IndexOf(PathConstants.ReservedPath) != -1);
            }
        }

        public override string ToString()
        {
            return this._uri;
        }
    }
}