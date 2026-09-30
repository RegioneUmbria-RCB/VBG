using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using VBG.Frontend.AppLogic.WsAnagraficheService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    public class RichiestaCreazioneAnagraficaDto
    {
        public readonly Anagrafe Anagrafe;
        public readonly CreazioneAnagrafeService.InserimentoAnagrafeRequestTipoInserimento AuthType;

        public RichiestaCreazioneAnagraficaDto(Anagrafe anagrafe, string authType)
        {
            this.Anagrafe = anagrafe;
            this.AuthType = (CreazioneAnagrafeService.InserimentoAnagrafeRequestTipoInserimento)Enum.Parse(typeof(CreazioneAnagrafeService.InserimentoAnagrafeRequestTipoInserimento), authType, true);
        }

        public CreazioneAnagrafeService.AnagrafeType GetAnagrafeType(IComuniService comuniService)
        {
            return new AnagrafeAdapter(this.Anagrafe, comuniService).ToAnagrafeType();
        }


        internal string GetXmlAnagrafica()
        {
            return this.Anagrafe.ToXmlString();
        }
    }
}
