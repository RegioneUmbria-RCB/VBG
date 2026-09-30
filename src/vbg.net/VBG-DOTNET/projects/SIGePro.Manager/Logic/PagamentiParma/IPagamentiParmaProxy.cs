using Init.SIGePro.Manager.WsParmaPagamenti;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public interface IPagamentiParmaProxy
    {
        DtoOutInserimentoEmissioni InserisciEmissioniByCodiceAnno(string codiceIdentificativo, RateizzazioneEmissioniParmaBase emissioniFactory);
        DtoEsitoPagameti InserisciEmissioniAvvisoPagoPA(int codiceIdentificativo, RateizzazioneEmissioniParmaBase emissioniFactory);
    }
}