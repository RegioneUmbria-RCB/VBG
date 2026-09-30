using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.RedirectFineDomanda.CopiaDomanda;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using ArControls = Init.Sigepro.FrontEnd.WebControls.FormControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class benvenuto_copia_da : System.Web.UI.UserControl
    {


        public IDomandaOnlineReadInterface Domanda { get; set; }

        [Inject]
        protected ITipiSoggettoService _tipiSoggettoService { get; set; }
        [Inject]
        protected ICopiaDatiDomandaService _copiaDomandaService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            if (this.Domanda == null)
            {
                this.Visible = false;
                return;
            }

            var ds = this.Domanda.Anagrafiche.Anagrafiche.Select(x => new
            {
                Id = x.Id.Value,
                Nominativo = x,
                TipoSoggetto = x.TipoSoggetto,
                PersonaFisica = x.TipoPersona == AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica,
                UsaNellaNuovaDomanda = true,
                SoggettoCollegato = x.AnagraficaCollegata
            });

            gvSoggetti.DataSource = ds; 
            gvSoggetti.DataBind();


            var allegati = this.Domanda.Documenti.Intervento.Documenti.Union(this.Domanda.Documenti.Endo.Documenti).Where(x => !x.FromDatiDinamici && x.AllegatoDellUtente != null);

            gvAllegati.DataSource = allegati;
            gvAllegati.DataBind();
        }

        protected void gvSoggetti_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var ddlTipoSoggetto = (Init.Sigepro.FrontEnd.WebControls.FormControls.DropDownList)e.Row.FindControl("ddlTipoSoggetto");
                var dataItem = e.Row.DataItem as dynamic;

                var ds = dataItem.PersonaFisica ? this._tipiSoggettoService.GetTipiSoggettoPersonaFisica(null) :
                                                                        this._tipiSoggettoService.GetTipiSoggettoPersonaGiurudica(null);

                ds = ds.Where(x => !x.RichiedeDescrizioneEstesa);

                ddlTipoSoggetto.DataSource = ds;
                ddlTipoSoggetto.DataBind();

                ddlTipoSoggetto.Items.Insert(0, new ListItem(""));


            }
        }

        public IEnumerable<string> GetErroriDiValidazione()
        {
            return gvSoggetti.Rows
                    .Cast<GridViewRow>()
                    .Select(row => new
                    {
                        chk = (CheckBox)row.FindControl("chkUsaInDomanda"),
                        dropDown = (ArControls.DropDownList)row.FindControl("ddlTipoSoggetto"),
                        anagrafe = row.Cells[0].Text
                    })
                    .Where(row => row.chk.Checked && String.IsNullOrEmpty(row.dropDown.Value))
                    .Select(row => $"Specificare una qualifica per l'anagrafica \"{row.anagrafe}\"");
        }

        public ElementiDaCopiare GetElementiSelezionati()
        {
            var anagraficheSelezionate = gvSoggetti.Rows
                    .Cast<GridViewRow>()
                    .Select(row => new
                    {
                        id = Convert.ToInt32(gvSoggetti.DataKeys[row.RowIndex].Value),
                        chk = (CheckBox)row.FindControl("chkUsaInDomanda"),
                        dropDown = (ArControls.DropDownList)row.FindControl("ddlTipoSoggetto"),
                        anagrafe = row.Cells[0].Text
                    })
                    .Where(row => row.chk.Checked)
                    .Select(row => 
                    {
                        var id = row.id;
                        var tipoSoggetto = Convert.ToInt32(row.dropDown.SelectedValue);
                        var descrizioneTipoSoggetto = row.dropDown.SelectedItem.Text;
                        var richiedeAnagraficaCollegata = this._tipiSoggettoService.GetById(Convert.ToInt32(row.dropDown.SelectedValue)).RichiedeAnagraficaCollegata;

                        return new AnagraficaDaCopiare(id, tipoSoggetto, descrizioneTipoSoggetto, richiedeAnagraficaCollegata);
                    }).ToList();

            var allegatiSelezionati = gvAllegati.Rows
                .Cast<GridViewRow>()
                .Select(row => new {
                    chk = (CheckBox)row.FindControl("chkUsaInDomanda"),
                    id = Convert.ToInt32(gvAllegati.DataKeys[row.RowIndex].Value)
                })
                .Where(row => row.chk.Checked)
                .Select(row => new AllegatoDaCopiare
                {
                   Id = row.id
                }).ToList();

            return new ElementiDaCopiare
            {
                Anagrafiche = anagraficheSelezionate,
                Allegati = allegatiSelezionati
            };
        }
    }
}