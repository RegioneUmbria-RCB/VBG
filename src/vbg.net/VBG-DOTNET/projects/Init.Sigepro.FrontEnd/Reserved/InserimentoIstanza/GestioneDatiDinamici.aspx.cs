using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneDatiDinamici : IstanzeStepPage
    {
        private static class Constants
        {
            public const string CssClassElementoCompilato = "compilato";
            public const string CssClassElementoNonCompilato = "";
        }

        public string TitoloSchedaCittadiniextracomunitari
        {
            get { var o = this.ViewState["TitoloSchedaCittadiniextracomunitari"]; return o == null ? "Modello per cittadini extracomunitari" : (string)o; }
            set { this.ViewState["TitoloSchedaCittadiniextracomunitari"] = value; }
        }

        public bool IsSchedaDinamicaEcCompilata
        {
            get { var o = this.ViewState["IsSchedaDinamicaEcCompilata"]; return o == null ? false : (bool)o; }
            set { this.ViewState["IsSchedaDinamicaEcCompilata"] = value; }
        }

        public bool IgnoraSchedaCittadinoExtracomunitario
        {
            get { var o = this.ViewState["IgnoraSchedaCittadinoExtracomunitario"]; return o == null ? false : (bool)o; }
            set { this.ViewState["IgnoraSchedaCittadinoExtracomunitario"] = value; }
        }

        public string TestoCompilazioneSchedeObbligatorie
        {
            get { var o = this.ViewState["TestoCompilazioneSchedeObbligatorie"]; return o == null ? "(*) E' necessario compilare tutte le schede contrassegnate con un asterisco" : (string)o; }
            set { this.ViewState["TestoCompilazioneSchedeObbligatorie"] = value; }
        }

        public string TestoSchedeDellIntervento
        {
            get { var o = this.ViewState["TestoSchedeDellIntervento"]; return o == null ? "Schede dell'intervento" : (string)o; }
            set { this.ViewState["TestoSchedeDellIntervento"] = value; }
        }




        [Inject]
        public IModelliDinamiciService _modelliDinamiciService { get; set; }

        [Inject]
        public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

        private List<int> _schedeDaRicompilare = new List<int>();

        public bool MostraTitoloSchedeIntervento
        {
            get { var o = this.ViewState["MostraTitoloSchedeintervento"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraTitoloSchedeintervento"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            // Il master si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
                this.DataBind();
        }

        #region Ciclo di vita dello step

        public override void OnInitializeStep()
        {
            this._modelliDinamiciService.SincronizzaModelliDinamici(this.IdDomanda, this.IgnoraSchedaCittadinoExtracomunitario);
        }

        public override bool CanEnterStep()
        {
            return this.ReadFacade.Domanda.DatiDinamici.Modelli.Count() > 0;
        }

        public override bool CanExitStep()
        {
            if (this.ReadFacade.Domanda.DatiDinamici.EsistonoModelliObbligatoriNonCompilati())
            {
                this.Errori.Add("Per proseguire è necessario compilare tutte le schede obbligatorie");

                return false;
            }

            return true;
        }

        public override void OnBeforeExitStep()
        {
            this.renderer.CleanupSession();
        }

        #endregion

        public class SchedeBindingItem
        {
            public int Codice { get; set; }
            public string Descrizione { get; set; }
            public bool Facoltativa { get; set; }
            public string Compilata { get; set; }
            public bool DaRicompilare { get; set; } = false;
        }


        public override void DataBind()
        {
            var interventoBindingSource = this.ReadFacade.Domanda
                                                    .DatiDinamici
                                                    .ModelliIntervento
                                                    .OrderBy(x => x.Ordine)
                                                    .Select(x => new SchedeBindingItem
                                                    {
                                                        Codice = x.Modello.IdModello,
                                                        Compilata = this.GetClasseCssSchedaDinamicaCompilata(x.Modello.Compilato),
                                                        Descrizione = x.Modello.Descrizione,
                                                        Facoltativa = x.Modello.Facoltativo,
                                                        DaRicompilare = this._schedeDaRicompilare.Contains(x.Modello.IdModello)
                                                    });

            this.rptSchedeIntervento.DataSource = interventoBindingSource;
            this.rptSchedeIntervento.DataBind();

            if (interventoBindingSource.Count() == 0)
                this.rptSchedeIntervento.Visible = false;


            var endoBindingSource = this.ReadFacade.Domanda
                                              .Endoprocedimenti
                                              .NonAcquisiti
                                              .OrderByDescending(x => this.ValoreEndo(x))
                                              .ThenBy(x => x.Ordine.OrdineFamiglia)
                                              .ThenBy(x => x.Ordine.OrdineTipo)
                                              .ThenBy(x => x.Ordine.OrdineEndo)
                                              .ThenBy(x => x.Descrizione)
                                              .Select(x =>
                                                    new
                                                    {
                                                        DescrizioneIntervento = x.Descrizione,
                                                        Schede = this.ReadFacade.Domanda
                                                                           .DatiDinamici
                                                                           .GetModelliEndo(x.Codice)
                                                                           .OrderBy(y => y.Ordine)
                                                                           .Select(scheda => new SchedeBindingItem
                                                                           {
                                                                               Codice = scheda.Modello.IdModello,
                                                                               Compilata = this.GetClasseCssSchedaDinamicaCompilata(scheda.Modello.Compilato),
                                                                               Descrizione = scheda.Modello.Descrizione,
                                                                               Facoltativa = scheda.Modello.Facoltativo,
                                                                               DaRicompilare = this._schedeDaRicompilare.Contains(scheda.Modello.IdModello)
                                                                           })
                                                    })
                                               .Where(x => x.Schede.Count() > 0);

            this.rptEndoprocedimenti.DataSource = endoBindingSource;
            this.rptEndoprocedimenti.DataBind();

            // Scheda cittadini extracomunitari
            var modelloEc = this.ReadFacade.Domanda.DatiDinamici.ModelloCittadinoExtracomunitario;

            this.pnlSchedaCittadiniExtracomunitari.Visible = modelloEc != null;

            if (modelloEc != null)
            {
                this.IsSchedaDinamicaEcCompilata = modelloEc.Compilato;

                this.lblTestoSchedaCittadiniExtracomunitari.Text = this.TitoloSchedaCittadiniextracomunitari;
                this.lnkEcSelezioneSchedaIntervento.Text = modelloEc.Descrizione;
                this.lnkEcSelezioneSchedaIntervento.CommandArgument = modelloEc.IdModello.ToString();
                this.ltrEcAsterisco.Text = modelloEc.Facoltativo ? "" : "*";
            }
        }

        public string GetClasseCssSchedaDinamicaCompilata(bool compilata)
        {
            return compilata ? Constants.CssClassElementoCompilato : Constants.CssClassElementoNonCompilato;
        }

        private int ValoreEndo(Endoprocedimento endo)
        {
            if (endo.Principale)
                return 10000;

            if (!endo.Facoltativo)
                return 1000;

            return 1;
        }


        public void OnSchedaSelezionata(object sender, EventArgs e)
        {
            var idScheda = Convert.ToInt32(((LinkButton)sender).CommandArgument);

            this.CaricaSchedaDinamica(idScheda, this.PrimoIndiceSelezionabileNellaScheda(idScheda));

            this.Master.MostraPaginatoreSteps = false;
        }

        public void OnSchedaEliminata(object sender, EventArgs e)
        {
            try
            {
                var idModello = this.renderer.DataSource.IdModello;
                var indiceModello = this.renderer.DataSource.IndiceModello;

                this._modelliDinamiciService.EliminaModello(this.IdDomanda, idModello, indiceModello);

                this.CaricaSchedaDinamica(this.IdSchedaSelezionata, this.PrimoIndiceSelezionabileNellaScheda(this.IdSchedaSelezionata));
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }

        }

        private int PrimoIndiceSelezionabileNellaScheda(int idScheda)
        {
            var indici = this._modelliDinamiciService.GetIndiciScheda(this.IdDomanda, idScheda);

            return (indici == null || indici.Count() == 0) ? 0 : indici.First();
        }


        public void cmdChiudi_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
            this.Master.MostraPaginatoreSteps = true;
        }


        private bool Salva()
        {
            try
            {
                if (this.renderer.DataSource == null)
                    return false;

                try
                {
                    this.renderer.DataSource.ValidaModello();
                }
                catch (ValidazioneModelloDinamicoException /*ex*/)
                {
                    this.MostraErroreSalvataggio();
                    return false;
                }

                var esitoSalvataggio = this._modelliDinamiciService.Salva(this.IdDomanda, this.renderer.DataSource);

                this._schedeDaRicompilare = esitoSalvataggio.IdSchedeDaRicompilare.ToList();

                return true;
            }
            catch (SalvataggioModelloDinamicoException)
            {
                this.MostraErroreSalvataggio();
                return false;
            }
        }


        public void cmdSalvaeResta_Click(object sender, EventArgs e)
        {
            if (this.Salva())
                this.CaricaSchedaDinamica(this.IdSchedaSelezionata, this.paginatoreSchedeDinamiche.IndiceCorrente);
        }

        public void cmdSalva_Click(object sender, EventArgs e)
        {
            if (this.Salva())
            {
                this.multiView.ActiveViewIndex = 0;
                this.Master.MostraPaginatoreSteps = true;

                this.DataBind();
            }
        }


        private void MostraErroreSalvataggio()
        {
            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
            //DataBind();
        }

        public int IdSchedaSelezionata
        {
            get { var o = this.ViewState["IdSchedaSelezionata"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdSchedaSelezionata"] = value; }
        }

        public int IndiceSchedaSelezionata
        {
            get { var o = this.ViewState["IndiceSchedaSelezionata"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IndiceSchedaSelezionata"] = value; }
        }


        protected void OnIndiceSelezionato(object sender, EventArgs e)
        {
            this.CaricaSchedaDinamica(this.IdSchedaSelezionata, this.paginatoreSchedeDinamiche.IndiceCorrente);
        }

        protected void OnNuovaScheda(object sender, EventArgs e)
        {
            this.CaricaSchedaDinamica(this.IdSchedaSelezionata, this.paginatoreSchedeDinamiche.IndiceNuovaScheda);
        }


        private void CaricaSchedaDinamica(int idScheda, int indiceScheda)
        {
            this.IdSchedaSelezionata = idScheda;
            this.IndiceSchedaSelezionata = indiceScheda;

            var scheda = this._modelliDinamiciService.GetModelloDinamico(this.IdDomanda, idScheda, indiceScheda);

            /*
			var idCampoAttivitaAteco = _configurazione.Parametri.IdCampoDinamicoPerAttivitaAtecoPrevalente;

			if (idCampoAttivitaAteco.HasValue && ReadFacade.Domanda.AltriDati.AttivitaAtecoPrimaria.HasValue)
			{
				var campo = scheda.TrovaCampoDaId(idCampoAttivitaAteco.Value);

				if (campo != null && String.IsNullOrEmpty(campo.ListaValori[0].Valore))
				{
					// TODO: Leggere il codice ateco del campo invece dell'id
					var idNodo = ReadFacade.Domanda.AltriDati.AttivitaAtecoPrimaria.Value;
					var voceAteco = ReadFacade.Ateco.GetDettagli( IdComune , idNodo );

					campo.ListaValori[0].Valore = voceAteco.Codicebreve;
				}
			}
			*/

            this.lblTitoloModello.Text = scheda.NomeModello;

            this.multiView.ActiveViewIndex = 1;

            scheda.EseguiScriptCaricamento();

            this.renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaVuota());
            //renderer.RicaricaModelloDinamico += new SIGePro.DatiDinamici.WebControls.ModelloDinamicoRenderer.RicaricaModelloDinamicoDelegate(renderer_RicaricaModelloDinamico);
            this.renderer.DataSource = scheda;
            this.renderer.DataBind();

            //scheda.scriptMo

            this.paginatoreSchedeDinamiche.Visible = scheda.ModelloMultiplo;
            this.paginatoreSchedeDinamiche.IndiciSchede = this._modelliDinamiciService.GetIndiciScheda(this.IdDomanda, idScheda);
            this.paginatoreSchedeDinamiche.IndiceCorrente = this.IndiceSchedaSelezionata;
            this.paginatoreSchedeDinamiche.DataBind();

            this.cmdSalvaEResta.Visible = scheda.ModelloMultiplo;
        }

    }
}
