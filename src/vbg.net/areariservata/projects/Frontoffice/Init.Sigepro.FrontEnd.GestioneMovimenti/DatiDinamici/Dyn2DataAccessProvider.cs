// -----------------------------------------------------------------------
// <copyright file="Dyn2DataAccessProvider.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	using System;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using Init.SIGePro.DatiDinamici.Interfaces;
	using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
	using Init.SIGePro.DatiDinamici.Interfaces.Attivita;
	using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
	using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
	using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
	using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class Dyn2DataAccessProvider : IDyn2DataAccessProvider
	{
		ITokenApplicazioneService _tokenService;
		string _aliasComune;

		IDyn2ProprietaCampiManager _proprietaCampiManager;
		IDyn2ModelliManager _modelliManager;
		IDyn2DettagliModelloManager _dettagliModelloManager;
		IDyn2TestoModelloManager _testoModelloManager;
		IDyn2CampiManager _campiManager;
		IDyn2ScriptCampiManager _scriptCampiManager;
		IDyn2ScriptModelloManager _scriptModelloManager;
		IIstanzeDyn2DatiManager _istanzeDyn2DatiManager;
		IIstanzeManager _istanzeManager;

        MovimentoDaEffettuare _movimentoDaEffettuare;

        public Dyn2DataAccessProvider(ModelloDinamicoCache cacheModelloDinamico, string aliasComune, 
                                        ITokenApplicazioneService tokenService, MovimentoDiOrigine movimentoDiOrigine, 
                                        MovimentoDaEffettuare movimentoDaEffettuare, ICommandSender bus, 
                                        IIstanzeManager istanzeManager)
		{
			this._aliasComune = aliasComune;
			
			this._tokenService = tokenService;

			this._proprietaCampiManager = new Dyn2ProprietaCampiManager(cacheModelloDinamico);
			this._modelliManager = new Dyn2ModelliManager(cacheModelloDinamico);
			this._dettagliModelloManager = new Dyn2DettagliModelloManager(cacheModelloDinamico);
			this._testoModelloManager = new Dyn2TestoModelloManager(cacheModelloDinamico);
			this._campiManager = new Dyn2CampiManager(cacheModelloDinamico);
			this._scriptCampiManager = new Dyn2ScriptCampiManager(cacheModelloDinamico);
			this._scriptModelloManager = new Dyn2ScriptModelloManager(cacheModelloDinamico);
			this._istanzeDyn2DatiManager = new IstanzeDyn2DatiManager(movimentoDaEffettuare, movimentoDiOrigine.SchedeDinamiche, bus);
			this._istanzeManager = istanzeManager;
            this._movimentoDaEffettuare = movimentoDaEffettuare;
		}



		#region IDyn2DataAccessProvider Members

		public IDyn2ProprietaCampiManager GetProprietaCampiManager()
		{
			return this._proprietaCampiManager;
		}

		public IDyn2ModelliManager GetModelliManager()
		{
			return this._modelliManager;
		}

		public IDyn2DettagliModelloManager GetDettagliModelloManager()
		{
			return _dettagliModelloManager;
		}

		public IDyn2TestoModelloManager GetTestoModelloManager()
		{
			return _testoModelloManager;
		}

		public IDyn2CampiManager GetCampiManager()
		{
			return _campiManager;
		}

		public IDyn2ScriptCampiManager GetScriptCampiManager()
		{
			return _scriptCampiManager;
		}

		public IDyn2ScriptModelloManager GetScriptModelliManager()
		{
			return this._scriptModelloManager;
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
			return this._istanzeManager;
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

		public IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager()
		{
			throw new NotImplementedException();
		}

		public string GetToken()
		{
			return this._tokenService.GetToken(this._aliasComune);
		}

		#endregion


		public IQueryLocalizzazioni GetQueryLocalizzazioni()
		{
			var istanza = (Istanze)this._istanzeManager.LeggiIstanza(this._aliasComune, this._movimentoDaEffettuare.CodiceIstanza);

			return new QueryLocalizzazioni(istanza);
		}
	}
}
