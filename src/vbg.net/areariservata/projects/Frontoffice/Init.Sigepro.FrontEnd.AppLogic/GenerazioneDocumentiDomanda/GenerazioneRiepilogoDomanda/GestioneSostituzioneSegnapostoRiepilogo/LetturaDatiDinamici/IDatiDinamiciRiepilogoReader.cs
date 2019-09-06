using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici
{
    public interface IDatiDinamiciRiepilogoReader
    {
        IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli();
        IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento();
        IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo);

        IEnumerable<int> GetIndiciSchede(int idModello, IStrutturaModelloReader strutturaReader);
        // IEnumerable<IdValoreCampo> GetCampiNonVisibili(int idModello);
        CampiNonVisibili GetCampiNonVisibili(int idModello);

        //IIstanzeManager CreateIstanzeManager();
        IDyn2DataAccessProvider CreateDataAccessProvider(int idScheda, ITokenApplicazioneService tokenApplicazioneService);
        IDyn2DataAccessProvider CreateDataAccessProviderStampaMolteplicita(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService);
        string GetIdComune();
        int GetCodiceIstanza();
        IValoreDatoDinamicoRiepilogo GetCampoDinamico(int idCampoDinamico, int indiceMolteplicita = 0);
        
        // caricamento delle schede non presenti
        bool PuoCaricareSchedeNonPresenti { get; }
        IModelloDinamicoRiepilogo CaricaSchedaNonPresenteDaId(int idScheda);
    }
}
