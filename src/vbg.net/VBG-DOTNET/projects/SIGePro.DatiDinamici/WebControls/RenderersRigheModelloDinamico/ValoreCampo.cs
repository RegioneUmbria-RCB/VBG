using System;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Web;

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
    internal class ValoreCampo
    {
        private readonly CampoDinamicoBase _campoScheda;
        private readonly bool _solaLettura;

        public ValoreCampo(CampoDinamicoBase campoScheda, bool solaLettura)
        {
            this._campoScheda = campoScheda;
            this._solaLettura = solaLettura;
        }


        private string EstraiValore(int indicemolteplicita)
        {
            if (this._campoScheda.TipoCampo == TipoControlloEnum.Label || this._campoScheda.TipoCampo == TipoControlloEnum.Titolo)
            {
                return (this._campoScheda as CampoDinamicoTestuale).TestoStatico;//_campoScheda.ListaValori[0].Valore;
            }

            var valore = this._campoScheda.ListaValori[indicemolteplicita].Valore;
            var valoreDecodificato = this._campoScheda.ListaValori[indicemolteplicita].ValoreDecodificato;

            if (this._solaLettura && !String.IsNullOrEmpty(valoreDecodificato) && this._campoScheda.TipoCampo != TipoControlloEnum.Checkbox)
                return valoreDecodificato;

            return valore;
        }

        internal string AllIndice(int indiceMolteplicita)
        {
            return this.EstraiValore(indiceMolteplicita);
        }
    }
}
