namespace Init.Sigepro.FrontEnd.AppLogic.WsInterventi
{
    public static class RisultatoVerificaAccessoInterventoExtensions
    {
        public static bool Is(this RisultatoVerificaAccessoIntervento risultato, TipoAccessibilitaIntervento tipo)
        {
            return risultato.Risultato == tipo;
        }
    }
}
