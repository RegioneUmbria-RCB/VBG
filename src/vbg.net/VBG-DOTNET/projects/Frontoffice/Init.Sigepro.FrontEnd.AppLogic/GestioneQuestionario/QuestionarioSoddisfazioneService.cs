using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario
{
    public class QuestionarioSoddisfazioneService: IQuestionarioSoddisfazioneService
    {
        private readonly IQuestionarioSoddisfazioneDao _questionarioSoddisfazioneDao;
        private readonly IConfigurazione<ParametriQuestionarioSoddisfazione> _configurazione;
        private readonly ILog _log = LogManager.GetLogger(typeof(QuestionarioSoddisfazioneService));

        public QuestionarioSoddisfazioneService(IQuestionarioSoddisfazioneDao questionarioSoddisfazioneDao, IConfigurazione<ParametriQuestionarioSoddisfazione> configurazione)
        {
            _questionarioSoddisfazioneDao = questionarioSoddisfazioneDao;
            _configurazione = configurazione;
        }

        public bool CompilazioneQuestionarioAttiva => this._configurazione.Parametri.QuestionarioSoddisfazioneAttivo;

        public bool QuestionarioCompilato(string uuidIstanza)
        {
            try
            {
                this._log.Debug($"Verifica della compilazione del questionario per l'istanza {uuidIstanza}");

                var result = this._questionarioSoddisfazioneDao.QuestionarioCompilato(uuidIstanza);

                this._log.Debug($"Esito della verifica della compilazione del questionario per l'istanza {uuidIstanza}: {result}");

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la verifica della compilazione del questionario per l'istanza {uuidIstanza}: {ex}");

                throw;
            }
        }

        public void SalvaQuestionario(string uuidIstanza, int valutazione, string note)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"Salvataggio del questionario con uuidIstanza={uuidIstanza}, valutazione={valutazione}, note={note}");
                }

                this._questionarioSoddisfazioneDao.SalvaQuestionario(uuidIstanza, valutazione, note);


                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"Questionario salvato con successo per l'istanza {uuidIstanza}"); 
                }
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante il salvataggio del questionario per l'istanza {uuidIstanza}, valutazione={valutazione}, note={note}: {ex}");

                throw;
            }
        }
    }
}
