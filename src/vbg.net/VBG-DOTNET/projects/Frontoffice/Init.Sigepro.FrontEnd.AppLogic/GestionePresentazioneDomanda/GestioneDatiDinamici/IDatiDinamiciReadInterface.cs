using System.Collections.Generic;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
    public interface IDatiDinamiciReadInterface
    {
        IEnumerable<ValoreDatoDinamico> DatiDinamici { get; }
        IEnumerable<ModelloDinamico> Modelli { get; }
        IEnumerable<ModelloDinamicoOrdinato> ModelliIntervento { get; }
        IEnumerable<ModelloDinamico> ModelliEndoprocedimenti { get; }

        bool EsistonoModelliObbligatoriNonCompilati();

        IEnumerable<string> VerificaUploadModelliRichiesti(bool ignoraObbligoFirma);

        IEnumerable<ModelloDinamicoOrdinato> GetModelliEndo(int codiceEndoprocedimento);
        ModelloDinamico ModelloCittadinoExtracomunitario { get; }
        ModelloDinamico GetModelloById(int idModello);
        IEnumerable<int> GetIndiciSchede(IStrutturaModelloDinamico strutturaModello);

        IEnumerable<IdValoreCampo> GetCampiNonVisibili(int idModello);

        /// <summary>
        /// Restituisce l'ordine assoluto di un modello all'intrno della lista dei modelli della domanda.
        /// Se il modello è presente tra i modelli dell'intervento allora restituisce l'ordine della lista dell'intervento
        /// altrimenti restituisce l'ordine all'interno della lista dei modelli dell'endo
        /// </summary>
        /// <param name="it">Modello di cui si vuole trovare l'ordine</param>
        /// <returns>ordine dell'elemento</returns>
        ModelloDinamicoOrdinato OrdineAssoluto(ModelloDinamico it);
    }
}
