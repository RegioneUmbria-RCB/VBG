using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Entities;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.DatiDinamici.WebControls;
using Ninject;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
	public class Dyn2DataAccessProviderImpl : IFoDyn2DataAccessProvider
    {
		[Inject]
		public IDatiDinamiciRepository _datiDinamiciRepository { get; set; }


		private int IdModello { get; set; }
		private IDomandaOnlineReadInterface _readInterface;
		private IDomandaOnlineWriteInterface _writeInterface;

		public Dyn2DataAccessProviderImpl(DomandaOnline domanda, int idModello)
		{
			FoKernelContainer.Inject(this);

			this._readInterface = domanda.ReadInterface;
			this._writeInterface = domanda.WriteInterface;


			IdModello = idModello;
		}

		private ModelloDinamicoCache m_cache = null;

		private ModelloDinamicoCache Cache
		{
			get
			{
				if (m_cache == null)
					m_cache = _datiDinamiciRepository.GetCacheModelloDinamico( IdModello );

				return m_cache;
			}
		}

		#region IIstanzeDyn2DatiManager Members

		public virtual SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceScheda)
		{
			var rVal = new SerializableDictionary<int, List<IIstanzeDyn2Dati>>();

			var listaValori = _readInterface.DatiDinamici
											.DatiDinamici
											.Where(x => x.IdCampo >= 0 && x.IndiceScheda == indiceScheda)
											.Select(x => new IstanzeDyn2Dati
											{
												FkD2cId = x.IdCampo,
												Codiceistanza = -1,
												Idcomune = String.Empty,
												Indice = x.IndiceScheda,
												IndiceMolteplicita = x.IndiceMolteplicita,
												Valore = x.Valore,
												Valoredecodificato = x.ValoreDecodificato
											});

			foreach (var valore in listaValori)
			{
				var idCampo = valore.FkD2cId.Value;

				if (!rVal.ContainsKey(idCampo))
					rVal.Add(idCampo, new List<IIstanzeDyn2Dati>());

				rVal[idCampo].Add(valore);
			}

			return rVal;
		}

		public void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaSalvare)
		{
			// Contiene l'indice massimo di molteplicità del modello.
			// Usato in futuro per la gestione della firma a blocchi
			var maxMolteplicita = 0;
			var indiceScheda = modello.IndiceModello;

			foreach (var campoDaSalvare in campiDaSalvare)
			{
				Debug.WriteLine("Salvataggio del campo " + campoDaSalvare.Id);

				var idCampo			= campoDaSalvare.Id;
				var nomeCampo		= campoDaSalvare.NomeCampo;
				var isCampoUpload	= campoDaSalvare.TipoCampo == TipoControlloEnum.Upload;
				var isCampoData = campoDaSalvare.TipoCampo == TipoControlloEnum.Data;

				// Conterrà la lista di righe da eliminare in quanto il campo ha come valore "" oppure
				// perchè la loro molteplicità eccede quella attuale del campo
				var righeDaEliminare = new List<PresentazioneIstanzaDataSet.Dyn2DatiRow>();

				for (int indiceMolteplicita = 0; indiceMolteplicita < campoDaSalvare.ListaValori.Count; indiceMolteplicita++)
				{
					var valore				= campoDaSalvare.ListaValori[indiceMolteplicita].Valore;
					var valoreDecodificato	= campoDaSalvare.ListaValori[indiceMolteplicita].ValoreDecodificato;
					var valoreVuoto			= String.IsNullOrEmpty(valore.Trim());

					if (!valoreVuoto)
						maxMolteplicita = Math.Max(maxMolteplicita, indiceMolteplicita);

					if (String.IsNullOrEmpty(valoreDecodificato))
						valoreDecodificato = valore;

					var rigaDatiDinamici = _readInterface.DatiDinamici.DatiDinamici
														 .Where( x => x.IdCampo == campoDaSalvare.Id && x.IndiceScheda == indiceScheda && x.IndiceMolteplicita == indiceMolteplicita)
														 .FirstOrDefault();

					var isNuovaRiga			= rigaDatiDinamici == null && !valoreVuoto;
					var isValoreSvuotato	= rigaDatiDinamici != null && valoreVuoto;
					var isRigaModificata	= rigaDatiDinamici != null && (rigaDatiDinamici.Valore != valore || rigaDatiDinamici.ValoreDecodificato != valoreDecodificato);

					if (isValoreSvuotato || (isRigaModificata && isCampoUpload))
					{
						_writeInterface.DatiDinamici.EliminaValoreDaIdcampoIndiceMolteplicita(rigaDatiDinamici.IdCampo, indiceScheda, rigaDatiDinamici.IndiceMolteplicita);
					}

                    if ((isValoreSvuotato || isRigaModificata) && isCampoUpload)
                    {
                        // Elimino il vecchio allegato del campo
                        _writeInterface.Documenti.ForzaEliminazioneDocumentoDaCodiceOggetto(Convert.ToInt32(rigaDatiDinamici.Valore));
                    }

					if (isCampoData && !valoreVuoto && Regex.IsMatch(valoreDecodificato, "^[0-9]{8}$"))	// HACK: il valore del campo data è stato probabilmente prepopolato con una formula ma non è stato convertito nel formato dd/MM/yyyy
					{
						var data = DateTime.Now;

						if (DateTime.TryParseExact(valoreDecodificato, "yyyyMMdd", null, System.Globalization.DateTimeStyles.None, out data))
						{
							valoreDecodificato = data.ToString("dd/MM/yyyy");
						}
					}

					if (isNuovaRiga || (isRigaModificata && isCampoUpload && !isValoreSvuotato))
					{
						if (isCampoUpload)
						{
							var nomeAllegato = String.Format("Documento della scheda {0}: {1} {2}", modello.NomeModello, campoDaSalvare.Etichetta, (indiceMolteplicita + 1));
							//_writeInterface.Documenti.AggiungiDocumentoInterventoLibero(nomeAllegato, Convert.ToInt32(valore), valoreDecodificato, -1, "Altri allegati", false);
							_writeInterface.Documenti.AggiungiDocumentoSchedaDinamica(nomeAllegato, Convert.ToInt32(valore), valoreDecodificato);
						}

						_writeInterface.DatiDinamici.AggiungiDatoDinamico(idCampo,indiceScheda, indiceMolteplicita, valore, valoreDecodificato, nomeCampo);
					}

					if (!isValoreSvuotato && isRigaModificata && !isCampoUpload)
					{
						_writeInterface.DatiDinamici.ModificaValoreCampo(rigaDatiDinamici.IdCampo, indiceScheda, rigaDatiDinamici.IndiceMolteplicita, valore, valoreDecodificato);
					}
				}

				// Elimino le righe con molteplicità superiore al numero massimo di valori nel campo
				// Questa funzionalità è stata introdotto con la cancellazione dei blocchi multipli
				var molteplicitaRiga = campoDaSalvare.ListaValori.Count - 1;

				var righeInEccesso = _readInterface
										.DatiDinamici
										.DatiDinamici
										.Where( x => x.IdCampo == campoDaSalvare.Id && x.IndiceMolteplicita > molteplicitaRiga )
										.ToList();
					
				foreach (var rigaDaEliminare in righeInEccesso)
				{
					if (isCampoUpload)
					{
						var valore = rigaDaEliminare.Valore;

						_writeInterface.Documenti.EliminaAllegatoADocumentoDaCodiceOggetto(Convert.ToInt32(valore));
					}

					_writeInterface.DatiDinamici.EliminaValoreDaIdcampoIndiceMolteplicita(rigaDaEliminare.IdCampo, indiceScheda, rigaDaEliminare.IndiceMolteplicita);
				}
			}
			
			// Se nel datase non è presente il modello a cui il campo è associato lo aggiungo
			// e lo marco come compilato
			_writeInterface.DatiDinamici.ModificaStatoCompilazioneModello(IdModello, maxMolteplicita, true);
		}

		public void EliminaValoriCampi(ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaEliminare)
		{
			foreach (var campo in campiDaEliminare)
			{
				for (int i = 0; i < campo.ListaValori.Count; i++)
				{
					_writeInterface.DatiDinamici.EliminaValoreDaIdcampoIndiceMolteplicita(campo.Id, modello.IndiceModello, i);
				}
			}
		}

		#endregion

		#region IDyn2ModelliManager Members

		public IDyn2Modello GetById(string idComune, int idModello)
		{
			return Cache.Modello;
		}

		#endregion

		#region IDyn2ScriptModelloManager Members

		public IDyn2ScriptModello GetById(string idComune, int idModello, TipoScriptEnum contesto)
		{
			if (!Cache.ScriptsModello.ContainsKey(contesto))
				return null;

			return Cache.ScriptsModello[contesto];
		}

		#endregion

		#region IDyn2DettagliModelloManager Members

		public List<IDyn2DettagliModello> GetList(string idComune, int idModello)
		{
			return Cache.Struttura;
		}

		#endregion

		#region IDyn2CampiManager Members

		IDyn2Campo IDyn2CampiManager.GetById(string idComune, int idCampo)
		{
			//return DatiDinamiciMgr.GetCampoDaId(AliasComune, idCampo);
			throw new NotImplementedException();
		}

		public SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(string idComune, int idModello)
		{
			return Cache.ListaCampiDinamici;
		}

		#endregion

		#region IDyn2TestoModelloManager Members
		/*
		IDyn2TestoModello IDyn2TestoModelloManager.GetById(string idComune, int idTesto)
		{
			throw new NotImplementedException();
		}
		*/
		public SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(string idComune, int idModello)
		{
			return Cache.ListaTesti;
		}

		#endregion

		#region IDyn2ScriptCampiManager Members

		public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(string idComune, int idCampo)
		{
			if (Cache.ScriptsCampiDinamici.ContainsKey(idCampo))
				return Cache.ScriptsCampiDinamici[idCampo];

			return new Dictionary<TipoScriptEnum, IDyn2ScriptCampo>();
		}

		#endregion

		#region IDyn2ProprietaCampiManager Members

		public List<IDyn2ProprietaCampo> GetProprietaCampo(string idComune, int idCampo)
		{
			if (Cache.ProprietaCampiDinamici.ContainsKey(idCampo))
				return Cache.ProprietaCampiDinamici[idCampo];

			return new List<IDyn2ProprietaCampo>();
		}

		#endregion
	}
}
