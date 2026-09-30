using ICSharpCode.SharpZipLib.Zip;
using Init.Utils;
using System;
using System.Collections;
using System.IO;
using System.Xml;

namespace Export
{

    /// <summary>
    /// Descrizione di riepilogo per FileManager.
    /// </summary>
    public class FileManager
    {
        private readonly FileXml _pFileXml = new FileXml();
        private readonly FileTxt _pFileTxt = new FileTxt();
        private string _folderPath = String.Empty;

        public FileXml XML
        {
            get { return this._pFileXml; }
        }

        public FileTxt TXT
        {
            get { return this._pFileTxt; }
        }

        public string FolderPath
        {
            get { return this._folderPath; }
            set { this._folderPath = value; }
        }


        #region Gestione directory

        private void CreateDirectory()
        {
            if (!Directory.Exists(this._folderPath))
                Directory.CreateDirectory(this._folderPath);
        }

        public ArrayList GetFileList(string sSearchPat)
        {
            ArrayList retVal = new ArrayList();

            try
            {
                string[] files = Directory.GetFiles(this._folderPath, sSearchPat);
                for (int i = 0; i < files.Length; i++)
                    retVal.Add(files[i]);

            }
            catch (System.Exception ex)
            {
                //gestire l'eccezione generata se la directory non esiste o non è stata passata
                throw new System.Exception("Problema durante la restituzione dei file presenti all'interno della cartella e che verificano la stringa di ricerca: " + sSearchPat, ex);
            }

            return retVal;
        }

        public ArrayList GetFileList()
        {
            ArrayList retVal = new ArrayList();

            try
            {
                string[] files = Directory.GetFiles(this._folderPath);
                for (int i = 0; i < files.Length; i++)
                    retVal.Add(files[i]);

            }
            catch (System.Exception ex)
            {
                //gestire l'eccezione generata se la directory non esiste o non è stata passata
                throw new System.Exception("Problema durante la restituzione di tutti i file presenti all'interno della cartella", ex);
            }

            return retVal;
        }

        public void DeleteFolder()
        {
            //Elimina la cartella
            try
            {
                if (Directory.Exists(this._folderPath))
                    Directory.Delete(this._folderPath);
            }
            catch (System.Exception ex)
            {
                //gestire il caso in cui il folder è inesistente o non viene passato
                throw new System.Exception("Problema durante l'eliminazione della cartella", ex);
            }
        }

        public void ClearFolder()
        {
            //Ripulisce la cartella da tutti i file (txt e zip)
            try
            {
                if (Directory.Exists(this._folderPath))
                {
                    string[] files = Directory.GetFiles(this._folderPath);
                    for (int i = 0; i < files.Length; i++)
                        File.Delete(files[i]);
                }
            }
            catch (System.Exception ex)
            {
                //gestire il caso in cui il folder è inesistente o non viene passato
                throw new System.Exception("Problema durante l'eliminazione di tutti i file presenti nella cartella", ex);
            }
        }

        public void ClearFolder(string sSearchPat)
        {
            //Ripulisce la cartella da tutti i file (txt e zip)
            try
            {
                if (Directory.Exists(this._folderPath))
                {
                    string[] files = Directory.GetFiles(this._folderPath, sSearchPat);
                    for (int i = 0; i < files.Length; i++)
                        File.Delete(files[i]);
                }
            }
            catch (System.Exception ex)
            {
                //gestire il caso in cui il folder è inesistente o non viene passato
                throw new System.Exception("Problema durante l'eliminazione dei file presenti nella cartella e che verificano la stringa di ricerca", ex);
            }
        }
        #endregion

        #region Gestione file txt

        public void Append(string fileName, string fileText)
        {
            string pFileName = this._folderPath + fileName;
            try
            {
                this.CreateDirectory();

                StreamWriter sw;
                if (!File.Exists(pFileName))
                    sw = File.CreateText(pFileName);
                else
                    sw = File.AppendText(pFileName);

                sw.Write(fileText);
                sw.Flush();
                sw.Close();
            }
            catch (System.Exception ex)
            {
                //gestire le eccezioni qualora il path del file non sia corretto
                throw new System.Exception("Problema durante la creazione del file: " + pFileName, ex);
            }
        }

