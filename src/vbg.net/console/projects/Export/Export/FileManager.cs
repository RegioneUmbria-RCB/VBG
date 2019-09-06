using System;
using System.Xml;
using System.IO;
using System.Collections;
using System.Collections.Specialized;
using ICSharpCode.SharpZipLib.Zip;
using System.Configuration;
using Init.Utils;

namespace Export
{
	
	/// <summary>
	/// Descrizione di riepilogo per FileManager.
	/// </summary>
	public class FileManager
	{
		private FileXml pFileXml = new FileXml();
		private FileTxt pFileTxt = new FileTxt();
		private string _FolderPath = String.Empty;

		public FileXml XML
		{
			get { return pFileXml; }
		}

		public FileTxt TXT
		{
			get { return pFileTxt; }
		}

		public string FolderPath
		{
			get { return _FolderPath; }
			set { _FolderPath = value; }
		}


		#region Gestione directory

		private void CreateDirectory()
		{
			if ( ! Directory.Exists( _FolderPath ) )
				Directory.CreateDirectory( _FolderPath );
		}

		public ArrayList GetFileList(string sSearchPat)
		{
			ArrayList retVal = new ArrayList();

			try
			{
				string[] files = Directory.GetFiles( _FolderPath, sSearchPat );
				for( int i=0; i<files.Length; i++ )
					retVal.Add( files[i] );

			}
			catch( System.Exception ex )
			{
				//gestire l'eccezione generata se la directory non esiste o non è stata passata
				throw new System.Exception("Problema durante la restituzione dei file presenti all'interno della cartella e che verificano la stringa di ricerca: "+sSearchPat,ex);
			}

			return retVal;
		}

		public ArrayList GetFileList()
		{
			ArrayList retVal = new ArrayList();

			try
			{
				string[] files = Directory.GetFiles( _FolderPath );
				for( int i=0; i<files.Length; i++ )
					retVal.Add( files[i] );

			}
			catch( System.Exception ex )
			{
				//gestire l'eccezione generata se la directory non esiste o non è stata passata
				throw new System.Exception("Problema durante la restituzione di tutti i file presenti all'interno della cartella",ex);
			}

			return retVal;
		}

		public void DeleteFolder()
		{
			//Elimina la cartella
			try
			{
				if ( Directory.Exists(_FolderPath) )
					Directory.Delete(_FolderPath);
			}
			catch( System.Exception ex )
			{
				//gestire il caso in cui il folder è inesistente o non viene passato
				throw new System.Exception("Problema durante l'eliminazione della cartella",ex);
			}
		}

		public void ClearFolder()
		{
			//Ripulisce la cartella da tutti i file (txt e zip)
			try
			{
				if ( Directory.Exists(_FolderPath) )
				{
					string[] files = Directory.GetFiles( _FolderPath );
					for( int i=0; i<files.Length; i++ )
						File.Delete( files[i] );
				}
			}
			catch( System.Exception ex )
			{
				//gestire il caso in cui il folder è inesistente o non viene passato
				throw new System.Exception("Problema durante l'eliminazione di tutti i file presenti nella cartella",ex);
			}
		}

		public void ClearFolder(string sSearchPat)
		{
			//Ripulisce la cartella da tutti i file (txt e zip)
			try
			{
				if ( Directory.Exists(_FolderPath) )
				{
					string[] files = Directory.GetFiles( _FolderPath,sSearchPat );
					for( int i=0; i<files.Length; i++ )
						File.Delete( files[i] );
				}
			}
			catch( System.Exception ex )
			{
				//gestire il caso in cui il folder è inesistente o non viene passato
				throw new System.Exception("Problema durante l'eliminazione dei file presenti nella cartella e che verificano la stringa di ricerca",ex);
			}
		}
		#endregion

		#region Gestione file txt

		public void Append( string fileName, string fileText )
		{
			string pFileName = _FolderPath + fileName;
			try
			{
				CreateDirectory();

				StreamWriter sw;
				if ( ! File.Exists( pFileName ) )
					sw = File.CreateText(pFileName);
				else
					sw = File.AppendText( pFileName );

				sw.Write( fileText );
				sw.Flush();
				sw.Close();
			}
			catch( System.Exception ex)
			{
				//gestire le eccezioni qualora il path del file non sia corretto
				throw new System.Exception("Problema durante la creazione del file: "+pFileName,ex);
			}
		}

		public void AppendTxt(string sFileName, string sText )
		{
			string pFileName = _FolderPath + sFileName;

			CreateDirectory();

			int iHandle = pFileTxt.OpenWriteTxtFile(pFileName);
			pFileTxt.AddText(iHandle,sText);
		}

		public void CloseTxt()
		{
			for ( int i=0; i<pFileTxt.GetFileTxtOpen(); i++ )
				pFileTxt.CloseWriteTxtFile(i);

			pFileTxt.pLstFile.Clear();
			pFileTxt.pLstTxtFile.Clear();
		}
		#endregion

		#region Gestione file xml
	
		public void CreateAppendXml(string sFileName, string sRoot, string sTagName, string sTagValue)
		{
			string pFileName = _FolderPath + sFileName;
			CreateDirectory();
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, sRoot);
			pFileXml.AddElement(iHandle, sTagName, sTagValue);
		}

