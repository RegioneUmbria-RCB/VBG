using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
    /// <summary>
    /// Combo che contiene i valori della tabella TIPISOGGETTO.
    /// </summary>
    [ToolboxData("<{0}:ComboTipiSoggetto runat=server></{0}:ComboTipiSoggetto>")]
    public class ComboTipiSoggetto : FilteredDropDownList
    {
        [Inject]
        public ITipiSoggettoService _tipiSoggettoRepository { get; set; }


        public ComboTipiSoggetto()
        {
            FoKernelContainer.Inject(this);
        }

        public override string SelectedValue
        {
            get { return base.SelectedValue; }
            set
            {
                try
                {
                    base.SelectedValue = value;
                }
                catch (ArgumentOutOfRangeException)
                {
                    this.SelectedIndex = -1;
                }
            }
        }


        public string TipoSoggetto
        {
            get
            {
                object o = this.ViewState["TipoSoggetto"];
                return (o == null) ? "F" : (string)o;
            }
            set { this.ViewState["TipoSoggetto"] = value; }
        }

        public int? CodiceIntervento
        {
            get { object o = this.ViewState["CodiceIntervento"]; return o == null ? (int?)null : (int?)o; }
            set { this.ViewState["CodiceIntervento"] = value; }
        }

        public RuoloTipoSoggettoDomandaEnum? RuoloTipoSoggetto
        {
            get { object o = this.ViewState["Ruolo"]; return o == null ? (RuoloTipoSoggettoDomandaEnum?)null : (RuoloTipoSoggettoDomandaEnum?)o; }
            set { this.ViewState["Ruolo"] = value; }
        }

        protected override void CreateChildControls()
        {
            this.DataTextField = "TIPOSOGGETTO";
            this.DataValueField = "CODICETIPOSOGGETTO";

            base.CreateChildControls();
        }


        public override void DataBind()
        {
            this.EnsureChildControls();

            var l = ((this.TipoSoggetto == "G") ? this._tipiSoggettoRepository.GetTipiSoggettoPersonaGiurudica(this.CodiceIntervento) :
                                                    this._tipiSoggettoRepository.GetTipiSoggettoPersonaFisica(this.CodiceIntervento));

            this.Items.Clear();
            this.Items.Add(new ListItem("Selezionare...", String.Empty));

            if (l != null)
            {
                if (this.RuoloTipoSoggetto.HasValue)
                {
                    var tsd = new TipoSoggettoDomanda();
                    tsd.Ruolo = this.RuoloTipoSoggetto.Value;

                    if (tsd.Ruolo != RuoloTipoSoggettoDomandaEnum.Altro)
                    {
                        l = l.Where(x => x.FlagTipoDato == tsd.RuoloAsCodiceBackoffice());
                    }
                    else
                    {
                        l = l.Where(x => String.IsNullOrEmpty(x.FlagTipoDato));
                    }

                }

                var lista = l.Select(x => new ListItem(x.Descrizione, x.Id.ToString())).ToArray();

                this.Items.AddRange(lista);
            }

            base.DataBind();
        }

    }
}