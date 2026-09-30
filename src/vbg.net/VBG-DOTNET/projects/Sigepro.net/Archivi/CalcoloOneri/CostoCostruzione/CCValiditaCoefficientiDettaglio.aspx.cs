using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Linq;
using System.Web.UI.WebControls;

namespace Sigepro.net.Archivi.CalcoloOneri.CostoCostruzione
{
    public partial class CCValiditaCoefficientiDettaglio : BasePage
    {
        private class BindingItem
        {
            public string Id { get; set; } = "";
            public string Descrizione { get; set; } = "";
        }

        private int ListinoId
        {
            get
            {

                var listinoId = this.Request.QueryString["ListinoId"];

                if (String.IsNullOrEmpty(listinoId))
                {
                    throw new ArgumentException("ListinoId non valorizzato");
                }
                return Convert.ToInt32(listinoId);
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();

                var interventi = new CCTipoInterventoMgr(this.Database).GetList(this.IdComune, this.Software)
                                                        .Select(x => new BindingItem
                                                        {
                                                            Id = x.Id.ToString(),
                                                            Descrizione = x.Intervento
                                                        }).ToArray();

                var destinazioni = new CCDestinazioniMgr(this.Database).GetList(this.IdComune, this.Software)
                                                        .Select(x => new BindingItem
                                                        {
                                                            Id = x.Id.ToString(),
                                                            Descrizione = x.Destinazione
                                                        }).ToArray();

                this.ddlIntervento.Item.DataSource = interventi;
                this.ddlIntervento.Item.DataBind();

                this.ddlDestinazione.Item.DataSource = destinazioni;
                this.ddlDestinazione.Item.DataBind();
            }
        }


        public override void DataBind()
        {
            var items = new CCValiditaCoefficientiDettaglioMgr(this.Database).GetListByIdListino(this.IdComune, this.ListinoId);

            this.gvLista.DataSource = items;
            this.gvLista.DataBind();
        }

        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            var cmdElimina = e.Row.FindControl("cmdElimina") as LinkButton;

            if (cmdElimina != null)
                this.ImpostaScriptEliminazione(cmdElimina);
        }

        protected void gvLista_RowDeleting(object sender, System.Web.UI.WebControls.GridViewDeleteEventArgs e)
        {
            try
            {
                var id = this.gvLista.DataKeys[e.RowIndex].Value.ToString();
                var key = new CCValiditaCoefficientiDettaglioMgr.BindingItemDataKey(id);

                var mgr = new CCValiditaCoefficientiDettaglioMgr(this.Database);

                mgr.Delete(this.IdComune, this.ListinoId, key.InterventoId, key.DestinazioneId);

                this.DataBind();

                this.bmCreaNuovo.Visible = false;
            }
            catch (Exception ex)
            {
                this.MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
            }
        }

        public void cmdClose_Click(object sender, EventArgs e)
        {
            this.Response.Redirect($"CCValiditaCoefficienti.aspx?software={this.Software}&token={this.Token}&Id={this.ListinoId}");
        }

        protected void bmModificaRiduzioni_OkClicked(object sender, EventArgs e)
        {
            try
            {
                var interventoId = Convert.ToInt32(this.ddlIntervento.Item.SelectedValue);
                var destinazioneId = Convert.ToInt32(this.ddlDestinazione.Item.SelectedValue);
                var importo = this.dtbImporto.Item.ValoreDecimal;

                new CCValiditaCoefficientiDettaglioMgr(this.Database).Insert(this.IdComune, this.ListinoId, interventoId, destinazioneId, importo.Value);

                this.ResetBootstrapModal();
                this.DataBind();
            }
            catch (Exception ex)
            {
                this.MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
            }
        }

        protected void bmModificaRiduzioni_KoClicked(object sender, EventArgs e)
        {
            this.ResetBootstrapModal();
            this.DataBind();
        }

        private void ResetBootstrapModal()
        {
            this.ddlDestinazione.Item.SelectedIndex = 0;
            this.ddlIntervento.Item.SelectedIndex = 0;
            this.dtbImporto.Value = "";
        }

        protected void cmdNew_Click(object sender, EventArgs e)
        {
            this.bmCreaNuovo.Visible = true;
        }
    }
}