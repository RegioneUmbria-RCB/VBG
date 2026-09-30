using Init.SIGePro.Data;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita
{
    public interface ISchedeDinamicheAttivitaService
    {
        void EliminaValoriCampi(int idAttivita, DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare);
        IEnumerable<IAttivitaDyn2Dati> GetValoriCampiDaIdModello(int idAttivita, int idModello, int indiceModello);
        void SalvaValoriCampi(int idAttivita, DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare);
        IEnumerable<IAttivitaDyn2DatiStorico> GetValoriCampiDaIdModelloStorico(int idAttivita, int idModello, int indiceModello, int idVersioneStorico);
        void SalvaStoricoModello(int idAttivita, ModelloDinamicoBase modelloDinamicoBase);
        void AggiungiSchedaDinamicaAdAttivita(int codiceAttivita, int idModello);
        void EliminaModello(int codiceAttivita, int idModello);
        IEnumerable<int> GetCampiModelloPresentiAncheNelleIstanze(int codiceAttivita, int idModello);
    }
}
