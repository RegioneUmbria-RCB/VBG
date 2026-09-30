using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Configurations;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public class Ads : ITipoFornitoreProtocolloDocArea
    {
        DocAreaSegnaturaParamConfiguration _configuration;

        public Ads(DocAreaSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
        }

        public Parametro[] GetParametriApplicativoProtocollo()
        {
            var parametri = new Parametro[] 
            { 
                new Parametro{ nome = TipoFornitoreProtocolloConstants.UO, valore = _configuration.VertParams.Uo ?? "" },
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPO_SMISTAMENTO, valore = _configuration.TipoSmistamento ?? "" }
            };

            return parametri;
        }

        public string TipoProtocolloDocumentoPrimario
        {
            get { return !String.IsNullOrEmpty(_configuration.TipoDocumento) ? _configuration.TipoDocumento : _configuration.VertParams.TipoDocumentoPrincipale ?? ""; }
        }

        public string TipoProtocolloAllegati
        {
            get { return _configuration.VertParams.TipoDocumentoAllegato ?? ""; }
        }
    }
}