		public void CreateAppendXml(string sFileName, string sRoot, string sTagName)
		{
			string pFileName = _FolderPath + sFileName;
			CreateDirectory();
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, sRoot);
			pFileXml.OpenElement(iHandle, sTagName);
		}

		public void CreateAppendXml(string sFileName, string sRoot)
		{
			string pFileName = _FolderPath + sFileName;
			CreateDirectory();
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, sRoot);
		}

		public void AppendXml(string sFileName, string sTagName, string sTagValue)
		{
			string pFileName = _FolderPath + sFileName;
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, null);
			pFileXml.AddElement(iHandle, sTagName, sTagValue);
		}

		public void AppendXml(string sFileName, string sTagName)
		{
			string pFileName = _FolderPath + sFileName;
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, null);
			pFileXml.OpenElement(iHandle, sTagName);
		}

		public void AppendXml(string sFileName)
		{
			string pFileName = _FolderPath + sFileName;
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, null);
			pFileXml.CloseElement(iHandle);
		}

		public void AppendAttrXml(string sFileName, string sAttName, string sAttValue)
		{
			string pFileName = _FolderPath + sFileName;
			int iHandle = pFileXml.OpenWriteXmlFile(pFileName, null);
			pFileXml.AddAttribute(iHandle, sAttName, sAttValue);
		}

		public void CloseXml()
		{
			for ( int i=0; i<pFileXml.GetFileXmlOpen(); i++ )
				pFileXml.CloseWriteXmlFile(i);

			pFileXml.pLstFile.Clear();
			pFileXml.pLstXmlFile.Clear();
		}
		
		#endregion

        #region Gestione file zip

        public byte[] CreateByteArray(string sFileName)
        {
            string pFileName = _FolderPath + sFileName;
            return StreamUtils.StreamToBytes(StreamUtils.FileToStream(pFileName));
        }

        #endregion

		#region Gestione file zip

		public byte[] Zip(string sFileNameZip, int iCmpLvl, ArrayList pLstFile)
		{
			string pFileName = _FolderPath + sFileNameZip;
			CreateDirectory();
			FileZip pFileZip = new FileZip();
			pFileZip.CreateZipArchive(pFileName,iCmpLvl,pLstFile);
			return pFileZip.CreateZipByteArray();
		}

		#endregion
	}

	public class FileTxt
	{
		public ArrayList pLstTxtFile = new ArrayList();
		public ArrayList pLstFile = new ArrayList();

		public int GetFileTxtOpen()
		{
			return pLstFile.Count;
		}

		public int OpenWriteTxtFile(string sFileName)
		{
			int iHandle = -1;
			try
			{
				//Gestione per la creazione della directory
				for (int i=0; i<pLstFile.Count; i++)
				{
					if (sFileName==pLstFile[i].ToString())
					{
						iHandle=i;
						break;
					}
				}

				if (iHandle==-1)
				{
					FileStream fs = new FileStream(sFileName, FileMode.Append, FileAccess.Write);
					pLstTxtFile.Add(new StreamWriter(fs));
					pLstFile.Add(sFileName);
					iHandle = pLstFile.Count-1;
				}
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante l'apertura del file txt: "+sFileName,ex);
			}
	
			return iHandle;
		}

		public void AddText(int iHandle, string sText)
		{
			try
			{
				((StreamWriter)pLstTxtFile[iHandle]).Write(sText);
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante l'inserimento di nuovo testo nel file txt..\rIl testo da aggiungere è: "+sText,ex);
			}
		}

		public void CloseWriteTxtFile(int iHandle)
		{
			try
			{
				((StreamWriter)pLstTxtFile[iHandle]).Flush();
				((StreamWriter)pLstTxtFile[iHandle]).Close();
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante la chiusura ed il rilascio del file txt",ex);
			}
		}
	}

	public class FileXml
	{
		public ArrayList pLstXmlFile = new ArrayList();
		public ArrayList pLstFile = new ArrayList();

		public int GetFileXmlOpen()
		{
			return pLstFile.Count;
		}

		public int OpenWriteXmlFile(string sFileName, string sRoot)
		{
			int iHandle = -1;
			try
			{
				for (int i=0; i<pLstFile.Count; i++)
				{
					if (sFileName==pLstFile[i].ToString())
					{
						iHandle=i;
						break;
					}
				}

				if (iHandle==-1)
				{
                    //pLstXmlFile.Add(new XmlTextWriter(sFileName, System.Text.Encoding.UTF8));
                    //Utilizzare questa istruzione per evitare di avere problemi con il BOM durante l'invio ad Infocamera
                    //pLstXmlFile.Add(new XmlTextWriter(sFileName, new System.Text.UTF8Encoding(false)));
                    //Con iso-8859-1 funzionava perchè non viene premesso BOM
					pLstXmlFile.Add(new XmlTextWriter(sFileName,System.Text.Encoding.GetEncoding("iso-8859-1")));
					pLstFile.Add(sFileName);
					iHandle = pLstFile.Count-1;
					((XmlTextWriter)pLstXmlFile[iHandle]).Formatting = Formatting.Indented;
					((XmlTextWriter)pLstXmlFile[iHandle]).WriteStartDocument();
					((XmlTextWriter)pLstXmlFile[iHandle]).WriteStartElement(sRoot);
				}
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante l'apertura e l'inserimento della dichiarazione del documento del file xml: "+sFileName,ex);
			}
	
			return iHandle;
		}

		public void CloseWriteXmlFile(int iHandle)
		{
			try
			{
				((XmlTextWriter)pLstXmlFile[iHandle]).WriteEndDocument();
				((XmlTextWriter)pLstXmlFile[iHandle]).Flush();
				((XmlTextWriter)pLstXmlFile[iHandle]).Close();
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante la chiusura ed il rilascio del file xml",ex);
			}
		}
		public void AddElement(int iHandle, string sTagName, string sTagValue)
		{
			try
			{
				if ( (sTagName != "") && (sTagName != null) )
					((XmlTextWriter)pLstXmlFile[iHandle]).WriteElementString(sTagName,sTagValue);
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante l'inserimento di un nuovo tag nel file xml.\rIl nome del tag è: "+sTagName+".\rIl valore del tag è: "+sTagValue,ex);
			}
		}
		public void OpenElement(int iHandle, string sTagName)
		{
			try
			{
				if ( (sTagName != "") && (sTagName != null) )
					((XmlTextWriter)pLstXmlFile[iHandle]).WriteStartElement(sTagName);
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante l'apertura di un nuovo tag nel file xml.\rIl nome del tag è: "+sTagName,ex);
			}
		}
		public void CloseElement(int iHandle)
		{
			try
			{
				((XmlTextWriter)pLstXmlFile[iHandle]).WriteEndElement();
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante la chiusura di un nuovo tag nel file xml.",ex);
			}
		}
		public void AddAttribute(int iHandle, string sAttName, string sAttValue)
		{
			try
			{
				if ( (sAttName != "") && (sAttName != null) )
					((XmlTextWriter)pLstXmlFile[iHandle]).WriteAttributeString(sAttName,sAttValue);
			}
			catch ( Exception ex )
			{
				throw new Exception("Problema durante l'inserimento di un nuovo attributo nel file xml.\rIl nome dell'attributo è: "+sAttName+".\rIl valore dell'attributo è: "+sAttValue,ex);
			}
		}
	}


	public class FileZip
	{
		private ZipOutputStream archive = null;

		/// <summary>
		/// Metodo usato per creare un file .zip usando tutti i file presenti all'interno di una cartella
		/// </summary>
		/// <param name="sFileNameZip">Nome del file .zip completo di path</param>
		/// <param name="iCmpLvl">Livello di compressione</param>
		/// <param name="pLstFile">Lista dei file da comprimere</param> 
		public void CreateZipArchive(string sFileNameZip, int iCmpLvl, ArrayList pLstFile)
		{
			try
			{
				archive = new ZipOutputStream(File.Create(sFileNameZip+".zip"));

				archive.SetLevel(iCmpLvl); // 0 - store only to 9 - means best compression
		
				byte[] buffer = new byte[4096];
				
				for ( int i=0; i<pLstFile.Count; i++ )
				{
					string file = pLstFile[i].ToString();
					// Using GetFileName makes the result compatible with XP
					// as the resulting path is not absolute.
					ZipEntry entry = new ZipEntry(Path.GetFileName(file));
					
					// Setup the entry data as required.
					
					// Crc and size are handled by the library for seakable streams
					// so no need to do them here.

					// Could also use the last write time or similar for the file.
					entry.DateTime = DateTime.Now;
					archive.PutNextEntry(entry);
					
					using ( FileStream fs = File.OpenRead(file) ) 
					{
		
						// Using a fixed size buffer here makes no noticeable difference for output
						// but keeps a lid on memory usage.
						int sourceBytes;
						do 
						{
							sourceBytes = fs.Read(buffer, 0, buffer.Length);
							archive.Write(buffer, 0, sourceBytes);
						} while ( sourceBytes > 0 );
					}
				}
			}
			catch ( Exception ex )
			{
				throw new System.Exception("Problema durante la creazione del file compresso",ex);
			}
		}

		/// <summary>
		/// Metodo usato per creare un array di byte a partire da un file .zip
		/// </summary>
		/// <returns>Array di byte tornato dal metodo</returns>
		public byte[] CreateZipByteArray()
		{
			Stream pStream = null;
			byte[] bytes;
			try
			{
				archive.Finish();
				pStream = archive.BaseOutputStrm;
				int numBytesToRead = (int) pStream.Length;
				bytes = new byte[numBytesToRead];
				pStream.Seek(0,SeekOrigin.Begin);
				int iRes = pStream.Read(bytes,0,numBytesToRead);
			}
			catch ( Exception ex )
			{
				throw new System.Exception("Problema durante la creazione dell'array di byte a partire dal file compresso",ex);
			}
			finally
			{
				if (archive!=null)
				{
					archive.Close();
				}
			}
			
			return bytes;
		}

	}	
}
