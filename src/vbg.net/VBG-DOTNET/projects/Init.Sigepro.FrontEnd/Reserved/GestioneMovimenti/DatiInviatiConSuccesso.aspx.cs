using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class DatiInviatiConSuccesso : ReservedBasePage
    {
        [Inject]
        protected IMovimentiDaEffettuareRepository _movimentiDaEffettuareRepository { get; set; }

        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazioni { get; set; }

        public QsUuidIstanza UuidIstanza => new QsUuidIstanza(this.Request.QueryString);

        protected string MessaggioDiSuccesso
        {
            get
            {
                var messaggio = this._parametriIntegrazioni.Parametri.MessaggioTermineInvio;
                if (!String.IsNullOrEmpty(messaggio))
                {
                    return messaggio;
                }
                return "La documentazione è stata trasmessa correttamente. La relativa ricevuta protocollata è visionabile tra gli allegati del movimento appena eseguito.";
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.cmdGotoPratica.Visible = this.UuidIstanza.HasValue;
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var redirUrl = UrlBuilder.Url("~/reserved/benvenuto.aspx", mp =>
            {
                mp.Add(new QsAliasComune(this.IdComune));
                mp.Add(new QsSoftware(this.Software));
            });

            this.Response.Redirect(redirUrl);
        }

        protected void cmdVisualizzaDati_Click(object sender, EventArgs e)
        {
            var redirUrl = UrlBuilder.Url("~/reserved/DettaglioIstanzaEx.aspx", mp =>
            {
                mp.Add(new QsAliasComune(this.IdComune));
                mp.Add(new QsSoftware(this.Software));
                mp.Add(this.UuidIstanza);
            });

            this.Response.Redirect(redirUrl);
        }
    }
}