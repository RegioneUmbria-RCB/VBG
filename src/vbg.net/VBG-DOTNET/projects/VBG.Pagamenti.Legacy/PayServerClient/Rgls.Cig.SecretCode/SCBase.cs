using Rgls.Cig.Utility;
using System;
using System.Security.Cryptography;
using System.Text;
using System.Web;
using System.Xml;

namespace Rgls.Cig.SecretCode
{
    public class SCBase
    {
        public bool UsaHashLowercase { get; private set; } = false;
        
        public const int PS2S_KT_CLEAR = 1;

        private readonly Tracer _tracer = null;
        private string _secret = null;
        private string _PS2S_Componente = null;
        private string _PS2S_HASHR = null;

        private string _PS2S_HASHC = null;
        private TrasfObj _trasfObj = null;

        private int PS2S_ProtocolVersion = 1;

        public string ServerURL { get; set; } = null;

        public string ProxyServer { get; set; } = null;

        public string ProxyPort { get; set; } = null;

        public string PS2S_NetBuffer { get; protected set; } = null;

        public string PS2S_DataBuffer { get; protected set; } = null;
        
        protected SCBase(string sChiave, int iType, bool usaHashLowercase) 
        {
            this._tracer = new Tracer("CIGSECR");
            this._trasfObj = new TrasfObj();
            this._secret = "X";
            this.UsaHashLowercase = usaHashLowercase;

            if (iType == 1)
            {
                this._secret = this._trasfObj.Cripta(sChiave);
            }
        }
        
        protected string GetErrorDescr(int err)
        {
            string result;
            switch (err)
            {
                case -101:
                    result = "Indirizzo IP non valido";
                    break;
                case -100:
                    result = "Coockie spirato";
                    break;
                default:
                    switch (err)
                    {
                        case -14:
                            result = "Errore di connessione al DB";
                            return result;
                        case -13:
                            result = "TID (response ID) gia' usato";
                            return result;
                        case -12:
                            result = "TID (response ID) non trovato";
                            return result;
                        case -11:
                            result = "RID (request ID) gia' usato";
                            return result;
                        case -10:
                            result = "RID (request ID) non trovato";
                            return result;
                        case -9:
                            result = "Connessione HTTP fallita";
                            return result;
                        case -8:
                            result = "Creazione hash fallito";
                            return result;
                        case -7:
                            result = "Finestra temporale scaduta";
                            return result;
                        case -6:
                            result = "XML non valido";
                            return result;
                        case -3:
                            result = "Componente non valido";
                            return result;
                        case -2:
                            result = "Hash non trovato";
                            return result;
                        case -1:
                            result = "Hash non valido";
                            return result;
                    }
                    result = "Errore sconoscito";
                    break;
            }
            return result;
        }

        protected string CreaBufferHash(string sDataBuffer, string sTag)
        {
            string result;
            if (this._secret == "X")
            {
                result = "";
            }
            else
            {
                this._tracer.traceInfo("Entr SCBase.CreaBufferHash(), DataBuffer=" + sDataBuffer + " Tag=" + sTag);
                string sTagOrario = (sTag == null) ? this._trasfObj.TagOrario() : sTag;
                string sTagOrarioS = this._trasfObj.TagOrarioShort(sTagOrario);
                string sBuffer = sTagOrario + sDataBuffer + this._trasfObj.Decripta(this._secret) + sTagOrarioS;
                byte[] bdata = CBuf.ToByteArray(sBuffer);
                byte[] hash = new MD5CryptoServiceProvider().ComputeHash(bdata);
                string sRet = CBuf.ToBase16String(hash);
                this._tracer.traceInfo("Exit SCBase.CreaBufferHash() Ret=" + sRet);
                result = sRet;
            }
            return this.UsaHashLowercase ? result.ToLower() : result.ToUpper();
        }
        
        protected int SelezionaSecret(string sComp)
        {
            this._tracer.traceInfo("Entr SCBase.SelezionaSecret(), Component=" + sComp);

            this._tracer.traceInfo("Entr SCBase.SelezionaSecret(), Component=" + sComp + " - Secret gia' configurato");
            this._tracer.traceInfo("Exit SCBase.SelezionaSecret()");
            return 0;
        }

        private int EstraiProtocolVersion(string xmlBuffer)
        {
            string sProtocolVersion = this.MySelectSingleNode(xmlBuffer, "ProtocolVersion");
            return ((sProtocolVersion.Length == 0) ? 1 : Convert.ToInt32(sProtocolVersion));
        }

        private DateTime TagOrarioToDate(string tagOrario)
        {
            return new DateTime(Convert.ToInt32(tagOrario.Substring(0, 4), 10), Convert.ToInt32(tagOrario.Substring(4, 2), 10), Convert.ToInt32(tagOrario.Substring(6, 2), 10), Convert.ToInt32(tagOrario.Substring(8, 2), 10), Convert.ToInt32(tagOrario.Substring(10, 2), 10), 0);
        }

