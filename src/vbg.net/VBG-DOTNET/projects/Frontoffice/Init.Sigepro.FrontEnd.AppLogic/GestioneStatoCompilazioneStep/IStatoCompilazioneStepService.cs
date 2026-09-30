using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneStatoCompilazioneStep
{
    public interface IStatoCompilazioneStepService
    {
        void RegistraStepsDaCompilare(int idDomanda, IEnumerable<InfoStepDomanda> stepDomanda);
        void ContrassegnaStepComeCompilato(int idDomanda, int indiceStep);
        bool TuttiGliStepSonoStatiCompilati(int idDomanda);
        int GetIndicePrimoStepNonCompilato(int idDomanda);
    }
}
