//using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
//using PersonalLib2.Data;
//using VBG.Backend.Protocollo.AppLogic.Shared.Data;
//using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
//using VBG.Backend.Protocollo.AppLogic.Shared;
//using System;
//using SIGePro.Manager.VerticalizzazioniBase;
//using VBG.Backend.Protocollo.Verticalizazioni.Legacy;

//namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder
//{
//    public class FascicoloParams
//    {
//        private static class Constants
//        {
//            public const string _backOfficePortUrl = "backofficeWS";
//            public const string _repositoryPortUrl = "repositoryWS";
//            public const int _conservazioneIllimitata = 99;
//        }

//        public DataBase Db { get; internal set; }
//        public string Token { get; internal set; }
//        public string IdComuneAlias { get; internal set; }
//        public string Idcomune { get; internal set; }
//        public int? CodiceIstanza { get; internal set; }
//        public int? CodiceMovimento { get; internal set; }
//        public string CodiceSerieFascicoli { get; internal set; }
//        public string IdProtocollo { get; internal set; }
//        public int? Anno { get; internal set; }
//        public string Descrizione { get; internal set; }
//        public string Numero { get; internal set; }
//        public string ParoleChiave { get; internal set; }
//        public string Soggetto { get; internal set; }
//        public string Voce { get; internal set; }
//        public string OggettoDocPrincipale { get; internal set; }
//        public ListaMittDest Mittenti { get; internal set; }
//        public ListaMittDest Destinatari { get; internal set; }
//        public int? NumeroAllegatiPresenti { get; internal set; }
//        public string NomeFilePrincipale { get; internal set; }
//        public string Software { get; internal set; }
//        public string CodiceComune { get; internal set; }
//        public string Operatore { get; internal set; }
//        public int IdTitolario { get; internal set; }
//        public int AnniConservazioneCorrente { get; internal set; }
//        public int AnniConservazioneGenerale { get; internal set; }
//        public IdAoo IdAoo { get; internal set; }

//        internal static FascicoloParams FromProtocolloBase(IProtocolloSerializer serializer, ProtocolloBase protocollo, ParametriRegoleInfo config, string idProtocollo, string annoProtocollo, string numeroProtocollo, IVerticalizzazioniFactory verticalizzazioniFactory)
//        {
//            var vert = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAcaris>(protocollo.DatiProtocollo.IdComuneAlias, protocollo.DatiProtocollo.Software, protocollo.DatiProtocollo.CodiceComune);

//            return new FascicoloParams
//            {
//                AnniConservazioneCorrente = vert.AnniConservazioneCorrente ?? Constants._conservazioneIllimitata,
//                AnniConservazioneGenerale = vert.AnniConservazioneGenerale ?? Constants._conservazioneIllimitata,
//                IdProtocollo = idProtocollo,
//                IdTitolario = vert.IdTitolario.Value,
//                Anno = String.IsNullOrEmpty(idProtocollo) ? Convert.ToInt32(annoProtocollo) : (int?)null,
//                CodiceComune = protocollo.DatiProtocollo.CodiceComune,
//                CodiceIstanza = String.IsNullOrEmpty(protocollo.DatiProtocollo.CodiceIstanza) ? (int?)null : Convert.ToInt32(protocollo.DatiProtocollo.CodiceIstanza),
//                CodiceMovimento = String.IsNullOrEmpty(protocollo.DatiProtocollo.CodiceMovimento) ? (int?)null : Convert.ToInt32(protocollo.DatiProtocollo.CodiceMovimento),
//                CodiceSerieFascicoli = "",
//                Db = protocollo.DatiProtocollo.Db,
//                Descrizione = "",
//                Destinatari = new ListaMittDest(),
//                IdComuneAlias = protocollo.DatiProtocollo.IdComuneAlias,
//                Idcomune = protocollo.DatiProtocollo.IdComune,
//                Mittenti = new ListaMittDest(),
//                NomeFilePrincipale = "",
//                Numero = protocollo.DatiProtocollo.NumeroIstanza,
//                NumeroAllegatiPresenti = (int?)null,
//                OggettoDocPrincipale = "",
//                Operatore = protocollo.Operatore,
//                ParoleChiave = "",
//                Software = protocollo.DatiProtocollo.Software,
//                Soggetto = "",
//                Token = protocollo.DatiProtocollo.Token,
//                Voce = "",
//                IdAoo = config.IdAoo
//            };
//        }
//        internal ParametriRegoleInfo GetConfigurazione()
//        {
//            throw new NotImplementedException();
//        }
//    }
//}
