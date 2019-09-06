using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService2;
using Init.SIGePro.Protocollo.InsielMercato2.Protocollazione.ProtocolliCollegati;

namespace Init.SIGePro.Protocollo.InsielMercato2.LeggiProtocollo.Identificativo
{
    public class IdentificativoNumeroData : IRecordIdentifier
    {
        int _numero;
        int _annoProtocollo;
        string _registro;
        string _ufficio;

        public IdentificativoNumeroData(int numero, int annoProtocollo, string registro, string ufficio)
        {
            _numero = numero;
            _annoProtocollo = annoProtocollo;
            _registro = registro;
            _ufficio = ufficio;
        }

        public recordIdentifier GetRecordIdentifier()
        {
            var res = new recordIdentifier
            {
                number = _numero,
                numberSpecified = true,
                year = _annoProtocollo,
                yearSpecified = true,
            };

            if (!String.IsNullOrEmpty(_registro))
                res.registryCode = _registro;

            if (!String.IsNullOrEmpty(_ufficio))
                res.officeCode = _ufficio;

            return res;
        }

        public previous GetPrevious(direction flusso)
        {
            var res = new previous
            {
                number = _numero,
                numberSpecified = true,
                year = _annoProtocollo,
                yearSpecified = true,
                linkType = ProtocolliCollegatiConstants.PRIMO,
                direction = flusso,
                directionSpecified = true
            };

            if(!String.IsNullOrEmpty(_registro))
                res.registryCode = _registro;

            if (!String.IsNullOrEmpty(_ufficio))
                res.officeCode = _ufficio;

            return res;
        }
    }
}
