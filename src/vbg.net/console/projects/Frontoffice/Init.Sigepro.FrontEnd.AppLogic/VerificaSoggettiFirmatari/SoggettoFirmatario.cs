using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class SoggettoFirmatario
    {
        string _nome;
        string _cognome;
        string _codicefiscale;
        string _tipoSoggetto;

        public SoggettoFirmatario(string codiceFiscale, string nome, string cognome, string tipoSoggetto)
        {
            this._codicefiscale = codiceFiscale.ToUpperInvariant();
            this._nome = nome.ToUpperInvariant();
            this._cognome = cognome.ToUpperInvariant();
            this._tipoSoggetto = tipoSoggetto;
        }

        internal bool VerificaPresenza(EsitoVerificaFirmaDigitale esitoVerificaFirma)
        {
            foreach(var stringaFirma in esitoVerificaFirma.StringheSoggettiFirmatari)
            {
                if (stringaFirma.IndexOf(this._codicefiscale) >= 0 )
                {
                    return true;
                }

                if (stringaFirma.IndexOf(this._nome) >= 0 && stringaFirma.IndexOf(this._cognome) >= 0 )
                {
                    return true;
                }
            }

            return false;
        }

        public override string ToString()
        {
            return String.Format("{0} {1} in qualità di \"{2}\"", this._cognome, this._nome, this._tipoSoggetto);
        }
    }
}
