using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.AllegatiMultipli;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneAllegatiMultipli : IstanzeStepPage
    {
        [Inject]
        protected AllegatiInterventoService AllegatiInterventoService { get; set; }

        [Inject]
        protected AllegatiEndoprocedimentiService AllegatiEndoService { get;set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        ILog _log = LogManager.GetLogger(typeof(GestioneAllegatiMultipli));


        private static class ProvenienzaAllegatoConstants
        {
            public const char Intervento = 'I';
            public const char Endoprocedimento = 'E';
        }

        public string ReturnTo
        {
            get { return Request.QueryString["ReturnTo"]; }
        }

        private ProveninzaAllegatoEnum ProvenienzaAllegato
        {
            get
            {
                var src = Request.QueryString["src"][0];

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
                return Convert.ToInt32(Request.QueryString["src"].Substring(1));
            }
        }

        AllegatiMultipliUploaderFactory _uploaderFactory;

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraDescrizioneStep = false;
            this.Master.MostraPaginatoreSteps = false;
            this.Master.ForzaTitoloStep = "Caricamento allegati multipli";

            this._uploaderFactory = new AllegatiMultipliUploaderFactory(IdDomanda, this.AllegatiInterventoService, this.AllegatiEndoService);

            DataBind();
        }

        public override void DataBind()
        {
            var uploader = this._uploaderFactory.Get(this.ProvenienzaAllegato);
            var allegatoOrigine = uploader.GetById(IdAllegato);

            this.ltrDescrizioneAllegato.Text = allegatoOrigine.Descrizione;
        }

        protected void cmdConfirmUpload_Click(object sender, EventArgs e)
        {
            var filesValidi = new List<BinaryFile>();

            for (int i = 0; i < Request.Files.Count; i++)
            {
                var f = Request.Files[i];

                if (f.ContentLength == 0)
                {
                    continue;
                }

                filesValidi.Add(new BinaryFile(f, this._validPostedFileSpecification));
            }

            if (filesValidi.Count() == 0)
            {
                Errori.Add("Caricare uno o più files");

                return;
            }

            try
            {
                var uploader = this._uploaderFactory.Get(this.ProvenienzaAllegato);
                var allegatoOrigine = uploader.GetById(IdAllegato);

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

                cmdCancelupload_Click(this, EventArgs.Empty);

            }catch(Exception ex)
            {
                Errori.Add("Si è verificato un errore durante il caricamento: " + ex.Message);

                _log.ErrorFormat("Errore durante il caricamento di allegati multipli: " + ex.ToString());
            }
        }

        protected void cmdCancelupload_Click(object sender, EventArgs e)
        {
            Response.Redirect(ReturnTo);
        }
    }
}