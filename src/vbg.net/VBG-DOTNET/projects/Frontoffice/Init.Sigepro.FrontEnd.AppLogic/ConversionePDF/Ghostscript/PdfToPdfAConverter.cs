using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript.API;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript
{
    public class PdfToPdfAConverter
    {
        #region Globals

        private static readonly string[] ARGS = new string[] {
				// Keep gs from writing information to standard output
                "-q",
                "-dQUIET",
                "-dPDFA=1",
                "-dBATCH",
                "-dUseCIEColor",
                "-sColorConversionStrategy=UseDeviceIndependentColor",
                "-sProcessColorModel=DeviceCMYK",
                "-dPDFACompatibilityPolicy=1",
                "-sDEVICE=pdfwrite",
                "-dNOPAUSE",
                "-dNOPROMPT"
        };
        private readonly IGhostscriptAPI _ghostscript;
        #endregion

        public PdfToPdfAConverter(IGhostscriptAPI ghostscript)
        {
            this._ghostscript = ghostscript;
        }

        public void ConvertiInPdfA(string inputPath, string outputPath)
        {
            this._ghostscript.CallAPI(this.GetArgs(inputPath, outputPath));
            /*
            if (IntPtr.Size == 4)
                API.GhostScript32.CallAPI(GetArgs(inputPath, outputPath));
            else
                API.GhostScript64.CallAPI(GetArgs(inputPath, outputPath));
            */
        }

        private string[] GetArgs(string inputPath, string outputPath)
        {
            var args = new List<string>(ARGS);

            args.Add(String.Format("-sOutputFile={0}", outputPath));
            args.Add(inputPath);

            return args.ToArray();
        }
    }
}
