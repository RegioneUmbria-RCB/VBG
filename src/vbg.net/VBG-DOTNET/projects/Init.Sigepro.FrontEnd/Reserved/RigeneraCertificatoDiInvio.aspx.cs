using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class RigeneraCertificatoDiInvio : ReservedBasePage
    {
        private static class Constants
        {
            public const string ParametroIdDomandaBackoffice = "IdDomandaBackoffice";
            public const string ParametroAllegaAPratica = "allega";
        }

        private string IdDomandaBackoffice
        {
            get { return this.Request.QueryString[Constants.ParametroIdDomandaBackoffice]; }
        }

        private bool AllegaAPratica
        {
            get
            {
                if (String.IsNullOrEmpty(this.Request.QueryString[Constants.ParametroAllegaAPratica]))
                {
                    return true;
                }
                return this.Request.QueryString[Constants.ParametroAllegaAPratica].ToString() != "0";
            }
        }

        [Inject]
        protected CertificatoDiInvioService _certificatoDiInvioService { get; set; }
        [Inject]
        protected IStcService _stcService { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            //var domanda = SalvataggioDomandaStrategy.GetById( IdDomanda );

            if (!this._stcService.PraticaEsisteNelBackend(this.IdDomandaBackoffice))
                throw new Exception("La pratica " + this.IdDomandaBackoffice + " non esiste nel backend");

            var cert = this._certificatoDiInvioService.GeneraCertificatoDiInvio(Convert.ToInt32(this.IdDomandaBackoffice), this.AllegaAPratica);

            this.Response.Clear();
            this.Response.ContentType = cert.MimeType;
            this.Response.AddHeader("content-disposition", "attachment;filename=\"" + cert.FileName + "\"");
            this.Response.BinaryWrite(cert.FileContent);
            this.Response.End();
        }
    }
}