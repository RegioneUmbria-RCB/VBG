using System;
using System.Collections.Generic;
using System.IO;
using System.IO.Compression;
using System.Linq;
using System.Text;
using ICSharpCode.SharpZipLib.Zip;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public class AllegatiAdapter
    {
        private static class Constants
        {
            public const string NomeDocumento = "DOCNAME";
            public const string DescrizioneDocumento = "ABSTRACT";
        }

        Dictionary<string, string> _dic;
        LeggiDocumentoInfo _info;

        //string _unitaDocumentale;
        //GestioneDocumentaleService _wrapper;
        //bool _estraiEml;
        //string[] _escludiFilesDaEml;

        public AllegatiAdapter(LeggiDocumentoInfo info, Dictionary<string, string> dic)
        {
            this._info = info;
            this._dic = dic;

            //this._unitaDocumentale = unitaDocumentale;
            //this._wrapper = wrapper;
            //this._estraiEml = estraiEml;
            //this._escludiFilesDaEml = escludiFilesDaEml;
        }

        public AllOut[] Adatta()
        {
            var listaAllegati = new List<AllOut>();

            //Gestione Eml

            if (this._info.EstraiEml && Path.GetExtension(_dic[Constants.NomeDocumento]).ToLower() == ".eml")
            {
                var emlUtils = new EmlUtils();

                var download = this._info.Wrapper.DownloadDocument(this._info.UnitaDocumentale);
                var stream = new MemoryStream(download.handler);

                var attachmentInfo = emlUtils.GetAttachmentsInfo(stream, _dic[Constants.NomeDocumento], this._info.EscludiFilesDaEml, 0, this._info.ZipExtensions, false);

                if (attachmentInfo != null)
                {
                    listaAllegati = attachmentInfo.Select((x, i) => new AllOut
                    {
                        IDBase = $"{this._info.UnitaDocumentale}.{i}",
                        Serial = x.FileName,
                        Commento = x.FileName
                    }).ToList();
                }
            }
            else
            {
                if (this._info.EstraiZip && this._info.ZipExtensions.Contains(Path.GetExtension(_dic[Constants.NomeDocumento]).ToLower()))
                {
                    var zipUtils = new ZipUtils();

                    var download = this._info.Wrapper.DownloadDocument(this._info.UnitaDocumentale);
                    var stream = new MemoryStream(download.handler);

                    var attachmentInfo = zipUtils.GetAttachmentsInfo(stream, _dic[Constants.NomeDocumento], 0, false);
                    if (attachmentInfo != null)
                    {
                        listaAllegati = attachmentInfo.Select((x, i) => new AllOut
                        {
                            IDBase = $"{this._info.UnitaDocumentale}.{i}",
                            Serial = x.FileName,
                            Commento = x.FileName
                        }).ToList();
                    }
                }
                else
                {
                    listaAllegati.Add(new AllOut
                    {
                        IDBase = this._info.UnitaDocumentale,
                        Serial = _dic[Constants.NomeDocumento],
                        Commento = _dic[Constants.NomeDocumento]
                    });
                }
            }

            var allegatiRelatedAdapter = new AllegatiRelatedAdapter(this._info.Wrapper, this._info.UnitaDocumentale);

            var allegati = allegatiRelatedAdapter.Adatta();

            if (allegati != null)
            {
                foreach (var allegato in allegati)
                {
                    if (this._info.EstraiEml && Path.GetExtension(allegato.Serial).ToLower() == ".eml")
                    {
                        var emlUtils = new EmlUtils();

                        var download = this._info.Wrapper.DownloadDocument(allegato.IDBase);
                        var stream = new MemoryStream(download.handler);

                        var attachmentInfo = emlUtils.GetAttachmentsInfo(stream, allegato.Serial, this._info.EscludiFilesDaEml, 0, this._info.ZipExtensions, false);

                        if (attachmentInfo != null)
                        {
                            var allegatiEml = attachmentInfo.Select((x, i) => new AllOut
                            {
                                IDBase = $"{allegato.IDBase}.{i}",
                                Serial = x.FileName,
                                Commento = x.FileName
                            });

                            listaAllegati.AddRange(allegatiEml.ToList());
                        }
                    }
                    else
                    {
                        if (this._info.EstraiZip && this._info.ZipExtensions.Contains(Path.GetExtension(_dic[Constants.NomeDocumento]).ToLower()))
                        {
                            var zipUtils = new ZipUtils();

                            var download = this._info.Wrapper.DownloadDocument(allegato.IDBase);
                            var stream = new MemoryStream(download.handler);

                            var attachmentInfo = zipUtils.GetAttachmentsInfo(stream, allegato.Serial, 0, false);
                            if (attachmentInfo != null)
                            {
                                var allegatiZip = attachmentInfo.Select((x, i) => new AllOut
                                {
                                    IDBase = $"{allegato.IDBase}.{i}",
                                    Serial = x.FileName,
                                    Commento = x.FileName
                                }).ToList();

                                listaAllegati.AddRange(allegatiZip);
                            }
                        }
                        else
                        {
                            listaAllegati.Add(allegato);
                        }
                    }
                }
            }

            return listaAllegati.ToArray();
        }
    }
}
