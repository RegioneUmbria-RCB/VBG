using System;
using System.Collections.Generic;
using System.Data;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Windows.Forms;
using TestConversioneRiepilogoDomanda.CertificatoDiInvio;
using TestConversioneRiepilogoDomanda.Infrastructure;

namespace TestConversioneRiepilogoDomanda
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            this.InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.BindListaFilesHtml();
            this.BindListaFilesXml();
        }

        private void BindListaFilesHtml()
        {
            this.lbFilesHtml.Items.Clear();

            foreach (var nomeFile in this.GetListaFiles("html"))
                this.lbFilesHtml.Items.Add(nomeFile);
        }

        private void BindListaFilesXml()
        {
            this.lbFilesXml.Items.Clear();

            foreach (var nomeFile in this.GetListaFiles("xml"))
                this.lbFilesXml.Items.Add(nomeFile);
        }

        private IEnumerable<string> GetListaFiles(string estensione)
        {
            var searchDir = this.GetWorkingPath();

            return Directory.GetFiles(searchDir, "*." + estensione).Select(x => Path.GetFileName(x));
        }

        private string GetWorkingPath()
        {
            const string cartellaDocumenti = "Documenti";

            var rootDir = Path.GetDirectoryName(Application.ExecutablePath);
            var testDir = Path.Combine(rootDir, cartellaDocumenti);

            if (Directory.Exists(testDir))
                return testDir;

            testDir = Path.Combine(rootDir, "..\\..\\", cartellaDocumenti);

            if (Directory.Exists(testDir))
                return testDir;

            return rootDir;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (this._pdfProcess != null)
            {
                if (!this._pdfProcess.HasExited)
                    this._pdfProcess.Kill();
            }


            if (this.lbFilesHtml.SelectedItems.Count == 0)
            {
                MessageBox.Show("Selezionare un template html");
                return;
            }

            if (this.lbFilesXml.SelectedItems.Count == 0)
            {
                MessageBox.Show("Selezionare un file di dati xml");
                return;
            }


            var fileConverter = new FileConverter("asd");

            var template = new TemplateCertificato(this.LeggiFileTemplate(), "html");
            var dati = new DatiCertificato(this.LeggiFileDati());

            var fileConvertito = template.ApplicaA(dati);

            var nomeFileInput = "risultato.html";
            var nomeFileOutput = "risultato.pdf";

            if (File.Exists(nomeFileInput))
                File.Delete(nomeFileInput);

            if (File.Exists(nomeFileOutput))
                File.Delete(nomeFileOutput);

            using (FileStream fs = File.Open(nomeFileInput, FileMode.Create))
            {
                fs.Write(fileConvertito, 0, fileConvertito.Length);
            }

            var generatore = new HtmlToPdfConverter();

            generatore.Converti(nomeFileInput, nomeFileOutput);

            //MessageBox.Show("Generazione completata");
            this._pdfProcess = Process.Start(nomeFileOutput);
        }

        private Process _pdfProcess;

        private string LeggiFileDati()
        {
            return this.LeggiFile(this.lbFilesXml.SelectedItem.ToString());
        }

        private string LeggiFileTemplate()
        {
            return this.LeggiFile(this.lbFilesHtml.SelectedItem.ToString());
        }

        private string LeggiFile(string relPath)
        {
            var rootDir = this.GetWorkingPath();
            var fullPath = Path.Combine(rootDir, relPath);

            using (var fs = File.OpenRead(fullPath))
            {
                var buff = new byte[fs.Length];
                fs.Read(buff, 0, buff.Length);

                return UnknownEncodingToString.Convert(buff);
            }
        }

        private void cmdRefresh_Click(object sender, EventArgs e)
        {
            this.BindListaFilesHtml();
            this.BindListaFilesXml();
        }
    }
}
