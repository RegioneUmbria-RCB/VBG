using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using Init.SIGePro.Data;
using Init.SIGePro;

namespace Sigepro.net.Configurazione
{
    public partial class GestioneLayoutTesti : BasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

            if (!IsAjaxPostBack(Request))
            {
                ResponsabiliMgr rMgr = new ResponsabiliMgr(Database);

                try
                {
                    Responsabili r = rMgr.GetById(AuthenticationInfo.CodiceResponsabile.ToString(), IdComune);

                    if (r != null)
                    {
                        if (r.AMMINISTRATORE == "0")
                            throw new AccessoNegatoException("L'accesso è stato negato, solamente un utente amministratore è abilitato alla gestione della pagina selezionata");
                    }
                    else
                        throw new AccessoNegatoException("L'accesso è stato negato, la classe responsabili non è stata valorizzata");
                    
                    CaricaLista(String.Empty);
                }
                catch (AccessoNegatoException ex)
                {
                    MostraErrore(ex);
                }
            }
        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            gvLista.EditIndex = -1;
            CaricaLista(txtCercaTesto.Value);
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            CloseCurrentPage();
        }

        #region Lista

        protected void CaricaLista(string filtro)
        {
            LayoutTestiBaseMgr tb = new LayoutTestiBaseMgr(Database);

            //Viene passato sempre TT in quanto la ricerca per software viene effettuata dopo
            //durante l'evento RowDataBound nel metodo GetTestoBase e GetLayoutTestiClass

            ConfigurazioneUtenteMgr cfg = new ConfigurazioneUtenteMgr(Database);

            gvLista.AllowPaging = true;
            //gvLista.PageSize = cfg.GetValoreNumRecordListe(IdComune, AuthenticationInfo.CodiceResponsabile.Value);

            Session["DATI_RICERCA"] = tb.Find(filtro, "TT");

            BindLista();
        }

        protected void BindLista()
        {
            gvLista.DataSource = Session["DATI_RICERCA"];
            gvLista.DataBind();        
        }

        protected void gvLista_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvLista.EditIndex = -1;
            gvLista.PageIndex = e.NewPageIndex;
            gvLista.DataSource = Session["DATI_RICERCA"];
            gvLista.DataBind();
        }

        protected string GetTestoBase(object codiceTesto, object testoTT)
        {

            string retVal = (testoTT??"").ToString();

            LayoutTestiBaseMgr mgr = new LayoutTestiBaseMgr(Database);
            LayoutTestiBase l = mgr.GetByCodice(codiceTesto.ToString(), Software);

            if (l != null)
                retVal = l.Testo;

            return retVal;
        }

        protected LayoutTesti GetLayoutTestiClass(object codiceTesto)
        {
            LayoutTestiMgr mgr = new LayoutTestiMgr(Database);
            LayoutTesti c = mgr.GetByCodice(codiceTesto.ToString(), IdComune, Software);

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
            gvLista.EditIndex = e.NewEditIndex;
            BindLista();
        }

        protected void gvLista_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
        {
            gvLista.EditIndex = -1;
            BindLista();
        }

        protected void gvLista_RowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            GridViewRow r = gvLista.Rows[e.RowIndex];

            TextBox txtNuovoTesto = r.FindControl("txtNuovoTesto") as TextBox;
            Label lblCodiceTesto = r.FindControl("lblCodiceTesto") as Label;
            
            LayoutTestiMgr mgr = new LayoutTestiMgr(Database);
            LayoutTesti c = mgr.GetById(IdComune, Software, lblCodiceTesto.Text);

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

                    c.Idcomune = IdComune;
                    c.Software = Software;
                    c.Codicetesto = lblCodiceTesto.Text;
                    c.Nuovotesto = txtNuovoTesto.Text;

                    mgr.Insert(c);
                }
            }
            gvLista.EditIndex = -1;
            CaricaLista(txtCercaTesto.Value);
        }

        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                if ( (e.Row.RowState & DataControlRowState.Edit) != DataControlRowState.Edit)
                {
                    Label lblCodiceTesto = e.Row.FindControl("lblCodiceTesto") as Label;
                    Label lblNuovoTesto = e.Row.FindControl("lblNuovoTesto") as Label;
                    Label lblSoftware = e.Row.FindControl("lblSoftware") as Label;

                    LayoutTesti lt = GetLayoutTestiClass(lblCodiceTesto.Text);

                    lblNuovoTesto.Text = lt.Nuovotesto;
                    lblSoftware.Text = lt.Software;

                    ImageButton imgEdit = e.Row.FindControl("imgEdit") as ImageButton;
                    ImageButton imgDelete = e.Row.FindControl("imgDelete") as ImageButton;

                    if (Software != "TT")
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
            GridViewRow r = gvLista.Rows[e.RowIndex];
            Label lblCodiceTesto = r.FindControl("lblCodiceTesto") as Label;
            
            LayoutTestiMgr mgr = new LayoutTestiMgr(Database);
            LayoutTesti c = mgr.GetById(IdComune, Software, lblCodiceTesto.Text);

            if (c != null)
                mgr.Delete(c);

            CaricaLista(txtCercaTesto.Value);
        }

        #endregion
        
    }
}
