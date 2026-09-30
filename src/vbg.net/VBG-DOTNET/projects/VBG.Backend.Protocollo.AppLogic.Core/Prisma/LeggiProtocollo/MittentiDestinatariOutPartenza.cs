
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public class MittentiDestinatariOutPartenza : ILeggiProtoMittentiDestinatari
    {
        IEnumerable<SmistamentoOutXml> _smistamenti;
        IEnumerable<RapportoOutXml> _destinatari;
        LeggiPecOutXML _responsePec;

        public MittentiDestinatariOutPartenza(IEnumerable<SmistamentoOutXml> smistamenti, IEnumerable<RapportoOutXml> destinatari, LeggiPecOutXML responsePec)
        {
            this._smistamenti = smistamenti;
            this._destinatari = destinatari;
            this._responsePec = responsePec;
        }

        public string InCaricoA { get { return this._smistamenti == null || this._smistamenti.Count() == 0 ? "" : this._smistamenti.Last().UfficioTrasmissione; } }

        public string InCaricoADescrizione { get { return this._smistamenti == null || this._smistamenti.Count() == 0 ? "" : this._smistamenti.Last().DescrizioneUfficioTrasmissione; } }

        public string Flusso { get { return ProtocolloConstants.COD_PARTENZA; } }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            if (_responsePec.MemoInviati == null || _responsePec.MemoInviati.Memo == null || String.IsNullOrEmpty(_responsePec.MemoInviati.Memo.Destinatari))
            {
                return _destinatari.Select(x => new MittDestOutType
                {
                    CognomeNome = x.CognomeNome
                }).ToArray();
            }

            var destinatari = new List<MittDestOutType>();
            var destinatariPec = this._responsePec.MemoInviati.Memo.Destinatari.ToUpper().Split(';').Cast<string>().Where(x => !String.IsNullOrEmpty(x)).ToList();

            foreach (var destinatario in this._destinatari)
            {
                if (!String.IsNullOrEmpty(destinatario.Email) && destinatario.CognomeNome.Trim().ToUpper() == destinatario.Email.Trim().ToUpper())
                {
                    continue;
                }

                var datoDaVisualizzare = destinatario.CognomeNome.Trim();

                if (destinatariPec.Contains(destinatario.Email))
                {
                    datoDaVisualizzare = $"{destinatario.CognomeNome} {destinatario.Email}";

                    if (!String.IsNullOrEmpty(this._responsePec.MemoInviati.Memo.DataSpedizione))
                    {
                        datoDaVisualizzare = $"{destinatario.CognomeNome} {destinatario.Email} Spedito il {this._responsePec.MemoInviati.Memo.DataSpedizione}";

                        if (destinatario.CognomeNome.Trim().ToUpper() == destinatario.Email.ToUpper())
                        {
                            datoDaVisualizzare = $"{destinatario.CognomeNome.Trim()} Spedito il {this._responsePec.MemoInviati.Memo.DataSpedizione}";
                        }
                    }

                    destinatariPec.Remove(destinatario.Email);
                }

                destinatari.Add(new MittDestOutType { CognomeNome = datoDaVisualizzare });
            }

            if (destinatariPec.Count() > 0)
            {
                foreach (var destinatariPecRimasti in destinatariPec)
                {
                    var datoDaVisualizzare = destinatariPecRimasti;
                    if (!String.IsNullOrEmpty(this._responsePec.MemoInviati.Memo.DataSpedizione))
                    {
                        datoDaVisualizzare = $"{destinatariPecRimasti} Spedito il {this._responsePec.MemoInviati.Memo.DataSpedizione}";
                    }

                    destinatari.Add(new MittDestOutType { CognomeNome = datoDaVisualizzare });
                }
            }

            return destinatari.ToArray();

        }
    }
}
