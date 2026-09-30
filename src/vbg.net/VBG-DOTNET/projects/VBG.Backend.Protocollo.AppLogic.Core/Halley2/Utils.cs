namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2
{
    internal static class Utils
    {
        public static string CreaClassifica(HalleyUtilityService.Fascicolo fascicolo)
        {
            if (string.IsNullOrWhiteSpace(fascicolo.categoria))
                return string.Empty;

            var classifica = fascicolo.categoria;

            if (!string.IsNullOrWhiteSpace(fascicolo.classe))
            {
                classifica += $".{fascicolo.classe}";

                if (!string.IsNullOrWhiteSpace(fascicolo.sottoclasse))
                {
                    classifica += $".{fascicolo.sottoclasse}";
                }
            }

            return classifica;
        }

        public static (int? Categoria, int? Classe, int? Sottoclasse) ParseClassifica(string classifica)
        {
            if (string.IsNullOrWhiteSpace(classifica))
                return (null, null, null);

            var parti = classifica.Split('.');

            int? categoria = null;
            int? classe = null;
            int? sottoclasse = null;

            if (parti.Length > 0 && int.TryParse(parti[0], out var cat))
                categoria = cat;

            if (parti.Length > 1 && int.TryParse(parti[1], out var cla))
                classe = cla;

            if (parti.Length > 2 && int.TryParse(parti[2], out var sub))
                sottoclasse = sub;

            return (categoria, classe, sottoclasse);
        }
    }
}
