namespace VBG.Backend.SIT.AppLogic.Genova
{
    public static class GenovaConstants
    {
        public const string ValoreNonPresente = "-";

        public static class Risorse
        {
            public const string GetElencoStrade = "rstGetElencoStrade";
            public const string GetElencoCivici = "rstGetElencoCivici";
            public const string GetElencoEsponenti = "rstGetElencoEsponenti";
            public const string GetElencoColori = "rstGetElencoColori";
            public const string GetElencoScale = "rstGetElencoScale";
            public const string GetElencoInterni = "rstGetElencoInterni";
            public const string GetElencoLettInterno = "rstGetElencoLettInterno";
            public const string ValidaCivico = "rstValidaCivico";
            public const string ValidaEsponente = "rstValidaEsponente";
            public const string ValidaColore = "rstValidaColore";
            public const string ValidaScala = "rstValidaScala";
            public const string ValidaInterno = "rstValidaInterno";
            public const string ValidaLettInterno = "rstValidaLettInterno";
        }

        public static class Parametri
        {
            public const string CodiceStrada = "CODICE_STRADA";
            public const string NumeroCivico = "NUMERO_CIVICO";
            public const string Esponente = "ESPONENTE";
            public const string Colore = "COLORE";
            public const string Scala = "SCALA";
            public const string Interno = "INTERNO";
            public const string LettInterno = "LETTINTERNO";
            public const string FormatoRisposta = "FORMATO_RISPOSTA";
        }

        public static class Campi
        {
            public const string NumeroCivico = "NUMERO_CIVICO";
            public const string Esponente = "ESPONENTE";
            public const string Colore = "COLORE";
            public const string Scala = "SCALA";
            public const string Interno = "INTERNO";
            public const string LettInterno = "LETTINTERNO";
        }

        public static class Esiti
        {
            public const string Valido = "VALIDO";
            public const string NonValido = "NON VALIDO";
        }
    }
}
