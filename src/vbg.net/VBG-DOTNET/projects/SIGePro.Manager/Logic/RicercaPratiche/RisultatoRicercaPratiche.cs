using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercaPratiche
{

    public class EstremiPraticaTrovata
    {
        public int CodiceIstanza { get; set; }
        public string NumeroIstanza { get; set; }
        public DateTime DataIstanza { get; set; }
        public string NumeroProtocollo { get; set; }
        public DateTime? DataProtocollo { get; set; }
        public string DescrizioneLavori { get; set; }
        public string Intervento { get; set; }
    }
    public class RisultatoRicercaPratiche
    {
        public EstremiPraticaTrovata EstremiPratica { get; set; }
        public List<StradarioIstanzaTrovata> Localizzazioni { get; set; } = new List<StradarioIstanzaTrovata>();
        public List<AnagraficaIstanzaTrovata> Anagrafiche { get; set; } = new List<AnagraficaIstanzaTrovata>();
    }
}
