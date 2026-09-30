using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Creazione;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione
{
    public class FascicolazioneResponse
    {
        public bool Ok { get; internal set; }
        public string ErrorCode { get; internal set; }
        public string Messaggio { get; internal set; }

        internal static FascicolazioneResponse FromWSFascicolazioneResponse(WSFascicolazioneResponse response)
        {
            if (response == null || response.InsDocInProtocolloResult == null)
            {
                return new FascicolazioneResponse
                {
                    Ok = false,
                    Messaggio = "Nessuna risposta dal servizio di fascicolazione"
                };
            }

            if (!String.IsNullOrEmpty(response.InsDocInProtocolloResult.ErrorCode))
            {
                return new FascicolazioneResponse
                {
                    Ok = false,
                    Messaggio = $"{response.InsDocInProtocolloResult.Message} ({response.InsDocInProtocolloResult.ErrorCode})"
                };
            }

            return new FascicolazioneResponse
            {
                Ok = true
            };
        }
    }
}
