using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.AllegatiMultipli;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Endoprocedimenti;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneAllegatiMultipli : IstanzeStepPage
    {
        [Inject]
        protected AllegatiInterventoService AllegatiInterventoService { get; set; }

        [Inject]
        protected IAllegatiEndoprocedimentiService AllegatiEndoService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(GestioneAllegatiMultipli));


        private static class ProvenienzaAllegatoConstants
        {
            public const char Intervento = 'I';
            public const char Endoprocedimento = 'E';
        }

        public string ReturnTo
        {
            get { return this.Request.QueryString["ReturnTo"]; }
        }

        private ProveninzaAllegatoEnum ProvenienzaAllegato
        {
            get
            {
                var src = this.Request.QueryString["src"][0];

                if (src == ProvenienzaAllegatoConstants.Intervento)
                {
                    return ProveninzaAllegatoEnum.Intervento;
                }
                else
                {
                    return ProveninzaAllegatoEnum.Endoprocedimento;
                }

                throw new InvalidOperationException("Provenienza allegato non valida: " + src);
            }
        }

        private int IdAllegato
        {
            get
            {
                return Convert.ToInt32(this.Request.QueryString["src"].Substring(1));
            }
        }

        private AllegatiMultipliUploaderFactory _uploaderFactory;

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraDescrizioneStep = false;
            this.Master.MostraPaginatoreSteps = false;
            this.Master.ForzaTitoloStep = "Caricamento allegati multipli";

            this._uploaderFactory = new AllegatiMultipliUploaderFactory(this.IdDomanda, this.AllegatiInterventoService, this.AllegatiEndoService);

            this.DataBind();
        }

        public override void DataBind()
        {
            var uploader = this._uploaderFactory.Get(this.ProvenienzaAllegato);
            var allegatoOrigine = uploader.GetById(this.IdAllegato);

            this.ltrDescrizioneAllegato.Text = allegatoOrigine.Descrizione;
        }

        protected void cmdConfirmUpload_Click(object sender, EventArgs e)
        {
            var filesValidi = new List<BinaryFile>();

            for (int i = 0; i < this.Request.Files.Count; i++)
            {
                var f = this.Request.Files[i];

                if (f.ContentLength == 0)
                {
                    continue;
                }

                filesValidi.Add(new WebFormsBinaryFile(f, this._validPostedFileSpecification));
            }

            if (filesValidi.Count() == 0)
            {
                this.Errori.Add("Caricare uno o più files");

                return;
            }

            try
            {
                var uploader = this._uploaderFactory.Get(this.ProvenienzaAllegato);
                var allegatoOrigine = uploader.GetById(this.IdAllegato);

                for (int i = 0; i < filesValidi.Count; i++)
                {
                    var file = filesValidi[i];

                    if (i == 0)
                    {
                        uploader.AggiungiAllegatoPrincipale(allegatoOrigine, file);
                    }
                    else
                    {
                        uploader.AggiungiAllegatoSecondario(allegatoOrigine, (i + 1), file);
                    }
                }

                this.cmdCancelupload_Click(this, EventArgs.Empty);

            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante il caricamento: " + ex.Message);

                this._log.ErrorFormat("Errore durante il caricamento di allegati multipli: " + ex.ToString());
            }
        }

        protected void cmdCancelupload_Click(object sender, EventArgs e)
        {
            this.Response.Redirect(this.ReturnTo);
        }
    }
}