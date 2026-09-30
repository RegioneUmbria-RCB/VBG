using Init.SIGePro;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Web.UI.WebControls;

namespace Sigepro.net.Configurazione
{
    public partial class GestioneLayoutTesti : BasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

            if (!this.IsAjaxPostBack(this.Request))
            {
                var rMgr = new ResponsabiliMgr(this.Database);

                try
                {
                    Responsabili r = rMgr.GetById(this.IdComune, this.AuthenticationInfo.CodiceResponsabile.Value);

                    if (r != null)
                    {
                        if (r.AMMINISTRATORE == "0")
                            throw new AccessoNegatoException("L'accesso è stato negato, solamente un utente amministratore è abilitato alla gestione della pagina selezionata");
                    }
                    else
                        throw new AccessoNegatoException("L'accesso è stato negato, la classe responsabili non è stata valorizzata");

                    this.CaricaLista(String.Empty);
                }
                catch (AccessoNegatoException ex)
                {
                    this.MostraErrore(ex);
                }
            }
        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            this.gvLista.EditIndex = -1;
            this.CaricaLista(this.txtCercaTesto.Value);
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this.CloseCurrentPage();
        }

        #region Lista

        protected void CaricaLista(string filtro)
        {
            var tb = new LayoutTestiBaseMgr(this.Database);

            //Viene passato sempre TT in quanto la ricerca per software viene effettuata dopo
            //durante l'evento RowDataBound nel metodo GetTestoBase e GetLayoutTestiClass

            var cfg = new ConfigurazioneUtenteMgr(this.Database);

            this.gvLista.AllowPaging = true;
            //gvLista.PageSize = cfg.GetValoreNumRecordListe(IdComune, AuthenticationInfo.CodiceResponsabile.Value);

            this.Session["DATI_RICERCA"] = tb.Find(filtro, "TT");

            this.BindLista();
        }

        protected void BindLista()
        {
            this.gvLista.DataSource = this.Session["DATI_RICERCA"];
            this.gvLista.DataBind();
        }

        protected void gvLista_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            this.gvLista.EditIndex = -1;
            this.gvLista.PageIndex = e.NewPageIndex;
            this.gvLista.DataSource = this.Session["DATI_RICERCA"];
            this.gvLista.DataBind();
        }

        protected string GetTestoBase(object codiceTesto, object testoTT)
        {

            string retVal = (testoTT ?? "").ToString();

            var mgr = new LayoutTestiBaseMgr(this.Database);
            LayoutTestiBase l = mgr.GetByCodice(codiceTesto.ToString(), this.Software);

            if (l != null)
                retVal = l.Testo;

            return retVal;
        }

        protected LayoutTesti GetLayoutTestiClass(object codiceTesto)
        {
            var mgr = new LayoutTestiMgr(this.Database);
            LayoutTesti c = mgr.GetByCodice(codiceTesto.ToString(), this.IdComune, this.Software);

            if (c == null)
            {
                c = new LayoutTesti();
                c.Software = "TT";
            }

            return c;
        }

        #endregion

        #region Operazioni su Lista

        protected void gvLista_RowEditing(object sender, GridViewEditEventArgs e)
        {
            this.gvLista.EditIndex = e.NewEditIndex;
            this.BindLista();
        }

        protected void gvLista_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            this.gvLista.EditIndex = -1;
            this.BindLista();
        }

        protected void gvLista_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            GridViewRow r = this.gvLista.Rows[e.RowIndex];

            var txtNuovoTesto = r.FindControl("txtNuovoTesto") as TextBox;
            var lblCodiceTesto = r.FindControl("lblCodiceTesto") as Label;

            var mgr = new LayoutTestiMgr(this.Database);
            LayoutTesti c = mgr.GetById(this.IdComune, this.Software, lblCodiceTesto.Text);

            if (c != null)
            {
                if (String.IsNullOrEmpty(txtNuovoTesto.Text))
                    mgr.Delete(c);
                else
                {
                    c.Nuovotesto = txtNuovoTesto.Text;
                    mgr.Update(c);
                }
            }
            else
            {

                if (!String.IsNullOrEmpty(txtNuovoTesto.Text))
                {
                    c = new LayoutTesti();

                    c.Idcomune = this.IdComune;
                    c.Software = this.Software;
                    c.Codicetesto = lblCodiceTesto.Text;
                    c.Nuovotesto = txtNuovoTesto.Text;

                    mgr.Insert(c);
                }
            }
            this.gvLista.EditIndex = -1;
            this.CaricaLista(this.txtCercaTesto.Value);
        }

        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                if ((e.Row.RowState & DataControlRowState.Edit) != DataControlRowState.Edit)
                {
                    var lblCodiceTesto = e.Row.FindControl("lblCodiceTesto") as Label;
                    var lblNuovoTesto = e.Row.FindControl("lblNuovoTesto") as Label;
                    var lblSoftware = e.Row.FindControl("lblSoftware") as Label;

                    LayoutTesti lt = this.GetLayoutTestiClass(lblCodiceTesto.Text);

                    lblNuovoTesto.Text = lt.Nuovotesto;
                    lblSoftware.Text = lt.Software;

                    var imgEdit = e.Row.FindControl("imgEdit") as ImageButton;
                    var imgDelete = e.Row.FindControl("imgDelete") as ImageButton;

                    if (this.Software != "TT")
                    {
                        imgEdit.ImageUrl = lblSoftware.Text == "TT" ? "~/Images/add.gif" : "~/Images/edit.gif";
                        imgDelete.Visible = lblSoftware.Text != "TT";
                        imgEdit.AlternateText = lblSoftware.Text == "TT" ? "Aggiungi il testo visualizzato per il software corrente" : "Modifica il testo visualizzato per il software corrente";
                    }
                    else
                    {
                        if (lt.Nuovotesto == null)
                        {
                            imgEdit.ImageUrl = "~/Images/add.gif";
                            imgDelete.Visible = false;
                            imgEdit.AlternateText = "Aggiungi il testo visualizzato per il software corrente";
                        }
                        else
                        {
                            imgEdit.ImageUrl = "~/Images/edit.gif";
                            imgDelete.Visible = true;
                            imgEdit.AlternateText = "Modifica il testo visualizzato per il software corrente";
                        }
                    }
                }
            }
        }

        protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            GridViewRow r = this.gvLista.Rows[e.RowIndex];
            var lblCodiceTesto = r.FindControl("lblCodiceTesto") as Label;

            var mgr = new LayoutTestiMgr(this.Database);
            LayoutTesti c = mgr.GetById(this.IdComune, this.Software, lblCodiceTesto.Text);

            if (c != null)
                mgr.Delete(c);

            this.CaricaLista(this.txtCercaTesto.Value);
        }

        #endregion

    }
}
