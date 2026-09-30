using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneQuestionario
{
    public interface IQuestionarioSoddisfazioneService
    {
        bool CompilazioneQuestionarioAttiva { get; }
        bool QuestionarioCompilato(string uuidIstanza);
        void SalvaQuestionario(string uuidIstanza, int valutazione, string note);
    }
}
