using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielMercatoService;
using Init.SIGePro.Protocollo.InsielMercato.Protocollazione.ProtocolliCollegati;

namespace Init.SIGePro.Protocollo.InsielMercato.LeggiProtocollo.Identificativo
{
    public class IdentificativoId : IRecordIdentifier
    {
        long _documentProg;
        int _movProg;

        public IdentificativoId(string idProtocollo)
        {
            var idProtoArray = idProtocollo.Split(PROTOCOLLO_INSIELMERCATO.Constants.SEPARATORE_ID_PROTOCOLLO.ToCharArray());

            if (idProtoArray.Length < 2)
                throw new Exception("FORMATO IDPROTOCOLLO NON VALIDO, DEVE ESSERE VALORIZZATO CON IL FORMATO [DOCUMENTPROG];[MOVEPROG]");

            _documentProg = Convert.ToInt64(idProtoArray[0]);
            _movProg = Convert.ToInt32(idProtoArray[1]);
        }

        public recordIdentifier GetRecordIdentifier()
        {

            return new recordIdentifier
            {
                moveProg = _movProg,
                documentProg = _documentProg,
                moveProgSpecified = true,
                documentProgSpecified = true,
                directionSpecified = false,
            };
        }


        public previous GetPrevious(direction flusso)
        {
            return new previous
            {
                documentProg = _documentProg,
                documentProgSpecified = true,
                moveProg = _movProg,
                moveProgSpecified = true,
                linkType = ProtocolliCollegatiConstants.PRIMO,
                direction = flusso,
                directionSpecified = true
            };
        }
    }
}
