using System;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Parma
{
    public class VariazioniResponse : AnagrafeResponse
    {
        public string tipo_variazione { get; set; }
        public string descrizione_variazione { get; set; }
        public DateTime data_evento { get; set; }

        public VariazioniResponse() : base()
        {

        }
    }
}