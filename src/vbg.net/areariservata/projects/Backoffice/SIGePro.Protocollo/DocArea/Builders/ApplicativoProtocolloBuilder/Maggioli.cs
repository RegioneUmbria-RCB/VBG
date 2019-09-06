using Init.SIGePro.Protocollo.DocArea.Configurations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public class Maggioli : ITipoFornitoreProtocolloDocArea
    {
        DocAreaSegnaturaParamConfiguration _configuration;

        public Maggioli(DocAreaSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
        }

        public Parametro[] GetParametriApplicativoProtocollo()
        {
            var retVal = new Parametro[]
            {
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPO_DOCUMENTO, valore = _configuration.TipoDocumento ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.INDIRIZZO, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.CITTA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Comune ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.CAP, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.SIGLA_PROVINCIA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""}
            };

            return retVal;
        }

        public string TipoProtocolloDocumentoPrimario
        {
            get { return _configuration.VertParams.TipoDocumentoPrincipale ?? ""; }
        }

        public string TipoProtocolloAllegati
        {
            get { return _configuration.VertParams.TipoDocumentoAllegato ?? ""; }
        }
    }
}
