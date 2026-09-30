//using Init.SIGePro.DatiDinamici.Interfaces.Anagrafe;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    public partial class AnagrafeDyn2DatiStorico : IValoreCampo
    {
        public string Valoredecodificato => this.Valore;
    }
}
