using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript
{
    public class PdfToPdfAConverter
    {
        #region Globals

        private static readonly string[] ARGS = new string[] {
				// Keep gs from writing information to standard output
                "-q",                     
                "-dQUIET",
               
                // Aggiunti per generazione pdf/a
                "-dPDFA",
                "-dUseCIEColor",
                "-sProcessColorModel=DeviceCMYK",
                "-sPDFACompatibilityPolicy=1",
                "-sDEVICE=pdfwrite",
                "-dPARANOIDSAFER",       // Run this command in safe mode
                "-dBATCH",               // Keep gs from going into interactive mode
                "-dNOPAUSE",             // Do not prompt and pause for each page
                "-dNOPROMPT",            // Disable prompts for user interaction           
               // "-dMaxBitmap=500000000", // Set high for better performance
			   // "-dNumRenderingThreads=4", // Multi-core, come-on!
               // 
               // // Configure the output anti-aliasing, resolution, etc
               // "-dAlignToPixels=0",
               // "-dGridFitTT=0",
               // "-dTextAlphaBits=4",
               // "-dGraphicsAlphaBits=4"
		};
        #endregion

        public void ConvertiInPdfA(string inputPath, string outputPath)
        {
            if (IntPtr.Size == 4)
                API.GhostScript32.CallAPI(GetArgs(inputPath, outputPath));
            else
                API.GhostScript64.CallAPI(GetArgs(inputPath, outputPath));
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