        protected int PS2S_Net2DataBuffer(string sNetBuffer, string sCompDefault, int lFinestraTemporale)
        {
            this._tracer.traceInfo($"Entr SCBase.PS2S_Net2DataBuffer(), NetBuff={sNetBuffer}, Finestra Temporale={lFinestraTemporale}");

            this.PS2S_ProtocolVersion = EstraiProtocolVersion(sNetBuffer);

            this._tracer.traceInfo("Sec: Entr PS2S_Net2DataBuffer, Protocol version =" + this.PS2S_ProtocolVersion);

            var sBufferRicevuto = (this.PS2S_ProtocolVersion == 1) ? CStr.Substitute(sNetBuffer, "%26", "&") : sNetBuffer;

            this.PS2S_DataBuffer = "";
            this._PS2S_HASHR = "";
            this._PS2S_HASHC = "";

            if (this.MySelectSingleNode(sBufferRicevuto, "CodicePortale") == "")
            {
                this._PS2S_Componente = sCompDefault;
            }
            else
            {
                this._PS2S_Componente = this.MySelectSingleNode(sBufferRicevuto, "CodicePortale");
            }

            string TagOrario = this.MySelectSingleNode(sBufferRicevuto, "TagOrario");

            if (TagOrario == "")
            {
                this._tracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Extract TagOrario Error");
                return -6;
            }

            string bufferData = this.MySelectSingleNode(sBufferRicevuto, "BufferDati");

            if (bufferData == "")
            {
                this._tracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Extract BufferDati Error");
                return -6;
            }

            var DataRic = TagOrarioToDate(TagOrario);

            int minutiDiff = (int)Math.Abs(DateTime.Now.Subtract(DataRic).TotalMinutes);
            if (minutiDiff > lFinestraTemporale)
            {
                this._tracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Finestra temporale scaduta");
                return -7;
            }

            if (this.SelezionaSecret(this._PS2S_Componente) != 0)
            {
                this._tracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Application Initializzation ErrorHash Verify Error");
                return -3;
            }

            this._PS2S_HASHC = this.CreaBufferHash(bufferData, TagOrario);
            if (this._PS2S_HASHC == "")
            {
                this._tracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Error Hash creation");
                return -8;
            }

            this._PS2S_HASHR = this.MySelectSingleNode(sBufferRicevuto, "Hash");
            if (this._PS2S_HASHR == "")
            {
                this._tracer.traceError("Exit SCBase.PS2S_Net2DataBuffer(): Extract Hash Error");
                return -2;
            }

            if (!String.Equals(this._PS2S_HASHC, this._PS2S_HASHR, StringComparison.OrdinalIgnoreCase))
            {
                this._tracer.traceError($"Exit SCBase.PS2S_Net2DataBuffer(): Hash Verify Error, HashRicevuto={this._PS2S_HASHR}, HashCalcolato={this._PS2S_HASHC}, Buffer dati={bufferData}");

                return -1;
            }

            this.PS2S_DataBuffer = (this.PS2S_ProtocolVersion == 2) ? CBuf.DecodeBase64(bufferData) : bufferData;

            this._tracer.traceInfo("Exit SCBase.PS2S_Net2DataBuffer(), sPS2S_DataBuffer=" + this.PS2S_DataBuffer);

            return 0;
        }

        protected int PS2S_Data2NetBuffer(string sDataBuffer, string sComp, DateTime dt, int iProtocolVersion)
        {
            this._tracer.traceInfo("Entr SCBase.PS2S_Data2NetBuffer(), DataBuff=" + sDataBuffer);

            int ret = this.SelezionaSecret(sComp);
            if (ret != 0)
            {
                this.PS2S_NetBuffer = "";
                this._tracer.traceError("Exit SCBase.PS2S_Data2NetBuffer(): Error secret key Component=" + sComp);
                return -3;
            }

            string sBufferDati = (iProtocolVersion == 2) ? CBuf.EncodeBase64(sDataBuffer) : sDataBuffer;
            string TagOrario = string.Format("{0:D4}{1:D2}{2:D2}{3:D2}{4:D2}", new object[]
            {
                        dt.Year,
                        dt.Month,
                        dt.Day,
                        dt.Hour,
                        dt.Minute
            });
            string HashCalc = this.CreaBufferHash(sBufferDati, TagOrario);
            if (HashCalc == "")
            {
                this._tracer.traceError("Exit SCBase.PS2S_Data2NetBuffer(): Error Hash creation");
                return -8;
            }
            string c = "<Buffer>";
            if (iProtocolVersion == 2)
            {
                c += "<ProtocolVersion>2</ProtocolVersion>";
            }
            string text = c;
            c = string.Concat(new string[]
            {
                            text,
                            "<TagOrario>",
                            TagOrario,
                            "</TagOrario><CodicePortale>",
                            sComp,
                            "</CodicePortale><BufferDati>",
                            sBufferDati,
                            "</BufferDati><Hash>",
                            HashCalc,
                            "</Hash></Buffer>"
            });
            if (iProtocolVersion == 2)
            {
                this.PS2S_NetBuffer = c;
            }
            else
            {
                this.PS2S_NetBuffer = CStr.Substitute(c, "&", "%26");
            }
            this._tracer.traceInfo("Exit SCBase.PS2S_Data2NetBuffer(), sPS2S_NetBuffer=" + this.PS2S_NetBuffer);

            return 0;
        }

