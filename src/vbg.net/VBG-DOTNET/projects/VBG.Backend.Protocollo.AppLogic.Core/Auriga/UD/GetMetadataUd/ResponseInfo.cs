using System.Globalization;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.GetMetadataUd
{
    public class ResponseInfo : ProxyResponseInfo
    {
        public DatiUD DatiUD;
        public bool IsFascicolato
        {
            get
            {
                return !String.IsNullOrEmpty(this.NumeroFascicolo);
            }
        }
        public string AnnoFascicolo
        {
            get
            {
                return GetAnnoNumeroPratica(this.DatiUD.CollocazioneClassificazioneUD);
            }
        }
        public string NumeroFascicolo
        {
            get
            {
                return GetNumeroPratica(this.DatiUD.CollocazioneClassificazioneUD);
            }
        }
        public string Classifica
        {
            get
            {
                return GetClassifica(this.DatiUD.CollocazioneClassificazioneUD);
            }
        }
        public string OggettoFasc
        {
            get
            {
                return GetOggettoFasc(this.DatiUD.CollocazioneClassificazioneUD);
            }
        }


        public DatiProtocolloLettoResponseType ToDatiProtocolloLetto()
        {
            if (!String.IsNullOrEmpty(this.WsError))
            {
                return new DatiProtocolloLettoResponseType
                {
                    Errore = new ErroreProtocolloType
                    {
                        Descrizione = this.WsError
                    }
                };
            }

            if (this.DatiUD == null || this.DatiUD.RegistrazioneData == null)
            {
                throw new InvalidOperationException("Non è possibile richiamare il metodo ToDatiProtocolloLetto senza aver prima valorizzato DatiUD");
            }

            #region Elimina l'ora dal campo this.DatiUD.DataOraCreazione
            DateTime data;

            // Specifica il formato esatto atteso
            var formato = "dd/MM/yyyy HH:mm:ss";

            // Prova a convertire la stringa in DateTime
            var successo = DateTime.TryParseExact(
                this.DatiUD.DataOraCreazione,
                formato,
                CultureInfo.InvariantCulture,
                DateTimeStyles.None,
                out data
            );

            if (successo)
            {
                this.DatiUD.DataOraCreazione = data.ToString("dd/MM/yyyy");
            }
            else
            {
                Console.WriteLine("Formato della data non valido. Verrà lasciata la stringa originale");
            }
            #endregion

            return new DatiProtocolloLettoResponseType
            {
                IdProtocollo = this.DatiUD.IdUD,
                AnnoProtocollo = this.DatiUD.RegistrazioneData[0].AnnoReg,
                DataProtocollo = this.DatiUD.DataOraCreazione,
                NumeroProtocollo = this.DatiUD.RegistrazioneData[0].NumReg,
                Oggetto = this.DatiUD.OggettoUD.Value,
                AnnoNumeroPratica = this.AnnoFascicolo,
                NumeroPratica = this.NumeroFascicolo,
                Classifica_Descrizione = this.Classifica,
                Origine = TipoProvenienzaToOrigine(this.DatiUD.TipoProvenienza),
                MittentiDestinatari = this.SetMittDestOut(),
                InCaricoA = this.SetInCaricoA(), // id 
                InCaricoA_Descrizione = this.SetInCaricoADescrizione(), // descrizione
                Allegati = SetAllegati(this.DatiUD)
            };
        }

        private MittDestOutType[] SetMittDestOut()
        {
            switch (this.DatiUD.TipoProvenienza)
            {
                case DatiUDTipoProvenienza.E:
                    {
                        return this.DatiUD.DatiEntrata.MittenteEsterno.ToList().ConvertAll(new Converter<SoggettoEsternoType, MittDestOutType>(SoggettoEsternoToMittDestOut)).ToArray();
                    }
                case DatiUDTipoProvenienza.I:
                    {
                        return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, MittDestOutType>(AssegnazioneInternaToMittDestOut)).ToArray();
                    }
                case DatiUDTipoProvenienza.U:
                    {
                        return this.DatiUD.DatiUscita.DestinatarioEsterno.ToList().ConvertAll(new Converter<DestinatarioEsternoType, MittDestOutType>(DestinatarioEsternoToMittDestOut)).ToArray();
                    }
            }

            return null;
        }
        private static MittDestOutType DestinatarioEsternoToMittDestOut(DestinatarioEsternoType soggetto)
        {
            if (soggetto != null)
            {
                return new MittDestOutType
                {
                    CognomeNome = $"{soggetto.Denominazione_Cognome} {soggetto.Nome}",
                    IdSoggetto = null,
                };
            }

            return null;
        }
        private static MittDestOutType UOTypeToMittDestOut(UOType soggetto)
        {
            if (soggetto != null)
            {
                return new MittDestOutType
                {
                    CognomeNome = $"{soggetto.DenominazioneUO}",
                    IdSoggetto = soggetto.IdUO
                };
            }

            return null;
        }
        private static MittDestOutType SoggettoEsternoToMittDestOut(SoggettoEsternoType soggetto)
        {
            if (soggetto != null)
            {
                return new MittDestOutType
                {
                    CognomeNome = $"{soggetto.Denominazione_Cognome} {soggetto.Nome}",
                    IdSoggetto = null,
                };
            }

            return null;
        }
        private static MittDestOutType AssegnazioneInternaToMittDestOut(AssegnazioneInternaType soggetto)
        {
            if (soggetto != null && soggetto.Item != null)
            {
                switch (soggetto.Item.GetType().Name)
                {
                    case "OggDiTabDiSistemaType":   //Gruppo
                        {
                            return new MittDestOutType
                            {
                                CognomeNome = ((OggDiTabDiSistemaType)soggetto.Item).Decodifica_Nome,
                                IdSoggetto = ((OggDiTabDiSistemaType)soggetto.Item).CodId
                            };
                        }
                    case "RuoloAmmContestualizzatoType":    // RuoloAmmContestualizzato
                        {
                            return new MittDestOutType
                            {
                                CognomeNome = ((RuoloAmmContestualizzatoType)soggetto.Item).RuoloAmm.Decodifica_Nome,
                                IdSoggetto = ((RuoloAmmContestualizzatoType)soggetto.Item).RuoloAmm.CodId
                            };
                        }
                    case "ScrivaniaVirtualeType":   //ScrivaniaVirtuale
                        {
                            return new MittDestOutType
                            {
                                CognomeNome = ((ScrivaniaVirtualeType)soggetto.Item).DesScrivaniaVirt,
                                IdSoggetto = null
                            };
                        }
                    case "UOType":   //UO
                        {
                            return new MittDestOutType
                            {
                                CognomeNome = ((UOType)soggetto.Item).DenominazioneUO,
                                IdSoggetto = ((UOType)soggetto.Item).IdUO
                            };
                        }
                    case "UserType":   //Utente
                        {
                            return new MittDestOutType
                            {
                                CognomeNome = ((UserType)soggetto.Item).Decodifica_CognomeNome,
                                IdSoggetto = ((UserType)soggetto.Item).IdInterno
                            };
                        }
                }
            }
            return null;
        }

        private string SetInCaricoA()
        {
            return this.DatiUD.AssegnazioneInterna?
                .Select(AssegnazioneInternaToInCaricoA)
                .DefaultIfEmpty(string.Empty)
                .Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        }

        // lo switch sembrava inutile...

        //private string SetInCaricoA()
        //{
        //    switch (this.DatiUD.TipoProvenienza)
        //    {
        //        case DatiUDTipoProvenienza.E:
        //            {
        //                return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, string>(AssegnazioneInternaToInCaricoA)).Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        //            }
        //        case DatiUDTipoProvenienza.I:
        //            {
        //                return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, string>(AssegnazioneInternaToInCaricoA)).Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        //            }
        //        case DatiUDTipoProvenienza.U:
        //            {
        //                return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, string>(AssegnazioneInternaToInCaricoA)).Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        //            }
        //    }

        //    return null;
        //}
        private static string AssegnazioneInternaToInCaricoA(AssegnazioneInternaType soggetto)
        {
            if (soggetto != null && soggetto.Item != null)
            {
                switch (soggetto.Item.GetType().Name)
                {
                    case "OggDiTabDiSistemaType":   //Gruppo
                        {
                            return ((OggDiTabDiSistemaType)soggetto.Item).CodId;
                        }
                    case "RuoloAmmContestualizzatoType":    // RuoloAmmContestualizzato
                        {
                            return ((RuoloAmmContestualizzatoType)soggetto.Item).RuoloAmm.CodId;
                        }
                    case "ScrivaniaVirtualeType":   //ScrivaniaVirtuale
                        {
                            return "";
                        }
                    case "UOType":   //UO
                        {
                            return ((UOType)soggetto.Item).IdUO;
                        }
                    case "UserType":   //Utente
                        {
                            return ((UserType)soggetto.Item).IdInterno;
                        }
                    case "String":   //stringa
                        {
                            var s = (string)soggetto.Item;

                            if (string.IsNullOrEmpty(s))
                                return string.Empty;

                            if (s.Contains('-'))
                            {
                                var splitted = s.Split('-');

                                return splitted[0].Trim();
                            }

                            return s;
                        }
                }
            }
            return string.Empty;
        }

        private string SetInCaricoADescrizione()
        {
            return this.DatiUD.AssegnazioneInterna?
                .Select(AssegnazioneInternaToInCaricoADescrizione)
                .DefaultIfEmpty(string.Empty)
                .Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        }


        //private string SetInCaricoADescrizione()
        //{
        //    switch (this.DatiUD.TipoProvenienza)
        //    {
        //        case DatiUDTipoProvenienza.E:
        //            {
        //                return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, string>(AssegnazioneInternaToInCaricoADescrizione)).Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        //            }
        //        case DatiUDTipoProvenienza.I:
        //            {
        //                return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, string>(AssegnazioneInternaToInCaricoADescrizione)).Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        //            }
        //        case DatiUDTipoProvenienza.U:
        //            {
        //                return this.DatiUD.AssegnazioneInterna.ToList().ConvertAll(new Converter<AssegnazioneInternaType, string>(AssegnazioneInternaToInCaricoADescrizione)).Aggregate((partialRetVal, assint) => $"{partialRetVal} {assint}");
        //            }
        //    }

        //    return null;
        //}
        private static string AssegnazioneInternaToInCaricoADescrizione(AssegnazioneInternaType soggetto)
        {
            if (soggetto != null && soggetto.Item != null)
            {
                switch (soggetto.Item.GetType().Name)
                {
                    case "OggDiTabDiSistemaType":   //Gruppo
                        {
                            return ((OggDiTabDiSistemaType)soggetto.Item).Decodifica_Nome;
                        }
                    case "RuoloAmmContestualizzatoType":    // RuoloAmmContestualizzato
                        {
                            return ((RuoloAmmContestualizzatoType)soggetto.Item).RuoloAmm.Decodifica_Nome;
                        }
                    case "ScrivaniaVirtualeType":   //ScrivaniaVirtuale
                        {
                            return ((ScrivaniaVirtualeType)soggetto.Item).DesScrivaniaVirt;
                        }
                    case "UOType":   //UO
                        {
                            return ((UOType)soggetto.Item).DenominazioneUO;
                        }
                    case "UserType":   //Utente
                        {
                            return ((UserType)soggetto.Item).Decodifica_CognomeNome;
                        }
                    case "String":   //stringa
                        {
                            var s = (string)soggetto.Item;

                            if (string.IsNullOrEmpty(s))
                                return string.Empty;

                            if (s.Contains('-'))
                            {
                                var splitted = s.Split('-');

                                return splitted[1].Trim();
                            }

                            return s;
                        }
                }
            }
            return null;
        }

        private static string TipoProvenienzaToOrigine(DatiUDTipoProvenienza provenienza)
        {
            switch (provenienza)
            {
                case DatiUDTipoProvenienza.E:
                    {
                        return ProtocolloConstants.COD_ARRIVO;
                    }
                case DatiUDTipoProvenienza.U:
                    {
                        return ProtocolloConstants.COD_PARTENZA;
                    }
                case DatiUDTipoProvenienza.I:
                    {
                        return ProtocolloConstants.COD_INTERNO;
                    }
            }
            return null;
        }

        private static string GetClassifica(DatiUDCollocazioneClassificazioneUD classifica)
        {
            string retVal = null;

            if (classifica != null && classifica.ClassifFascicolo != null)
            {
                classifica.ClassifFascicolo.ToList().ForEach((x) =>
                {
                    if (x.Item.GetType().Name == "ClassifUAType")
                    {
                        ((ClassifUAType)x.Item).LivelloClassificazione.ToList().ForEach((a) =>
                        {
                            retVal += a.Codice + ".";
                        });
                    }
                });
            }

            if (!String.IsNullOrEmpty(retVal))
            {
                retVal = retVal.Substring(0, retVal.Length - 1);
            }
            return retVal;
        }

        private static string GetAnnoNumeroPratica(DatiUDCollocazioneClassificazioneUD classifica)
        {
            if (classifica != null)
            {
                if (classifica.ClassifFascicolo != null)
                {
                    var cf = classifica.ClassifFascicolo.Where(x => x.Item.GetType().Name == "ClassifUAType").FirstOrDefault();
                    if (cf != null)
                    {
                        return ((ClassifUAType)cf.Item).AnnoAperturaUA;
                    }
                }
            }

            return null;
        }
        private static string GetNumeroPratica(DatiUDCollocazioneClassificazioneUD classifica)
        {
            if (classifica != null)
            {
                if (classifica.ClassifFascicolo != null)
                {
                    var cf = classifica.ClassifFascicolo.Where(x => x.Item.GetType().Name == "ClassifUAType").FirstOrDefault();
                    if (cf != null)
                    {
                        return ((ClassifUAType)cf.Item).NroProgrUA;
                    }
                }
            }

            return null;
        }
        private static string GetOggettoFasc(DatiUDCollocazioneClassificazioneUD classifica)
        {
            if (classifica != null)
            {
                if (classifica.ClassifFascicolo != null)
                {
                    return classifica.ClassifFascicolo[0].OggettoFasc;
                }
            }

            return null;
        }

        private static AllegatoResponseType[] SetAllegati(DatiUD dati)
        {

            if (String.IsNullOrEmpty(dati.IdDocPrimario) && dati.AllegatoUD == null)
            {
                return null;
            }

            var allegati = dati.AllegatoUD;
            var elenco = new List<AllegatoResponseType>();

            //allegato primario
            if (!String.IsNullOrEmpty(dati.IdDocPrimario))
            {
                elenco.Add(new AllegatoResponseType
                {
                    IDBase = "",
                    Serial = dati.VersioneElettronica.NomeFile,
                    TipoFile = null,
                    Commento = dati.VersioneElettronica.NomeFile,
                    ContentType = ""
                });
            }

            //allegati secondari
            if (allegati != null)
            {
                for (var i = 0; i < allegati.Length; i++)
                {
                    elenco.Add(new AllegatoResponseType
                    {
                        IDBase = (i + 1).ToString(),
                        Serial = allegati[i].VersioneElettronica.NomeFile,
                        TipoFile = (allegati[i].TipoDocAllegato != null) ? allegati[i].TipoDocAllegato.Decodifica_Nome : null,
                        Commento = allegati[i].DesAllegato,
                        ContentType = ""
                    });
                }

            }
            return (elenco.Count > 0) ? elenco.ToArray() : null;
        }

    }
}
