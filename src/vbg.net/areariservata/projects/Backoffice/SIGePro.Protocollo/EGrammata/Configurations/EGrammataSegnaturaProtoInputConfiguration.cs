using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.ProtoInput;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Manager;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.EGrammata.Configurations
{
    public class EGrammataSegnaturaProtoInputConfiguration
    {

        public static class Constants
        {
            public const string CATEGORIA_REGISTRO = "R";
            public const string FLAG_ANNO_PRECEDENTE = "R";

            public const string ENTRATA = "E";
            public const string USCITA = "U";
            public const string INTERNO = "I";
        }
        
        private IDatiProtocollo _protoIn;
        private List<ProtocolloAllegati> _listAllegati;
        private string _classifica;
        DataBase _db;
        string _idComune;

        public readonly AllegatoUDType[] AllegatiInput;
        public readonly NuovaUDAltraRegistrazioneDaDare AltraRegistrazione;
        public readonly NuovaUDCopiaConoscenza[] CopiaConoscenza;
        public readonly NuovaUDDatiUscita DatiUscita;
        public readonly string MezzoTrasmissione; //Viene indicato il tipo documento in quanto il mezzo, in vbg, è legato ad ogni anagrafica in arrivo / partenza, 
                                                  //al contrario il mezzo, in questo protocollo, è associato direttamente al protocollo così come il tipo documento.
        public readonly string Oggetto;
        public readonly NuovaUDRegistrazionePrimariaDaDare RegistrazionePrimaria;
        public readonly string TipoDocumento;
        public readonly string Flusso;
        public readonly object DatiProtocollo;
        public readonly OriginaleType Originale;

        

        public EGrammataSegnaturaProtoInputConfiguration(IDatiProtocollo protoIn, List<ProtocolloAllegati> listAllegati, string oggetto, 
            string classifica, string registro, string tipoDocumento, DataBase db, string idComune)
        {
            try
            {
                _db = db;
                _idComune = idComune;
                _protoIn = protoIn;
                _listAllegati = listAllegati;
                _classifica = classifica;
                AllegatiInput = GetAllegati();
                AltraRegistrazione = GetAltraRegistrazione();
                CopiaConoscenza = GetCopiaConoscenza();
                DatiUscita = GetDatiUscita();

                TipoDocumento = tipoDocumento;

                if (oggetto.Length > 500)
                    throw new Exception("OGGETTO DEL PROTOCOLLO TROPPO LUNGO, SUPERATA LA LUNGHEZZA MASSIMA DI 500 CARATTERI");

                Oggetto = oggetto;

                RegistrazionePrimaria = GetNuovaRegistrazionePrimaria(registro);


                Flusso = GetFlusso();
                DatiProtocollo = GetDatiProtocollo();
                Originale = GetOriginale();
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE (MAPPATURA) DEI DATI REQUEST DA INVIARE AL PROTOCOLLO", ex);
            }
        }

        private NuovaUDRegistrazionePrimariaDaDare GetNuovaRegistrazionePrimaria(string registro)
        {
            NuovaUDRegistrazionePrimariaDaDareCategoriaRegistro enumRegistro;
            bool parseRegistro = Enum.TryParse(registro, out enumRegistro);
            if (!parseRegistro)
                throw new Exception(String.Format("IL REGISTRO HA UN VALORE NON VALIDO {0}", registro));

            var registrazionePrimaria = new NuovaUDRegistrazionePrimariaDaDare
            {
                CategoriaRegistro = enumRegistro,
            };

            return registrazionePrimaria;
        }

        private OriginaleType GetOriginale()
        {
            var originale = new OriginaleType();

            var LivelliUo = new List<LivelloSOType>();

            var livelli = _protoIn.Amministrazione.PROT_UO.Split('.');

            if (livelli.Length != 5)
                throw new Exception("LIVELLI UO NON IMPOSTATI CORRETTAMENTE");

            var livelliSoTypeList = new List<LivelloSOType>();

            for (int i = 0; i < livelli.Length; i++)
                livelliSoTypeList.Add(new LivelloSOType { Nro = (i+1).ToString(), Codice = livelli[i] });

            originale.Item = new UOType { LivelloUO = livelliSoTypeList.ToArray() };

            var arrClassifica = _classifica.Split('.');

            if (arrClassifica.Length == 3)
            {
                string titolo = arrClassifica[0];
                string classe = arrClassifica.Length > 0 ? arrClassifica[1] : "0";
                string sottoClasse = arrClassifica.Length > 1 ? arrClassifica[2] : "0";

                string[] clasArr = new string[] { titolo, classe, sottoClasse };
                var choiceType = new ItemsChoiceType[] { ItemsChoiceType.Titolo, ItemsChoiceType.Classe, ItemsChoiceType.Sottoclasse };

                originale.ClassifFascicolo = new ClassifFascicoloType { Classificazione = new ClassifFascicoloTypeClassificazione { Items = clasArr, ItemsElementName = choiceType } };
            }
            else
            {/*
                var choiceType = new ItemsChoiceType[] { ItemsChoiceType.VoceIndice };
                originale.ClassifFascicolo = originale.ClassifFascicolo = new ClassifFascicoloType { Classificazione = new ClassifFascicoloTypeClassificazione { Items = new string[] { _classifica }, ItemsElementName = choiceType } };*/
            }

            return originale;
        }

        private object GetDatiProtocollo()
        {
            object o = null;

            if (_protoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                var datiEntrata = new NuovaUDDatiEntrata();
                var listSoggettiEsterni = new List<SoggettoEstEstesoType>();

                foreach (var a in _protoIn.AnagraficheProtocollo)
                { 
                    var soggetto = new SoggettoEstEstesoType
                    {
                        Nome = a.NOME,
                        Denominazione_Cognome = a.NOMINATIVO,
                        CodiceFiscale = a.CODICEFISCALE,
                        PartitaIva = a.PARTITAIVA
                    };

                    /*if (a.DATANASCITA.HasValue)
                        soggetto.DataNascita = a.DATANASCITA.Value;

                    if (!String.IsNullOrEmpty(a.SESSO))
                        soggetto.Sesso = a.SESSO;*/
                    if (a.DATANASCITA.HasValue)
                    {
                        var formatDataNascita = a.DATANASCITA.Value.ToString("yyyy-MM-dd");
                        soggetto.DataNascita = DateTime.Parse(formatDataNascita);
                    }

                    if (!String.IsNullOrEmpty(a.CODCOMNASCITA))
                    {
                        var comune = new ComuniMgr(_db).GetById(a.CODCOMNASCITA);
                        if(comune != null)
                            soggetto.ComuneNascita = new ComuneItalianoType { Item = comune.COMUNE, ItemElementName = ItemChoiceType.Nome };
                    }


                    listSoggettiEsterni.Add(soggetto);
                }

                if (_protoIn.AmministrazioniProtocollo.Where(x => (!String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_RUOLO))).ToList().Count > 0)
                    throw new Exception("NON E' POSSIBILE USARE UN MITTENTE INTERNO NEL PROTOCOLLO IN ARRIVO");
                
                _protoIn.AmministrazioniProtocollo.ForEach(x => listSoggettiEsterni.Add(new SoggettoEstEstesoType
                {
                    Denominazione_Cognome = x.AMMINISTRAZIONE,
                    PartitaIva = x.PARTITAIVA
                }));

                if (listSoggettiEsterni.Count == 0)
                    throw new Exception("NON SONO PRESENTI MITTENTI NEL PROTOCOLLO IN ARRIVO");

                var formatData = DateTime.Now.ToString("yyyy-MM-dd");

                datiEntrata.DataOraArrivo = DateTime.Parse(formatData);
                datiEntrata.MittenteEsterno = listSoggettiEsterni.ToArray();

                o = datiEntrata;
            }
            else
            {
                var datiProduzione = new NuovaUDDatiProduzione();
                datiProduzione.Provenienza = new UOType { DenominazioneUO = _protoIn.Amministrazione.PROT_UO };
                o = datiProduzione;
            }
            
            return o;
        }

        private string GetFlusso()
        { 
            string flusso = String.Empty;

            if (_protoIn.Flusso == ProtocolloConstants.COD_PARTENZA)
                flusso = Constants.USCITA;
            else if (_protoIn.Flusso == ProtocolloConstants.COD_ARRIVO)
                flusso = Constants.ENTRATA;
            else
                flusso = Constants.INTERNO;

            return flusso;
        }

        private NuovaUDDatiUscita GetDatiUscita()
        {
            try
            {
                if (_protoIn.Flusso != ProtocolloConstants.COD_PARTENZA)
                    return null;

                var datiUscita = new NuovaUDDatiUscita { DataSped = DateTime.Now };
                var destinatarioEsternoList = new List<DestinatarioEsternoType>();

                _protoIn.AnagraficheProtocollo.ForEach(x => destinatarioEsternoList.Add(new DestinatarioEsternoType
                {
                    Denominazione_Cognome = x.NOMINATIVO,
                    CodiceFiscale = x.CODICEFISCALE,
                    Nome = x.NOME,
                    PartitaIva = x.PARTITAIVA
                }));

                if (_protoIn.AmministrazioniProtocollo.Where(x => !String.IsNullOrEmpty(x.PROT_UO) || !String.IsNullOrEmpty(x.PROT_RUOLO)).ToList().Count > 0)
                    throw new Exception("NON E' POSSIBILE USARE UN DESTINATARIO INTERNO NEL PROTOCOLLO IN ARRIVO");

                _protoIn.AmministrazioniProtocollo.ForEach(x => destinatarioEsternoList.Add(new DestinatarioEsternoType
                {
                    Denominazione_Cognome = x.AMMINISTRAZIONE,
                    PartitaIva = x.PARTITAIVA
                }));

                if (destinatarioEsternoList.Count == 0)
                    throw new Exception("NON SONO PRESENTI DESTINATARI");

                datiUscita.DestinatarioEsterno = destinatarioEsternoList.ToArray();

                return datiUscita;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CONFIGURAZIONE DEI DESTINATARI", ex);
            }
        }

        private NuovaUDCopiaConoscenza[] GetCopiaConoscenza()
        {
            var copiaConoscenza = new List<NuovaUDCopiaConoscenza>(); 
            copiaConoscenza.Add(new NuovaUDCopiaConoscenza { Note = String.Empty });
            
            return copiaConoscenza.ToArray();
        }

        private NuovaUDAltraRegistrazioneDaDare GetAltraRegistrazione()
        {
            var altraRegistrazione = new NuovaUDAltraRegistrazioneDaDare
            {
                CategoriaRegistro = Constants.CATEGORIA_REGISTRO,
                FlgAnnoPrecedente = Constants.FLAG_ANNO_PRECEDENTE
            };

            return altraRegistrazione;
        }

        private AllegatoUDType[] GetAllegati()
        {
            if (_listAllegati == null || _listAllegati.Count == 0)
                return null;

            var listAllegatiInput = new List<AllegatoUDType>();

            int i = 1;
            foreach (ProtocolloAllegati protoAll in _listAllegati)
            {
                listAllegatiInput.Add(new AllegatoUDType
                {
                    DesAllegato = protoAll.Descrizione,
                    VersioneElettronica = new VersioneElettronicaType
                    {
                        NomeFile = protoAll.NOMEFILE,
                        NroAttachmentAssociato = i.ToString()
                    },
                    TipoDocAllegato = new OggDiTabDiSistemaType {  ItemElementName = ItemChoiceType2.CodId, Item = "0" }
                });
                i++;
            }

            /*_listAllegati.ForEach(x => listAllegatiInput.Add(new AllegatoUDType
            {
                DesAllegato = x.Descrizione,
                VersioneElettronica = new VersioneElettronicaType
                {
                    NomeFile = x.NOMEFILE,
                    AttivaVerificaFirma = 1
                }
            }));*/

            return listAllegatiInput.ToArray();

        }
    }
}
