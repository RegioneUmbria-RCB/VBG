using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
{
    public class DatiProtocolloIn
    {
        public DatiProtocolloIn()
        {
            this.Allegati = new List<ProtocolloAllegati>();
            this.Metadati = new List<MetadatoType>();
        }

        public string Classifica { get; set; }
        public string TipoDocumento { get; set; }
        public string TipoSmistamento { get; set; }
        public string Oggetto { get; set; }
        public MailData Mail { get; set; } = new MailData();
        public string OggettoMail => string.IsNullOrEmpty(this.Mail?.Oggetto) ? this.Oggetto : this.Mail.Oggetto;
        public string CorpoMail => string.IsNullOrEmpty(this.Mail?.Corpo) ? string.Empty : this.Mail.Corpo;
        /// <summary>
        /// A=Arrivo, P=Partenza, I=Interno.
        /// </summary>
        public string Flusso { get; set; }
        public string NumProtMitt { get; set; }
        public string DataProtMitt { get; set; }
        public ListaMittDest Mittenti { get; set; }
        public ListaMittDest Destinatari { get; set; }
        private List<ProtocolloAllegati> Allegati { get; }
        private List<MetadatoType> Metadati { get; }
        /// <summary>
        /// Se la protocollazione è in ambito istanza allora prende la data dell'istanza, se è in ambito movimento allora prende la data del movimento,
        /// se l'ambito è pec allora prende la data della pec, altrimenti la data corrente.
        /// </summary>
        public DateTime? DataRegistrazione { get; set; }

        public void AggiungiMetadati(IEnumerable<MetadatoType> metadati)
        {
            this.Metadati.AddRange(metadati);
        }

        public bool HaMetadati() => this.Metadati.Any();

        public IEnumerable<MetadatoType> RecuperaMetadati()
        {
            return this.Metadati ?? [];
        }

        public void AggiungiAllegato(ProtocolloAllegati allegato)
        {
            this.Allegati.Add(allegato);
        }

        public void AggiungiAllegati(IEnumerable<ProtocolloAllegati> allegati)
        {
            this.Allegati.AddRange(allegati);
        }

        public bool HaAllegati() => this.NumeroAllegatiPresenti > 0;

        public int NumeroAllegatiPresenti => this.Allegati.Count;

        public IEnumerable<int> CodiciOggettoAllegati()
        {
            return this
                    .Allegati?
                    .Where(x => !String.IsNullOrEmpty(x.CODICEOGGETTO))
                    .Select(x => Convert.ToInt32(x.CODICEOGGETTO)) ?? [];
        }

        public IEnumerable<ProtocolloAllegati> RecuperaAllegati()
        {
            return this.Allegati ?? [];
        }

        internal void ImpostaAllegatoPrincipaleDaCodiceOggetto(int? codiceOggettoAllegatoPrincipale)
        {
            if (!codiceOggettoAllegatoPrincipale.HasValue)
            {
                return;
            }

            var allegatoPrincipale = this.RecuperaAllegati()
                                            .FirstOrDefault(x => x.CODICEOGGETTO == codiceOggettoAllegatoPrincipale.Value.ToString());

            if (allegatoPrincipale == null)
            {
                return;
            }

            this.SpostaAllegatoInCima(allegatoPrincipale);
        }

        public void SpostaAllegatoInCima(ProtocolloAllegati allegatoPrincipale)
        {
            if (this.HaAllegati())
            {
                this.Allegati.Remove(allegatoPrincipale);
                this.Allegati.Insert(0, allegatoPrincipale);
            }
        }
    }
}