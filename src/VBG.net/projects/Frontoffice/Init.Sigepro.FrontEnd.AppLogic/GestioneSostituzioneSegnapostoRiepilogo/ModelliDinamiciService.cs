// -----------------------------------------------------------------------
// <copyright file="ModelliDinamiciService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
	using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
	using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
	using Init.SIGePro.DatiDinamici;
	using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

	public class ModelliDinamiciService
	{
		//public delegate BinaryFile GeneraRiepilogoSchedaDinamicaDelegate(int idDomanda, int idModello, string nomeRiepilogo, int indiceMolteplicita);

		IAliasResolver _aliasResolver;
		IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		IConfigurazione<ParametriWorkflow> _configurazione;
		IConfigurazione<ParametriSchedaCittadiniExtracomunitari> _configurazioneSchedaCittadiniEC;
		IDatiDinamiciRepository _datiDinamiciRepository;
		ILog _log = LogManager.GetLogger(typeof(ModelliDinamiciService));
		RiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory;
		ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
		IStrutturaModelloReader _strutturaModelloReader;
		ITokenApplicazioneService _tokenApplicazioneService;
		FileConverterService _fileConverterService;

		public ModelliDinamiciService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAliasResolver aliasResolver, IAllegatiDomandaFoRepository allegatiDomandaFoRepository, IDatiDinamiciRepository datiDinamiciRepository, IConfigurazione<ParametriWorkflow> configurazione, IConfigurazione<ParametriSchedaCittadiniExtracomunitari> configurazioneSchedaCittadiniEC, ITokenApplicazioneService tokenApplicazioneService, IStrutturaModelloReader strutturaModelloReader, RiepilogoModelloInHtmlFactory riepilogoModelloInHtmlFactory, FileConverterService fileConverterService)
		{
			Condition.Requires(salvataggioDomandaStrategy, "salvataggioDomandaStrategy")
					 .IsNotNull();

			Condition.Requires(aliasResolver, "aliasResolver")
					 .IsNotNull();

			Condition.Requires(allegatiDomandaFoRepository, "allegatiDomandaFoRepository")
					 .IsNotNull();

			Condition.Requires(datiDinamiciRepository, "datiDinamiciRepository")
					 .IsNotNull();

			Condition.Requires(configurazione, "configurazione")
					 .IsNotNull();

			Condition.Requires(configurazioneSchedaCittadiniEC, "configurazioneSchedaCittadiniEC")
					 .IsNotNull();

			Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService")
					 .IsNotNull();

			Condition.Requires(strutturaModelloReader, "strutturaModelloReader")
					 .IsNotNull();

			Condition.Requires(fileConverterService, "fileConverterService")
					 .IsNotNull();

			Condition.Requires(riepilogoModelloInHtmlFactory, "riepilogoModelloInHtmlFactory")
					 .IsNotNull();

			
			this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
			this._aliasResolver = aliasResolver;
			this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			this._datiDinamiciRepository = datiDinamiciRepository;
			this._configurazione = configurazione;
			this._configurazioneSchedaCittadiniEC = configurazioneSchedaCittadiniEC;
			this._tokenApplicazioneService = tokenApplicazioneService;
			this._strutturaModelloReader = strutturaModelloReader;
			this._riepilogoModelloInHtmlFactory = riepilogoModelloInHtmlFactory;
			this._fileConverterService = fileConverterService;
		}

		public void AggiungiOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita, BinaryFile file)
		{
			try
			{
				var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

				var aliasComune = _aliasResolver.AliasComune;

				var datiModello = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idModello).FirstOrDefault();
				var rigaRiepilogo = domanda.ReadInterface.RiepiloghiSchedeDinamiche.GetByIdModelloIndiceMolteplicita(idModello, indiceMolteplicita);

				var verificaFirma = datiModello.TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna;
				SalvataggioAllegatoResult esitoSalvataggio = null;

				if (verificaFirma && _configurazione.Parametri.VerificaHashFilesFirmati)
				{
					var md5Confronto = rigaRiepilogo.HashConfronto;

					esitoSalvataggio = _allegatiDomandaFoRepository.SalvaAllegatoConfrontaHash(idDomanda, file, md5Confronto);
				}
				else
				{
					esitoSalvataggio = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, verificaFirma);
				}

				domanda.WriteInterface.RiepiloghiSchedeDinamiche.SalvaOggettoRiepilogo(idModello, indiceMolteplicita, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

				_salvataggioDomandaStrategy.Salva(domanda);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in AggiungiOggettoRiepilogo: {0}", ex.ToString());

				throw;
			}
		}

		public void EliminaModello(int idDomanda, int idModello, int indice)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			/*
			var idModello = modelloDinamico.IdModello;

			modelloDinamico.DataAccessProvider = new Dyn2DataAccessProvider(domanda, idModello, _tokenApplicazioneService);
			modelloDinamico.Elimina();
			*/
			var modello = GetModelloDinamico(domanda, idModello, indice);

			modello.Elimina();

			/*if (modello.ErroriScript.Count() > 0)
				throw new SalvataggioModelloDinamicoException(modello.ErroriScript);*/

			domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaByIdModello(idModello);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void EliminaOggettoRiepilogo(int idDomanda, int idModello, int indiceMolteplicita)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaOggettoRiepilogo(idModello, indiceMolteplicita);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public IEnumerable<int> GetIndiciScheda(int idDomanda, int idScheda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var strutturaModello = this._strutturaModelloReader.Read(idScheda);

			return domanda.ReadInterface.DatiDinamici.GetIndiciSchede(strutturaModello);
		}

		public ModelloDinamicoIstanza GetModelloDinamico(int idDomanda, int idScheda, int indiceScheda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			return GetModelloDinamico(domanda, idScheda, indiceScheda);
		}

		private ModelloDinamicoIstanza GetModelloDinamico(DomandaOnline domanda, int idScheda, int indiceScheda)
		{
			var dap = new Dyn2DataAccessProvider(domanda, idScheda, _tokenApplicazioneService);
			var loader = new ModelloDinamicoLoader(dap, _aliasResolver.AliasComune, true);
			var scheda = new ModelloDinamicoIstanza(loader, idScheda, -1, indiceScheda, false);

			return scheda;
		}

		public void RigeneraRiepiloghi(int idDomanda, bool generaRiepilogoSchedeCheNonRichiedonoFirma)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var generatoreRiepilogoModelloDinamico = new GeneratoreRiepilogoModelloDinamico(domanda, _allegatiDomandaFoRepository, this._riepilogoModelloInHtmlFactory, this._fileConverterService);

			domanda.WriteInterface.RiepiloghiSchedeDinamiche.RigeneraRiepiloghiModelli(generatoreRiepilogoModelloDinamico, generaRiepilogoSchedeCheNonRichiedonoFirma);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void Salva(int idDomanda, ModelloDinamicoBase modelloDinamico)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var idModello = modelloDinamico.IdModello;

			var dap = new Dyn2DataAccessProvider(domanda, idModello, _tokenApplicazioneService);
			var loader = new ModelloDinamicoLoader(dap, domanda.DataKey.IdComune, true);

			modelloDinamico.Loader = loader;
			modelloDinamico.Salva();

			var campiNonVisibili = modelloDinamico.GetStatoVisibilitaCampi(false);

			if (modelloDinamico.ErroriScript.Count() > 0)
				throw new SalvataggioModelloDinamicoException(modelloDinamico.ErroriScript);

			domanda.WriteInterface.DatiDinamici.SalvaCampiNonVisibili(modelloDinamico.IdModello, campiNonVisibili);

			domanda.WriteInterface.RiepiloghiSchedeDinamiche.EliminaByIdModello(idModello);

			_salvataggioDomandaStrategy.Salva(domanda);
		}

		public void SincronizzaModelliDinamici(int idDomanda)
		{
			var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);

			var alias = domanda.DataKey.IdComune;
			var idIntervento = domanda.ReadInterface.AltriDati.Intervento.Codice;
			var endoSelezionati = domanda.ReadInterface.Endoprocedimenti.NonAcquisiti.Select(x => x.Codice).ToList();
			var tipiLocalizzazioni = domanda.ReadInterface.Localizzazioni.Indirizzi.Select(x => x.TipoLocalizzazione).Distinct();

			var schedeDinamicheRichieste = _datiDinamiciRepository.GetSchedeDaInterventoEEndo(alias, idIntervento, endoSelezionati, tipiLocalizzazioni, UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.Si);

			var schedeintervento = schedeDinamicheRichieste.SchedeIntervento.Select(x => new ModelloDinamicoInterventoDaSincronizzare( x.CodiceIntervento, x.Codice, x.Descrizione, x.TipoFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));

			var schedeEndo = schedeDinamicheRichieste.SchedeEndoprocedimenti.Select(x => new ModelloDinamicoEndoprocedimentoDaSincronizzare(x.CodiceEndo, x.Codice, x.Descrizione, x.TipoFirma, x.Facoltativa, x.Ordine.GetValueOrDefault(999)));



			bool richiedenteIsExtracomunitario = domanda.ReadInterface.Anagrafiche.GetRichiedente().IsCittadinoExtracomunitario;

			ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare modelloCittadiniExtracomunitari = null;

			if (richiedenteIsExtracomunitario && _configurazioneSchedaCittadiniEC.Parametri.EsisteSchedaDinamicaPerCittadiniExtracomunitari)
			{
				modelloCittadiniExtracomunitari = new ModelloDinamicoPerCittadiniExtracomunitariDaSincronizzare(
					_configurazioneSchedaCittadiniEC.Parametri.IdSchedaDinamica.Value,
					_configurazioneSchedaCittadiniEC.Parametri.NomeScheda,
					_configurazioneSchedaCittadiniEC.Parametri.RichiedeFirma ? TipoFirmaEnum.SingoliBlocchi : TipoFirmaEnum.NessunaFirma,
					!_configurazioneSchedaCittadiniEC.Parametri.RichiedeFirma
				);
			}

			var cmd = new SincronizzaModelliDinamiciCommand(schedeintervento, schedeEndo, modelloCittadiniExtracomunitari);

			domanda.WriteInterface.DatiDinamici.SincronizzaModelliDinamici(cmd);

			_salvataggioDomandaStrategy.Salva(domanda);
		}
	}
}
