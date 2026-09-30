using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    public class ValidatoreConfrontoCampi : IValidatoreCampi
    {
        public class RegolaValidazione
        {
            public readonly string NomeCampo;
            public readonly string NomeCampoConfronto;
            public readonly string NomeCampoDipendeDa;
            public readonly string Errore;

            public RegolaValidazione(string fileLine)
            {
                var parts = fileLine.Split(';');

                this.NomeCampo = parts[0].Trim();
                this.NomeCampoConfronto = parts[1].Trim();
                this.NomeCampoDipendeDa = parts[2].Trim();
                this.Errore = parts[3].Trim();
            }
        }

        IEnumerable<RegolaValidazione> _regole;
        DatiPdfCompilabile _datiModello1;
        DatiPdfCompilabile _datiModello2;

        public ValidatoreConfrontoCampi(string nomeFileConfigurazione, DatiPdfCompilabile datiModello1, DatiPdfCompilabile datiModello2)
        {
            this._datiModello1 = datiModello1;
            this._datiModello2 = datiModello2;
            this._regole = LeggiFile(nomeFileConfigurazione);
        }

        private IEnumerable<RegolaValidazione> LeggiFile(string nomeFile)
        {
            return File.ReadAllLines(nomeFile)
                .Where(x => !String.IsNullOrEmpty(x.Trim()) &&
                            !x.StartsWith("#"))
                .Select(x => new RegolaValidazione(x)).ToList();
        }


        public IEnumerable<string> GetErroriValidazione()
        {
            var errori = new List<string>();

            foreach (var r in this._regole)
            {
                var valore1 = this._datiModello1.Valore(r.NomeCampo);
                var valore2 = this._datiModello2.Valore(r.NomeCampoConfronto);

                if (!String.IsNullOrEmpty(r.NomeCampoDipendeDa) && String.IsNullOrEmpty(this._datiModello1.Valore(r.NomeCampoDipendeDa)))
                {
                    continue;
                }

                if (valore1 != valore2)
                {
                    errori.Add(String.Format(r.Errore, this._datiModello1.NomeFile, valore1, this._datiModello2.NomeFile, valore2));
                }
            }

            return errori;
        }
    }
}
