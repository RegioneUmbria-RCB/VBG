using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend
{
    public class AnagraficheBackendService : IAnagraficheBackendService
    {
        private readonly IAnagraficheRepository _anagrafeRepo;
        private readonly IAliasResolver _aliasResolver;

        public AnagraficheBackendService(IAnagraficheRepository anagrafeRepo, IAliasResolver aliasResolver)
        {
            this._anagrafeRepo = anagrafeRepo;
            this._aliasResolver = aliasResolver;
        }


        public Anagrafe RicercaAnagraficaBackoffice(TipoPersonaEnum tipoPersona, string codiceFiscale)
        {
            return this._anagrafeRepo.RicercaAnagrafica(tipoPersona, codiceFiscale);
        }

        public Anagrafe GetPersonaFisicaByUserId(string userId)
        {
            return this._anagrafeRepo.GetByUserId(this._aliasResolver.AliasComune, userId, TipoPersonaEnum.Fisica);
        }

        public Anagrafe GetPersonaGiuridicaByUserId(string userId)
        {
            return this._anagrafeRepo.GetByUserId(this._aliasResolver.AliasComune, userId, TipoPersonaEnum.Giuridica);
        }


        public CreazioneAnagraficaResult CreaAnagrafica(RichiestaCreazioneAnagraficaDto richiesta)
        {
            return this._anagrafeRepo.CreaAnagrafica(richiesta);
        }
    }
}
