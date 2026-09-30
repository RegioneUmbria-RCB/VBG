using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.SIGePro.DatiDinamici.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{

    public class VisuraDyn2DataAccessProvider : IDyn2DataAccessProvider
    {
        ITokenApplicazioneService _tokenService;
        VisuraDyn2ModelliManager _modelliMgr;
        IDyn2ScriptModelloManager _scriptModelloMgr;
        VisuraIstanzeDyn2DatiManager _dyn2DatiManager;

        public VisuraDyn2DataAccessProvider(ITokenApplicazioneService tokenService, VisuraDyn2ModelliManager modelliMgr, VisuraIstanzeDyn2DatiManager dyn2DatiManager)
        {
            this._tokenService = tokenService;
            this._modelliMgr = modelliMgr;
            this._dyn2DatiManager = dyn2DatiManager;

            this._scriptModelloMgr = new DummyScriptModelloMgr();
        }

        #region IDyn2DataAccessProvider
        public IDyn2ProprietaCampiManager GetProprietaCampiManager()
        {
            return this._modelliMgr;
        }

        public IDyn2ModelliManager GetModelliManager()
        {
            return this._modelliMgr;
        }

        public IDyn2DettagliModelloManager GetDettagliModelloManager()
        {
            return this._modelliMgr;
        }

        public IDyn2TestoModelloManager GetTestoModelloManager()
        {
            return this._modelliMgr;
        }

        public IDyn2CampiManager GetCampiManager()
        {
            return this._modelliMgr;
        }

        public IDyn2ScriptCampiManager GetScriptCampiManager()
        {
            return this._modelliMgr;
        }

        public IDyn2ScriptModelloManager GetScriptModelliManager()
        {
            return this._scriptModelloMgr;
        }

        public SIGePro.DatiDinamici.Interfaces.Istanze.IIstanzeDyn2DatiManager GetIstanzeDyn2DatiManager()
        {
            return this._dyn2DatiManager;
        }

        public SIGePro.DatiDinamici.Interfaces.Istanze.IIstanzeDyn2DatiStoricoManager GetIstanzeDyn2DatiStoricoManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.Istanze.IIstanzeManager GetIstanzeManager()
        {
            return new DummyIstanzeManager();
        }
        #endregion 

        #region Sicuramente da non implementare
        public SIGePro.DatiDinamici.Interfaces.Anagrafe.IAnagrafeDyn2DatiManager GetAnagrafeDyn2DatiManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.Anagrafe.IAnagrafeDyn2DatiStoricoManager GetAnagrafeDyn2DatiStoricoManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.Anagrafe.IAnagrafeManager GetAnagrafeManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.Attivita.IIAttivitaDyn2DatiManager GetAttivitaDyn2DatiManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.Attivita.IIAttivitaDyn2DatiStoricoManager GetAttivitaDyn2DatiStoricoManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.Attivita.IIAttivitaManager GetAttivitaManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.Interfaces.WebControls.IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager()
        {
            throw new NotImplementedException();
        }

        public SIGePro.DatiDinamici.GestioneLocalizzazioni.IQueryLocalizzazioni GetQueryLocalizzazioni()
        {
            throw new NotImplementedException();
        }
        #endregion

        public string GetToken()
        {
            return this._tokenService.GetToken();
        }
    }
}
