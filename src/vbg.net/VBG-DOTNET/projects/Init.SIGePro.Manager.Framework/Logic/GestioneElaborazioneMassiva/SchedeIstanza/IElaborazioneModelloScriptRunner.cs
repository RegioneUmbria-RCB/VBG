using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public interface IElaborazioneModelloScriptRunner
    {
        void EseguiScriptElaborazioneMassiva(int codiceIstanza, int idScheda);
    }
}
