using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo
{
    public class IndividuazioneCertificatoInvioDaProcedura : IStrategiaIndividuazioneCertificatoInvio
    {
        private static class Constants
        {
            public const int NoRiepilogo = -1;
        }

        IInterventiRepository _repository;
        int? _codiceOggettoRiepilogo;
        int _codiceIntervento = -1;
        private readonly IVisuraService _visuraService;

        public IndividuazioneCertificatoInvioDaProcedura(IInterventiRepository repository, IVisuraService visuraService)
        {
            _repository = repository;
            _visuraService = visuraService;
        }


        public int CodiceOggetto(int idIstanza)
        {
            EnsureCodiceOggettoRiepilogo(idIstanza);

            if (_codiceOggettoRiepilogo.Value == Constants.NoRiepilogo)
                throw new InvalidOperationException("Si sta cercando di leggere il codice oggetto del riepilogo della domanda ma non esiste un riepilogo domanda collegato alla procedura dell'intervento " + _codiceIntervento);

            return _codiceOggettoRiepilogo.Value;
        }

        public bool IsCertificatoDefinito(int idIstanza)
        {
            EnsureCodiceOggettoRiepilogo(idIstanza);

            return _codiceOggettoRiepilogo.Value != Constants.NoRiepilogo;
        }

        private void EnsureCodiceOggettoRiepilogo(int idIstanza)
        {
            if (_codiceOggettoRiepilogo.HasValue)
                return;

            _codiceIntervento = Convert.ToInt32(_visuraService.GetById(idIstanza, new VisuraIstanzaFlags { LeggiDatiConfigurazione = false }).CODICEINTERVENTOPROC);

            _codiceOggettoRiepilogo = _repository.GetCodiceOggettoCertificatoDiInvioDaIdIntervento(_codiceIntervento);

            if (!_codiceOggettoRiepilogo.HasValue)
                _codiceOggettoRiepilogo = Constants.NoRiepilogo;
        }

    }
}
