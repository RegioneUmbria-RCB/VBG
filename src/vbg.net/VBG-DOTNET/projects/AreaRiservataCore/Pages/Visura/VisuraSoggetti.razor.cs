using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraSoggetti
    {
        [Parameter]
        public IEnumerable<VisuraSoggettiListItem> DataSource { get; set; }
    }

    public class VisuraSoggettiListItem
    {
        public string Nominativo { get; set; }
        public string InQualitaDi { get; set; }
        public string NominativoCollegato { get; set; }
        public string Procuratore { get; set; }

        public VisuraSoggettiListItem()
        {

        }

        public VisuraSoggettiListItem(Anagrafe soggetto, TipiSoggetto tipoSoggetto, Anagrafe anagrafeCollegata = null, Anagrafe procuratore = null)
        {
            this.Nominativo = soggetto.NOMINATIVO + " " + soggetto.NOME;

            if (tipoSoggetto != null)
                this.InQualitaDi = tipoSoggetto.TIPOSOGGETTO;

            if (anagrafeCollegata != null)
                this.NominativoCollegato = anagrafeCollegata.NOMINATIVO + " " + anagrafeCollegata.NOME;

            if (procuratore != null)
                this.Procuratore = procuratore.NOMINATIVO + " " + procuratore.NOME;
        }
    }
}
