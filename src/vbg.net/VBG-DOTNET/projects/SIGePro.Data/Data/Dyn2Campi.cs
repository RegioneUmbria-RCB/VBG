using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
    public partial class Dyn2Campi : IDyn2Campo
    {
        public override string ToString()
        {
            return this.Nomecampo;
        }
    }
}