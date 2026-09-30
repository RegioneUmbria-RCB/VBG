namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza
{

    public interface IProvenienzaDomandaInBozzaService
    {
        string Provenienza { get; }
    }

#if NET48
    public class ProvenienzaDomandaService : IProvenienzaDomandaInBozzaService
    {
        public string Provenienza => "AreaRiservata";
    }
#endif
}
