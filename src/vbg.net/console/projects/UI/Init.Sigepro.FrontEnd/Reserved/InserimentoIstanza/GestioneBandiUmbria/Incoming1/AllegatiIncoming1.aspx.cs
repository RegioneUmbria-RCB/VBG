using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.Incoming1
{
    public partial class AllegatiIncoming1 : IstanzeStepPage
    {
        public int CodiceOggettoAllegato1
        {
            get { object o = this.ViewState["CodiceOggettoAllegato1"]; return o == null ? 49 : (int)o; }
            set { this.ViewState["CodiceOggettoAllegato1"] = value; }
        }

        public int CodiceOggettoAllegato2
        {
            get { object o = this.ViewState["CodiceOggettoAllegato2"]; return o == null ? 50 : (int)o; }
            set { this.ViewState["CodiceOggettoAllegato2"] = value; }
        }

        public int CodiceOggettoAllegato7
        {
            get { object o = this.ViewState["CodiceOggettoAllegato7"]; return o == null ? 53 : (int)o; }
            set { this.ViewState["CodiceOggettoAllegato7"] = value; }
        }

        public int CodiceOggettoAllegatoAltreSedi
        {
            get { object o = this.ViewState["CodiceOggettoAllegatoAltreSedi"]; return o == null ? 894 : (int)o; }
            set { this.ViewState["CodiceOggettoAllegatoAltreSedi"] = value; }
        }


        [Inject]
        protected IBandiIncomingService _service { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            var datiBando = this._service.GetDatiDomandaIncoming(IdDomanda, CodiceOggettoAllegato1, CodiceOggettoAllegato2, CodiceOggettoAllegato7, CodiceOggettoAllegatoAltreSedi);

            var dati = new List<AziendaBando>(datiBando.Aziende);

            var aziendaBando = new AziendaBando()
            {
                RagioneSociale = "Allegati della domanda",
                Allegati = new List<AllegatoDomandaBandi>{
					datiBando.Allegato1,
					datiBando.Allegato2/*,
					datiBando.AllegatoAltreSediOperative*/
				}
            };

            dati.Insert(0, aziendaBando);


            this.rptAziende.DataSource = dati;
            this.rptAziende.DataBind();
        }

        protected void AziendaDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var dataItem = (AziendaBando)e.Item.DataItem;
                var crtlDatiAzienda = (AllegatiBandoIncomingBindingGrid)e.Item.FindControl("crtlDatiAzienda");

                crtlDatiAzienda.DataSource = dataItem;
                crtlDatiAzienda.DataBind();
            }
        }

        protected void OnErrore(object sender, Exception e)
        {
            this.Errori.Add(e.Message);

            DataBind();
        }

        protected void OnFileUploaded(object sender, AllegatiBandoIncomingBindingGrid.FileUploadedEventArgs e)
        {
            try
            {
                this._service.AllegaADomanda(IdDomanda, e.IdAllegato, e.File, e.CercaTag);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
            finally
            {
                DataBind();
            }
        }


        protected void OnFileDeleted(object sender, AllegatiBandoIncomingBindingGrid.FileEventArgs e)
        {
            this._service.RimuoviAllegatoDaDomanda(IdDomanda, e.IdAllegato);

            DataBind();
        }


        public override bool CanExitStep()
        {
            var errori = this._service.ValidaPresenzaAllegati(IdDomanda);

            this.Errori.AddRange(errori);

            return errori.Count() == 0;
        }

    }
}