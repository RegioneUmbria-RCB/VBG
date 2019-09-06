using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FvgDyn2DataAccessProvider : IDyn2DataAccessProvider
    {
        private class InnerDyn2CampiManager : IDyn2CampiManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerDyn2CampiManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public IDyn2Campo GetById(string idComune, int idCampo)
            {
                throw new NotImplementedException();
            }

            public SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello)
            {
                return this._modelloDinamico.Value.ListaCampiDinamici;
            }
        }

        private class InnerDettagliModelloManager : IDyn2DettagliModelloManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerDettagliModelloManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public List<IDyn2DettagliModello> GetList(string idComune, int idModello)
            {
                return this._modelloDinamico.Value.Struttura;
            }
        }

        private class NullIstanzeManager : IIstanzeManager
        {
            public IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza)
            {
                return new Istanze();
            }
        }

        private class InnerModelliManager : IDyn2ModelliManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerModelliManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public IDyn2Modello GetById(string idComune, int idModello)
            {
                return this._modelloDinamico.Value.Modello;
            }
        }

        private class InnerProprietaManager : IDyn2ProprietaCampiManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerProprietaManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo)
            {
                if (this._modelloDinamico.Value.ProprietaCampiDinamici.ContainsKey(idCampo))
                    return this._modelloDinamico.Value.ProprietaCampiDinamici[idCampo];

                return new List<IDyn2ProprietaCampo>();

            }
        }

        private class NullQueryLocalizzazioni : IQueryLocalizzazioni
        {
            public IEnumerable<RiferimentiLocalizzazione> Execute(string tipoLocalizzazione, string espressioneFormattazioneDati)
            {
                return Enumerable.Empty<RiferimentiLocalizzazione>();
            }
        }

        private class InnerScriptModelloManager : IDyn2ScriptModelloManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerScriptModelloManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public IDyn2ScriptModello GetById(string idComune, int idModello, TipoScriptEnum contesto)
            {
                if (!this._modelloDinamico.Value.ScriptsModello.ContainsKey(contesto))
                    return null;

                return this._modelloDinamico.Value.ScriptsModello[contesto];
            }
        }

        private class InnerScriptCampiManager : IDyn2ScriptCampiManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerScriptCampiManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo)
            {
                if (this._modelloDinamico.Value.ScriptsCampiDinamici.ContainsKey(idCampo))
                    return this._modelloDinamico.Value.ScriptsCampiDinamici[idCampo];

                return new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>();
            }
        }

        private class InnerTestiModelloManager : IDyn2TestoModelloManager
        {
            Lazy<ModelloDinamicoCache> _modelloDinamico;

            public InnerTestiModelloManager(Lazy<ModelloDinamicoCache> modelloDinamico)
            {
                this._modelloDinamico = modelloDinamico;
            }

            public SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(string idComune, int idModello)
            {
                return this._modelloDinamico.Value.ListaTesti;
            }
        }


        IDatiDinamiciRepository _datiDinamiciRepository;
        InnerDyn2CampiManager _dyn2CampiManager = null;
        InnerDettagliModelloManager _dettagliModelloManager = null;
        IIstanzeDyn2DatiManager _istanzeDyn2DatiManager = null;
        InnerModelliManager _modelliManager = null;
        InnerProprietaManager _proprietaManager = null;
        NullQueryLocalizzazioni _queryLocalizzazioni = null;
        InnerScriptModelloManager _scriptModelloManager = null;
        InnerScriptCampiManager _scriptCampiManager = null;
        InnerTestiModelloManager _testiManager = null;
        ITokenApplicazioneService _tokenService = null;

        public FvgDyn2DataAccessProvider(int idModello, IDatiDinamiciRepository datiDinamiciRepository, IIstanzeDyn2DatiManager istanzeDyn2DatiManager, ITokenApplicazioneService tokenService )
        {
            this._datiDinamiciRepository = datiDinamiciRepository;

            var modelloLazy = new Lazy<ModelloDinamicoCache>(() => this._datiDinamiciRepository.GetCacheModelloDinamico(idModello));

            this._dyn2CampiManager = new InnerDyn2CampiManager(modelloLazy);
            this._dettagliModelloManager = new InnerDettagliModelloManager(modelloLazy);
            this._istanzeDyn2DatiManager = istanzeDyn2DatiManager;
            this._modelliManager = new InnerModelliManager(modelloLazy);
            this._proprietaManager = new InnerProprietaManager(modelloLazy);
            this._queryLocalizzazioni = new NullQueryLocalizzazioni();
            this._scriptModelloManager = new InnerScriptModelloManager(modelloLazy);
            this._scriptCampiManager = new InnerScriptCampiManager(modelloLazy);
            this._testiManager = new InnerTestiModelloManager(modelloLazy);

            this._tokenService = tokenService;
        }

        public IAnagrafeDyn2DatiManager GetAnagrafeDyn2DatiManager()
        {
            throw new NotImplementedException();
        }

        public IAnagrafeDyn2DatiStoricoManager GetAnagrafeDyn2DatiStoricoManager()
        {
            throw new NotImplementedException();
        }

        public IAnagrafeManager GetAnagrafeManager()
        {
            throw new NotImplementedException();
        }

        public IIAttivitaDyn2DatiManager GetAttivitaDyn2DatiManager()
        {
            throw new NotImplementedException();
        }

        public IIAttivitaDyn2DatiStoricoManager GetAttivitaDyn2DatiStoricoManager()
        {
            throw new NotImplementedException();
        }

        public IIAttivitaManager GetAttivitaManager()
        {
            throw new NotImplementedException();
        }

        public IDyn2CampiManager GetCampiManager()
        {
            return this._dyn2CampiManager;
        }

        public IDyn2DettagliModelloManager GetDettagliModelloManager()
        {
            return this._dettagliModelloManager;
        }

        public IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager()
        {
            throw new NotImplementedException();
        }

        public IIstanzeDyn2DatiManager GetIstanzeDyn2DatiManager()
        {
            return this._istanzeDyn2DatiManager;
        }

        public IIstanzeDyn2DatiStoricoManager GetIstanzeDyn2DatiStoricoManager()
        {
            throw new NotImplementedException();
        }

        public IIstanzeManager GetIstanzeManager()
        {
            return new NullIstanzeManager();
        }

        public IDyn2ModelliManager GetModelliManager()
        {
            return this._modelliManager;
        }

        public IDyn2ProprietaCampiManager GetProprietaCampiManager()
        {
            return this._proprietaManager;
        }

        public IQueryLocalizzazioni GetQueryLocalizzazioni()
        {
            return this._queryLocalizzazioni;
        }

        public IDyn2ScriptCampiManager GetScriptCampiManager()
        {
            return this._scriptCampiManager;
        }

        public IDyn2ScriptModelloManager GetScriptModelliManager()
        {
            return this._scriptModelloManager;
        }

        public IDyn2TestoModelloManager GetTestoModelloManager()
        {
            return this._testiManager;
        }

        public string GetToken()
        {
            return this._tokenService.GetToken();
        }
    }
}
