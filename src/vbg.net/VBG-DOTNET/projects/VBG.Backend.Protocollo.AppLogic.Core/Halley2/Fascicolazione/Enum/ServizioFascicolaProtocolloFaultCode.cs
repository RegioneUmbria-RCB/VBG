namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione.Enum
{
    public enum ServizioFascicolaProtocolloFaultCode
    {
        ProtocolloGiaPresenteNelFascicolo = 1,
        WsNonAttivo = 51,
        OperatoreOPasswordOperatoreAssente = 81,
        CredenzialiOperatoreOUtenteNonCorrette = 91,
        ValoreObbligatorioNonPresente = 400,
        SoapActionNonValorizzata = 404,
        ServizioNonPresente = 901,
        WSDL = 902,
        WsNonConfigurato = 903,
    }

    public static class ServizioFascicolaProtocolloFaultCodeExt
    {
        public static ServizioFascicolaProtocolloFaultCode? ToFaultCode(int code)
        {
            return code switch
            {
                1 => ServizioFascicolaProtocolloFaultCode.ProtocolloGiaPresenteNelFascicolo,
                51 => ServizioFascicolaProtocolloFaultCode.WsNonAttivo,
                81 => ServizioFascicolaProtocolloFaultCode.OperatoreOPasswordOperatoreAssente,
                91 => ServizioFascicolaProtocolloFaultCode.CredenzialiOperatoreOUtenteNonCorrette,
                400 => ServizioFascicolaProtocolloFaultCode.ValoreObbligatorioNonPresente,
                404 => ServizioFascicolaProtocolloFaultCode.SoapActionNonValorizzata,
                901 => ServizioFascicolaProtocolloFaultCode.ServizioNonPresente,
                902 => ServizioFascicolaProtocolloFaultCode.WSDL,
                903 => ServizioFascicolaProtocolloFaultCode.WsNonConfigurato,
                _ => null
            };
        }
    }
}
