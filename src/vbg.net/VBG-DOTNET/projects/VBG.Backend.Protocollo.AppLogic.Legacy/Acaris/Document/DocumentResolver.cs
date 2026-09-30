using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using System.Collections.Generic;
using System.Linq;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document
{
    public class DocumentResolver : IDocumentResolver
    {
        private readonly ListaMittDest _mittenti;
        private readonly ListaMittDest _destinatari;

        private class Constants
        {
            public const string Separatore = " - ";
            public const string Annotazione = "Documentazione presentata ai sensi dell'art.65 comma 1, lettera b) del Codice dell'Amministrazione Digitale e s.m.i.";
        }

        public DocumentResolver(ParametriRegoleInfo configurazione, DatiProtocolloIn protocollo)
        {
            this.Configurazione = configurazione;
            this._mittenti = protocollo.Mittenti;
            this._destinatari = protocollo.Destinatari;

            this.OggettoDocumentoPrincipale = protocollo.Oggetto;
            this.NumeroAllegati = protocollo.NumeroAllegatiPresenti > 0 ? protocollo.NumeroAllegatiPresenti - 1 : 0;
            this.Flusso = protocollo.Flusso;
        }

        public string ObjectWSUrl => this.Configurazione.ObjectPortUrl;

        public string DocumentWsUrl => this.Configurazione.DocumentPortUrl;

        public string ManagementWsUrl => this.Configurazione.ManagementPortUrl;

        public string NavigationWSUrl => this.Configurazione.NavigationPortUrl;

        public List<string> MittentiPF
        {
            get
            {
                List<string> retVal = new List<string>();
                var pf = this._mittenti.Anagrafe?.Where(x => x.TIPOANAGRAFE == "F").Select(x => x.GetNomeCompleto());
                if (pf?.Any() == true)
                {
                    retVal.AddRange(pf);
                }
                return retVal;
            }
        }

        public List<string> MittentiPG
        {
            get
            {
                List<string> retVal = new List<string>();

                if (this._mittenti.Amministrazione?.Count > 0)
                {
                    retVal.AddRange(this._mittenti.Amministrazione.Select(x => x.AMMINISTRAZIONE));
                }

                var pg = this._mittenti.Anagrafe?.Where(x => x.TIPOANAGRAFE == "G").Select(x => x.GetNomeCompleto());
                if (pg?.Any() == true)
                {
                    retVal.AddRange(pg);
                }
                return retVal;
            }
        }

        public List<string> DestinatariPG
        {
            get
            {
                List<string> retVal = new List<string>();

                if (this._destinatari?.Amministrazione?.Count > 0)
                {
                    retVal.AddRange(this._destinatari.Amministrazione.Select(x => x.AMMINISTRAZIONE));
                }

                var pg = this._destinatari?.Anagrafe?.Where(x => x.TIPOANAGRAFE == "G").Select(x => x.GetNomeCompleto());
                if (pg?.Any() == true)
                {
                    retVal.AddRange(pg);
                }
                return retVal;
            }
        }

        public List<string> DestinatariPF
        {
            get
            {
                List<string> retVal = new List<string>();

                var pf = this._destinatari?.Anagrafe?.Where(x => x.TIPOANAGRAFE == "F")?.Select(x => x.GetNomeCompleto());
                if (pf?.Any() == true)
                {
                    retVal.AddRange(pf);
                }

                return retVal;
            }
        }

        public int NumeroAllegati { get; }
        
        public string OggettoDocumentoPrincipale { get; }

        public RepositoryId RepositoryId => this.Configurazione.RepositoryID;

        public PrincipalId PrincipalId => this.Configurazione.PrincipalID;

        public string AnnotazioneDocPrincipale => Constants.Annotazione;

        public string UtenteEsteso
        {
            get
            {
                if (this.MittentiPF.Any())
                {
                    var pf = this._mittenti.Anagrafe?.First(x => x.TIPOANAGRAFE == "F");
                    return new StringBuilder()
                                .Append(pf.NOMINATIVO)
                                .Append(" ")
                                .Append(pf.NOME)
                                .Append(" ")
                                .Append(pf.CODICEFISCALE)
                                .ToString()
                                .Trim();
                }

                var pg = this._mittenti.Anagrafe?.Where(x => x.TIPOANAGRAFE == "G");
                if (pg?.Any() == true)
                {
                    return new StringBuilder()
                                .Append(pg.First().NOMINATIVO)
                                .Append(" ")
                                .Append(String.IsNullOrEmpty(pg.First().PARTITAIVA) ? pg.First().CODICEFISCALE : pg.First().PARTITAIVA)
                                .ToString()
                                .Trim();
                }

                return this._mittenti.Amministrazione
                        .Select(x => new StringBuilder()
                                            .Append(x.AMMINISTRAZIONE)
                                            .Append(" ")
                                            .Append(x.PARTITAIVA)
                        )
                        .First()
                        .ToString()
                        .Trim();
            }
        }

        public ParametriRegoleInfo Configurazione { get; }

        public string Flusso { get; }

        public string AccessToken => this.Configurazione.AccessToken;
    }
}
