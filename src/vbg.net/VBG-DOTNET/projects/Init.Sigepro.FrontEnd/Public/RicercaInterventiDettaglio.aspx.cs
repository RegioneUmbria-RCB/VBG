using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda;
using Init.Sigepro.FrontEnd.Contenuti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class RicercaInterventiDettaglio : ContenutiBasePage
    {
        [Inject]
        protected RiepilogoDomandaAllegatoService _riepilogoDomandaService { get; set; }

        public new string IdComune
        {
            get { return this.Request.QueryString["IdComune"]; }
        }


        public string Id
        {
            get { return this.Request.QueryString["Id"]; }
        }

        protected bool ModelloDomandaPresente
        {
            get
            {
                var codiceOggettoRiepilogo = this._riepilogoDomandaService.GetCodiceOggettoDelModelloDiRiepilogo(Convert.ToInt32(this.Id));

                return codiceOggettoRiepilogo.HasValue;
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public string GetUrlStampaPagina()
        {
            return this.GetBaseUrlAssoluto() + "Public/MostraDettagliIntervento.aspx?idComune=" + this.IdComune + "&Id=" + this.Id + "&Print=true";
        }

        public string GetUrlDownloadPagina()
        {
            var downloadUrl = this.GetUrlStampaPagina();

            return this.ResolveClientUrl("~/Public/DownloadPage.ashx") + "?IdComune=" + this.IdComune + "&url=" + this.Server.UrlEncode(downloadUrl);
        }

        public string GetUrlEndoAttivabili()
        {
            return this.ResolveClientUrl("~/Public/ListaEndoAttivabili.aspx") + "?IdComune=" + this.IdComune + "&intervento=" + this.Id + "&fromAreaRiservata=false";
        }

        public override void DataBind()
        {
        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            this.Response.Redirect("~/Public/RicercaInterventi.aspx?IdComune=" + this.IdComune + "&Software=" + this.Software);
        }
    }
}