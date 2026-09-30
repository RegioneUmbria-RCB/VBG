using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSTAR;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
using Init.SIGePro.Manager.DTO.Interventi;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class ListaIstanzePresentate : ReservedBasePage
    {
        private static class Constants
        {
            public const string IstanzaConBookmark = "1";
            public const string IstanzaSenzaBookmark = "0";
            public const string UrlIstanzeInSospesoStar = "~/reserved/istanzeInSospesoSTAR.aspx";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(ListaIstanzePresentate));

        [Inject]
        public IInterventiRepository _alberoProcRepository { get; set; }
        [Inject]
        public DomandeOnlineService _datiDomandaService { get; set; }
        [Inject]
        public STARUrlService _starUrlService { get; set; }

        public bool MostraDatiCatastaliEstesi
        {
            get
            {
                var obj = ConfigurationManager.AppSettings["MostraDatiCatastaliEstesi"];

                if (String.IsNullOrEmpty(obj))
                    return false;

                try
                {
                    return Convert.ToBoolean(obj);
                }
                catch (Exception)
                {
                    return false;
                }
            }
        }

        protected bool NoStar
        {
            get
            {
                return this.Request.QueryString["star"] == "0";
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.VerificaRedirectStar();

                this.DataBind();
            }

        }

        private void VerificaRedirectStar()
        {
            if (!this.NoStar && this._starUrlService.StarAttivo())
            {
                var qs = this.Request.QueryString.ToString();
                this.Response.Redirect(String.Format("{0}?{1}", Constants.UrlIstanzeInSospesoStar, qs));
                this.Response.End();
            }
        }

        protected override void DataBind(bool raiseOnDataBinding)
        {
            var codiceAnagrafe = this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value;

            var ds = this._datiDomandaService.GetDomandeInSospeso(codiceAnagrafe);

            this.dgIstanzePresentate.DataSource = ds;
            this.dgIstanzePresentate.DataBind();
        }

        public void dgIstanzePresentate_SelectedIndexChanged(object sender, EventArgs e)
        {
            var key = this.dgIstanzePresentate.DataKeys[this.dgIstanzePresentate.SelectedIndex].Value.ToString();
            var bookmark = (HiddenField)this.dgIstanzePresentate.Rows[this.dgIstanzePresentate.SelectedIndex].FindControl("hidBookmark");
            var redirPage = "~/Reserved/InserimentoIstanza/Benvenuto.aspx";

            if (bookmark != null && bookmark.Value == Constants.IstanzaConBookmark)
            {
                redirPage = "~/Reserved/InserimentoIstanza/BenvenutoBookmark.aspx";
            }

            this.Redirect(redirPage, qs =>
            {
                qs.Add("StepId", "0");
                qs.Add("IdPresentazione", key);

                if (this.NoStar)
                {
                    qs.Add("star", "0");
                }
            });
        }

        public void dgIstanzePresentate_ItemDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var lblRichiedente = (Label)e.Row.FindControl("lblRichiedente");
                var lblTipoIntervento = (Label)e.Row.FindControl("lblTipoIntervento");
                var lblIdDomanda = (Label)e.Row.FindControl("lblIdDomanda");
                var hidBookmark = (HiddenField)e.Row.FindControl("hidBookmark");
                var lblOggetto = (Label)e.Row.FindControl("lblOggetto");

                var datiDomanda = (DatiDomandaOnlineDto)e.Row.DataItem;

                try
                {
                    //var domanda = this._datiDomandaService.GetById(datiDomanda.Id);

                    // Bookmarks
                    hidBookmark.Value = Constants.IstanzaSenzaBookmark;
                    if (!String.IsNullOrEmpty(datiDomanda.Bookmark))
                    {
                        hidBookmark.Value = Constants.IstanzaConBookmark;
                    }

                    // Nominativo
                    lblRichiedente.Text = datiDomanda.Richiedente;
                    lblOggetto.Text = datiDomanda.Oggetto;
                    lblTipoIntervento.Text = (datiDomanda.Intervento ?? "").Replace(Environment.NewLine, "<br />"); ;


                    // oneri in sospeso (se esistono non si può eliminare la domanda)
                    //var oneriInSospeso = domanda.ReadInterface.Oneri.GetOperazioniConPagamentoInSospeso();
                    var chkChecked = e.Row.FindControl("chkChecked");
                    /*
                    var literalOneriInSospeso = new Literal
                    {
                        Text = "<div class='alert alert-warning alert-pagamenti'>Operazione di pagamento in sospeso</div>"
                    };
                    var literalOneriPagatiOnLine = new Literal
                    {
                        Text = "<div class='alert alert-warning alert-pagamenti'>La domanda contiene oneri pagati</div>"
                    };
                    */
                    chkChecked.Visible = !datiDomanda.PagamentoAvviato && !datiDomanda.PagamentoCompletato;

                    var warnings = this.GetWarningsPagamenti(datiDomanda);

                    foreach (var warning in warnings)
                    {
                        e.Row.Cells[1].Controls.Add(new Literal
                        {
                            Text = $"<div class='alert alert-warning alert-pagamenti'>{warning}</div>"
                        });
                    }
                }
                catch (Exception ex)
                {
                    lblTipoIntervento.Text = "Errore nella lettura dei dati della domanda: " + ex.Message;
                }
            }
        }

        private IEnumerable<string> GetWarningsPagamenti(DatiDomandaOnlineDto domanda)
        {
            if (domanda.PagamentoAvviato)
                yield return "Operazione di pagamento in sospeso";

            if (domanda.PagamentoCompletato)
                yield return "La domanda contiene oneri pagati";
        }

        public string StringaIntervento(DomandaOnline domanda)
        {
            var strIntervento = "Non specificato";

            if (domanda.ReadInterface.AltriDati.Intervento == null)
                return strIntervento;

            var idcomune = domanda.DataKey.IdComune;
            var codIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;

            var albero = this._alberoProcRepository.GetAlberaturaNodoDaId(idcomune, codIntervento);

            return this.AttraversaAlbero(albero);

        }

        private string AttraversaAlbero(ClassTree<InterventoDto> albero)
        {
            var str = albero.Elemento.Descrizione;

            str += Environment.NewLine;

            if (albero.NodiFiglio.Any())
                str += this.AttraversaAlbero(albero.NodiFiglio[0]);

            return str;

        }

        protected void cmdDeleteRows_Click(object sender, EventArgs e)
        {
            foreach (GridViewRow it in this.dgIstanzePresentate.Rows)
            {
                var chkChecked = (CheckBox)it.FindControl("chkChecked");

                if (chkChecked.Checked)
                {
                    try
                    {
                        int key = (int)this.dgIstanzePresentate.DataKeys[it.RowIndex].Value;
                        this._datiDomandaService.Elimina(key, this.UserAuthenticationResult.DatiUtente.Codicefiscale);
                    }
                    catch (Exception ex)
                    {
                        var guid = Guid.NewGuid().ToString();
                        var id = this.dgIstanzePresentate.DataKeys[it.RowIndex].Value;
                        this._log.Error($"{guid} Errore durante la cancellazione della pratica in sospeso, id={id}, ex={ex.ToString()}");
                        this.Errori.Add($"Si è verificato un errore inaspettato durante la cancellazione della domanda (dati tecnici: errore={guid}, id={id})");
                    }
                }
            }

            this.DataBind();
        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/benvenuto.aspx", pb =>
            {
                pb.Add(new QsAliasComune(this.IdComune));
                pb.Add(new QsSoftware(this.Software));
            });

            this.Response.Redirect(url);
        }
    }
}
