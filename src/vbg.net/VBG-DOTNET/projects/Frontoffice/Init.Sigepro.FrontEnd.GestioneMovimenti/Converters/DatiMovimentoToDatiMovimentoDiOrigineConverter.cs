// -----------------------------------------------------------------------
// <copyright file="MovimentoDaEffettuareConverter.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.SIGePro.Manager.DTO.Scadenzario;
using log4net;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Converters
{
    /// <summary>
    /// Responsabile della conversione tra un oggetto di tipo <see cref="DatiMovimento"/> e un oggetto di tipo <see cref="MovimentoDiOrigine"/>
    /// </summary>
    public static class DatiMovimentoExtensions
    {
        public static MovimentoDiOrigine ToMovimentoDiOrigine(this DatiMovimentoDaEffettuareDto source)
        {
            ILog _log = LogManager.GetLogger(typeof(DatiMovimentoExtensions));

            var origine = source;

            var destinazione = new MovimentoDiOrigine();
            destinazione.Software = origine.Software;

            destinazione.Amministrazione = origine.Amministrazione;
            destinazione.DataAttivita = origine.DataMovimento;
            destinazione.DatiIstanza = new RiferimentiIstanza
            {
                CodiceIstanza = origine.CodiceIstanza,
                IdComune = origine.IdComune,
                NumeroIstanza = origine.NumeroIstanza,
                DataIstanza = origine.DataIstanza,
                Protocollo = new DatiProtocolloMovimento
                {
                    Data = origine.DataProtocolloIstanza,
                    Numero = origine.NumeroProtocolloIstanza
                }
            };
            destinazione.Esito = origine.Esito;
            destinazione.IdMovimento = origine.CodiceMovimento;
            destinazione.NomeAttivita = origine.Descrizione;
            destinazione.Note = origine.Note;
            destinazione.Oggetto = origine.Parere;
            destinazione.CodiceProcedimento = origine.CodiceInventario;
            destinazione.Procedimento = origine.DescInventario;
            destinazione.Protocollo = new DatiProtocolloMovimento
            {
                Data = origine.DataProtocollo,
                Numero = origine.NumeroProtocollo
            };

            destinazione.Pubblica = origine.Pubblica;
            destinazione.PubblicaEsito = origine.VisualizzaEsito;
            destinazione.PubblicaOggetto = origine.VisualizzaParere;
            destinazione.PubblicaSchede = origine.PubblicaSchede;

            if (origine.Allegati != null)
            {
                destinazione.Allegati = origine
                                            .Allegati
                                            .Where(x => x.CodiceOggetto.HasValue)
                                            .Select(x => new DatiAllegatoMovimento
                                            {
                                                IdAllegato = x.CodiceOggetto.Value,
                                                Descrizione = x.Descrizione,
                                                Note = x.Note
                                            }).ToList();
            }

            if (origine.SchedeDinamiche != null)
            {
                destinazione.SchedeDinamiche = origine.SchedeDinamiche.Select(scheda => new SchedaDinamicaMovimento
                {
                    Compilata = false,
                    IdScheda = scheda.Id,
                    NomeScheda = scheda.Titolo,
                    Valori = scheda.Valori == null ? new List<ValoreSchedaDinamicaMovimento>() : scheda.Valori.Select(val => new ValoreSchedaDinamicaMovimento
                    {
                        Id = val.Id,
                        IndiceMolteplicita = val.Indice,
                        Valore = val.Valore,
                        ValoreDecodificato = val.ValoreDecodificato
                    }).ToList(),
                    IdCampiDinamiciContenuti = scheda.IdCampiContenuti.ToList()
                }).ToList();
            }

            return destinazione;
        }
    }
}
