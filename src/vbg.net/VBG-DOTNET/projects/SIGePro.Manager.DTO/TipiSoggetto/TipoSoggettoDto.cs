using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.TipiSoggetto
{
    [DataContract]
    public class TipiSoggettoInterventoDto
    {
        [DataMember]
        public IEnumerable<TipoSoggettoDto> PersoneFisiche { get; set; }
        [DataMember]
        public IEnumerable<TipoSoggettoDto> PersoneGiuridiche { get; set; }
        [DataMember]
        public int NumeroSoggettiObbligatori { get; set; } = 0;
        [DataMember]
        public int TotaleSoggetti { get; set; } = 0;
    }


    [DataContract]
    public class TipoSoggettoDto : IEquatable<TipoSoggettoDto>
    {
        [DataMember]
        public int? Id { get; set; }
        [DataMember]
        public string Descrizione { get; set; } = "";
        [DataMember]
        public string DescrizioneEstesa { get; set; } = "";

        /// <summary>
        /// Tipo di anagrafica in cui il tipo soggetto viene utilizzato:
        /// F: Persona fisica
        /// G: Persona Giuridica
        /// null: Entrambe
        /// </summary>
        [DataMember]
        public string TipoAnagrafe { get; set; } = "";

        /// <summary>
        /// True se nella DOL è necessario che il tipo soggetto compaia tra i soggetti della domanda
        /// </summary>
        [DataMember]
        public bool Richiesto { get; set; } = false;

        /// <summary>
        /// Quante volte il soggetto può comparire al massimo nella domanda
        /// </summary>
        [DataMember]
        public int OccorrenzeMax { get; set; } = int.MaxValue;

        /// <summary>
        /// Tipo dato dell'anagrafica:
        /// R: Richiedente
        /// T: Tecnico
        /// A: Azienda
        /// blank: Altro soggetto
        /// </summary>
        [DataMember]
        public string FlagTipoDato { get; set; } = "";

        /// <summary>
        /// True se il tipo soggetto richiede un'anagrafica collegata
        /// </summary>
        [DataMember]
        public bool RichiedeAnagraficaCollegata { set; get; }

        /// <summary>
        /// True se il tipo soggetto può essere identificato come il legale tappresentante di un'azienda
        /// </summary>
        [DataMember]
        public bool FlagLegaleRappresentante { set; get; }

        /// <summary>
        /// True se nella DOL l'utente deve specificare una descrizione (es. tipo soggetto "Altro (specificare...)")
        /// </summary>
        [DataMember]
        public bool RichiedeSpecificaDescrizione { set; get; }

        /// <summary>
        /// True se nella DOL l'utente deve specificare i dati dell'albo professionale
        /// </summary>
        [DataMember]
        public bool RichiedeDatiAlbo { set; get; }

        public override bool Equals(object obj)
        {
            return this.Equals(obj as TipoSoggettoDto);
        }

        public bool Equals(TipoSoggettoDto other)
        {
            return !(other is null) &&
                   this.Id == other.Id;
        }

        public override int GetHashCode()
        {
            return 2108858624 + this.Id.GetHashCode();
        }

        public static bool operator ==(TipoSoggettoDto left, TipoSoggettoDto right)
        {
            return EqualityComparer<TipoSoggettoDto>.Default.Equals(left, right);
        }

        public static bool operator !=(TipoSoggettoDto left, TipoSoggettoDto right)
        {
            return !(left == right);
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
