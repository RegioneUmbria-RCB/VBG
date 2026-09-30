using Init.SIGePro.Manager.DTO.Commissioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.WebService
{
    public class CommissioniWsDao : ICommissioniDao
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CommissioniWsDao));
        private readonly CommissioniWsProxyCreator _proxyCreator;

        public CommissioniWsDao(CommissioniWsProxyCreator proxyCreator)
        {
            _proxyCreator = proxyCreator;
        }

        public bool ApprovaAllegato(int idCommissione, int idAllegato, int codiceAnagrafe)
        {
            _log.Debug($"ApprovaAllegato (idCommissione={idCommissione}, idAllegato={idAllegato}, codiceAnagrafe={codiceAnagrafe})");

            try
            {
                var result = this._proxyCreator.Call(ws => ws.Service.ApprovaAllegato(ws.Token,idCommissione, idAllegato, codiceAnagrafe));


                if (result)
                {
                    _log.Info($"Allegato approvato con successo (idCommissione={idCommissione}, idAllegato={idAllegato}, codiceAnagrafe={codiceAnagrafe})");
                }
                else
                {
                    _log.Error($"Approvazione allegato fallita, consultare i log di backend per ulteriori dettagli (idCommissione={idCommissione}, idAllegato={idAllegato}, codiceAnagrafe={codiceAnagrafe})");
                }

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata al metodo ApprovaAllegato (idCommissione={idCommissione}, idAllegato={idAllegato}, codiceAnagrafe={codiceAnagrafe}): {ex}");

                throw;
            }
        }

        public IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(int codiceAnagrafe)
        {
            _log.Debug($"Lettura delle commissioni aperte per il codice anagrafe {codiceAnagrafe}");

            try
            {
                var commissioni = this._proxyCreator.Call(ws => ws.Service.GetCommissioniAperteByCodiceAnagrafe(ws.Token, codiceAnagrafe));

                _log.Debug($"Trovate {commissioni.Length} commissioni per il codice anagrafe {codiceAnagrafe}");

                return commissioni;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata al metodo GetCommissioniAperteByCodiceAnagrafe per il codice anagrafe {codiceAnagrafe}: {ex}");

                throw;
            }
        }

        public DettaglioCommissioneDto GetDettaglioCommissione(int idCommissione, int codiceAnagrafe)
        {
            _log.Debug($"Lettura della commissione {idCommissione} per il codice anagrafe {codiceAnagrafe}");

            try
            {
                var commissione = this._proxyCreator.Call(ws => ws.Service.GetDettaglioCommissione(ws.Token, idCommissione, codiceAnagrafe));

                if (commissione == null)
                {
                    _log.Error($"Pratica {idCommissione} NON trovata per il codice anagrafe {codiceAnagrafe}. L'utente potrebbe non avere diritto di accesso");

                    return null;
                }

                _log.Debug($"Commissione trovata ({commissione.Testata.Descrizione}, {commissione.Pratiche.Length} pratiche totali) per il codice anagrafe {codiceAnagrafe}");
                return commissione;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata al metodo GetDettaglioCommissione per id commissione {idCommissione} e codice anagrafe {codiceAnagrafe}: {ex}");

                throw;
            }
        }

        public PraticaCommissioneEstesaDto GetDettaglioPratica(int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            _log.Debug($"Lettura della pratica {uuidIstanza} per la commissione {idCommissione} e codice anagrafe {codiceAnagrafe}");

            try
            {
                var pratica = this._proxyCreator.Call(ws => ws.Service.GetDettaglioPratica(ws.Token, idCommissione, codiceAnagrafe, uuidIstanza));

                if (pratica == null)
                {
                    _log.Error($"Pratica {uuidIstanza} NON trovata per il codice anagrafe {codiceAnagrafe} e id commissione {idCommissione}");
                    
                    return null;
                }

                _log.Debug($"Pratica trovata ({pratica.DatiPratica.NumeroData}) per il codice anagrafe {codiceAnagrafe} e id commissione {idCommissione}");

                return pratica;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata al metodo GetDettaglioPratica per id commissione {idCommissione}, codice anagrafe {codiceAnagrafe} e codice istanza {uuidIstanza}: {ex}");

                throw;
            }
        }

        public bool VerificaAccessoAFile(int idCommissione, int codiceAnagrafe, string uuidIstanza, int codiceOggetto)
        {
            _log.Debug($"Accesso a file con codiceOggetto {codiceOggetto} della pratica {uuidIstanza} per la commissione {idCommissione} e codice anagrafe {codiceAnagrafe}");

            try
            {
                var esito = this._proxyCreator.Call(ws => ws.Service.VerificaAccessoFile(ws.Token, idCommissione, codiceAnagrafe, uuidIstanza, codiceOggetto));

                _log.Debug($"Verifica dell'accesso {(esito?"": "NON ")}riuscita (codiceOggetto={codiceOggetto}, pratica={uuidIstanza}, commissione={idCommissione}, codiceAnagrafe={codiceAnagrafe})");

                return esito;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata al metodo VerificaAccessoFile (codiceOggetto={codiceOggetto}, pratica={uuidIstanza}, commissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}): {ex}");

                throw;
            }
        }
    }
}
