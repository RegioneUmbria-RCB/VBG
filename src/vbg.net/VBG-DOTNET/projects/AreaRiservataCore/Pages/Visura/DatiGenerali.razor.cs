using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Microsoft.AspNetCore.Components;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class DatiGenerali
    {
        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; } = default!;

        [Parameter]
        public VisuraDatiGeneraliDataSource? DataSource { get; set; }

        [Parameter]
        public bool MostraDatiProtocollo { get; set; } = false;

        [Parameter]
        public bool UtenteTester { get; set; } = false;

        public bool MostraStatoPratica => !this._configurazione.Parametri.DettaglioPratica.NascondiStato;

        public bool MostraRiferimenti => !this._configurazione.Parametri.DettaglioPratica.NascondiResponsabili;

        public bool MostraPosizioneArchivio => this._configurazione.Parametri.DettaglioPratica.MostraPosizioneArchivio;

        public class VisuraDatiGeneraliDataSource
        {
            public required string Uuid { get; set; }
            public required string ComunePratica { get; set; }
            public required string NumeroProtocollo { get; set; }
            public required DateTime? DataProtocollo { get; set; }
            public required string NumeroPratica { get; set; }
            public required DateTime? DataPratica { get; set; }
            public required string Oggetto { get; set; }
            public required string Intervento { get; set; }
            public required string Stato { get; set; }
            public required string ResponsabileProcedimento { get; set; }
            public required string Istruttore { get; set; }
            public required string Operatore { get; set; }
            public required string PosizioneArchivio { get; set; }
        }
    }
}
