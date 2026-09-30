using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione;
using PersonalLib2.Data;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using Init.SIGePro.Protocollo.AcarisObjectServicePort;
using System;
using System.Collections.Generic;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
{
    public class FascicoloRealeAnnualeResolver : FolderResolver
    {
        private static class Constants
        {
            public const string _separatore = " - ";
            public const string _annotazione = "Documentazione presentata ai sensi dell'art.65 comma 1, lettera b) del Codice dell'Amministrazione Digitale e s.m.i.";
        }

        private DataBase _db;
        private string _idComuneAlias;
        private string _idComune;
        private string _codiceComune;
        private string _software;
        private string _operatore;
        private int _idTitolario;
        private int _codiceIstanza;
        private string _codiceSerieFascicoli;
        private int _anno;
        private string _numero;
        private string _paroleChiave;
        private string _soggetto;
        private string _voce;
        private string _oggettoDocPrincipale;
        private ListaMittDest _mittenti;
        private ListaMittDest _destinatari;
        private int _numeroAllegati;
        private string _nomeFilePrincipale;
        private int _anniConservazioneCorrente;
        private int _anniConservazioneGenerale;
        private string _oggettoFascicolo;

        public override enumFolderObjectType TypeId => enumFolderObjectType.FascicoloRealeAnnualePropertiesType;

        public override DataBase Db => this._db;
        public override string IdComuneAlias => this._idComuneAlias;
        public override string IdComune => this._idComune;
        public override string CodiceComune => this._codiceComune;
        public override string Software => this._software;
        public override string Operatore => this._operatore;
        public override int IdTitolario => this._idTitolario;
        public override string OggettoFascicolo => this._oggettoFascicolo;
        public override string Numero => this._numero;
        public override string IdProtocollo { get; }
        public override string CodiceSerie
        {
            get
            {
                var serie = new IstanzeMgr(this.Db).GetNumeroFascicoloFromIstanza(this.IdComune, this._codiceIstanza);

                if (String.IsNullOrEmpty(serie))
                {
                    serie = this._codiceSerieFascicoli;
                }

                return serie;
            }
        }
        public override PropertiesType Properties => new FascicoloRealeAnnualePropertiesType
        {
            anno = this._anno,
            archivioCorrente = true,
            conservazioneCorrente = this._anniConservazioneCorrente,
            conservazioneGenerale = this._anniConservazioneGenerale,
            descrizione = this._oggettoFascicolo,
            dataCreazione = DateTime.Now,
            datiPersonali = true,
            datiSensibili = false,
            datiRiservati = false,
            idAOORespMat = new IdAOOType { value = this.IdAoo.Id },
            idNodoRespMat = new IdNodoType { value = this.IdNodo.Id },
            idStrutturaRespMat = new IdStrutturaType { value = this.IdStruttura.Id },
            idVitalRecordCode = new IdVitalRecordCodeType { value = this.IdGradoVitalita },
            numero = this._numero,
            objectTypeId = enumArchiveObjectType.FascicoloRealeAnnualePropertiesType,
            oggetto = this.OggettoFascicolo,
            paroleChiave = this._paroleChiave,
            soggetto = this._soggetto,
            utenteCreazione = new CodiceFiscaleType { value = this.CodiceFiscale }
        };
        public override string Voce => this._voce;
        public override string OggettoDocumentoPrincipale => this._oggettoDocPrincipale;
        public override List<string> MittentiPG
        {
            get
            {
                List<string> retVal = new List<string>();

                if (this._mittenti?.Amministrazione?.Count > 0)
                {
                    retVal.AddRange(this._mittenti.Amministrazione.Select(x => x.AMMINISTRAZIONE));
                }

                var pg = this._mittenti?.Anagrafe?.Where(x => x.TIPOANAGRAFE == "G").Select(x => x.GetNomeCompleto());
                if (pg?.Any() == true)
                {
                    retVal.AddRange(pg);
                }
                return retVal;
            }
        }
        public override List<string> MittentiPF
        {
            get
            {
                List<string> retVal = new List<string>();
                var pf = this._mittenti?.Anagrafe?.Where(x => x.TIPOANAGRAFE == "F").Select(x => x.GetNomeCompleto());
                if (pf?.Any() == true)
                {
                    retVal.AddRange(pf);
                }
                return retVal;
            }
        }
        public override List<string> DestinatariPG
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
        public override List<string> DestinatariPF
        {
            get
            {
                List<string> retVal = new List<string>();

                var pf = this._destinatari?
                    .Anagrafe?
                    .Where(x => x.TIPOANAGRAFE == "F")
                    .Select(x => x.GetNomeCompleto());
                if (pf?.Any() == true)
                {
                    retVal.AddRange(pf);
                }

                return retVal;
            }
        }
        public override int NumeroAllegati => this._numeroAllegati;
        public override string NomeFilePrincipale => this._nomeFilePrincipale;
        public override string AnnotazioneDocPrincipale => Constants._annotazione;
        public override string UtenteEsteso
        {
            get
            {
                if (this.MittentiPF.Any())
                {
                    var pf = this._mittenti.Anagrafe?.First(x => x.TIPOANAGRAFE == "F");
                    return new StringBuilder()
                                .Append(pf?.NOMINATIVO)
                                .Append(" ")
                                .Append(pf?.NOME)
                                .Append(" ")
                                .Append(pf?.CODICEFISCALE)
                                .ToString()
                                .Trim();
                }

                var pg = this._mittenti?.Anagrafe?.Where(x => x.TIPOANAGRAFE == "G");
                if (pg?.Any() == true)
                {
                    return new StringBuilder()
                                .Append(pg.First().NOMINATIVO)
                                .Append(" ")
                                .Append(String.IsNullOrEmpty(pg.First().PARTITAIVA) ? pg.First().CODICEFISCALE : pg.First().PARTITAIVA)
                                .ToString()
                                .Trim();
                }

                return this._mittenti?.Amministrazione
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
        public override IdFolder IdFolder { get; }
        public override string IdDossier { get; set; }
        public override IEnumerable<OggettiMetadati> GetMetadati(int codiceOggetto)
        {
            return new OggettiMetadatiMgr(this.Db).GetMetadatiVerificaFirma(this.IdComune, codiceOggetto);
        }

        public FascicoloRealeAnnualeResolver(ProtocolloExt protocollo) : base(protocollo.Configurazione)
        {
            this.Initialize(protocollo, null);
        }

        public FascicoloRealeAnnualeResolver(ProtocolloExt protocollo, IDescrizioneFascicoloResolver descrizioneFascicoloResolver) : base(protocollo.Configurazione)
        {
            this.Initialize(protocollo, descrizioneFascicoloResolver);
        }

        private void Initialize(ProtocolloExt protocollo, IDescrizioneFascicoloResolver descrizioneFascicoloResolver)
        {
            this._db = protocollo.Db;
            this._idComuneAlias = protocollo.IdComuneAlias;
            this._idComune = protocollo.Idcomune;
            this._codiceComune = protocollo.CodiceComune;
            this._software = protocollo.Software;
            this._operatore = protocollo.Operatore;
            this._idTitolario = protocollo.Configurazione.IdTitolario;
            this._codiceIstanza = protocollo.CodiceIstanza.Value;
            this._codiceSerieFascicoli = protocollo.Configurazione.SerieFascicoli;
            this._anno = protocollo.DatiProtocollo.DataRegistrazione.GetValueOrDefault(DateTime.Now).Year;
            this._numero = protocollo.NumeroFascicolo;
            this._paroleChiave = String.Join(",", protocollo.InterventoIstanza.Split(Convert.ToChar("-")).Select(x => x.Trim()));
            this._soggetto = protocollo.Configurazione.ValorizzaSoggettoFascicolo ? String.Join(Constants._separatore, protocollo.Soggetti) : null;
            this._voce = protocollo.DatiProtocollo.Classifica;
            this._oggettoDocPrincipale = protocollo.DatiProtocollo.Oggetto;
            this._mittenti = protocollo.DatiProtocollo.Mittenti;
            this._destinatari = protocollo.DatiProtocollo.Destinatari;
            this._numeroAllegati = protocollo.DatiProtocollo.NumeroAllegatiPresenti;
            this._nomeFilePrincipale = protocollo.DatiProtocollo.RecuperaAllegati().FirstOrDefault()?.NOMEFILE;
            this._anniConservazioneCorrente = protocollo.Configurazione.AnniConservazioneCorrente;
            this._anniConservazioneGenerale = protocollo.Configurazione.AnniConservazioneGenerale;
            if (descrizioneFascicoloResolver != null)
            {
                this._oggettoFascicolo = descrizioneFascicoloResolver.Get();
            }
        }
    }
}
