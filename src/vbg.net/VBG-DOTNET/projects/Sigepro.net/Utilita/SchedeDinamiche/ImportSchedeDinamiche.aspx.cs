using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.Export;
using Ninject;
using Ninject.Web;
using System;
using System.Collections.Generic;
using System.Web;

namespace Sigepro.net.Utilita.SchedeDinamiche
{
    public partial class ImportSchedeDinamiche : PageBase
    {
        private sealed class MessaggioEsito
        {
            public string Tipo { get; set; }
            public string Messaggio { get; set; } = string.Empty;

            public static MessaggioEsito Successo(string testo) => new MessaggioEsito { Tipo = "success", Messaggio = testo };
            public static MessaggioEsito Warning(string testo) => new MessaggioEsito { Tipo = "warning", Messaggio = testo };
        }

        [Inject]
        public IAuthenticationManager AuthenticationManager { get; set; }

        protected bool PermetteImport = false;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.Request.QueryString["alias"]))
            {
                this.txtAlias.Value = this.Request.QueryString["alias"];
            }

            if (!String.IsNullOrEmpty(this.Request.QueryString["software"]))
            {
                this.txtSoftware.Value = this.Request.QueryString["software"];
            }
        }

        protected void btnVerifica_Click(object sender, EventArgs e)
        {
            this.PermetteImport = this.Verifica();
        }

        private bool Verifica()
        {
            this.rptEsiti.DataSource = null;
            this.rptEsiti.DataBind();

            var idComune = this.txtAlias.Value;
            var software = this.txtSoftware.Value;

            if (this.fileUpload.PostedFile.ContentLength == 0)
            {
                this.Master.Errori.Add("Nessun file caricato");
            }

            if (String.IsNullOrEmpty(idComune))
            {
                this.Master.Errori.Add("Il campo idComune è obbligatorio");
            }

            if (String.IsNullOrEmpty(software))
            {
                this.Master.Errori.Add("Il campo software è obbligatorio");
            }

            if (this.Master.Errori.Count > 0)
            {
                return false;
            }

            var authInfo = this.AuthenticationManager.GetTokenApplicativo(idComune);

            using (var db = authInfo.CreateDatabase())
            {
                var importService = new ImportSchedeDinamicheService(db, authInfo.IdComune);
                var validation = importService.Verifica(software, this.EstraiSchedaDinamicaDaFileJson(this.fileUpload.PostedFiles[0]));

                foreach (var error in validation)
                {
                    this.Master.Errori.Add(error);
                }
            }

            return this.Master.Errori.Count == 0;
        }

        private SchedaDinamicaEsportata EstraiSchedaDinamicaDaFileJson(HttpPostedFile httpPostedFile)
        {
            // Carica il file postato ne effettua il parsing da json a oggetto
            return SchedaDinamicaEsportata.FromJson(httpPostedFile.InputStream);
        }

        protected void btnImporta_Click(object sender, EventArgs e)
        {
            var esitoVerifica = this.Verifica();

            if (!esitoVerifica)
            {
                return;
            }

            var alias = this.txtAlias.Value;
            var software = this.txtSoftware.Value;

            var authInfo = this.AuthenticationManager.GetTokenApplicativo(alias);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    var importService = new ImportSchedeDinamicheService(db, authInfo.IdComune);

                    var result = importService.Import(software, this.EstraiSchedaDinamicaDaFileJson(this.fileUpload.PostedFiles[0]));

                    var messaggi = new List<MessaggioEsito>();

                    messaggi.Add(MessaggioEsito.Successo($"Modello importato con successo. Nuovo id modello: {result.IdModello.NewId}"));

                    foreach (var campo in result.IdCampi)
                    {
                        messaggi.Add(MessaggioEsito.Successo($"Campo importato con successo. Vecchio id: {campo.OldId}, nuovo id: {campo.NewId}"));
                    }
                    foreach (var testo in result.IdTesti)
                    {
                        messaggi.Add(MessaggioEsito.Successo($"Testo importato con successo. Vecchio id: {testo.OldId}, nuovo id: {testo.NewId}"));
                    }
                    foreach (var avviso in result.AvvisiScript)
                    {
                        messaggi.Add(MessaggioEsito.Warning(avviso));
                    }

                    this.rptEsiti.DataSource = messaggi;
                    this.rptEsiti.DataBind();
                }
                catch (Exception ex)
                {
                    this.Master.Errori.Add(ex.Message);
                }
            }
        }
    }
}