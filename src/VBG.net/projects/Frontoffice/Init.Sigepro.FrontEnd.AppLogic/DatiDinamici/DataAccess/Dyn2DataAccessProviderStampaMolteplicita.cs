using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;

using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
	public class Dyn2DataAccessProviderStampaMolteplicita : IDyn2DataAccessProvider
	{
		DomandaOnline _domanda;
		Dyn2DataAccessProviderImpl _dapImpl;
		IIstanzeManager _istanzeMgr;
		ITokenApplicazioneService _tokenApplicazioneService;
		string _aliasComune;

		public Dyn2DataAccessProviderStampaMolteplicita(DomandaOnline domanda, int idModello, int limiteIndiceMolteplicita, IIstanzeManager istanzeMgr, ITokenApplicazioneService tokenApplicazioneService)
		{
			this._domanda = domanda;
			this._aliasComune = domanda.ReadInterface.AltriDati.AliasComune;
			this._dapImpl = new Dyn2DataAccessProviderStampaMolteplicitaImpl(domanda, idModello, limiteIndiceMolteplicita);
			this._istanzeMgr = istanzeMgr;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

		public Dyn2DataAccessProviderStampaMolteplicita(DomandaOnline domanda, int idModello, int limiteIndiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
			: this(domanda, idModello, limiteIndiceMolteplicita, new Dyn2IstanzeManager(new IstanzaSigeproAdapter(domanda.ReadInterface)), tokenApplicazioneService)
		{
		}

		#region IDyn2DataAccessProvider Members

		public IDyn2ProprietaCampiManager GetProprietaCampiManager()
		{
			return _dapImpl;
		}

		public IDyn2ModelliManager GetModelliManager()
		{
			return _dapImpl;
		}

		public IDyn2DettagliModelloManager GetDettagliModelloManager()
		{
			return _dapImpl;
		}

		public IDyn2TestoModelloManager GetTestoModelloManager()
		{
			return _dapImpl;
		}

		public IDyn2CampiManager GetCampiManager()
		{
			return _dapImpl;
		}

		public IDyn2ScriptCampiManager GetScriptCampiManager()
		{
			return _dapImpl;
		}

		public IDyn2ScriptModelloManager GetScriptModelliManager()
		{
			return _dapImpl;
		}

		public IIstanzeDyn2DatiManager GetIstanzeDyn2DatiManager()
		{
			return _dapImpl;
		}

		public IIstanzeDyn2DatiStoricoManager GetIstanzeDyn2DatiStoricoManager()
		{
			throw new NotImplementedException();
		}

		public IIstanzeManager GetIstanzeManager()
		{
			return _istanzeMgr;
		}

		public Init.SIGePro.DatiDinamici.Interfaces.Anagrafe.IAnagrafeDyn2DatiManager GetAnagrafeDyn2DatiManager()
		{
			throw new NotImplementedException();
		}

		public Init.SIGePro.DatiDinamici.Interfaces.Anagrafe.IAnagrafeDyn2DatiStoricoManager GetAnagrafeDyn2DatiStoricoManager()
		{
			throw new NotImplementedException();
		}

		public Init.SIGePro.DatiDinamici.Interfaces.Anagrafe.IAnagrafeManager GetAnagrafeManager()
		{
			throw new NotImplementedException();
		}

		public Init.SIGePro.DatiDinamici.Interfaces.Attivita.IIAttivitaDyn2DatiManager GetAttivitaDyn2DatiManager()
		{
			throw new NotImplementedException();
		}

		public Init.SIGePro.DatiDinamici.Interfaces.Attivita.IIAttivitaDyn2DatiStoricoManager GetAttivitaDyn2DatiStoricoManager()
		{
			throw new NotImplementedException();
		}

		public Init.SIGePro.DatiDinamici.Interfaces.Attivita.IIAttivitaManager GetAttivitaManager()
		{
			throw new NotImplementedException();
		}

		public Init.SIGePro.DatiDinamici.Interfaces.WebControls.IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager()
		{
			throw new NotImplementedException();
		}

		public string GetToken()
		{
			return this._tokenApplicazioneService.GetToken(this._aliasComune);
		}

		#endregion


		public IQueryLocalizzazioni GetQueryLocalizzazioni()
		{
			return new QueryLocalizzazioni(this._domanda.ReadInterface);
		}
	}
}