        private int pPS2S_Client_Request2RID(string sRequestBuffer, string sComp, DateTime dt, int iProtocolVersion)
        {
            this._tracer.traceInfo("Entr SCBase.pPS2S_Client_Request2RID(), RequestBuffer=" + sRequestBuffer);
            int ret = this.PS2S_Data2NetBuffer(sRequestBuffer, sComp, dt, iProtocolVersion);

            if (ret != 0)
            {
                this._tracer.traceError("Exit SCBase.pPS2S_Client_Request2RID(), ret=" + ret);
                return ret;
            }

            URLWorker oURLWorker = new URLWorker();
            oURLWorker.URL = this.ServerURL;
            oURLWorker.ProxyServer = this.ProxyServer;
            oURLWorker.ProxyPort = this.ProxyPort;
            oURLWorker.BufferIn = "buffer=" + HttpUtility.UrlEncode(this.PS2S_NetBuffer, Encoding.Default);
            string sret = oURLWorker.DoRequest("POST");
            if (sret != null)
            {
                this._tracer.traceError("Exit SCBase.pPS2S_Client_Request2RID(), impossibile raggiungere il webserver di cig: " + this.ServerURL + " " + sret);
                return -9;
            }

            ret = this.PS2S_Data2NetBuffer(oURLWorker.BufferOut, sComp, dt, iProtocolVersion);
            this._tracer.traceInfo("Exit SCBase.pPS2S_Client_Request2RID(), ret=" + ret);
            return ret;
        }

        protected int PS2S_PC_Request2RID(string sRequestBuffer, string sComp, DateTime dt)
        {
            return this.pPS2S_Client_Request2RID(sRequestBuffer, sComp, dt, 2);
        }

        public int PS2S_PC_PID2Data(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale)
        {
            return this.pPS2S_Client_TID2Ticket(sIdBuffer, sComp, dt, lFinestraTemporale, 2);
        }

        private int pPS2S_Client_TID2Ticket(string sIdBuffer, string sComp, DateTime dt, int lFinestraTemporale, int iProtocolVersion = 2)
        {
            this._tracer.traceInfo("Entr SCBase.pPS2S_Client_TID2Ticket(), IdBuffer=" + sIdBuffer);
            int ret = this.PS2S_Data2NetBuffer(sIdBuffer, sComp, dt, iProtocolVersion);

            if (ret != 0)
            {
                this._tracer.traceError("Exit SCBase.pPS2S_Client_TID2Ticket() - DATA2NET, ret=" + ret);
                return ret;
            }

            URLWorker oURLWorker = new URLWorker();
            oURLWorker.URL = this.ServerURL;
            oURLWorker.ProxyServer = this.ProxyServer;
            oURLWorker.ProxyPort = this.ProxyPort;
            oURLWorker.BufferIn = "buffer=" + this.PS2S_NetBuffer;

            string sret = oURLWorker.DoRequest("POST");
            if (sret != null)
            {
                this._tracer.traceError("Exit SCBase.pPS2S_Client_TID2Ticket(), impossibile raggiungere il webserver di cig: " + this.ServerURL + " " + sret);
                return -9;
            }

            ret = this.PS2S_Net2DataBuffer(oURLWorker.BufferOut, sComp, lFinestraTemporale);

            this._tracer.traceInfo("Exit SCBase.pPS2S_Client_TID2Ticket(), ret=" + ret);

            return ret;
        }

        private string MySelectSingleNode(string xmlBuffer, string tag)
        {
            string s = "";
            string iTag = "<" + tag + ">";
            string fTag = "</" + tag + ">";
            int pi = xmlBuffer.IndexOf(iTag);
            int pf = xmlBuffer.IndexOf(fTag);
            if (pi > 0 && pi < pf)
            {
                try
                {
                    s = xmlBuffer.Substring(0, pf);
                    s = s.Substring(pi + iTag.Length);
                }
                catch
                {
                    s = "";
                }
            }
            return s;
        }

    }
}
