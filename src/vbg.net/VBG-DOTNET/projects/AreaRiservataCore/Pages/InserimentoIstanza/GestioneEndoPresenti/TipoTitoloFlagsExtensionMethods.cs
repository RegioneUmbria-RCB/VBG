using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneEndoPresenti
{
    public static class TipoTitoloFlagsExtensionMethods
    {
        public static Dictionary<string, bool> ToDictionary(this TipoTitoloFlags flags)
        {
            return new Dictionary<string, bool>
            {
                { nameof(flags.Data), flags.Data },
                { nameof(flags.Numero), flags.Numero },
                { nameof(flags.RilasciatoDa), flags.RilasciatoDa },
                { nameof(flags.Allegato), flags.Allegato },
                { nameof(flags.VerificaFirmaAllegato), flags.VerificaFirmaAllegato },
                { nameof(flags.AllegatoObbligatorio), flags.AllegatoObbligatorio }
            };
        }

        public static TipoTitoloFlags ToTipoTitoloFlags(this DropDownItem item)
        {
            return new TipoTitoloFlags(
                item.GetExtraValue<bool>(nameof(TipoTitoloFlags.Data)),
                item.GetExtraValue<bool>(nameof(TipoTitoloFlags.Numero)),
                item.GetExtraValue<bool>(nameof(TipoTitoloFlags.RilasciatoDa)),
                item.GetExtraValue<bool>(nameof(TipoTitoloFlags.Allegato)),
                item.GetExtraValue<bool>(nameof(TipoTitoloFlags.VerificaFirmaAllegato)),
                item.GetExtraValue<bool>(nameof(TipoTitoloFlags.AllegatoObbligatorio))
            );
        }
    }
}
