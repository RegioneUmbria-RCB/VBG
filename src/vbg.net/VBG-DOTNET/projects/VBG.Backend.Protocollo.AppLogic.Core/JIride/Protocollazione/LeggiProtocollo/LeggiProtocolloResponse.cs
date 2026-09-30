using VBG.Backend.Protocollo.AppLogic.Core.JIride.PEC;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo
{
    public class LeggiProtocolloResponse
    {
        public DocumentoOutXml DocumentoOut { get; internal set; }
        public Accettazione[] Accettazioni { get; internal set; }
        public Consegna[] Consegne { get; internal set; }
        public bool IsCopia { get; internal set; }

        public DatiProtocolloLettoResponseType ToDatiProtocolloLetto()
        {
            if (this.DocumentoOut == null)
            {
                throw new Exception(($"ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DAL WEB SERVICE DOPO LA LETTURA DEL PROTOCOLLO: Non è presente nessuna risposta della lettura del Protocollo dal WebService di JIride"));
            }

            if (this.DocumentoOut.IdDocumento == 0)
            {
                throw new Exception(($"ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DAL WEB SERVICE DOPO LA LETTURA DEL PROTOCOLLO: Id documento uguale a 0, Messaggio: {this.DocumentoOut.Messaggio}, Errore: {this.DocumentoOut.Errore}"));
            }


            var riferimentiPraticaDaAltriFascicoli = this.IsCopia && this.DocumentoOut.AltriFascicoli != null && this.DocumentoOut.AltriFascicoli.Length > 0;

            return new DatiProtocolloLettoResponseType
            {
                Allegati = this.PopolaAllegati(),
                AnnoProtocollo = this.DocumentoOut.AnnoProtocollo.ToString(),
                AnnoNumeroPratica = riferimentiPraticaDaAltriFascicoli
                                    ? this.DocumentoOut.AltriFascicoli[0].AnnoNumeroAltroFascicolo
                                    : !String.IsNullOrEmpty(this.DocumentoOut.AnnoNumeroPratica)
                                        ? $"{this.DocumentoOut.AnnoNumeroPratica}/{this.DocumentoOut.Classifica}"
                                        : null,
                Classifica = this.DocumentoOut.Classifica,
                Classifica_Descrizione = !String.IsNullOrEmpty(this.DocumentoOut.Classifica_Descrizione) ? $"{this.DocumentoOut.Classifica} {this.DocumentoOut.Classifica_Descrizione}" : "",
                DataInserimento = this.DocumentoOut.DataInserimento.HasValue
                                    ? this.DocumentoOut.DataInserimento.Value.ToString("dd/MM/yyyy")
                                    : null,
                DataProtocollo = this.DocumentoOut.DataProtocollo.HasValue ? this.DocumentoOut.DataProtocollo.Value.ToString("dd/MM/yyyy") : "",
                DocAllegati = this.DocumentoOut.DocAllegati,
                IdProtocollo = this.DocumentoOut.IdDocumento.ToString(),
                InCaricoA = this.DocumentoOut.InCaricoA,
                InCaricoA_Descrizione = this.DocumentoOut.InCaricoA_Descrizione,
                MittentiDestinatari = this.PopolaMittentiDestinatari(),
                NumeroPratica = riferimentiPraticaDaAltriFascicoli
                                    ? this.DocumentoOut.AltriFascicoli[0].NumeroAltroFascicolo
                                    : this.DocumentoOut.NumeroPratica,
                NumeroProtocollo = this.DocumentoOut.NumeroProtocollo.ToString(),
                Oggetto = this.DocumentoOut.Oggetto,
                Origine = this.DocumentoOut.Origine,
                TipoDocumento = this.DocumentoOut.TipoDocumento,
                TipoDocumento_Descrizione = this.DocumentoOut.TipoDocumento_Descrizione
            };
        }
        private AllegatoResponseType[] PopolaAllegati()
        {
            if (this.DocumentoOut.Allegati == null || this.DocumentoOut.Allegati.Count() == 0)
            {
                return null;
            }

            var allegati = this.DocumentoOut
                                        .Allegati?
                                        .GroupBy(x => x.IDBase)
                                        .Select(x => x.Key)
                                        .Select(x => this.DocumentoOut
                                                       .Allegati
                                                       .Where(z => z.IDBase == x)
                                                       .OrderByDescending(y => y.Versione)
                                                       .First())
                                        .Select(x => new AllegatoResponseType
                                        {
                                            Commento = this.RecuperaCommento(x.NomeAllegato, x.Commento, x.TipoFile, x.SottoEstensione),
                                            IDBase = x.IDBase.ToString(),
                                            Serial = this.RecuperaCommento(x.NomeAllegato, x.Commento, x.TipoFile, x.SottoEstensione),
                                            Versione = x.Versione.ToString(),
                                            Image = x.Image,
                                            TipoFile = String.IsNullOrEmpty(x.TipoFile) ? "" : x.TipoFile,
                                            //ContentType = String.IsNullOrEmpty(x.TipoFile) ? "" : new OggettiMgr(db).GetContentType(nomeFile)
                                        })
                                        .ToList();

            if (this.Accettazioni != null && this.Accettazioni.Count() > 0)
            {
                allegati.AddRange
                (
                    this.Accettazioni
                        .Select(x => new AllegatoResponseType
                        {
                            IDBase = x.IdRepAccettazione,
                            Commento = $"ACCETTAZIONE_{x.IdRepAccettazione}_({x.EmailDestinatario})",
                            Serial = $"ACCETTAZIONE_{x.IdRepAccettazione}_({x.EmailDestinatario})"
                        })
                );
            }

            if (this.Consegne != null && this.Consegne.Count() > 0)
            {
                allegati.AddRange
                (
                    this.Consegne
                        .Select(x => new AllegatoResponseType
                        {
                            IDBase = x.IdRepConsegna,
                            Commento = $"CONSEGNA_{x.IdRepConsegna}_({x.EmailDestinatario})",
                            Serial = $"CONSEGNA_{x.IdRepConsegna}_({x.EmailDestinatario})"
                        })
                );
            }

            return allegati.ToArray();
        }

        private string RecuperaCommento(string nomeAllegato, string commento, string tipoFile, string sottoEstensione)
        {
            if (!String.IsNullOrEmpty(nomeAllegato))
            {
                return nomeAllegato;
            }

            string retVal = commento;
            if (!String.IsNullOrEmpty(tipoFile))
            {
                retVal = String.Format("{0}.{1}", Path.GetFileNameWithoutExtension(retVal), tipoFile);
                if (!String.IsNullOrEmpty(sottoEstensione))
                {
                    retVal = String.Format("{0}.{1}.{2}", Path.GetFileNameWithoutExtension(retVal), sottoEstensione, tipoFile);
                }
            }

            return retVal;
        }

        private MittDestOutType[] PopolaMittentiDestinatari()
        {

            var presenzaMittentiDestinatari = this.DocumentoOut.MittentiDestinatari != null && this.DocumentoOut.MittentiDestinatari.Count() > 0;
            var popolaMittenteInterno = !String.IsNullOrEmpty(this.DocumentoOut.MittenteInterno) && this.DocumentoOut.Origine != ProtocolloConstants.COD_ARRIVO;


            return presenzaMittentiDestinatari
                    ? this.DocumentoOut.MittentiDestinatari
                        .Select(x => new MittDestOutType
                        {
                            IdSoggetto = x.IdSoggetto.ToString(),
                            CognomeNome = this.DocumentoOut.Origine == ProtocolloConstants.COD_ARRIVO ||
                                            this.DocumentoOut.Origine == ProtocolloConstants.COD_PARTENZA
                                            ? x.CognomeNome
                                            : null
                        })
                        .ToArray()
                    : popolaMittenteInterno
                        ? new MittDestOutType[]
                        {
                            new MittDestOutType
                            {
                                IdSoggetto = this.DocumentoOut.MittenteInterno,
                                CognomeNome = this.DocumentoOut.MittenteInterno_Descrizione
                            }
                        }
                        : null;
        }
    }
}