        public void AppendTxt(string sFileName, string sText)
        {
            string pFileName = this._folderPath + sFileName;

            this.CreateDirectory();

            int iHandle = this._pFileTxt.OpenWriteTxtFile(pFileName);
            this._pFileTxt.AddText(iHandle, sText);
        }

        public void CloseTxt()
        {
            for (int i = 0; i < this._pFileTxt.GetFileTxtOpen(); i++)
                this._pFileTxt.CloseWriteTxtFile(i);

            this._pFileTxt._pLstFile.Clear();
            this._pFileTxt._pLstTxtFile.Clear();
        }
        #endregion

        #region Gestione file xml

        public void CreateAppendXml(string sFileName, string sRoot, string sTagName, string sTagValue)
        {
            string pFileName = this._folderPath + sFileName;
            this.CreateDirectory();
            int iHandle = this._pFileXml.OpenWriteXmlFile(pFileName, sRoot);
            this._pFileXml.AddElement(iHandle, sTagName, sTagValue);
        }

        public void CreateAppendXml(string sFileName, string sRoot, string sTagName)
        {
            string pFileName = this._folderPath + sFileName;
            this.CreateDirectory();
            int iHandle = this._pFileXml.OpenWriteXmlFile(pFileName, sRoot);
            this._pFileXml.OpenElement(iHandle, sTagName);
        }

        public void CreateAppendXml(string sFileName, string sRoot)
        {
            string pFileName = this._folderPath + sFileName;
            this.CreateDirectory();
            this._pFileXml.OpenWriteXmlFile(pFileName, sRoot);
        }

        public void AppendXml(string sFileName, string sTagName, string sTagValue)
        {
            string pFileName = this._folderPath + sFileName;
            int iHandle = this._pFileXml.OpenWriteXmlFile(pFileName, null);
            this._pFileXml.AddElement(iHandle, sTagName, sTagValue);
        }

        public void AppendXml(string sFileName, string sTagName)
        {
            string pFileName = this._folderPath + sFileName;
            int iHandle = this._pFileXml.OpenWriteXmlFile(pFileName, null);
            this._pFileXml.OpenElement(iHandle, sTagName);
        }

        public void AppendXml(string sFileName)
        {
            string pFileName = this._folderPath + sFileName;
            int iHandle = this._pFileXml.OpenWriteXmlFile(pFileName, null);
            this._pFileXml.CloseElement(iHandle);
        }

        public void AppendAttrXml(string sFileName, string sAttName, string sAttValue)
        {
            string pFileName = this._folderPath + sFileName;
            int iHandle = this._pFileXml.OpenWriteXmlFile(pFileName, null);
            this._pFileXml.AddAttribute(iHandle, sAttName, sAttValue);
        }

        public void CloseXml()
        {
            for (int i = 0; i < this._pFileXml.GetFileXmlOpen(); i++)
                this._pFileXml.CloseWriteXmlFile(i);

            this._pFileXml._pLstFile.Clear();
            this._pFileXml._pLstXmlFile.Clear();
        }

        #endregion

        #region Gestione file zip

        public byte[] CreateByteArray(string sFileName)
        {
            string pFileName = this._folderPath + sFileName;
            return StreamUtils.StreamToBytes(StreamUtils.FileToStream(pFileName));
        }

        #endregion

        #region Gestione file zip

        public byte[] Zip(string sFileNameZip, int iCmpLvl, ArrayList pLstFile)
        {
            string pFileName = this._folderPath + sFileNameZip;
            this.CreateDirectory();
            FileZip pFileZip = new FileZip();
            pFileZip.CreateZipArchive(pFileName, iCmpLvl, pLstFile);
            return pFileZip.CreateZipByteArray();
        }

        #endregion
    }

    public class FileTxt
    {
        public ArrayList _pLstTxtFile = new ArrayList();
        public ArrayList _pLstFile = new ArrayList();

        public int GetFileTxtOpen()
        {
            return this._pLstFile.Count;
        }

