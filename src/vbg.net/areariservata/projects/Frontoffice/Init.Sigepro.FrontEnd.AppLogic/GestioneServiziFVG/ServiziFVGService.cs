using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.GenerazionePdfModulo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;
using Init.Sigepro.FrontEnd.Infrastructure.FileEncoding;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class ServiziFVGService
    {
        private static class Constants
        {
            public const string SegnapostoNomeModello = "$nomeModello";
            public const string SegnapostoDataUltimaModifica = "$dataUltimaModifica";
        }


        public class SchedaDinamicaEndoprocedimento
        {
            public int Id { get; set; }
            public string Descrizione { get; set; }
            public bool Compilata { get; set; }
            public int Ordine { get; set; }
        }

        public class AllegatoEndoprocedimento
        {
            public int Id { get; internal set; }
            public int CodiceOggetto { get; internal set; }
        }

        public class EndoprocedimentoDaCompilare
        {
            public readonly int Id;
            public readonly string Descrizione;
            public readonly DateTime? DataUltimaModifica;
            public IEnumerable<SchedaDinamicaEndoprocedimento> ListaSchede;
            public IEnumerable<AllegatoEndoprocedimento> Allegati;

            public EndoprocedimentoDaCompilare(int id, string descrizione, DateTime? dataUltimaModifica)
            {
                this.Id = id;
                this.Descrizione = descrizione;
                this.DataUltimaModifica = dataUltimaModifica;

                this.ListaSchede = Enumerable.Empty<SchedaDinamicaEndoprocedimento>();
                this.Allegati = Enumerable.Empty<AllegatoEndoprocedimento>();
            }
            
        }



        IAliasResolver _aliasResolver;
        ITokenApplicazioneService _tokenApplicazioneService;
        IDatiDinamiciRepository _datiDinamiciRepository;
        IFVGWebServiceProxy _webServiceProxy;
        IHtmlToPdfFileConverter _fileConverter;
        //FvgDatabase _database;
        Lazy<FvgManagedDataMapper> _managedDataMapper;
        IEndoprocedimentiService _endoService;
        SostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        IOggettiService _oggettiService;


        public ServiziFVGService(IAliasResolver aliasResolver, ITokenApplicazioneService tokenApplicazioneService, IDatiDinamiciRepository datiDinamiciRepository, FVGWebServiceProxyFactory webServiceProxyFactory, IEndoprocedimentiService endoService, SostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService, IHtmlToPdfFileConverter fileConverter, IOggettiService oggettiService)
        {
            this._aliasResolver = aliasResolver;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._webServiceProxy = webServiceProxyFactory.CreateService();
            this._endoService = endoService;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._fileConverter = fileConverter;
            this._oggettiService = oggettiService;

            this._managedDataMapper = new Lazy<FvgManagedDataMapper>(() => {
                var configurationFile = "~/moduli-fvg/compilazione/managed-data-mappings.xml";
                return FvgManagedDataMapper.LoadFrom(configurationFile);
            });            
        }

        public ModelloDinamicoIstanza GetModelloDinamico(long codiceIstanza, string idModulo, int idModello, int indiceScheda)
        {
            var persistenceMedium = new FvgWebServicePersistenceMedium(codiceIstanza, idModulo, this._webServiceProxy);
            var database = new FvgDatabase(persistenceMedium);

            var modello = this._datiDinamiciRepository.GetCacheModelloDinamico(idModello);
            var dataReader = new FvgManagedDataReader(this._managedDataMapper.Value, this._webServiceProxy, modello.ListaCampiDinamici);

            dataReader.ReadAllValues(codiceIstanza)
                      .ToList()
                      .ForEach(dato => {
                          database.ImpostaValoreCampo(dato.Id, dato.NomeCampo, dato.Valore, dato.ValoreDecodificato);
                      });
            
            var dyn2DatiRepository = new FvgDyn2DatiRepository(database);

            var dap = new FvgDyn2DataAccessProvider(idModello, this._datiDinamiciRepository, dyn2DatiRepository, _tokenApplicazioneService);
            var loader = new ModelloDinamicoLoader(dap, _aliasResolver.AliasComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Frontoffice);
            var scheda = new ModelloDinamicoIstanza(loader, idModello, -1, indiceScheda, false);

            return scheda;
        }

        public EndoprocedimentoDaCompilare GetDatiModulo(long codiceIstanza, string idModulo)
        {
            var persistenceMedium = new FvgWebServicePersistenceMedium(codiceIstanza, idModulo, this._webServiceProxy);
            var database = new FvgDatabase(persistenceMedium);

            return GetDatiModulo(database, idModulo);
        }

        private EndoprocedimentoDaCompilare GetDatiModulo(FvgDatabase database, string idModulo)
        {
            var endoprocedimento = this._endoService.GetByIdEndoMappato(idModulo);

            var rVal = new EndoprocedimentoDaCompilare(endoprocedimento.Id, endoprocedimento.Descrizione, endoprocedimento.DataUltimaModifica);

            database.SincronizzaSchede(endoprocedimento.Schede);

            // Se il database non contiene valori probabilmente allora è nuovo. In questo caso cerco di recuperare
            // i possibili valori dei campi presenti in un modulo dal managegd data
            if (!database.ContieneValori)
            {
                var listaCampiDelModulo = endoprocedimento.Schede
                                                          .SelectMany(x => this._datiDinamiciRepository.GetCacheModelloDinamico(x.Id).ListaCampiDinamici)
                                                          .Select(x => x.Value.Nomecampo);

                database.InizializzaValoriDaManagedData(listaCampiDelModulo, this._webServiceProxy);
            }

            database.Salva();

            rVal.ListaSchede = endoprocedimento.Schede.Select(x => new SchedaDinamicaEndoprocedimento
            {
                Id = x.Id,
                Descrizione = x.Descrizione,
                Ordine = x.Ordine.GetValueOrDefault(9999),
                Compilata = database.IsSchedaCompilata(x.Id)
            }).OrderByDescending(x => x.Ordine);

            rVal.Allegati = endoprocedimento.Allegati == null ? 
                                Enumerable.Empty< AllegatoEndoprocedimento>() : 
                                endoprocedimento.Allegati
                                                .Select(x => new AllegatoEndoprocedimento
                                                {
                                                    Id = x.Codice,
                                                    CodiceOggetto = x.CodiceOggetto.Value
                                                });

            return rVal;
        }

        public BinaryFile GeneraPdfModulo(long codiceIstanza, string idModulo)
        {
            var persistenceMedium = new FvgWebServicePersistenceMedium(codiceIstanza, idModulo, this._webServiceProxy);
            var database = new FvgDatabase(persistenceMedium);

            return GeneraPdfModulo(database, idModulo);
        }

        private BinaryFile GeneraPdfModulo(FvgDatabase database, string idModulo)
        {
            var endoInCompilazione = this.GetDatiModulo(database, idModulo);
            var reader = new FvgDatiDinamiciRiepilogoReader(this._aliasResolver, this._datiDinamiciRepository, database, endoInCompilazione.ListaSchede);
            var template = @"<!doctype html>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html;charset=utf-8' />
	<title>Document</title>
</head>
<body>
    Nome modello: $nomeModello<br />
    Data ultima modifica: $dataUltimaModifica<br />
	<schedeDinamiche />
    <noteCompilazione />
</body>
</html>";

            if (endoInCompilazione.Allegati.Any())
            {
                var oggetto = this._oggettiService.GetById(endoInCompilazione.Allegati.First().CodiceOggetto);
                template = UnknownEncodingToString.Convert(oggetto.FileContent);
            }

            // Da rimuovere in produzione
            // template = File.ReadAllText(@"c:\temp\B1_-_Commercio_in_sede_fissa_web.html");
            template = template.Replace(Constants.SegnapostoDataUltimaModifica, endoInCompilazione.DataUltimaModifica.HasValue ? endoInCompilazione.DataUltimaModifica.Value.ToString("dd/MM/yyyy") : "");
            template = template.Replace(Constants.SegnapostoNomeModello, endoInCompilazione.Descrizione);

            var html = this._sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(reader, template);

            return this._fileConverter.Converti($"riepilogo-{idModulo}.pdf", html, new RenderingFlags
            {
                ConvertToPdfa = false
            });
        }

        public void AllegaPdfADomanda(long codiceIstanza, string idModulo)
        {
            var binaryFile = GeneraPdfModulo(codiceIstanza, idModulo);

            this._webServiceProxy.SalvaFilePdf(codiceIstanza, idModulo, binaryFile.FileContent);
        }

        public BinaryFile GeneraAnteprimaModulo(string idModulo)
        {
            return GeneraPdfModulo(new FvgDatabase(), idModulo);
        }
    }
}
