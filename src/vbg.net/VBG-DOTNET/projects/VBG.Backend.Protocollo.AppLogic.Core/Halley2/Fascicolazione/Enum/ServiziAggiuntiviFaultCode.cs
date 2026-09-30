namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione.Enum
{
    public enum ServiziAggiuntiviFaultCode
    {
        NessunFascicoloEstratto = 21,
        ErroreEstrazioneFascicoli = 22,
        ErroreConsultazioneFascicoli = 23,
        WsNonAttivo = 51,
        CredenzialiAssenti = 81,
        CredenzialiNonCorrette = 91,
        ValoreObbligatorioNonPresente = 400,
        SoapActionNonRiconosciuta = 404,
        ServizioNonPresente = 901,
        WsdlNonPresente = 902,
    }

    public static class ServiziAggiuntiviFaultCodeExt
    {
        public static ServiziAggiuntiviFaultCode? ToFaultCode(int code)
        {
            return code switch
            {
                21 => ServiziAggiuntiviFaultCode.NessunFascicoloEstratto,
                22 => ServiziAggiuntiviFaultCode.ErroreEstrazioneFascicoli,
                >= 23 and <= 29 => ServiziAggiuntiviFaultCode.ErroreConsultazioneFascicoli,
                51 => ServiziAggiuntiviFaultCode.WsNonAttivo,
                81 => ServiziAggiuntiviFaultCode.CredenzialiAssenti,
                >= 91 and <= 99 => ServiziAggiuntiviFaultCode.CredenzialiNonCorrette,
                400 => ServiziAggiuntiviFaultCode.ValoreObbligatorioNonPresente,
                404 => ServiziAggiuntiviFaultCode.SoapActionNonRiconosciuta,
                901 => ServiziAggiuntiviFaultCode.ServizioNonPresente,
                902 => ServiziAggiuntiviFaultCode.WsdlNonPresente,
                _ => null
            };
        }
    }
}
