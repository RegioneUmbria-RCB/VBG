using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg.Zip;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.AccessoAtti;
using Microsoft.VisualStudio.Threading;
using Ninject;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Reserved.accesso_atti
{
    public partial class accesso_atti_dettaglio : ReservedBasePage
    {

        [Inject]
        public IVbgAccessoAttiService _accessoAttiService { get; set; }
        [Inject]
        public IZipAccessoAttiService _zipAccessoAttiService { get; set; }

        [Inject]
        public IConfigurazione<ParametriAccessoAtti> _configurazione { get; set; }

        protected QsUuidIstanza UuidIstanza => new QsUuidIstanza(this.Request.QueryString);
        protected QsIdAccessoAtti IdAccessoAtti => new QsIdAccessoAtti(this.Request.QueryString);

        public bool MostraBottoneDownloadZip { get; set; } = false;

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            // Loggo l'accesso
            var codiceAnagrafe = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;
            var uuid = this.UuidIstanza.Value;
            var idAccessoAtti = this.IdAccessoAtti.Value;

            this._accessoAttiService.LogAccessoPratica(codiceAnagrafe, idAccessoAtti, uuid);

            var livelloAccessoDocumenti = this._accessoAttiService.GetLivelloAccessoDocumenti(idAccessoAtti, uuid);

            this.VisuraExCtrl1.LivelloAccesso = LivelloAccessoVisura.DaValoreFlag(
                LivelloAccessoVisura.Constants.DatiGenerali |
                LivelloAccessoVisura.Constants.Schede |
                LivelloAccessoVisura.Constants.Documenti |
                LivelloAccessoVisura.Constants.Endoprocedimenti |
                (this._configurazione.Parametri.MostraDatiMovimenti ? LivelloAccessoVisura.Constants.MovimentiEffettuati : 0)
                );

            this.VisuraExCtrl1.CallbackValidazioneAllegati = (doc) =>
            {
                if (doc.ContieneDatiSensibili)
                {
                    return false;
                }

                switch (livelloAccessoDocumenti)
                {
                    case 0:
                        return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido;
                    case 2:
                        return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido ||
                                doc.EsitoVerifica == StatoVerificaDocumentoEnum.DaVerificare;
                }

                return true;
            };

            this.VisuraExCtrl1.MostraScadenze = false;
            this.VisuraExCtrl1.MostraPraticheCollegate = false;
            this.VisuraExCtrl1.EffettuaVisuraIstanza(uuid);

            this.MostraBottoneDownloadZip = this._accessoAttiService.GetCodiciOggettoScaricabiliComeZip(idAccessoAtti, uuid).Any();
        }

        private void TornaAllaLista()
        {
            var url = UrlBuilder.Url("~/reserved/accesso-atti/accesso-atti-list.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
            });

            this.Response.Redirect(url);
        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            this.TornaAllaLista();
        }

        protected void cmdDownloadZip_Click(object sender, EventArgs e)
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());

            jtf.Run(() => this.DownloadZipAsync());

            // this.RegisterAsyncTask(new PageAsyncTask(this.DownloadZipAsync));
        }

        private async Task DownloadZipAsync()
        {
            try
            {
                var zipFile = await this._zipAccessoAttiService.GetZipFileDocumentiAsync(this.IdAccessoAtti.Value, this.UuidIstanza.Value);

                this.BinaryWrite(zipFile);

                this.Response.End();
            }
            catch (Exception)
            {
                this.Errori.Add("Si è verificato un errore durante il download. Riprovare più tardi");
            }
        }
    }
}