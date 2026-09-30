using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneDatiDinamici
{
    public record class DettaglioSchedaBindingItem(ModelloDinamicoIstanza Scheda, IMascheraSolaLettura MascheraSolaLettura, IEnumerable<int> IndiciScheda);
}
