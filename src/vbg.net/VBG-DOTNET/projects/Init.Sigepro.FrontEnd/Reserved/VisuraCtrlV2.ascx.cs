using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class VisuraCtrlV2 : Ninject.Web.UserControlBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(VisuraCtrlV2));

        public delegate void ErroreRenderingDelegate(object sender, string testoErrore);
        public event ErroreRenderingDelegate ErroreRendering;

        //public delegate void ScadenzaSelezionataDelegate(object sender, int idScadenza);
        //public event ScadenzaSelezionataDelegate ScadenzaSelezionata;

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public bool DaArchivio
        {
            get { var o = this.ViewState["DaArchivio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["DaArchivio"] = value; }
        }

        public bool TempisticheVisibili
        {
            get { var o = this.ViewState["TempisticheVisibili"]; return o == null ? false : (bool)o; }
            set { this.ViewState["TempisticheVisibili"] = value; }
        }

        protected string IdComune
        {
            get { return this.Request.QueryString["IdComune"]; }
        }

        protected string Software
        {
            get { return this.Request.QueryString["Software"]; }
        }

        public DettaglioPraticaVisuraType DataSource { get; set; }

        public VisuraCtrlV2()
        {
            FoKernelContainer.Inject(this);
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            try
            {
                // Tempistica
                if (this.DataSource.tempisticaProcedimento != null)
                {
                    var t = this.DataSource.tempisticaProcedimento;

                    this.TempisticheVisibili = true;

                    if (t.dataInizioSpecified)
                    {
                        this.lblDataInizio.Text = t.dataInizio.ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.lblDataInizio.Text = "Non disponibile";
                    }

                    if (t.dataFineSpecified)
                    {
                        this.lblDataFine.Text = t.dataFine.ToString("dd/MM/yyyy");
                    }
                    else
                    {
                        this.lblDataFine.Text = "Non disponibile";
                    }

                    if (t.durataGGSpecified)
                    {
                        this.lblDurataGiorni.Text = t.durataGG.ToString() + " giorni";
                    }
                    else
                    {
                        this.lblDurataGiorni.Text = "Non disponibile";
                    }
                }

                // Dati domanda
                this.lblIntervento.Text = this.DataSource.dettaglioPratica.intervento.descrizione;
                this.lblNumeroPratica.Text = this.DataSource.dettaglioPratica.numeroPratica;
                this.lblOggetto.Text = this.DataSource.dettaglioPratica.oggetto;
                this.lblProtocollo.Text = this.DataSource.dettaglioPratica.numeroProtocolloGenerale;
                this.lblStatoPratica.Text = this.DataSource.statoPratica.ToString();
                this.lblDataPresentazione.Text = this.DataSource.dettaglioPratica.dataPratica.ToString("dd/MM/yyyy");

                if (this.DataSource.dettaglioPratica.dataProtocolloGeneraleSpecified)
                    this.lblDataProtocollo.Text = this.DataSource.dettaglioPratica.dataProtocolloGenerale.ToString("dd/MM/yyyy");

                // Riferimenti
                this.lblResponsabileProc.Text = this.DataSource.responsabileProcedimento;
                this.lblIstruttore.Text = this.DataSource.istruttorePratica;
                //lblOperatore.Text = DataSource.dettaglioPratica.re

                this.dgSoggetti.DataSource = this.EstraiSoggettiDomanda();
                this.dgSoggetti.DataBind();

                //localizzazioni
                this.dgLocalizzazioni.DataSource = this.DataSource.dettaglioPratica.localizzazione;
                this.dgLocalizzazioni.DataBind();
                this.divLocalizzazioni.Visible = (this.dgLocalizzazioni.DataSource != null);

                this.dgDatiCatastali.DataSource = this.EstraiDatiCatastali();
                this.dgDatiCatastali.DataBind();

                //allegati
                this.divAllegati.Visible = false;
                if (this.DataSource.dettaglioPratica.documenti != null)
                {
                    this.gvAllegati.DataSource = this.DataSource.dettaglioPratica.documenti.Where(x => x.allegati != null);
                    this.gvAllegati.DataBind();
                    this.divAllegati.Visible = (this.gvAllegati.DataSource != null);
                }

                //endoprocedimenti
                this.dgProcedimenti.DataSource = this.DataSource.dettaglioPratica.procedimenti;
                this.dgProcedimenti.DataBind();
                this.divProcedimenti.Visible = (this.dgProcedimenti.DataSource != null);

                //onrei
                this.dgOneri.DataSource = this.DataSource.dettaglioPratica.oneri;
                this.dgOneri.DataBind();
                this.divOneri.Visible = (this.dgOneri.DataSource != null);

                //movimenti
                this.dgMovimenti.DataSource = this.DataSource.listaAttivita;
                this.dgMovimenti.DataBind();
                this.divMovimenti.Visible = (this.dgMovimenti.DataSource != null);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in Visuractrlv2.DataBind: {0}", ex.ToString());

                if (ErroreRendering != null)
                    ErroreRendering(this, ex.Message);
            }

        }

        protected void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        #region allegati

        protected void gvAllegati_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var hlDownloadAllegato = (HyperLink)e.Row.FindControl("hlDownloadAllegato");

                var doc = (DocumentiType)e.Row.DataItem;

                if (String.IsNullOrEmpty(doc.allegati?.id))
                {
                    hlDownloadAllegato.Visible = false;
                }
                else
                {
                    var documentoFirmato = (Path.GetExtension(doc.allegati.allegato).ToUpper() == ".P7M");

                    hlDownloadAllegato.NavigateUrl = this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(doc.allegati.id));
                }
            }
        }

        protected string ProcessaDescrizione(object descr)
        {
            if (descr == null) return "";
            return descr.ToString().Replace("\n", "<br />");

        }

        protected string DescrizioneAttivita(object objAttivita)
        {
            if (objAttivita == null)
            {
                return String.Empty;
            }

            return ((TipoAttivitaType)objAttivita).descrizione;
        }

        protected bool VerificaEsistenzaAllegatiProcedimento(object objProcedimento)
        {
            var endo = (ProcedimentoType)objProcedimento;

            if (endo.documenti != null && endo.documenti.Length > 0)
            {
                foreach (var allegatoEndo in endo.documenti)
                {
                    if (allegatoEndo.allegati != null)
                        return true;
                }
            }

            return false;
        }

        protected bool VerificaEsistenzaAllegatiMovimento(object objMovimentiAllegatiList)
        {
            return false;
            /*var listaAllegati = (MovimentiAllegati[])objMovimentiAllegatiList;

			if (listaAllegati == null || listaAllegati.Length == 0)
				return false;

			for (int i = 0; i < listaAllegati.Length; i++)
			{
				var allegato = listaAllegati[i];

				if (allegato.FlagPubblica.GetValueOrDefault(0) == 0)
					continue;

				if (String.IsNullOrEmpty(allegato.CODICEOGGETTO))
					continue;

				return true;
			}

			return false;*/
        }

        #endregion

        private List<RiferimentoCatastaleType> EstraiDatiCatastali()
        {
            var rVal = new List<RiferimentoCatastaleType>();

            if (this.DataSource.dettaglioPratica.localizzazione != null)
            {
                foreach (var indirizzo in this.DataSource.dettaglioPratica.localizzazione)
                {
                    if (indirizzo.riferimentoCatastale != null)
                        rVal.AddRange(indirizzo.riferimentoCatastale);
                }
            }

            return rVal;
        }

        #region dati anagrafici

        protected class DatiRichiedente
        {
            public string Nominativo { get; set; }
            public string InQualitaDi { get; set; }
            public string NominativoCollegato { get; set; }
            public string Procuratore { get; set; }
        }

        private List<DatiRichiedente> EstraiSoggettiDomanda()
        {
            var listaRichiedenti = new List<DatiRichiedente>();

            listaRichiedenti.Add(new DatiRichiedente
            {
                Nominativo = this.DataSource.dettaglioPratica.richiedente.anagrafica.ToString(),
                InQualitaDi = this.DataSource.dettaglioPratica.richiedente.ruolo == null ? String.Empty : this.DataSource.dettaglioPratica.richiedente.ruolo.ToString()
            });

            if (this.DataSource.dettaglioPratica.aziendaRichiedente != null)
            {
                listaRichiedenti.Add(new DatiRichiedente
                {
                    Nominativo = this.DataSource.dettaglioPratica.aziendaRichiedente.ToString()
                });
            }
            if (this.DataSource.dettaglioPratica.intermediario != null && this.DataSource.dettaglioPratica.intermediario.Item != null)
            {
                listaRichiedenti.Add(new DatiRichiedente
                {
                    Nominativo = this.DataSource.dettaglioPratica.intermediario.Item.ToString()
                });
            }

            if (this.DataSource.dettaglioPratica.altriSoggetti != null)
            {
                foreach (var it in this.DataSource.dettaglioPratica.altriSoggetti)
                {
                    listaRichiedenti.Add(new DatiRichiedente
                    {
                        Nominativo = it.soggetto.Item.ToString(),
                        InQualitaDi = it.tipoRapporto.ruolo,
                        NominativoCollegato = (it.anagraficaCollegata != null && it.anagraficaCollegata.Item != null) ? it.anagraficaCollegata.Item.ToString() : String.Empty
                    });
                }
            }

            return listaRichiedenti;
        }

        #endregion
    }
}