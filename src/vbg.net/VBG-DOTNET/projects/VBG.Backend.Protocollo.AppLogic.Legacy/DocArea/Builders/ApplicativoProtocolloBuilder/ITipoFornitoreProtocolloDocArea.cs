using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Builders.ApplicativoProtocolloBuilder
{
    public interface ITipoFornitoreProtocolloDocArea
    {
        Parametro[] GetParametriApplicativoProtocollo();
        string TipoProtocolloDocumentoPrimario { get; }
        string TipoProtocolloAllegati { get; }
    }
}
