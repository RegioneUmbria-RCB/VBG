// -----------------------------------------------------------------------
// <copyright file="TipoSoggetto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{
    using Init.SIGePro.Manager.DTO.TipiSoggetto;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class TipoSoggetto
    {

        public int Id { get; }

        public string Descrizione { get; } = "";

        public string DescrizioneEstesa { get; } = "";

        /// <summary>
        /// Tipo di anagrafica in cui il tipo soggetto viene utilizzato:
        /// F: Persona fisica
        /// G: Persona Giuridica
        /// null: Entrambe
        /// </summary>

        public string TipoAnagrafe { get; } = "";

        /// <summary>
        /// True se nella DOL è necessario che il tipo soggetto compaia tra i soggetti della domanda
        /// </summary>

        public bool Richiesto { get; } = false;

        /// <summary>
        /// Quante volte il soggetto può comparire al massimo nella domanda
        /// </summary>

        public int OccorrenzeMax { get; } = int.MaxValue;

        /// <summary>
        /// Tipo dato dell'anagrafica:
        /// R: Richiedente
        /// T: Tecnico
        /// A: Azienda
        /// blank: Altro soggetto
        /// </summary>

        public string FlagTipoDato { get; } = "";

        /// <summary>
        /// True se il tipo soggetto richiede un'anagrafica collegata
        /// </summary>

        public bool RichiedeAnagraficaCollegata { get; }

        /// <summary>
        /// True se il tipo soggetto può essere identificato come il legale tappresentante di un'azienda
        /// </summary>

        public bool FlagLegaleRappresentante { get; }

        /// <summary>
        /// True se nella DOL l'utente deve specificare una descrizione (es. tipo soggetto "Altro (specificare...)")
        /// </summary>

        public bool RichiedeSpecificaDescrizione { get; }

        /// <summary>
        /// True se nella DOL l'utente deve specificare i dati dell'albo professionale
        /// </summary>

        public bool RichiedeDatiAlbo { get; }

        public TipoSoggetto()
        {
        }

        public TipoSoggetto(TipoSoggettoDto ts)
        {
            this.Id = ts.Id.Value;
            this.Descrizione = ts.Descrizione;
            this.DescrizioneEstesa = ts.DescrizioneEstesa;
            this.TipoAnagrafe = ts.TipoAnagrafe;
            this.Richiesto = ts.Richiesto;
            this.OccorrenzeMax = ts.OccorrenzeMax;
            this.FlagTipoDato = ts.FlagTipoDato;
            this.RichiedeAnagraficaCollegata = ts.RichiedeAnagraficaCollegata;
            this.FlagLegaleRappresentante = ts.FlagLegaleRappresentante;
            this.RichiedeSpecificaDescrizione = ts.RichiedeSpecificaDescrizione;
            this.RichiedeDatiAlbo = ts.RichiedeDatiAlbo;
        }

        public bool IsRichiedente()
        {
            return this.FlagTipoDato == "R";
        }

        public bool IsTecnico()
        {
            return this.FlagTipoDato == "T";
        }

        public int ValorePerOrdinamento
        {
            get
            {
                switch (this.FlagTipoDato)
                {
                    case ("R"):
                        return 1000;
                    case ("A"):
                        return 100;
                    case ("T"):
                        return 10;
                }

                return 0;
            }
        }

    }
}
