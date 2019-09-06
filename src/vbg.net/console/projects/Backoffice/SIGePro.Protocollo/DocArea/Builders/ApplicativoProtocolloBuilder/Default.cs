using Init.SIGePro.Protocollo.DocArea.Configurations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public class Default : ITipoFornitoreProtocolloDocArea
    {
        DocAreaSegnaturaParamConfiguration _configuration;

        public Default(DocAreaSegnaturaParamConfiguration configuration)
        {
            _configuration = configuration;
        }

        public Parametro[] GetParametriApplicativoProtocollo()
        {
            var parametri = new List<Parametro>()
            {
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPO_SPEDIZIONE, valore = _configuration.TipoDocumento ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.FORMATO_DOCUMENTO, valore = _configuration.TipoDocumento ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPO_DOCUMENTO, valore = _configuration.TipoDocumento ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPO_SMISTAMENTO, valore = _configuration.TipoSmistamento ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_INDIRIZZO, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Indirizzo ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_LOCALITA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_CAP, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.MITTENTE_PROVINCIA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.OPERATORE_INSERIMENTO, valore = _configuration.Operatore ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.CODICE_COMUNE, valore = _configuration.DatiIndirizzoApplicativoProtocollo.CodiceIstatComune ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.COMUNE, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Comune ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.LOCALITA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Localita ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.CAP, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Cap ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.SIGLA_PROVINCIA, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Provincia ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.DESCRIZIONE_INDIRIZZO, valore = _configuration.DatiIndirizzoApplicativoProtocollo.DescrizioneIndirizzo ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TOPONIMO, valore = _configuration.DatiIndirizzoApplicativoProtocollo.Toponimo ?? ""},
                new Parametro{ nome = TipoFornitoreProtocolloConstants.TIPODESTSOGG, valore = _configuration.DatiIndirizzoApplicativoProtocollo.ModalitaInvio ?? ""},
            };

            if (!String.IsNullOrEmpty(_configuration.VertParams.Uo))
                parametri.Add(new Parametro { nome = TipoFornitoreProtocolloConstants.UO, valore = _configuration.VertParams.Uo ?? "" });
                
            return parametri.ToArray();
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
