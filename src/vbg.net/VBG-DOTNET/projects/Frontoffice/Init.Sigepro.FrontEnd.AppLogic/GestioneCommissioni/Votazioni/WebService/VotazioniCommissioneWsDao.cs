using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using log4net;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCommissioni.Votazioni.WebService
{
    public class VotazioniCommissioneWsDao : IVotazioniCommissioneDao
    {
        private readonly VotazioniCommissioneWsProxyCreator _proxyCreator;
        private readonly ILog _log = LogManager.GetLogger(typeof(VotazioniCommissioneWsDao));

        public VotazioniCommissioneWsDao(VotazioniCommissioneWsProxyCreator proxyCreator)
        {
            _proxyCreator = proxyCreator ?? throw new ArgumentNullException(nameof(proxyCreator));
        }

        public void EsprimiVotoPerUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza, VotoPraticaCommissioneDto voto)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"Inizio chiamata a EsprimiVotoPerUtente (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza}, voto={voto.ToJsonString()})");
                }

                this._proxyCreator.CallVoid(ws => ws.Service.EsprimiVotoPerUtente(ws.Token, idCommissione, codiceAnagrafe, uuidIstanza, voto));
            }
            catch (Exception ex)
            {
                var uuid = Guid.NewGuid().ToString();
                this._log.Error($"Errore nella chiamata a EsprimiVotoPerUtente (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza}, voto={voto.ToJsonString()}, guid errore: {uuid}): {ex}");

                throw new Exception($"Si è verificato un errore durante il salvataggio del voto, si prega di riprovare in un secondo momento. <br/>Se l'errore persiste contattare l'assistenza fornendo il seguente riferimento errore: <span class=\"fw-bold\">{uuid}</span>", ex);
            }
            finally
            {
                this._log.Debug($"Fine chiamata a EsprimiVotoPerUtente");
            }
        }

        public IEnumerable<CommissioniVotiBaseDto> GetListaPareri()
        {
            try
            {
                this._log.Debug($"Inizio chiamata a GetListaPareri");

                return this._proxyCreator.Call(ws => ws.Service.GetListaPareri(ws.Token));
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata a GetListaPareri: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Fine chiamata a GetListaPareri");
            }
        }

        public VotazionePraticaCommissioneDto GetVotoUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            try
            {

                this._log.Debug($"Inizio chiamata a GetVotoUtente (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza})");

                var result = this._proxyCreator.Call(ws => ws.Service.GetVotoUtente(ws.Token, idCommissione, codiceAnagrafe, uuidIstanza));

                if (result == null)
                {
                    this._log.Error($"GetVotoUtente (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza}) ha restituito null. Probabilmente l'utente non poteva accedere alla pratica.");
                }

                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"GetVotoUtente invocato con successo (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza}), {result.ToJsonString()}");
                }

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata a GetVotoUtente (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza}): {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Fine chiamata a GetVotoUtente");
            }
        }

        public bool UtentePuoEsprimereVoto(int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            try
            {
                this._log.Debug($"Inizio chiamata a UtentePuoEsprimereVoto (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza})");

                var result = this._proxyCreator.Call(ws => ws.Service.UtentePuoEsprimereVoto(ws.Token, idCommissione, codiceAnagrafe, uuidIstanza));

                this._log.Debug($"Risultato della chiamata: {result}");

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata a UtentePuoEsprimereVoto (idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe}, uuidIstanza={uuidIstanza}): {ex}");

                throw;
            }
            finally
            {
                this._log.Debug($"Fine chiamata a UtentePuoEsprimereVoto");
            }
        }
    }
}
