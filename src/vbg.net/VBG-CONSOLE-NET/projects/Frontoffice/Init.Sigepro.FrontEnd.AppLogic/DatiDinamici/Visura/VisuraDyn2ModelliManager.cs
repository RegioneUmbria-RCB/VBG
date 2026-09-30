using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Scripts;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public class VisuraDyn2ModelliManager : IDyn2ModelliManager, IDyn2DettagliModelloManager, IDyn2CampiManager, IDyn2TestoModelloManager,
                                                IDyn2ScriptCampiManager, IDyn2ProprietaCampiManager
    {
        WsDatiDinamiciRepository _datiDinamiciRepository;
        ModelloDinamicoCache _cacheModello { get; set; }

        internal VisuraDyn2ModelliManager(WsDatiDinamiciRepository datiDinamiciRepository)
        {
            this._datiDinamiciRepository = datiDinamiciRepository;
        }

        public IDyn2Modello GetById(string idComune, int idModello)
        {
            EnsureCacheModello(idModello);

            return this._cacheModello.Modello;
        }

        private void EnsureCacheModello(int idModello)
        {
            if (this._cacheModello == null || this._cacheModello.Modello.Id != idModello)
            {
                this._cacheModello = this._datiDinamiciRepository.GetCacheModelloDinamico(idModello);
            }
        }



        public List<IDyn2DettagliModello> GetList(string idComune, int idModello)
        {
            EnsureCacheModello(idModello);

            return this._cacheModello.Struttura;
        }

        IDyn2Campo IDyn2CampiManager.GetById(string idComune, int idCampo)
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Utils.SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello)
        {
            EnsureCacheModello(idModello);

            return this._cacheModello.ListaCampiDinamici;
        }

        public SIGePro.DatiDinamici.Utils.SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(string idComune, int idModello)
        {
            EnsureCacheModello(idModello);

            return this._cacheModello.ListaTesti;
        }


        public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo)
        {
            // EnsureCacheModello(idModello);

            if (this._cacheModello != null && this._cacheModello.ScriptsCampiDinamici.ContainsKey(idCampo))
                return this._cacheModello.ScriptsCampiDinamici[idCampo];

            return new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>();
        }

        public List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo)
        {
            // EnsureCacheModello(idModello);

            if (this._cacheModello != null && this._cacheModello.ProprietaCampiDinamici.ContainsKey(idCampo))
                return this._cacheModello.ProprietaCampiDinamici[idCampo];

            return new List<IDyn2ProprietaCampo>();
        }

    }
}