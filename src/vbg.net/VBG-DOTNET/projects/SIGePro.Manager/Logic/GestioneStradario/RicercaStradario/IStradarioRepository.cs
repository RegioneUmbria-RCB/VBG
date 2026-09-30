using Init.SIGePro.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneStradario.RicercaStradario
{
    public interface IStradarioRepository
    {
        IEnumerable<Stradario> FindByMatchParziale(CondizioniRicercaStradarioPerDescrizione condizioniRicerca, bool escludiDisabilitate);
    }
}
