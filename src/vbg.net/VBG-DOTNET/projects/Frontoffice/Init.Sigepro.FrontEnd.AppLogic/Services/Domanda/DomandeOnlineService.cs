using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{


    public class DomandeOnlineService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(DomandeOnlineService));
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IDatiDomandaFoRepository _datiDomandaFoRepository;
        private readonly IPathMapper _pathMapper;

        // Utilizzare solamente per l'aggiornamento delle domande. Per evitare di mettere in cache troppa roba
        private readonly SalvataggioDirettoStrategy _salvataggioDirettoStrategy;

        public DomandeOnlineService(ISalvataggioDomandaStrategy salvataggioStrategy, IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver,
            IDatiDomandaFoRepository datiDomandaFoRepository, IPathMapper pathMapper, SalvataggioDirettoStrategy salvataggioDirettoStrategy)
        {
            this._salvataggioStrategy = salvataggioStrategy;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._datiDomandaFoRepository = datiDomandaFoRepository;
            this._pathMapper = pathMapper;
            this._salvataggioDirettoStrategy = salvataggioDirettoStrategy;
        }

        public int GetProssimoIdDomanda()
        {
            return this._datiDomandaFoRepository.GeneraProssimoIdDomanda(this._aliasSoftwareResolver.AliasComune);
        }

        public async Task<DomandaOnline> GetByIdAsync(int idPresentazione)
        {
            var domanda = await this._salvataggioStrategy.GetByIdAsync(idPresentazione);

            if (domanda == null)
                throw new ArgumentException("Impossibile caricare la domanda con id " + idPresentazione + " e alias " + this._aliasSoftwareResolver.AliasComune);

            return domanda;
        }

        public DomandaOnline GetById(int idPresentazione)
        {
            var domanda = this._salvataggioStrategy.GetById(idPresentazione);

            if (domanda == null)
                throw new ArgumentException("Impossibile caricare la domanda con id " + idPresentazione + " e alias " + this._aliasSoftwareResolver.AliasComune);

            return domanda;
        }

        public int SalvaDomanda(DomandaOnline domanda)
        {
            this._salvataggioStrategy.Salva(domanda, true);

            return domanda.DataKey.IdPresentazione;
        }

        public async Task<int> SalvaDomandaAsync(DomandaOnline domanda)
        {
            await this._salvataggioStrategy.SalvaAsync(domanda, true);

            return domanda.DataKey.IdPresentazione;
        }


        public async Task<int> SalvaDomandaAsync(int idDomanda)
        {
            var domanda = await this.GetByIdAsync(idDomanda);

            return await this.SalvaDomandaAsync(domanda);
        }

        public object LeggiDomandeDaSottoscrivere(string codiceFiscale)
        {
            throw new NotImplementedException();
        }

        public List<DatiDomandaOnlineDto> GetDomandeInSospeso(int codiceAnagrafe)
        {
            var domandeInSospeso = this._datiDomandaFoRepository.LeggiDomandeInSospeso(this._aliasSoftwareResolver.AliasComune,
                                                                                        this._aliasSoftwareResolver.Software,
                                                                                        codiceAnagrafe);

            var domandeNonAggiornate = domandeInSospeso.Where(x => !x.UpgradeCompleto);

            if (domandeNonAggiornate.Any())
            {
                this.AggiornaDatiDomande(domandeNonAggiornate);

                domandeInSospeso = this._datiDomandaFoRepository.LeggiDomandeInSospeso(this._aliasSoftwareResolver.AliasComune,
                                                                                        this._aliasSoftwareResolver.Software,
                                                                                        codiceAnagrafe);
            }

            return domandeInSospeso;
        }

        public async Task<List<DatiDomandaOnlineDto>> GetDomandeInSospesoAsync(int codiceAnagrafe)
        {
            var domandeInSospeso = await this._datiDomandaFoRepository.LeggiDomandeInSospesoAsync(this._aliasSoftwareResolver.AliasComune,
                                                                    this._aliasSoftwareResolver.Software,
                                                                    codiceAnagrafe);

            var domandeNonAggiornate = domandeInSospeso.Where(x => !x.UpgradeCompleto);

            if (domandeNonAggiornate.Any())
            {
                this.AggiornaDatiDomande(domandeNonAggiornate);

                domandeInSospeso = await this._datiDomandaFoRepository.LeggiDomandeInSospesoAsync(this._aliasSoftwareResolver.AliasComune,
                                                                                        this._aliasSoftwareResolver.Software,
                                                                                        codiceAnagrafe);
            }

            return domandeInSospeso;
        }

        private void AggiornaDatiDomande(IEnumerable<DatiDomandaOnlineDto> domandeNonAggiornate)
        {
            foreach (var dom in domandeNonAggiornate)
            {
                try
                {
                    var domanda = this._salvataggioDirettoStrategy.GetById(dom.Id);

                    this._salvataggioDirettoStrategy.Salva(domanda, false);
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat($"Impossibile effettuare l'aggiornamento della domanda {dom.IdentificativoDomanda}: {ex}");
                }
            }
        }



        public void Elimina(int idDomanda, string codiceFiscaleUtenteCheEliminaDomanda)
        {
            try
            {
                this._log.DebugFormat("Inizio dell'eliminazione della domanda {0} da parte dell'utente {1}", idDomanda, codiceFiscaleUtenteCheEliminaDomanda);

                var domanda = this.GetById(idDomanda);

                if (!domanda.UtentePuoAccedere(codiceFiscaleUtenteCheEliminaDomanda))
                    throw new InvalidOperationException("L'utente " + codiceFiscaleUtenteCheEliminaDomanda + " non dispone di diritti sufficienti per eliminare la domanda");

                // HACK: imposto l'id intervento a -1 in modo da eliminare tutti i files della domanda
                domanda.WriteInterface.AltriDati.ImpostaIntervento(-1, null, null);

                this._salvataggioStrategy.Elimina(domanda);

                this._log.DebugFormat("Eliminazione della domanda terminato");
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'eliminazione della domanda: {0}", ex.ToString());

                throw;
            }
        }

        public void DumpDomanda(int idDomanda)
        {
            var basePath = this._pathMapper.MapPath("~/");
            var outPath = Path.Combine(basePath, "Logs");
            var outfile = Path.Combine(outPath, "dumpDomanda.xml");

            var domanda = this.GetById(idDomanda);

            var dati = domanda.SerializeTo(new V5DataSetSerializer());

            if (File.Exists(outfile))
                File.Delete(outfile);

            using (var file = File.OpenWrite(outfile))
                file.Write(dati, 0, dati.Length);

        }
    }
}
