using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
using Vbg.EventBus;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita
{
    public class SchedeAttivitaEventBusModule : EventBusModule
    {
        public override void Load(EventSubscribersRegistry registry)
        {
            registry.Add<SchedaDinamicaAggiuntaAdAttivita, EventiSchedeDinamicheAttivitaService>();
            registry.Add<SchedaDinamicaAttivitaSalvata, EventiSchedeDinamicheAttivitaService>();
            registry.Add<SchedaDinamicaIstanzaEliminata, EventiSchedeDinamicheAttivitaService>();
            registry.Add<SchedaDinamicaIstanzaSalvata, EventiSchedeDinamicheAttivitaService>();
            registry.Add<SchedaDinamicaRimossaDaAttivita, EventiSchedeDinamicheAttivitaService>();
        }
    }
}
