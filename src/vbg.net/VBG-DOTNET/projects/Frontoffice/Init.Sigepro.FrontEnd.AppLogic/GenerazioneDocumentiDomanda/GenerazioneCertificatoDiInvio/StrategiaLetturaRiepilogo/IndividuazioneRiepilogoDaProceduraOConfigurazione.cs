using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo
{
    public class IndividuazioneCertificatoInvioDaProceduraOConfigurazione : IStrategiaIndividuazioneCertificatoInvio
    {
        IndividuazioneCertificatoInvioDaConfigurazione _configurazioneStrategy;
        IndividuazioneCertificatoInvioDaProcedura _proceduraStrategy;

        int _idRiepilogo = -1;

        public IndividuazioneCertificatoInvioDaProceduraOConfigurazione(IndividuazioneCertificatoInvioDaConfigurazione individuazioneRiepilogoDaConfigurazione, IndividuazioneCertificatoInvioDaProcedura individuazioneRiepilogoDaProcedura)
        {
            _configurazioneStrategy = individuazioneRiepilogoDaConfigurazione;
            _proceduraStrategy = individuazioneRiepilogoDaProcedura;
        }

        #region IStrategiaIndividuazioneRiepilogo Members

        public bool IsCertificatoDefinito(int idIstanza)
        {
            if (_proceduraStrategy.IsCertificatoDefinito(idIstanza))
            {
                _idRiepilogo = _proceduraStrategy.CodiceOggetto(idIstanza);
                return true;
            }

            if (_configurazioneStrategy.IsCertificatoDefinito(idIstanza))
            {
                _idRiepilogo = _configurazioneStrategy.CodiceOggetto(idIstanza);
                return true;
            }

            return false;
        }

        public int CodiceOggetto(int idIstanza)
        {
            if (_idRiepilogo == -1)
                throw new InvalidOperationException("Si sta cercando di leggere l'id del riepilogo della domanda ma non è stato possibile individuare un riepilogo della domanda tramite la procedura o tramite la configurazione");

            return _idRiepilogo;
        }

        #endregion
    }
}
