using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers
{
    internal static class ValoreDatoDinamicoExtensions
    {
        public static ElementoValoreCampoDinamicoType? ToElementoValoreCampoDinamico(this ValoreDatoDinamico? val)
        {
            if (val is null)
                return null;

            return new ElementoValoreCampoDinamicoType
            {
                indice = val.IndiceScheda,
                indiceSpecified = true,
                indiceMolteplicita = val.IndiceMolteplicita,
                indiceMolteplicitaSpecified = true,
                codice = val.Valore,
                descrizione = val.ValoreDecodificato
            };
        }
    }

    internal class SchedeStcAdapter
    {
        private readonly IStrutturaModelloDinamico _struttura;
        private readonly IDatiDinamiciReadInterface _datiDinamiciReadInterface;

        public SchedeStcAdapter(IStrutturaModelloDinamico struttura, IDatiDinamiciReadInterface datiDinamiciReadInterface)
        {
            this._struttura = struttura;
            this._datiDinamiciReadInterface = datiDinamiciReadInterface;
        }

        public SchedaType CreaSchedaStc()
        {
            return new SchedaType
            {
                codice = this._struttura.Modello.Id.ToString(),
                nome = this._struttura.Modello.CodiceScheda,
                descrizione = this._struttura.Modello.Descrizione,
                campi = this.GetCampi().ToArray()
            };
        }

        private IEnumerable<CampoSchedaType> GetCampi()
        {
            foreach (var campo in this._struttura.ListaCampiDinamici.Where(c => c.Key >= 0))
            {
                yield return new CampoSchedaType
                {
                    codice = campo.Value.Id.ToString(),
                    descrizione = String.IsNullOrEmpty(campo.Value.Etichetta) ? String.Empty : campo.Value.Etichetta,
                    campoDinamico = new CampoDinamicoType
                    {
                        valoreUtente = new ValoreCampoDinamicoType
                        {
                            nome = campo.Value.Nomecampo,
                            valore = this.GetValoriStc(campo.Value).ToArray()
                        }
                    }
                };
            }
        }

        private IEnumerable<ElementoValoreCampoDinamicoType> GetValoriStc(IDyn2Campo campo)
        {
            return this._datiDinamiciReadInterface
                        .DatiDinamici
                        .Where(x => x.IdCampo == campo.Id)
                        .Select(x => x.ToElementoValoreCampoDinamico());
        }

    }
}
