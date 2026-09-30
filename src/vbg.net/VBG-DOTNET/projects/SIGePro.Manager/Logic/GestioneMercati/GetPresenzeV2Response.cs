using Init.SIGePro.Manager.WsMercatiService;

namespace Init.SIGePro.Manager.Logic.GestioneMercati
{
    public class GetPresenzeV2Response
    {
        public int NumeroPresenze { get; internal set; }
        public int NumeroPresenzeProp { get; internal set; }
        public bool InseritoAutorizzazione { get; internal set; }
        public bool InseritoPresenze { get; internal set; }
        public bool InseritoSpuntistiMercato { get; internal set; }

        internal static GetPresenzeV2Response FromPresenzeManifestazioneV2Response(PresenzeManifestazioneV2Response response)
        {
            return new GetPresenzeV2Response
            {
                InseritoAutorizzazione = response.inseritoAutorizzazione,
                InseritoPresenze = response.inseritoPresenze,
                InseritoSpuntistiMercato = response.inseritoSpuntistiMercato,
                NumeroPresenze = response.numeroPresenze,
                NumeroPresenzeProp = response.numeroPresenzeProp
            };
        }
    }
}