        public int OpenWriteTxtFile(string sFileName)
        {
            int iHandle = -1;
            try
            {
                //Gestione per la creazione della directory
                for (int i = 0; i < this._pLstFile.Count; i++)
                {
                    if (sFileName == this._pLstFile[i].ToString())
                    {
                        iHandle = i;
                        break;
                    }
                }

                if (iHandle == -1)
                {
                    FileStream fs = new FileStream(sFileName, FileMode.Append, FileAccess.Write);
                    this._pLstTxtFile.Add(new StreamWriter(fs));
                    this._pLstFile.Add(sFileName);
                    iHandle = this._pLstFile.Count - 1;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante l'apertura del file txt: " + sFileName, ex);
            }

            return iHandle;
        }

        public void AddText(int iHandle, string sText)
        {
            try
            {
                ((StreamWriter)this._pLstTxtFile[iHandle]).Write(sText);
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante l'inserimento di nuovo testo nel file txt..\rIl testo da aggiungere è: " + sText, ex);
            }
        }

        public void CloseWriteTxtFile(int iHandle)
        {
            try
            {
                ((StreamWriter)this._pLstTxtFile[iHandle]).Flush();
                ((StreamWriter)this._pLstTxtFile[iHandle]).Close();
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante la chiusura ed il rilascio del file txt", ex);
            }
        }
    }

    public class FileXml
    {
        public ArrayList _pLstXmlFile = new ArrayList();
        public ArrayList _pLstFile = new ArrayList();

        public int GetFileXmlOpen()
        {
            return this._pLstFile.Count;
        }

        public int OpenWriteXmlFile(string sFileName, string sRoot)
        {
            int iHandle = -1;
            try
            {
                for (int i = 0; i < this._pLstFile.Count; i++)
                {
                    if (sFileName == this._pLstFile[i].ToString())
                    {
                        iHandle = i;
                        break;
                    }
                }

                if (iHandle == -1)
                {
                    //pLstXmlFile.Add(new XmlTextWriter(sFileName, System.Text.Encoding.UTF8));
                    //Utilizzare questa istruzione per evitare di avere problemi con il BOM durante l'invio ad Infocamera
                    //pLstXmlFile.Add(new XmlTextWriter(sFileName, new System.Text.UTF8Encoding(false)));
                    //Con iso-8859-1 funzionava perchè non viene premesso BOM
                    this._pLstXmlFile.Add(new XmlTextWriter(sFileName, System.Text.Encoding.GetEncoding("iso-8859-1")));
                    this._pLstFile.Add(sFileName);
                    iHandle = this._pLstFile.Count - 1;
                    ((XmlTextWriter)this._pLstXmlFile[iHandle]).Formatting = Formatting.Indented;
                    ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteStartDocument();
                    ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteStartElement(sRoot);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante l'apertura e l'inserimento della dichiarazione del documento del file xml: " + sFileName, ex);
            }

            return iHandle;
        }

        public void CloseWriteXmlFile(int iHandle)
        {
            try
            {
                ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteEndDocument();
                ((XmlTextWriter)this._pLstXmlFile[iHandle]).Flush();
                ((XmlTextWriter)this._pLstXmlFile[iHandle]).Close();
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante la chiusura ed il rilascio del file xml", ex);
            }
        }
        public void AddElement(int iHandle, string sTagName, string sTagValue)
        {
            try
            {
                if ((sTagName != "") && (sTagName != null))
                    ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteElementString(sTagName, sTagValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante l'inserimento di un nuovo tag nel file xml.\rIl nome del tag è: " + sTagName + ".\rIl valore del tag è: " + sTagValue, ex);
            }
        }
        public void OpenElement(int iHandle, string sTagName)
        {
            try
            {
                if ((sTagName != "") && (sTagName != null))
                    ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteStartElement(sTagName);
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante l'apertura di un nuovo tag nel file xml.\rIl nome del tag è: " + sTagName, ex);
            }
        }
        public void CloseElement(int iHandle)
        {
            try
            {
                ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteEndElement();
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante la chiusura di un nuovo tag nel file xml.", ex);
            }
        }
        public void AddAttribute(int iHandle, string sAttName, string sAttValue)
        {
            try
            {
                if ((sAttName != "") && (sAttName != null))
                    ((XmlTextWriter)this._pLstXmlFile[iHandle]).WriteAttributeString(sAttName, sAttValue);
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante l'inserimento di un nuovo attributo nel file xml.\rIl nome dell'attributo è: " + sAttName + ".\rIl valore dell'attributo è: " + sAttValue, ex);
            }
        }
    }


    
}
