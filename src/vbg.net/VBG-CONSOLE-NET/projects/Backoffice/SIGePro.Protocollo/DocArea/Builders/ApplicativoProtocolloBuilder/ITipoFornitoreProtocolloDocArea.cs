using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public interface ITipoFornitoreProtocolloDocArea
    {
        Parametro[] GetParametriApplicativoProtocollo();
        string TipoProtocolloDocumentoPrimario { get; }
        string TipoProtocolloAllegati { get; }
    }
}
