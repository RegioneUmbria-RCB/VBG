using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Commissioni;
using Ninject;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.Commissioni
{
    public partial class dettaglio_pratica : CommissioniBasePage
    {
        public QsIdCommissione IdCommissione => new QsIdCommissione(this.Request.QueryString);
        public QsUuidIstanza UuidIstanza => new QsUuidIstanza(this.Request.QueryString);

        [Inject]
        protected ICommissioniService _commissioniService { get; set; }

        [Inject]
        protected IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        [Inject]
        public IVotazioniCommissioniService _votazioniCommissioniService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var pratica = this._commissioniService.GetDettaglioPraticaPerUtenteCorrente(this.IdCommissione.Value, this.UuidIstanza.Value);

            if (pratica == null)
            {
                this.ErroreAccesso();
                return;
            }

            this.lblComune.Value = pratica.DatiPratica.Comune;
            this.lblNumero.Value = pratica.DatiPratica.NumeroData;
            this.lblProtocollo.Value = pratica.DatiPratica.DatiProtocollo;
            this.lblRichiedente.Value = pratica.DatiPratica.Richiedente;
            this.lblIntervento.Value = pratica.DatiPratica.Intervento;
            this.lblLavori.Value = pratica.DatiPratica.DescrizioneLavori;
            this.lblDescrizioneParere.Value = pratica.DatiPratica.DescrizioneParere;
            this.lblParereEsteso.Value = pratica.DatiPratica.ParereEsteso;

            this.gvLocalizzazioni.DataSource = pratica.Localizzazioni;
            this.gvLocalizzazioni.DataBind();

            this.fsLocalizzazioni.Visible = pratica.Localizzazioni.Any();
            this.fsParere.Visible = !String.IsNullOrEmpty(pratica.DatiPratica.DescrizioneParere);

            // Visualizzazione del div del voto espresso
            this.fsParereEspresso.Visible = false;


            var parere = this._votazioniCommissioniService.GetVotoUtenteLoggato(this.IdCommissione.Value, this.UuidIstanza.Value);

            if (parere != null && parere.Voto != null)
            {
                this.lblVotoEspresso.Value = parere.Voto.DescrizioneParere;
                this.lblNoteVoto.Value = parere.Voto.Note;
                this.lblProtocolloVoto.Value = String.IsNullOrEmpty(parere.Voto.NumeroProtocollo) ?
                                            "Non protocollato" :
                                            $"N.{parere.Voto.NumeroProtocollo} del {parere.Voto.DataProtocollo}";

                this.fsParereEspresso.Visible = true;
                this.cmdParere.Visible = false;

                this.MessaggiInformativi.Add("Hai già espresso un parere per questa pratica");
            }
            else
            {
                this.cmdParere.Visible = this._votazioniCommissioniService.UtenteLoggatoPuoEsprimereVoto(this.IdCommissione.Value, this.UuidIstanza.Value);
            }

            var documenti = pratica.Documenti.Istanza
                                                    .Union(pratica.Documenti.Endoprocedimenti)
                                                    .Union(pratica.Documenti.Movimenti)
                                                    .OrderBy(x => x.Categoria)
                                                    .ThenBy(x => x.Descrizione);

            this.gvDocumenti.DataSource = documenti;
            this.gvDocumenti.DataBind();

            this.fsDocumenti.Visible = documenti.Any();
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {

            var url = UrlBuilder.Url("~/reserved/commissioni/dettaglio-commissione.aspx", mp =>
            {
                mp.Add(new QsAliasComune(this.IdComune));
                mp.Add(new QsSoftware(this.Software));
                mp.Add(this.IdCommissione);
            });

            this.Response.Redirect(url);
        }

        protected void gvDocumenti_SelectedIndexChanged(object sender, EventArgs e)
        {
            var codiceOggetto = (int)this.gvDocumenti.DataKeys[this.gvDocumenti.SelectedIndex].Values[0];
            var firmato = (bool)this.gvDocumenti.DataKeys[this.gvDocumenti.SelectedIndex].Values[1];

            var verificaAccesso = this._commissioniService.VerificaAccessoAFilePerUtenteCorrente(this.IdCommissione.Value, this.UuidIstanza.Value, codiceOggetto);

            if (!verificaAccesso)
            {
                this.ErroreAccesso();
                return;
            }

            //if (firmato)
            //{
            //    var url = this._urlDownloadOggettiService.GetUrlDownloadFirmato(codiceOggetto);
            //    Response.Redirect(url);
            //    return;
            //}

            var urlDownload = this._urlDownloadOggettiService.GetUrlDownload(codiceOggetto);

            var script = $"window.open('{this.ResolveClientUrl(urlDownload)}');";

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "apriDownload", script, true);
        }

        protected void lnkParere_Click(object sender, EventArgs e)
        {

            var url = UrlBuilder.Url("~/reserved/commissioni/parere-pratica.aspx", mp =>
            {
                mp.Add(new QsAliasComune(this.IdComune));
                mp.Add(new QsSoftware(this.Software));
                mp.Add(this.IdCommissione);
                mp.Add(this.UuidIstanza);
            });

            this.Response.Redirect(url);
        }

    }
}