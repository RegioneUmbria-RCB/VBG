

namespace SIGePro.Manager.VerticalizzazioniBase
{
    public interface IVerticalizzazioniFactory
    {
        T Create<T>(string alias, string software = "TT", string codiceComune = "") where T : Verticalizzazione, new();

        VerticalizzazioneGenerica CreaVerticalizzazioneGenerica(string nomeVerticalizzazione, string alias, string software = "TT", string codiceComune = "");
    }
}