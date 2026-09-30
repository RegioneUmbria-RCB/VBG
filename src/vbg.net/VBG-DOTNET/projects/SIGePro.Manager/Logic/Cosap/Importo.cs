using System;

namespace Init.SIGePro.Manager.Logic.Cosap
{
    public class Importo
    {
        public string Descrizione { get; }
        public double Valore { get; }
        private readonly int _cifreDecimali;


        public Importo(double valore, int cifreDecimali = 2)
        {
            if (cifreDecimali < 0)
            {
                throw new ArgumentException($"Valore non valido: {cifreDecimali}", nameof(cifreDecimali));
            }
            this.Descrizione = "";
            this.Valore = valore;
            this._cifreDecimali = cifreDecimali;
        }

        public Importo(string descrizione, double valore, int cifreDecimali = 2)
        {
            if (cifreDecimali < 0)
            {
                throw new ArgumentException($"Valore non valido: {cifreDecimali}", nameof(cifreDecimali));
            }
            this.Descrizione = descrizione;
            this.Valore = valore;
            this._cifreDecimali = cifreDecimali;
        }


        public override string ToString()
        {
            var fi = new System.Globalization.NumberFormatInfo
            {
                NumberDecimalSeparator = ",",
                NumberGroupSeparator = ""
            };

            string format = $"N{this._cifreDecimali}";
            return $"{this.Valore.ToString(format, fi)} €";
        }

        public Importo ImportoRidotto(double percentuale)
        {
            return new Importo(this.Valore * percentuale / 100, this._cifreDecimali);
        }
    }
}
