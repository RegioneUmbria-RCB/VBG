using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using System.Collections.Generic;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
    public interface IDatiDinamiciWriteInterface
    {
        void AggiornaOCrea(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato, string? nomeCampo);
        void EliminaValoriCampo(int idCampo);
        void EliminaValoreDaIdcampoIndiceMolteplicita(int idCampo, int indiceScheda, int indiceMolteplicita);
        void EliminaValoreDaIdcampo(int idCampo);
        void EliminaValoreDaIdcampo(int idCampo, int indiceScheda);
        void AggiungiDatoDinamico(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato, string nomeCampo);
        void ModificaValoreCampo(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato);
        void ModificaStatoCompilazioneModello(int idModello, int maxMolteplicita, bool compilato);
        void SincronizzaModelliDinamici(SincronizzaModelliDinamiciCommand command);
        void SalvaCampiNonVisibili(int idModello, IEnumerable<IdValoreCampo> campiNonVisibili);
    }
}
