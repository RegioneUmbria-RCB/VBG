using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    public class ValidatoreCampi: IValidatoreCampi
    {
        public class RegolaValidazione
        {
            public string NomeCampo { get; set; }
            public string MessaggioErrore { get; set; }
            public string DipendeDaCampo { get; set; }
            public int DeltaIndice{ get; set; }
            public bool LoopValori { get; set; }

            public bool DipendeDaAltroCampo { get { return !String.IsNullOrEmpty(this.DipendeDaCampo); } }

            public RegolaValidazione(string rowLine)
            {
                var parts = rowLine.Split(';');

                this.NomeCampo = parts[0].Trim();
                this.DipendeDaCampo = parts[1].Trim();
                this.DeltaIndice = String.IsNullOrEmpty(parts[2].Trim()) ? 0 : Convert.ToInt32(parts[2].Trim());
                this.LoopValori = parts[3] == "1";

                var err = parts[4].Trim();

                if (String.IsNullOrEmpty(err))
                {
                    err = NomeCampo;
                }

                if (err.StartsWith("\""))
                {
                    err = err.Substring(1, err.Length - 2);
                } 
                else
                {
                    err = "Il campo \"" + err + "\" è obbligatorio";
                }

                this.MessaggioErrore = err;
                
            }
        }

        public class AnalizzaValoriLista
        {
            DatiPdfCompilabile _datiModello;

            public AnalizzaValoriLista(DatiPdfCompilabile datiModello)
            {
                this._datiModello = datiModello;
            }

            public IEnumerable<string> Valida(RegolaValidazione regola)
            {
                const int maxNum = 50;
                var errori = new List<string>();

                for(var i = 0 ; i < maxNum; i++)
                {
                    var nomeCampoControllo = regola.DipendeDaCampo.Replace("[X]", i.ToString());
                    var campoDaVerificare = regola.NomeCampo.Replace("[X]", (i + regola.DeltaIndice).ToString());

                    if (String.IsNullOrEmpty(nomeCampoControllo))
                    {
                        continue;
                    }

                    var valoreControllo = this._datiModello.Valore(nomeCampoControllo);
                    var valoreCampo = this._datiModello.Valore(campoDaVerificare);

                    if (String.IsNullOrEmpty(valoreControllo))
                    {
                        continue;
                    }

                    if(String.IsNullOrEmpty(valoreCampo))
                    {
                        var testoErrore = "File \"" + this._datiModello.NomeFile + "\": " + regola.MessaggioErrore;
                        
                        if (regola.LoopValori) 
                        { 
                            testoErrore += " (riga " + (i + regola.DeltaIndice) + ")";
                        }

                        errori.Add(testoErrore);
                    }

                    if (!regola.LoopValori)
                    {
                        break;
                    }
                }

                return errori;
            }
        }

        IEnumerable<RegolaValidazione> _regoleValidazione;

        DatiPdfCompilabile _datiModello;

        public ValidatoreCampi(string nomeFile, DatiPdfCompilabile datiModello)
        {
            this._regoleValidazione = LeggiFile(nomeFile);
            this._datiModello = datiModello;
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

            foreach(var regola in this._regoleValidazione.Where( x => !x.DipendeDaAltroCampo))
            {
                if (!String.IsNullOrEmpty(this._datiModello.Valore(regola.NomeCampo)))
                {
                    continue;
                }

                errori.Add("File \"" + this._datiModello.NomeFile + "\": " + regola.MessaggioErrore);
            }

            foreach(var regola in this._regoleValidazione.Where(x => x.DipendeDaAltroCampo))
            {
                var err = new AnalizzaValoriLista(this._datiModello).Valida(regola);

                foreach(var e in err)
                {
                    errori.Add(e);
                }
            }

            return errori.Distinct();
        }
    }
}
