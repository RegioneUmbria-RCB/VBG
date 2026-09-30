using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.SharedInfo;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.AddUD.V1
{

    public class SoggettoNotifica
    {
        public string Mezzo { get; set; }
        public string Pec { get; set; }
    }


    public class AddUDServiceWrapperV1 : ProxyServiceWrapper, IServiceWrapper
    {

        private DatiProtocolloIn _protoIn;
        private static string _caratteriDaEliminare;

        private static class Constants
        {
            public const string Titolo = "PROTOCOLLAZIONE";
            public const string NameSpace = @"http://addunitadoc.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSAddUd";
            public const string RequestServiceName = "add";
            public const NewUDRegistrazioneDaDareCategoriaReg registroDefault = NewUDRegistrazioneDaDareCategoriaReg.PG;
        }

        public AddUDServiceWrapperV1(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
            _caratteriDaEliminare = this._parametri.CaratteriDaEliminare;
        }

        public ResponseInfo Protocolla(DatiProtocolloIn protoIn)
        {
            try
            {
                this._protoIn = protoIn;


                using (var ws = this.CreaWebService())
                {
                    var addUdRequest = new ServiceRequestInfo
                    {
                        RegistrazioneDaDare = this.SetNewUDRegistrazioneDaDare(),
                        OggettoUD = this._protoIn.Oggetto,
                        TipoProvenienza = this.MapProvenienza(),
                        Items = this.SetItems(),
                        AssegnazioneInterna = this.SetAssegnazioneInterna(),
                        CollocazioneClassificazioneUD = this.SetCollocazioneInterna(),
                        VersioneElettronica = this.SetPrincipale(),
                        AllegatoUD = this.SetAltriAllegati(),
                        AttributoAddUD = this._protoIn.Flusso == "P" ? this.SetAttributoAddUD() : null
                    };

                    var addUdRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("addUdRequest.xml", addUdRequest));


                    this._request.xml = addUdRequestXML;

                    this._request.hash = this.getHashSHA1();

                    if (this._protoIn.HaAllegati())
                    {
                        this._request.codiciOggetto = new List<int>();
                        this._request.codiciOggetto.AddRange(this._protoIn.CodiciOggettoAllegati());
                    }

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.ADD_UD_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, serviceResponse);

                    this.LogInfoResponseWS(responseXml);

                    var response = new ResponseInfoAdapter(serviceResponse).Adatta();

                    if (response.WsResult != "1")
                        throw new Exception(response.WsError);

                    this.LogSuccess();

                    return response;

                }
            }
            catch (Exception ex)
            {
                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }

        private AttributoAddizionaleType[] SetAttributoAddUD()
        {
            var retVal = new List<AttributoAddizionaleType>();

            var elencoSoggetti = new List<SoggettoNotifica>();

            if (this._protoIn.Destinatari.Amministrazione != null)
            {
                elencoSoggetti.AddRange(this._protoIn.Destinatari.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PEC)).Select(x => new SoggettoNotifica
                {
                    Mezzo = x.Mezzo,
                    Pec = x.PEC
                }));
            }

            if (this._protoIn.Destinatari.Anagrafe != null)
            {
                elencoSoggetti.AddRange(this._protoIn.Destinatari.Anagrafe.Where(x => !String.IsNullOrEmpty(x.PecProtocollazione)).Select(x => new SoggettoNotifica
                {
                    Mezzo = x.Mezzo,
                    Pec = x.PecProtocollazione
                }));
            }

            if (elencoSoggetti.Any(x => x.Mezzo == "PEC"))
            {
                retVal.Add(new AttributoAddizionaleType
                {
                    Nome = "INDIRIZZO_EMAIL_DEST_Ud",
                    Item = new AttributoAddizionaleTypeLista
                    {
                        Riga = elencoSoggetti
                                    .Where(x => x.Mezzo == "PEC")
                                    .Select(x =>
                                             new AttributoAddizionaleTypeListaRiga
                                             {
                                                 Colonna = new AttributoAddizionaleTypeListaRigaColonna { Nro = "1", Text = new[] { x.Pec } }
                                             })
                                    .ToArray()
                    }
                });
            }

            return retVal.ToArray();
        }

        private string MapProvenienza()
        {
            switch (this._protoIn.Flusso)
            {
                case "A": return "E";
                case "P": return "U";
                default: return this._protoIn.Flusso;
            }
        }
        private NewUDRegistrazioneDaDare[] SetNewUDRegistrazioneDaDare()
        {
            return new NewUDRegistrazioneDaDare[]
            {
                new NewUDRegistrazioneDaDare{
                    CategoriaReg = Constants.registroDefault,
                    AnnoReg = DateTime.Now.Year.ToString(),
                    SiglaReg = null
                }
            };
        }
        private object[] SetItems()
        {
            var list = new List<object>();

            switch (this._protoIn.Flusso)
            {
                case "A":
                    {
                        list.AddRange(this.GetDatiEntrata());
                        break;
                    }
                case "I":
                    {
                        list.AddRange(this.GetNewUDDatiProduzione());
                        break;
                    }
                case "P":
                    {
                        list.AddRange(this.GetNewUDDatiProduzione());
                        list.AddRange(this.GetDatiUscita());
                        break;
                    }
                default:
                    {
                        return null;
                    }
            }

            return list.ToArray();
        }
        private NewUDDatiEntrata[] GetDatiEntrata()
        {
            return new NewUDDatiEntrata[] {
                new NewUDDatiEntrata
                {
                    MittenteEsterno = this.SetMittenti(),
                    DataDocRicevutoSpecified = false,
                    DataOraArrivo = this.GetNowWithoutSeconds(),
                    DataOraArrivoSpecified = true,
                    DataRaccomandataSpecified = false,
                }
            };
        }

        private DateTime GetNowWithoutSeconds()
        {
            return new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, 0, 0, DateTimeKind.Local);
        }

        private SoggettoEsternoType[] SetMittenti()
        {
            var mittenti = new List<SoggettoEsternoType>();
            if (this._protoIn.Mittenti != null)
            {
                if (this._protoIn.Mittenti.Anagrafe != null)
                {
                    var v = this._protoIn.Mittenti.Anagrafe.ConvertAll(new Converter<ProtocolloAnagrafe, SoggettoEsternoType>(AnagrafeToSoggettoEsternoType));
                    mittenti.AddRange(v);
                }
                if (this._protoIn.Mittenti.Amministrazione != null)
                {
                    var v = this._protoIn.Mittenti.Amministrazione.ConvertAll(new Converter<ProtocolloAmministrazioni, SoggettoEsternoType>(AmministrazioneToSoggettoEsternoType));
                    mittenti.AddRange(v);
                }
            }
            return mittenti.ToArray();
        }
        private static SoggettoEsternoType AnagrafeToSoggettoEsternoType(ProtocolloAnagrafe anagrafe)
        {
            return new SoggettoEsternoType
            {
                CodiceFiscale = anagrafe.CODICEFISCALE,
                DataNascitaIstituzioneSpecified = false,
                Denominazione_Cognome = anagrafe.NOMINATIVO,
                FlagFisica = (anagrafe.TIPOANAGRAFE == "F") ? "1" : "0",
                Nome = anagrafe.NOME,
                PartitaIva = anagrafe.PARTITAIVA,
            };
        }
        private static SoggettoEsternoType AmministrazioneToSoggettoEsternoType(ProtocolloAmministrazioni amministrazione)
        {
            return new SoggettoEsternoType
            {
                DataNascitaIstituzioneSpecified = false,
                Denominazione_Cognome = amministrazione.AMMINISTRAZIONE,
                FlagFisica = "0",
                PartitaIva = amministrazione.PARTITAIVA
            };
        }
        private NewUDDatiProduzione[] GetNewUDDatiProduzione()
        {
            return new NewUDDatiProduzione[]
            {
                new NewUDDatiProduzione
                {
                    UffProduttore = new UOType[]
                    {
                        new UOType
                        {
                            LivelloUO = new LivelloGerarchiaType[] {
                                new LivelloGerarchiaType
                                {
                                    Codice = this._protoIn.Mittenti.Amministrazione[0].PROT_UO,
                                    Nro = "1"
                                }
                            }
                        }
                    }
                }
            };
        }
        private NewUDDatiUscita[] GetDatiUscita()
        {
            return new NewUDDatiUscita[] {
                new NewUDDatiUscita
                {
                    DataOraSped = this.GetNowWithoutSeconds(),
                    DataOraSpedSpecified = true,
                    DataRaccomandataSpecified = false,
                    DestinatarioEsterno = this.SetDestinatari(),
                }
            };
        }
        private DestinatarioEsternoType[] SetDestinatari()
        {
            var destinatari = new List<DestinatarioEsternoType>();
            if (this._protoIn.Destinatari != null)
            {
                if (this._protoIn.Destinatari.Anagrafe != null && this._protoIn.Destinatari.Anagrafe.Count > 0)
                {
                    var v = this._protoIn.Destinatari.Anagrafe.ConvertAll(new Converter<ProtocolloAnagrafe, DestinatarioEsternoType>(AnagrafeToDestinatarioEsternoType));
                    destinatari.AddRange(v);
                }

                if (this._protoIn.Destinatari.Amministrazione != null && this._protoIn.Destinatari.Amministrazione.Count > 0)
                {
                    var v = this._protoIn.Destinatari.Amministrazione.ConvertAll(new Converter<ProtocolloAmministrazioni, DestinatarioEsternoType>(AmministrazioneToDestinatarioEsternoType));
                    destinatari.AddRange(v);
                }
            }
            return destinatari.ToArray();
        }
        private static DestinatarioEsternoType AnagrafeToDestinatarioEsternoType(ProtocolloAnagrafe anagrafe)
        {
            return new DestinatarioEsternoType
            {
                CodiceFiscale = anagrafe.CODICEFISCALE,
                DataNascitaIstituzioneSpecified = false,
                Denominazione_Cognome = anagrafe.NOMINATIVO,
                FlagFisica = (anagrafe.TIPOANAGRAFE == "F") ? "1" : "0",
                Nome = anagrafe.NOME,
                PartitaIva = anagrafe.PARTITAIVA,
            };
        }
        private static DestinatarioEsternoType AmministrazioneToDestinatarioEsternoType(ProtocolloAmministrazioni amministrazione)
        {
            return new DestinatarioEsternoType
            {
                DataNascitaIstituzioneSpecified = false,
                Denominazione_Cognome = amministrazione.AMMINISTRAZIONE,
                FlagFisica = "0",
                PartitaIva = amministrazione.PARTITAIVA,
            };
        }
        private AssegnazioneInternaType[] SetAssegnazioneInterna()
        {
            if (this._protoIn.Flusso != "P")
            {
                if (this._protoIn.Destinatari.Amministrazione != null)
                {
                    var retval = new List<AssegnazioneInternaType>();
                    var v = this._protoIn.Destinatari.Amministrazione.ConvertAll(new Converter<ProtocolloAmministrazioni, AssegnazioneInternaType>(AmministrazioneToAssegnazioneInternaType));
                    retval.AddRange(v);
                    return retval.ToArray();
                }
            }
            return null;
        }
        private static AssegnazioneInternaType AmministrazioneToAssegnazioneInternaType(ProtocolloAmministrazioni amministrazione)
        {
            return new AssegnazioneInternaType
            {
                Item = new UOType
                {
                    DenominazioneUO = null,
                    IdUO = null,
                    LivelloUO = new LivelloGerarchiaType[] {
                        new LivelloGerarchiaType
                        {
                            Codice = amministrazione.PROT_UO,
                            Nro = "1"
                        }
                    }
                }
            };
        }
        private NewUDCollocazioneClassificazioneUD SetCollocazioneInterna()
        {
            return new NewUDCollocazioneClassificazioneUD
            {
                ClassifFascicolo = new ClassifFascicoloType[]
                {
                    new ClassifFascicoloType
                    {
                        Item = this.ClassificaToClassifUAType(  )
                    }
                }
            };
        }

        private ClassifUAType ClassificaToClassifUAType()
        {
            return new ClassifUAType
            {
                LivelloClassificazione = Utility.GetLivelloGerarchiaDaClassifica(this._protoIn.Classifica)
            };
        }

        private VersioneElettronicaType SetPrincipale()
        {
            if (this._protoIn.HaAllegati())
            {
                var retVal = this._protoIn
                                    .RecuperaAllegati()
                                    .First();

                return new VersioneElettronicaType
                {
                    NroAttachmentAssociato = "1",
                    NomeFile = new String(retVal.NOMEFILE.Where(c => !_caratteriDaEliminare.Contains(c)).ToArray()),
                    Note = retVal.Descrizione
                };
            }

            return null;
        }

        private AllegatoUDType[] SetAltriAllegati()
        {
            if (this._protoIn.HaAllegati() && this._protoIn.NumeroAllegatiPresenti > 1)
            {
                var i = 2;
                var retVal = this._protoIn
                                    .RecuperaAllegati()
                                    .Skip(1)
                                    .ToList()
                                    .ConvertAll(new Converter<ProtocolloAllegati, AllegatoUDType>(AllegatiToAllegatoUDType));

                retVal.ForEach(x =>
                {
                    x.VersioneElettronica.NroAttachmentAssociato = i.ToString();
                    i++;
                });

                return retVal.ToArray();
            }

            return null;
        }
        private static AllegatoUDType AllegatiToAllegatoUDType(ProtocolloAllegati allegato)
        {
            return new AllegatoUDType
            {
                DesAllegato = new String(allegato.Descrizione.Replace(allegato.Extension, "").Where(c => !_caratteriDaEliminare.Contains(c)).ToArray()),
                VersioneElettronica = new VersioneElettronicaType
                {
                    NomeFile = new String(allegato.NOMEFILE.Where(c => !_caratteriDaEliminare.Contains(c)).ToArray()),
                    Note = allegato.Descrizione
                },
                AttributoAddAlleg = null
            };
        }
    }
}
