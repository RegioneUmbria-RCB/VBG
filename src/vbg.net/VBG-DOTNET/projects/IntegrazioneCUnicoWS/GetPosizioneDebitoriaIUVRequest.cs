using IntegrazioneCUnicoWS.CUnicoWS;
using System;

namespace IntegrazioneCUnicoWS
{
    public class GetPosizioneDebitoriaIUVRequest
    {

        public string IUV { get; set; }

        internal posizioneDebitoriaIUVRichiesta ToposizioneDebitoriaIUVRichiesta(CUnicoConfigurazione configurazione)
        {
            if (configurazione is null) 
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            return new posizioneDebitoriaIUVRichiesta
            {
                codBel = configurazione.CodBel,
                codUte = configurazione.CodUte,
                codiceEnte = 0,
                IUV = this.IUV,
                servizio = 0
            };
        }
    }
}