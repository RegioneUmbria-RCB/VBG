
namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare
{
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

    public class MovimentoDaEffettuare
    {
        public int? Version { get; set; }
        public int CodiceIstanza { get; set; }
        public string AliasComune { get; set; }
        public int Id { get; set; }
        public string IdTipoAttivita { get; set; }
        public string NomeAttivita { get; set; }
        public string Note { get; set; }
        public List<DatiAllegatoMovimento> Allegati { get; set; }

        public int IdMovimentoDiOrigine { get; set; }
        public List<DatiRiepilogoSchedaDinamica> RiepiloghiSchedeDinamiche { get; set; }
        [Obsolete("Utilizzare solo per la serializzazione")]
        public List<ValoreSchedaDinamicaMovimento> ValoriSchedeDinamiche { get { return _valoriSchedeDinamiche; } set { _valoriSchedeDinamiche = value; } }
        private List<ValoreSchedaDinamicaMovimento> _valoriSchedeDinamiche = new List<ValoreSchedaDinamicaMovimento>();
        public bool Trasmesso { get; set; }
        public List<int> ListaIdSchedeCompilate { get; set; }
        public List<SostituzioneDocumentale> SostituzioniDocumentali { get; set; }

        public MovimentoDaEffettuare()
        {
            this.Allegati = new List<DatiAllegatoMovimento>();
            this.RiepiloghiSchedeDinamiche = new List<DatiRiepilogoSchedaDinamica>();
            this.ListaIdSchedeCompilate = new List<int>();
            this.SostituzioniDocumentali = new List<SostituzioneDocumentale>();
        }

        public IEnumerable<RiepilogoSchedaDinamica> GetRiepiloghiSchedeDinamiche(IEnumerable<SchedaDinamicaMovimento> schedeRichieste)
        {
            return schedeRichieste.Select(x => new RiepilogoSchedaDinamica(
                x.IdScheda,
                x.NomeScheda,
                this.RiepiloghiSchedeDinamiche
                         .Where(riepilogo => riepilogo.IdScheda == x.IdScheda)
                         .FirstOrDefault()
            ));
        }

        internal void SegnaSchedaComeCompilata(int idScheda)
        {
            if (!this.ListaIdSchedeCompilate.Contains(idScheda))
                this.ListaIdSchedeCompilate.Add(idScheda);
        }

        public void AggiungiValoreSchedaDinamica(ValoreSchedaDinamicaMovimento valore)
        {
            var campoDaEliminare = this._valoriSchedeDinamiche
                                        .Where(x => x.Id == valore.Id && x.IndiceMolteplicita == valore.IndiceMolteplicita)
                                        .ToList();

            foreach (var campo in campoDaEliminare)
            {
                this._valoriSchedeDinamiche.Remove(campo);
            }

            this._valoriSchedeDinamiche.Add(valore);
        }

        public void EliminaValoriCampo(int idCampo)
        {
            this._valoriSchedeDinamiche.RemoveAll(x => x.Id == idCampo);
        }



        internal IEnumerable<ValoreSchedaDinamicaMovimento> GetValoriCampiDinamiciODefault(IEnumerable<ValoreSchedaDinamicaMovimento> valoriSchede)
        {
            if (this.ValoriSchedeDinamiche.Count() > 0)
            {
                return this.ValoriSchedeDinamiche;
            }

            return valoriSchede;
        }

        internal void AnnullaSostituzioneDocumentale(int codiceOggettoSostitutivo)
        {
            this.SostituzioniDocumentali.RemoveAll(x => x.CodiceOggettoSostitutivo == codiceOggettoSostitutivo);
        }

        internal void RimuoviAllegatoDaSchedaDinamica(int idCampoDinamico, int indiceMolteplicita, string vecchioValore)
        {
            if (String.IsNullOrEmpty(vecchioValore))
            {
                return;
            }

            var allegato = this.Allegati.Where(x => x.IdAllegato == Convert.ToInt32(vecchioValore)).FirstOrDefault();

            if (allegato != null)
            {
                this.Allegati.Remove(allegato);
            }            
        }
    }
}
