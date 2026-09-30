using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
using log4net;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories
{
    internal class WsDatiDomandaFoRepository : IDatiDomandaFoRepository
    {
        private const string LEGGI_DOMANDA_SESSION_FMT_STRING = "WsDatiDomandaFoRepository.domandaCorrente_{0}_{1}";
        private readonly ILog _log = LogManager.GetLogger(typeof(WsDatiDomandaFoRepository));
        private readonly V5DataSetSerializer _datasetSerializer;
        private readonly IOggettiService _oggettiService;
        private readonly IProvenienzaDomandaInBozzaService _provenienzaDomandaService;
        private readonly DatiDomandaUpgradeService _upgradeService = new DatiDomandaUpgradeService();
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly WsDatiDomandaServiceCreator _datiDomandaServiceCreator;

        public WsDatiDomandaFoRepository(WsDatiDomandaServiceCreator datiDomandaServiceCreator, IAuthenticationDataResolver authenticationDataResolver, V5DataSetSerializer datasetSerializer,
            IOggettiService oggettiService, IProvenienzaDomandaInBozzaService provenienzaDomandaService)
        {
            this._datasetSerializer = datasetSerializer;
            this._oggettiService = oggettiService;
            this._provenienzaDomandaService = provenienzaDomandaService;
            this._authenticationDataResolver = authenticationDataResolver;
            this._datiDomandaServiceCreator = datiDomandaServiceCreator;
        }

        /// <summary>
        /// Aggiorna il dataset associato alla domanda
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idPresentazione"></param>
        /// <param name="dataSet"></param>
        /// <returns></returns>
        public EsitoSalvataggioDomandaOnlineDto Salva(DomandaOnline domanda, bool aggiornaDataultimaModifica)
        {
            var cmd = this.PreparaComandoSalvataggiodomanda(domanda, aggiornaDataultimaModifica);

            return this._datiDomandaServiceCreator.Call(ws =>
            {
                return ws.Service.SalvaDomanda(ws.Token, cmd);
            });
        }



        public async ValueTask<EsitoSalvataggioDomandaOnlineDto> SalvaAsync(DomandaOnline domanda, bool aggiornaDataultimaModifica)
        {
            var cmd = this.PreparaComandoSalvataggiodomanda(domanda, aggiornaDataultimaModifica);

            return await this._datiDomandaServiceCreator.CallAsync(async ws =>
            {
                return await ws.Service.SalvaDomandaAsync(ws.Token, cmd);
            });
        }

        private SalvaDomandaCommandDto PreparaComandoSalvataggiodomanda(DomandaOnline domanda, bool aggiornaDataultimaModifica)
        {
            var anagrafica = this._authenticationDataResolver.DatiAutenticazione.DatiUtente;

            var cmd = new SalvaDomandaCommandDto
            {
                Bookmark = domanda.ReadInterface?.Bookmarks?.Bookmark,
                CodiceAnagrafe = anagrafica.Codiceanagrafe.Value,
                CodiceIntervento = domanda.ReadInterface?.AltriDati?.Intervento?.Codice,
                DatiDomanda = this.ConvertToXml(domanda),
                FlagPresentata = domanda.Flags.Presentata,
                FlagTrasferita = false,
                IdDomanda = domanda.DataKey.IdPresentazione,
                IdentificativoDomanda = domanda.DataKey.ToSerializationCode(),
                Intervento = domanda.ReadInterface?.AltriDati?.Intervento?.Descrizione,
                Oggetto = domanda.ReadInterface?.AltriDati?.DescrizioneLavori,
                PagamentoAvviato = domanda.ReadInterface?.Oneri?.GetOneriOnlineConPagamentoAvviato()?.Any() ?? false,
                PagamentoCompletato = domanda.ReadInterface?.Oneri?.GetOneriOnlineConPagamentoRiuscito()?.Any() ?? false,
                Richiedente = domanda.ReadInterface?.Anagrafiche?.GetRichiedente()?.ToString() ?? "",
                Software = domanda.DataKey.Software,
                AggiornaDataUltimaModifica = aggiornaDataultimaModifica,
                Provenienza = this._provenienzaDomandaService.Provenienza
            };
            return cmd;
        }

        public byte[] ConvertToXml(DomandaOnline domanda)
        {
            return domanda.SerializeTo(this._datasetSerializer);
        }

        /// <summary>
        /// Legge il dataset dei dati di una domanda a partire dal suo id
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public PresentazioneIstanzaDbV2 LeggiDataSetDomanda(string aliasComune, DatiDomandaOnlineDto datiDomanda)
        {
            if ((datiDomanda?.CodiceOggetto) == null)
            {
                return new PresentazioneIstanzaDbV2();
            }

            // string sessionKey = String.Format(LEGGI_DOMANDA_SESSION_FMT_STRING, aliasComune, idDomanda);

            //			if (SessionHelper.KeyExists(sessionKey))
            //				return SessionHelper.GetEntry<PresentazioneIstanzaDbV2>(sessionKey);

            return this._datiDomandaServiceCreator.Call(ws =>
            {
                var file = this._oggettiService.GetById(datiDomanda.CodiceOggetto ?? -1);//   ws.Service.LeggiDomanda(ws.Token, idDomanda);

                if (file == null)
                {
                    throw new System.Exception($"Impossibile caricare i dati della domanda {datiDomanda.CodiceOggetto}");
                }

                return this.UpgradeDatasetDomanda(file.FileContent);
            });
        }

        private PresentazioneIstanzaDbV2 UpgradeDatasetDomanda(byte[] datiDomanda)
        {
            var domandaUpgraded = this._upgradeService.PerformUpgrade(datiDomanda);

            var ds = this._datasetSerializer.Deserialize(domandaUpgraded);// DeserializzaDataSet(datiDomanda, enforceConstraints);

            //SessionHelper.AddEntry(sessionKey, ds);

            return ds;
        }

        /// <summary>
        /// Legge il dataset dei dati di una domanda a partire dal suo id
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public async Task<PresentazioneIstanzaDbV2> LeggiDataSetDomandaAsync(string aliasComune, DatiDomandaOnlineDto datiDomanda)
        {
            if ((datiDomanda?.CodiceOggetto) == null)
            {
                return new PresentazioneIstanzaDbV2();
            }

            // string sessionKey = String.Format(LEGGI_DOMANDA_SESSION_FMT_STRING, aliasComune, idDomanda);

            //			if (SessionHelper.KeyExists(sessionKey))
            //				return SessionHelper.GetEntry<PresentazioneIstanzaDbV2>(sessionKey);

            return await this._datiDomandaServiceCreator.CallAsync(async (ws) =>
            {
                var file = await this._oggettiService.GetByIdAsync(datiDomanda.CodiceOggetto ?? -1);//   ws.Service.LeggiDomanda(ws.Token, idDomanda);

                if (file == null)
                {
                    throw new System.Exception($"Impossibile caricare i dati della domanda {datiDomanda.CodiceOggetto}");
                }

                return this.UpgradeDatasetDomanda(file.FileContent);
            });
        }

        public async Task<DatiDomandaOnlineDto> LeggiDatiDomandaAsync(int idDomanda)
        {
            return await this._datiDomandaServiceCreator.CallAsync(async (ws) =>
            {
                return await ws.Service.LeggiDatiDomandaAsync(ws.Token, idDomanda);
            });
        }

        public DatiDomandaOnlineDto LeggiDatiDomanda(string aliasComune, int idDomanda)
        {
            return this._datiDomandaServiceCreator.Call(ws =>
            {
                return ws.Service.LeggiDatiDomanda(ws.Token, idDomanda);
            });
        }

        /// <summary>
        /// Elimina una domanda dal mezzo di persistenza
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="idDomanda"></param>
        public void Elimina(int idDomanda)
        {
            this._datiDomandaServiceCreator.CallVoid(ws =>
            {
                ws.Service.EliminaDomanda(ws.Token, idDomanda);
            });
        }


        public int GeneraProssimoIdDomanda(string aliasComune)
        {
            return this._datiDomandaServiceCreator.Call(ws =>
            {
                return ws.Service.GetProssimoIdDomanda(ws.Token);
            });
        }

        public async Task<List<DatiDomandaOnlineDto>> LeggiDomandeInSospesoAsync(string aliasComune, string software, int codiceAnagrafe)
        {
            return await this._datiDomandaServiceCreator.CallAsync(async (ws) =>
            {
                var rVal = new List<DatiDomandaOnlineDto>();

                rVal.AddRange(await ws.Service.GetListaDomandeInSospesoAsync(ws.Token, software, codiceAnagrafe, this._provenienzaDomandaService.Provenienza));

                return rVal;
            });
        }


        /// <summary>
        /// Legge le domande in sospeso di un utente
        /// </summary>
        /// <param name="aliasComune"></param>
        /// <param name="software"></param>
        /// <param name="codiceFiscale"></param>
        /// <returns></returns>
        public List<DatiDomandaOnlineDto> LeggiDomandeInSospeso(string aliasComune, string software, int codiceAnagrafe)
        {
            return this._datiDomandaServiceCreator.Call(ws =>
            {
                return ws.Service.GetListaDomandeInSospeso(ws.Token, software, codiceAnagrafe, this._provenienzaDomandaService.Provenienza)?.ToList() ?? new List<DatiDomandaOnlineDto>();
            });
        }

        /// <summary>
        /// Verifica se la domanda è già stata flaggata come presentata.
        /// </summary>
        /// <param name="aliasComune">id comune</param>
        /// <param name="idDomanda">id domanda</param>
        /// <returns>true se la domanda è stata presentata</returns>
        public bool DomandaPresentata(int idDomanda)
        {
            return this._datiDomandaServiceCreator.Call(ws =>
            {
                return ws.Service.VerificaStatoInvio(ws.Token, idDomanda);
            });
        }

        public async ValueTask<bool> DomandaPresentataAsync(int idDomanda)
        {
            return await this._datiDomandaServiceCreator.CallAsync((ws) => ws.Service.VerificaStatoInvioAsync(ws.Token, idDomanda));
        }

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            this._datiDomandaServiceCreator.CallVoid(ws =>
            {
                ws.Service.ImpostaIdIstanzaOrigine(ws.Token, idDomanda, idDomandaOrigine);
            });
        }

        public bool DomandaEliminata(int idDomanda)
        {
            return this._datiDomandaServiceCreator.Call(ws =>
            {
                return ws.Service.DomandaEliminata(ws.Token, idDomanda);
            });
        }
    }
}
