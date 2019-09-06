using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneAmmissibilitaIntervento : IstanzeStepPage
    {
        [Inject]
        public IInterventiRepository _interventiService { get; set; }

        public string MessaggioErroreDomandaPerInterventoPresentata
        {
            get { object o = this.ViewState["MessaggioErroreDomandaPerInterventoPresentata"]; return o == null ? "Non è possibile presentare una domanda per questa attività" : (string)o; }
            set { this.ViewState["MessaggioErroreDomandaPerInterventoPresentata"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public bool PuoPresentareDomandaPerIntervento()
        {
            var idIntervento = ReadFacade.Domanda.AltriDati.Intervento.Codice;

            if (ReadFacade.Domanda.Anagrafiche.GetRichiedente() == null)
            {
                throw new Exception("Richiedente della domanda non trovato. Lo step deve essere posizionato a seguito della step di inserimento anagrafiche");
            }

            var codiceFiscale = ReadFacade.Domanda.Anagrafiche.GetRichiedente().Codicefiscale;
            var haPresentatoDomanda = ReadFacade.Interventi.HaPresentatoDomandePerIntervento(idIntervento, codiceFiscale);

            return !haPresentatoDomanda;
        }

        public override bool CanEnterStep()
        {
            if (PuoPresentareDomandaPerIntervento())
            {
                return false;
            }

            this.Master.MostraBottoneAvanti = false;

            return true;
        }
    }
}