using VBG.DatiDinamici.Interfaces;
using VisuraVbg;

namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneIstanze
{
    public static class IstanzeExtensions
    {
        private class ValoreCampo : IValoreCampo
        {
            public int Id { get; set; }
            public string? Valore { get; set; }
            public string? Valoredecodificato { get; set; }
            public int? Indice { get; set; }
            public int? IndiceMolteplicita { get; set; }
        }

        public static Dictionary<int, List<IValoreCampo>> GetValoriDatiDinamici(this Istanze istanza)
        {
            var valoriCampi = new Dictionary<int, List<IValoreCampo>>();
            foreach (var dati in istanza.IstanzeDyn2Dati)
            {
                var id = dati.FkD2cId!.Value;
                var valore = dati.Valore;
                var valoreDecodificato = dati.Valoredecodificato;
                var indice = dati.Indice!.Value;
                var indiceMolteplicita = dati.IndiceMolteplicita!.Value;

                if (!valoriCampi.ContainsKey(dati.FkD2cId!.Value))
                {
                    valoriCampi[id] = new List<IValoreCampo>();
                }

                valoriCampi[id].Add(new ValoreCampo
                {
                    Id = id,
                    Valore = valore,
                    Valoredecodificato = valoreDecodificato,
                    Indice = indice,
                    IndiceMolteplicita = indiceMolteplicita
                });
            }
            return valoriCampi;
        }
    }
}
