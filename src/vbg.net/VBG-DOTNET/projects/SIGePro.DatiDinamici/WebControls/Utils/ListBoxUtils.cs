using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.DatiDinamici.WebControls.Utils
{
    public class ElementoListaValoriListBox
    {
        private string _valore;
        private string _testo;

        public ElementoListaValoriListBox()
        {

        }

        public ElementoListaValoriListBox(string valore)
        {
            valore = valore ?? "";

            if (valore.IndexOf("$") != -1)
            {
                var parts = valore.Split('$');

                this.Valore = parts[0];
                this.Testo = parts[1];
            }
            else
            {
                this.Valore = this.Testo = valore;
            }
        }

        public ElementoListaValoriListBox(string valore, string testo)
        {
            this.Valore = valore;
            this.Testo = testo;
        }



        public string Valore { get => this._valore; set => this._valore = ListBoxUtils.FixString(value); }

        public string Testo { get => this._testo; set => this._testo = ListBoxUtils.FixString(value); }

        public string GetStringaRappresentazione() => this.Valore == this.Testo ? this.Testo : $"{this.Valore}${this.Testo}";
    }

    internal static class ListBoxUtils
    {
        public static string ConvertiInStringaValori(IEnumerable<ElementoListaValoriListBox> elementiLista)
        {
            return String.Join(";",
                        elementiLista.Select(el => el.GetStringaRappresentazione()).ToArray()
                    );
        }


        public static IEnumerable<ElementoListaValoriListBox> ConvertiDaStringaValori(string value)
        {
            return value.Trim()
                        .Split(';')
                        .Select(x => new ElementoListaValoriListBox(x))
                        .ToList();
        }

        public static string FixString(string value)
        {
            return (value ?? "").Trim().Replace("  ", " ").Replace("\t", " ");
        }
    }
}
