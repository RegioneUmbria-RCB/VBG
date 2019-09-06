using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.IO.Compression;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsDatiDomandaFoRepository : WsAreaRiservataRepositoryBase, IDatiDomandaFoRepository
	{
		const string PERMESSI_ACCESSO_SESSION_KEY = "VERIFICA_PERMESSI_ACCESSO_{0}_{1}_{2}_{3}";
		const string LEGGI_DOMANDA_SESSION_FMT_STRING = "WsDatiDomandaFoRepository.domandaCorrente_{0}_{1}";		

		ILog _log = LogManager.GetLogger(typeof(WsDatiDomandaFoRepository));
		V5DataSetSerializer _datasetSerializer;
		DatiDomandaUpgradeService _upgradeService = new DatiDomandaUpgradeService();
        IAuthenticationDataResolver _authenticationDataResolver;

        public WsDatiDomandaFoRepository(AreaRiservataServiceCreator sc, IAuthenticationDataResolver authenticationDataResolver, V5DataSetSerializer datasetSerializer)
			: base(sc)
		{
			this._datasetSerializer = datasetSerializer;
            this._authenticationDataResolver = authenticationDataResolver;
		}

		/// <summary>
		/// Aggiorna il dataset associato alla domanda
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idPresentazione"></param>
		/// <param name="dataSet"></param>
		/// <returns></returns>
		public EsitoSalvataggioDomandaOnline Salva(DomandaOnline domanda)
		{
			var alias					= domanda.DataKey.IdComune;
			var software				= domanda.DataKey.Software;
			var id						= domanda.DataKey.IdPresentazione;
			var codiceFiscale			= domanda.DataKey.CodiceUtente;
            var datiDomanda             = ConvertToXml(domanda);
			var identificativoDomanda	= domanda.DataKey.ToSerializationCode();
			var flagPresentata			= domanda.Flags.Presentata;
			var flagTrasferita			= false;

            var anagrafica = _authenticationDataResolver.DatiAutenticazione.DatiUtente;

			using (var ws = _serviceCreator.CreateClient(alias))
			{
				return ws.Service.SalvaDomanda(ws.Token, software, id, anagrafica.Codiceanagrafe.Value, datiDomanda, identificativoDomanda, flagTrasferita, flagPresentata);
			}
		}
		
        public byte[] ConvertToXml(DomandaOnline domanda)
        {
            return domanda.SerializeTo(_datasetSerializer);
        }

		/// <summary>
		/// Legge il dataset dei dati di una domanda a partire dal suo id
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idDomanda"></param>
		/// <returns></returns>
		public PresentazioneIstanzaDbV2 LeggiDataSetDomanda(string aliasComune, int idDomanda)
		{
			if (idDomanda == -1)
			{
				return new PresentazioneIstanzaDbV2();
			}

			string sessionKey = String.Format(LEGGI_DOMANDA_SESSION_FMT_STRING, aliasComune, idDomanda);

//			if (SessionHelper.KeyExists(sessionKey))
//				return SessionHelper.GetEntry<PresentazioneIstanzaDbV2>(sessionKey);

			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				byte[] datiDomanda = ws.Service.LeggiDomanda(ws.Token, idDomanda);

				var domandaUpgraded = _upgradeService.PerformUpgrade(datiDomanda);

				var ds = this._datasetSerializer.Deserialize(domandaUpgraded);// DeserializzaDataSet(datiDomanda, enforceConstraints);

				//SessionHelper.AddEntry(sessionKey, ds);

				return ds;
			}
		}


		public FoDomande LeggiDatiDomanda(string aliasComune, int idDomanda)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.LeggiDatiDomanda(ws.Token, idDomanda);
			}
		}

		/// <summary>
		/// Elimina una domanda dal mezzo di persistenza
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="idDomanda"></param>
		public void Elimina(string aliasComune, int idDomanda)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				ws.Service.EliminaDomanda(ws.Token, idDomanda);
			}
		}


		public int GeneraProssimoIdDomanda(string aliasComune)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				return ws.Service.GetProssimoIdDomanda(ws.Token);
			}
		}


		/// <summary>
		/// Legge le domande in sospeso di un utente
		/// </summary>
		/// <param name="aliasComune"></param>
		/// <param name="software"></param>
		/// <param name="codiceFiscale"></param>
		/// <returns></returns>
		public List<FoDomande> LeggiDomandeInSospeso(string aliasComune, string software, int codiceAnagrafe)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
			{
				var rVal = new List<FoDomande>();

				rVal.AddRange(ws.Service.GetListaDomandeInSospeso(ws.Token, software, codiceAnagrafe));

				return rVal;
			}
		}

		/// <summary>
		/// Verifica se la domanda è già stata flaggata come presentata.
		/// </summary>
		/// <param name="aliasComune">id comune</param>
		/// <param name="idDomanda">id domanda</param>
		/// <returns>true se la domanda è stata presentata</returns>
		public bool DomandaPresentata(string aliasComune, int idDomanda)
		{
			using (var ws = _serviceCreator.CreateClient(aliasComune))
				return ws.Service.VerificaStatoInvio(ws.Token, idDomanda);
		}

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                ws.Service.ImpostaIdIstanzaOrigine(ws.Token, idDomanda, idDomandaOrigine);
            }
        }
    }
}
