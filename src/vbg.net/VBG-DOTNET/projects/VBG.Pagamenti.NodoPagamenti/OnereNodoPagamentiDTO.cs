using System;

namespace VBG.Pagamenti.NodoPagamenti
{
    public class OnereNodoPagamentiDTO
    {
        private readonly string _uniqueId;
        private readonly string _descrizione;
        private readonly decimal _importo;
        private readonly string _codiceMappaturaNodoPagamenti;

        public OnereNodoPagamentiDTO(string uniqueId, string codiceMappaturaNodoPagamenti, string descrizioneCausale, decimal importo)
        {
            if (string.IsNullOrEmpty(uniqueId))
            {
                throw new ArgumentException($"'{nameof(uniqueId)}' cannot be null or empty.", nameof(uniqueId));
            }

            if (string.IsNullOrEmpty(codiceMappaturaNodoPagamenti))
            {
                throw new ArgumentException($"'{nameof(codiceMappaturaNodoPagamenti)}' cannot be null or empty.", nameof(codiceMappaturaNodoPagamenti));
            }

            if (string.IsNullOrEmpty(descrizioneCausale))
            {
                throw new ArgumentException($"'{nameof(descrizioneCausale)}' cannot be null or empty.", nameof(descrizioneCausale));
            }

            if (importo < 0.0m)
            {
                throw new ArgumentException($"'{nameof(importo)}' non può essere minore di 0.", nameof(importo));
            }

            this._uniqueId = uniqueId;
            this._descrizione = descrizioneCausale;
            this._importo = importo;
            this._codiceMappaturaNodoPagamenti = codiceMappaturaNodoPagamenti;
        }

        public RegistrazioneContabileWsInType ToRegistrazioneContabileType(SoggettoDebitoreType soggettoDebitore, string riferimentoPratica, DateTime dataScadenza)
        {
            return new RegistrazioneContabileWsInType
            {
                descrizione = $"{this._descrizione} prat. {riferimentoPratica}",
                anno = DateTime.Now.Year.ToString(),
                data = DateTime.Now,
                dataSpecified = true,
                note = "",
                soggettoDebitore = soggettoDebitore,
                rate = new[]{
                    new PosizioneDebitoriaWsInType
                    {
                        dataScadenza = dataScadenza,
                        descrizione = this._descrizione,
                        numeroRata = "1",
                        riferimentiClient = new[]{this._uniqueId },
                        importi = new[] {
                            new ImportoPagamentoWsInType
                            {
                                codiceMappatura = this._codiceMappaturaNodoPagamenti,
                                importo = this._importo
                            }
                        }
                    }
                },
            };
        }
    }
}
