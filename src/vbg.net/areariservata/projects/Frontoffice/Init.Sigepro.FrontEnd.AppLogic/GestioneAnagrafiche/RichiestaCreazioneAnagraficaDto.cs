using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

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

        public CreazioneAnagrafeService.AnagrafeType GetAnagrafeType()
        {
            return new AnagrafeAdapter(this.Anagrafe).ToAnagrafeType();
        }


        internal string GetXmlAnagrafica()
        {
            return this.Anagrafe.ToXmlString();
        }
    }
}
