using Init.SIGePro.Manager.DTO.Endoprocedimenti;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneEndoPresenti
{
    public record class TipoTitoloFlags(bool Data, bool Numero, bool RilasciatoDa, bool Allegato, bool VerificaFirmaAllegato, bool AllegatoObbligatorio)
    {
        public TipoTitoloFlags(TipiTitoloDtoFlags flags)
        : this(flags.MostraData, flags.MostraNumero, flags.MostraRilasciatoDa, flags.RichiedeAllegato, flags.VerificaFirmaAllegato, flags.AllegatoObbligatorio)
        {
        }
    }
}
