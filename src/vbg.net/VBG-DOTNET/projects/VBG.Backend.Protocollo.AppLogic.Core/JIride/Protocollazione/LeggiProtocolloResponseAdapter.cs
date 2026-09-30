using Init.SIGePro.Manager;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione
{
    public class LeggiProtocolloResponseAdapter
    {
        private ProtocolloLogs _logger;

        public LeggiProtocolloResponseAdapter(ProtocolloLogs logger)
        {
            this._logger = logger;
        }

        public DatiProtocolloLettoResponseType Adatta(DocumentoOutXml response, DataBase db, bool isCopia, List<AllegatoResponseType> allegatiEsitiPec)
        {
            try
            {
                var protoLetto = new DatiProtocolloLettoResponseType();

                if (response.IdDocumento != 0)
                {
                    this._logger.Debug($"Adattamento della risposta della lettura del protocollo con id {response.IdDocumento}");

                    protoLetto.IdProtocollo = response.IdDocumento.ToString();
                    protoLetto.AnnoProtocollo = response.AnnoProtocollo.ToString();
                    protoLetto.NumeroProtocollo = response.NumeroProtocollo.ToString();
                    protoLetto.DataProtocollo = response.DataProtocollo.HasValue ? response.DataProtocollo.Value.ToString("dd/MM/yyyy") : "";

                    if (!String.IsNullOrEmpty(response.Oggetto))
                        protoLetto.Oggetto = response.Oggetto;
                    if (!String.IsNullOrEmpty(response.Origine))
                        protoLetto.Origine = response.Origine;
                    if (!String.IsNullOrEmpty(response.Classifica))
                        protoLetto.Classifica = response.Classifica;
                    if (!String.IsNullOrEmpty(response.Classifica_Descrizione))
                        protoLetto.Classifica_Descrizione = $"{response.Classifica} {response.Classifica_Descrizione}";
                    if (!String.IsNullOrEmpty(response.TipoDocumento))
                        protoLetto.TipoDocumento = response.TipoDocumento;
                    if (!String.IsNullOrEmpty(response.TipoDocumento_Descrizione))
                        protoLetto.TipoDocumento_Descrizione = response.TipoDocumento_Descrizione;

                    if (!String.IsNullOrEmpty(response.MittenteInterno) && response.Origine != ProtocolloConstants.COD_ARRIVO)
                    {
                        this._logger.Debug($"Adattamento della risposta della lettura del protocollo con id {response.IdDocumento}: sezione mittente interno");
                        protoLetto.MittentiDestinatari = new MittDestOutType[]
                        {
                            new MittDestOutType
                            {
                                IdSoggetto = response.MittenteInterno,
                                CognomeNome = response.MittenteInterno_Descrizione
                            }
                        };
                    }

                    if (!String.IsNullOrEmpty(response.InCaricoA))
                        protoLetto.InCaricoA = response.InCaricoA;

                    if (!String.IsNullOrEmpty(response.InCaricoA_Descrizione))
                        protoLetto.InCaricoA_Descrizione = response.InCaricoA_Descrizione;

                    if (!String.IsNullOrEmpty(response.DocAllegati))
                        protoLetto.DocAllegati = response.DocAllegati;

                    if (!String.IsNullOrEmpty(response.NumeroPratica))
                        protoLetto.NumeroPratica = response.NumeroPratica;

                    if (!String.IsNullOrEmpty(response.AnnoNumeroPratica))
                        protoLetto.AnnoNumeroPratica = $"{response.AnnoNumeroPratica}/{response.Classifica}";

                    if (isCopia)
                    {
                        if (response.AltriFascicoli != null && response.AltriFascicoli.Length > 0)
                        {
                            protoLetto.NumeroPratica = response.AltriFascicoli[0].NumeroAltroFascicolo;
                            protoLetto.AnnoNumeroPratica = response.AltriFascicoli[0].AnnoNumeroAltroFascicolo;
                        }
                    }

                    protoLetto.DataInserimento = response.DataInserimento.HasValue ? response.DataInserimento.Value.ToString("dd/MM/yyyy") : "";

                    if (protoLetto.Origine == "I")
                    {
                        protoLetto.MittentiDestinatari = new MittDestOutType[]
                        {
                            new MittDestOutType
                            {
                                IdSoggetto = response.MittenteInterno,
                                CognomeNome = response.MittenteInterno_Descrizione
                            }
                        };
                    }

                    if (response.MittentiDestinatari != null)
                    {
                        this._logger.Debug($"Adattamento della risposta della lettura del protocollo con id {response.IdDocumento}: sezione mittenti/destinatari");
                        protoLetto.MittentiDestinatari = new MittDestOutType[response.MittentiDestinatari.Length];

                        int iIndex = 0;
                        foreach (MittenteDestinatarioOut pMittDestOut in response.MittentiDestinatari)
                        {
                            protoLetto.MittentiDestinatari[iIndex] = new MittDestOutType();
                            protoLetto.MittentiDestinatari[iIndex].IdSoggetto = pMittDestOut.IdSoggetto.ToString();
                            if (!String.IsNullOrEmpty(pMittDestOut.CognomeNome))
                            {
                                switch (protoLetto.Origine)
                                {
                                    case "A":
                                        protoLetto.MittentiDestinatari[iIndex].CognomeNome = pMittDestOut.CognomeNome;
                                        break;
                                    case "P":
                                        protoLetto.MittentiDestinatari[iIndex].CognomeNome = pMittDestOut.CognomeNome;
                                        break;
                                }
                            }

                            iIndex++;
                        }
                        this._logger.Debug($"Fine adattamento della risposta della lettura del protocollo con id {response.IdDocumento}: sezione mittenti/destinatari");
                    }

                    if (response.Allegati != null && response.Allegati.Length > 0)
                    {
                        this._logger.Debug($"Adattamento della risposta della lettura del protocollo con id {response.IdDocumento}: sezione allegati");
                        var allegatiDistinct = response.Allegati.GroupBy(x => x.IDBase).Select(x => x.Key);
                        var allegatiRet = allegatiDistinct.Select(x =>
                        {
                            var a = response.Allegati.Where(z => z.IDBase == x).OrderByDescending(y => y.Versione).First();
                            var nomeFile = a.NomeAllegato;
                            if (String.IsNullOrEmpty(nomeFile))
                            {
                                nomeFile = a.Commento;
                                if (!String.IsNullOrEmpty(a.TipoFile))
                                {
                                    nomeFile = String.Format("{0}.{1}", Path.GetFileNameWithoutExtension(nomeFile), a.TipoFile);
                                    if (!String.IsNullOrEmpty(a.SottoEstensione))
                                        nomeFile = String.Format("{0}.{1}.{2}", Path.GetFileNameWithoutExtension(nomeFile), a.SottoEstensione, a.TipoFile);
                                }
                            }
                            return new AllegatoResponseType
                            {
                                Commento = nomeFile,
                                IDBase = a.IDBase.ToString(),
                                Serial = nomeFile,
                                Versione = a.Versione.ToString(),
                                Image = a.Image,
                                TipoFile = String.IsNullOrEmpty(a.TipoFile) ? "" : a.TipoFile,
                                ContentType = String.IsNullOrEmpty(a.TipoFile) ? "" : new OggettiMgr(db).GetContentType(nomeFile)
                            };
                        }).ToList();

                        if (allegatiEsitiPec.Count > 0)
                        {
                            allegatiRet.AddRange(allegatiEsitiPec);
                        }
                        protoLetto.Allegati = allegatiRet.ToArray();
                        this._logger.Debug($"Fine adattamento della risposta della lettura del protocollo con id {response.IdDocumento}: sezione allegati");
                    }
                }
                else
                {
                    throw new Exception(($"ID DOCUMENTO UGUALE A 0, MESSAGGIO: {response.Messaggio}, ERRORE: {response.Errore}"));
                }
                this._logger.Debug($"Fine adattamento della risposta della lettura del protocollo con id {response.IdDocumento}");
                return protoLetto;
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DAL WEB SERVICE DOPO LA LETTURA DEL PROTOCOLLO, {ex.Message}", ex);
            }
        }
    }
}
