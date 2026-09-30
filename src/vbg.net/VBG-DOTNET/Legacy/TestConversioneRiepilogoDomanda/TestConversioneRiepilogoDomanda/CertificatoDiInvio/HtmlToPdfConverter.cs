using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
    public class HtmlToPdfConverter
    {
        //string _phantomJsPath = ".\\";
        string _phantomJsExecutable = "phantomjs.exe";

        public void Converti(string nomeFileInput, string nomeFileOutput="risultato.pdf")
        {
            var command = this._phantomJsExecutable;
            var arg = $" rasterize.js \"{nomeFileInput.Replace("\\", "/")}\" \"{nomeFileOutput}\" \"A4\"";

            ProcessStartInfo pi;
            Process p;

            pi = new ProcessStartInfo(this._phantomJsExecutable, arg);
            pi.CreateNoWindow = true;
            pi.UseShellExecute = false;
            //pi.WorkingDirectory = this._phantomJsPath;

            p = Process.Start(pi);

            p.WaitForExit();
        }
    }
}
