using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using System.Collections.Generic;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici
{
    public interface ISchedeDinamicheDomandaAlRiepilogoService
    {
        IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli();
        IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento();
        IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo);
        IEnumerable<int> GetIndiciSchede(int idModello);
        CampiNonVisibili GetCampiNonVisibili(int idModello);
        ModelloDinamicoLoader CreateLoader(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService);
        string GetIdComune();
        int GetCodiceIstanza();
        IValoreDatoDinamicoRiepilogo GetCampoDinamico(int idCampoDinamico, int indiceMolteplicita = 0);
        // caricamento delle schede non presenti
        bool PuoCaricareSchedeNonPresenti { get; }
        // IModelloDinamicoRiepilogo CaricaSchedaNonPresenteDaId(int idScheda);

        /// <summary>
        /// La domanda online e le domande fvg salvano lo stato dei campi non visibili per poterlo utilizzare in fase di generazione del riepilogo.
        /// Se sto effettuando una visura non ho più questa informazione e devo ricalcolarla invocando lo script di caricamento.
        /// Questa potenzialmente è un'operazione rischiosa nel caso in cui la formula eseguisse delle operazioni sullo stato del modello ma
        /// è l'unico modo per ripristinare lo stato di visualizzazione dei campi. Vd.ticket 2022110810000324 di Trieste
        /// </summary>
        bool SupportaCachingCampiNonVisibili { get; }
    }
}
