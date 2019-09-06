using Init.SIGePro.Protocollo.DocArea.Configurations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public class Datamanagement : ITipoFornitoreProtocolloDocArea
    {
        DocAreaSegnaturaParamConfiguration _configuration;

        public Datamanagement(DocAreaSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
        }

        public Parametro[] GetParametriApplicativoProtocollo()
        {
            var retVal = new Parametro[]
            {
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPO_SPEDIZIONE, valore = _configuration.TipoDocumento ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.OPERATORE_INSERIMENTO, valore = _configuration.Operatore ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_INDIRIZZO, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_LOCALITA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_CAP, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_PROVINCIA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""}
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
