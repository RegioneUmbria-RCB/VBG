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

using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AlboPretorioService;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class PubblicazioniAlboPretorio : PublicBasePage
    {
		[Inject]
		public IAlboPretorioRepository _alboPretorioRepository { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!Page.IsPostBack)
                CaricaDefault();
        }

        #region Calendario

        protected void calValidiAl_SelectionChanged(object sender, EventArgs e)
        {
            txtValidaAl.Text = calValidiAl.SelectedDate.ToString("dd/MM/yyyy");
            calValidiAl.Visible = false;
        }

        protected void imgCalendar_Click(object sender, ImageClickEventArgs e)
        {
            DateTime dt = new DateTime();

            bool isValidDate = String.IsNullOrEmpty(txtValidaAl.Text) ? true : DateTime.TryParse(txtValidaAl.Text, out dt);
            if (!isValidDate)
            {
                txtValidaAl.Text = DateTime.Now.ToString("dd/MM/yyyy");
                calValidiAl.SelectedDate = DateTime.Now;
            }
            else
                calValidiAl.SelectedDate = dt;

            calValidiAl.Visible = !calValidiAl.Visible;
        }

        #endregion        

        #region Cerca

        private void BindCategorie()
        {
            try
            {
				List<ListaCategorie> list = _alboPretorioRepository.GetCategorie(IdComune, Software);
                List<ListaCategorie> listToBind = new List<ListaCategorie>();

                foreach (ListaCategorie l in list)
                {
                    l.DESCRIZIONE = l.DESCRIZIONE.ToUpper();
                    listToBind.Add(l);
                }

                ddlCategoriaSearch.DataSource = listToBind;
                ddlCategoriaSearch.DataBind();

                ddlCategoriaSearch.Items.Insert(0, new ListItem("", ""));

            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            try
            {
                DateTime dt = new DateTime();
                bool isValidDate = String.IsNullOrEmpty(txtValidaAl.Text) ? true : DateTime.TryParse(txtValidaAl.Text, out dt);

                if (!isValidDate)
                    throw new Exception("La data non è valida");
                
                gvAlboPretorio.SelectedIndex = -1;
                pnDettaglio.Visible = false;
                pnLista.Visible = true;
                BindAlboPretorio();
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
			Session["DATI_RICERCA"] = null;
            ClientScript.RegisterStartupScript(this.GetType(), "load", "<script type='text/javascript'>window.close();</script>");
        }

        #endregion

        #region Lista

        private void BindAlboPretorio()
        {
            try
            {
                PubblicazioniValideAlRequest l = new PubblicazioniValideAlRequest();

                l.oggetto = txtOggettoSearch.Text;
                l.categoria = String.IsNullOrEmpty(ddlCategoriaSearch.SelectedValue) ? 0 : int.Parse(ddlCategoriaSearch.SelectedValue);
                l.dataValiditaAl = String.IsNullOrEmpty(txtValidaAl.Text) ? (DateTime?)null : DateTime.Parse(txtValidaAl.Text);

				List<ListaPubblicazioniValideAl> list = _alboPretorioRepository.GetPubblicazioni(l, IdComune, Software);

                Session["DATI_RICERCA"] = list;

                gvAlboPretorio.DataSource = Session["DATI_RICERCA"];
                gvAlboPretorio.DataBind();
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        protected void gvAlboPretorio_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {
                int idPubblicazione = int.Parse(gvAlboPretorio.DataKeys[gvAlboPretorio.SelectedIndex].Values["ID"].ToString());
                //mvAlboPretorio.ActiveViewIndex = 2;
                pnDettaglio.Visible = true;
                BindDettaglio(idPubblicazione);
            }
            catch (Exception ex)
            {
                //mvAlboPretorio.ActiveViewIndex = 1;
                Errori.Add(ex.Message);
            }
        }

        protected void gvAlboPretorio_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            pnDettaglio.Visible = false;
            gvAlboPretorio.SelectedIndex = -1;

            gvAlboPretorio.PageIndex = e.NewPageIndex;
            gvAlboPretorio.DataSource = Session["DATI_RICERCA"];
            gvAlboPretorio.DataBind();
        }

        #endregion

        #region Dettaglio

        private void BindDettaglio(int idPubblicazione)
        {
            try
            {
                DettaglioPubblicazioneRequest req = new DettaglioPubblicazioneRequest();
                req.idPubblicazione = idPubblicazione;

				DettaglioPubblicazioneResponse resp = _alboPretorioRepository.GetDettaglioPubblicazioni(req, IdComune, Software);

                lblNumero.Text = resp.NUMERO_PUBBLICAZIONE;
                lblAnno.Text = resp.DATA_PUBBLICAZIONE.HasValue ? resp.DATA_PUBBLICAZIONE.Value.ToString("yyyy") : String.Empty;
                lblOggettoDelibera.Text = resp.DESCRIZIONE;
                lblCategoria.Text = resp.ALBO_CATEGORIE_DESCR;
                lblUfficio.Text = resp.AMMINISTRAZIONE_DESCR;
                lblProtocollo.Text = resp.NUMERO_PROTOCOLLO;
                lblAnnoProtocollo.Text = resp.DATA_PROTOCOLLO.HasValue ? resp.DATA_PROTOCOLLO.Value.ToString("yyyy") : String.Empty;
                lblValiditaDal.Text = resp.VALIDA_DAL.HasValue ? resp.VALIDA_DAL.Value.ToString("dd/MM/yyyy") : String.Empty;
                lblValiditaAl.Text = resp.VALIDA_AL.HasValue ? resp.VALIDA_AL.Value.ToString("dd/MM/yyyy") : String.Empty;
                lblAnnotazioni.Text = resp.NOTE;
                
                List<pubblicazioniAllegati> list = new List<pubblicazioniAllegati>();
                pnAllegati.Visible = resp.ListaPubblicazioniAllegati.pubblicazioniAllegati != null;

                if (resp.ListaPubblicazioniAllegati.pubblicazioniAllegati != null)
                {
                    foreach (pubblicazioniAllegati p in resp.ListaPubblicazioniAllegati.pubblicazioniAllegati)
                    {
                        if (p.CODICEOGGETTO != 0)
                            list.Add(p);
                    }
                }

                rptAllegati.DataSource = list;
                rptAllegati.DataBind();
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            pnDettaglio.Visible = false;
        }

        #endregion

        #region Generale

        private void CaricaDefault()
        {
            BindCategorie();
            txtValidaAl.Text = DateTime.Now.ToString("dd/MM/yyyy");
            BindAlboPretorio();
        }

        #endregion

    }
}
