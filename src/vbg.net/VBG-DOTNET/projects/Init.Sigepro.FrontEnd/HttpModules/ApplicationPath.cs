namespace Init.Sigepro.FrontEnd.HttpModules
{
    public class ApplicationPath
    {
        private static class PathConstants
        {
            public const string ReservedPath = "/RESERVED/";
            public const string DatiDinamiciScriptServicePath = "/HELPER/";
            public const string DatiDinamiciFileUploadHandlers = "/HELPER/FILEUPLOADHANDLERS/";
            public const string FirmaAppletPath = "/FIRMADIGITALE/APPLETS/";
            public const string JavascriptFileExtension = ".JS";
            public const string PngFileExtension = ".PNG";
            public static string AppletCompilazioneOggetti = "/AREARISERVATA/RESERVED/INSERIMENTOISTANZA/EDITOGGETTI/APPLET/INIT-EDITDOCS-APPLET.JAR";
        }

        private readonly string _uri;

        public ApplicationPath(string uri)
        {
            this._uri = uri.ToUpper();
        }

        public bool IsReserved
        {
            get
            {
                return this.IsPathDaIncludere(this._uri) || !this.IsPathDaEscludere(this._uri);
            }
        }

        private bool IsPathDaIncludere(string localUri)
        {
            if (localUri.IndexOf(PathConstants.DatiDinamiciFileUploadHandlers) > -1)
            {
                return true;
            }

            return false;
        }

        private bool IsPathDaEscludere(string upath)
        {
            if (upath.EndsWith(PathConstants.AppletCompilazioneOggetti))
                return true;

            if (upath.EndsWith(PathConstants.JavascriptFileExtension))
                return true;

            if (upath.EndsWith(PathConstants.PngFileExtension))
                return true;

            if (upath.IndexOf(PathConstants.DatiDinamiciScriptServicePath) > -1)
                return true;

            if (upath.IndexOf(PathConstants.FirmaAppletPath) > -1)
                return true;

            return (upath.IndexOf(PathConstants.ReservedPath) == -1);
        }

        public override string ToString()
        {
            return this._uri;
        }
    }
}