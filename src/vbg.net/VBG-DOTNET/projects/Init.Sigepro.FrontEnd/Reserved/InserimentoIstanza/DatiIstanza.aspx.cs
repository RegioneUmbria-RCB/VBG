using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using System;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DatiIstanza : IstanzeStepPage
    {
        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            Master.IgnoraSalvataggioDati = true;

            if (!IsPostBack)
                DataBind();
        }

        #region Ciclo di vita dello step
        public override void OnBeforeExitStep()
        {
            DatiDomandaService.ImpostaDatiIstanza(IdDomanda, Note.Text, Oggetto.Text, DenominazioneAttivita.Text);
        }

        public override bool CanExitStep()
        {
            Page.Validate();

            if (!Page.IsValid)
            {
                Errori.Add("Compilare tutti i campi obbligatori");
                return false;
            }

            return true;
        }

        #endregion

        public override void DataBind()
        {
            Note.Text = ReadFacade.Domanda.AltriDati.Note;
            Oggetto.Text = ReadFacade.Domanda.AltriDati.DescrizioneLavori;
            DenominazioneAttivita.Text = ReadFacade.Domanda.AltriDati.DenominazioneAttivita;
        }

    }
}
