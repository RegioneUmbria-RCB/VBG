using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriQuestionarioSoddisfazione : IParametriConfigurazione
    {
        public readonly bool QuestionarioSoddisfazioneAttivo;

        public ParametriQuestionarioSoddisfazione(bool questionarioSoddisfazioneAttivo)
        {
            QuestionarioSoddisfazioneAttivo = questionarioSoddisfazioneAttivo;
        }
    }
}
