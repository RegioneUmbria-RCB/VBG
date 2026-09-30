using VBG.DatiDinamici;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public class ElaborazioneMassivaSchedeIstanzaService : IElaborazioneMassivaSchedeIstanzaService
    {

        private readonly IElaborazioneMassivaSchedeIstanzaDao _dao;
        private readonly ILog _log = LogManager.GetLogger(typeof(ElaborazioneMassivaSchedeIstanzaService));
        private readonly IElaborazioneModelloScriptRunner _esecutoreScript;

        public ElaborazioneMassivaSchedeIstanzaService(DataBase db, string idComune)
            :this(new ElaborazioneMassivaSchedeIstanzaDao(db, idComune), new ElaborazioneModelloScriptRunner(db, idComune))
        {
        }

        public ElaborazioneMassivaSchedeIstanzaService(IElaborazioneMassivaSchedeIstanzaDao dao, IElaborazioneModelloScriptRunner esecutoreScript)
        {
            this._dao = dao;
            this._esecutoreScript = esecutoreScript;
        }

        public EsitoElaborazione Elabora(int idElaborazione)
        {
            if (this._dao.IsElaborazioneInCorso(idElaborazione))
            {
                this._log.Error($"Impossibile avviare l'elaborazione massiva delle schede con id {idElaborazione}");
                return new EsitoElaborazione(EsitoElaborazioneMassivaSchedeEnum.ElaborazioneInCorso);
            }

            var elementiElaborati = 0;
            var elementiConErrori = 0;

            try
            {
                var praticheDaElaborare = this._dao.GetPraticheDaElaborare(idElaborazione);
                var schedeDaElaborare = this._dao.GetSchedeDaElaborare(idElaborazione);

                this._log.Info($"Avvio dell'elaborazione {idElaborazione}: {praticheDaElaborare.Count()} pratiche, {schedeDaElaborare.Count()} schede");

                this._dao.AvviaElaborazione(idElaborazione);

                foreach (var pratica in praticheDaElaborare)
                {
                    this._dao.AvviaElaborazioneIstanza(idElaborazione, pratica.CodiceIstanza);

                    elementiElaborati++;

                    foreach (var scheda in schedeDaElaborare)
                    {
                        if (!this._dao.PraticaContieneScheda(pratica.CodiceIstanza, scheda.Id))
                        {
                            pratica.AggiungiErrore($"L'istanza non è collegata alla scheda con id {scheda.Id}");
                            continue;
                        }

                        try
                        {
                            this._esecutoreScript.EseguiScriptElaborazioneMassiva(pratica.CodiceIstanza, scheda.Id);
                        }
                        catch (Exception ex)
                        {
                            pratica.AggiungiErrore(ex.Message);
                        }
                    }

                    if (pratica.HaErrori)
                    {
                        this._dao.ImpostaErroriElaborazione(pratica.IdElaborazione, pratica.CodiceIstanza, pratica.Errori);

                        elementiConErrori++;
                    }
                    else
                    {
                        this._dao.TerminaElaborazioneIstanza(idElaborazione, pratica.CodiceIstanza);
                    }
                }

                this._log.Info($"Elaborazione {idElaborazione} terminata: {elementiElaborati} pratiche elaborate, {elementiConErrori} pratiche con errori");

                if (elementiConErrori > 0)
                {
                    return new EsitoElaborazione(EsitoElaborazioneMassivaSchedeEnum.ElaborazioneCompletataConErrori, elementiElaborati, elementiConErrori);
                }

                return new EsitoElaborazione(EsitoElaborazioneMassivaSchedeEnum.ElaborazioneCompletataConSuccesso, elementiElaborati);

            }
            catch(Exception ex)
            {
                this._log.Error($"L'elaborazione {idElaborazione} è stata interrotta prematuramente a causa di un errore: {ex}");

                return new EsitoElaborazione(EsitoElaborazioneMassivaSchedeEnum.ElaborazioneCompletataConErrori, elementiElaborati, elementiConErrori);
            }
            finally
            {
                this._dao.TerminaElaborazione(idElaborazione);

                this._log.Info($"Elaborazione {idElaborazione} terminata");
            }
        }

    }
}
