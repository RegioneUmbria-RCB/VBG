using Init.SIGePro.Data;
using Init.SIGePro.Manager.Events;
using log4net;
using PersonalLib2.Data;
using System;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneDomandaOnLine
{
    public class EliminaDomandaService : IEliminaDomandaService
    {
        private readonly DataBase _dataBase;
        private readonly string _idComune;
        ILog _log = LogManager.GetLogger(typeof(EliminaDomandaService));

        public EliminaDomandaService(DataBase dataBase, string idComune)
        {
            this._dataBase = dataBase;
            this._idComune = idComune;
        }

        public void EliminaDomanda(int idDomanda, IEventPublisher eventPublisher)
        {
            this._log.Debug($"Inizio eliminazione della bozza di domanda on-line con id {idDomanda}");

            try
            {
                var dom = this.GetById(idDomanda);

                if (dom == null)
                {
                    // Domanda già eliminata???
                    return;
                }

                this._log.Debug($"Pubblico l'evento {nameof(DomandaFOInCancellazioneEvent)} per l'id domanda on-line {idDomanda}");

                try
                {
                    eventPublisher.Publish(new DomandaFOInCancellazioneEvent(idDomanda));
                }
                catch (Exception)
                {
                    this._log.Error($"Errore durante la notifica dell'evento {typeof(DomandaFOInCancellazioneEvent)} per l'id domanda {idDomanda}. La cancellazione verrà comunque effettuata");
                }
                this.Delete(dom);

                this._log.Debug($"Eliminazione della bozza di domanda on-line con id {idDomanda} riuscita");

            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'eliminazione della domanda on-line con id {idDomanda}: {ex.ToString()}");

                throw;
            }
        }

        private FoDomande GetById(int idDomanda)
        {
            var mgr = new FoDomandeMgr(_dataBase);

            return mgr.GetById(this._idComune, idDomanda);
        }

        private void Delete(FoDomande domanda)
        {
            var mgr = new FoDomandeMgr(_dataBase);

            mgr.Delete(domanda);
        }
    }
}
