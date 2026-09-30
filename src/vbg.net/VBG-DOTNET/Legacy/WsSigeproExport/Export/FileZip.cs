using ICSharpCode.SharpZipLib.Zip;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Export
{
    public class FileZip
    {
        private string _zipFilePath;
        private ZipOutputStream _archive = null;

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
                this._zipFilePath = sFileNameZip + ".zip";
                this._archive = new ZipOutputStream(File.Create(_zipFilePath));

                this._archive.SetLevel(iCmpLvl); // 0 - store only to 9 - means best compression

                byte[] buffer = new byte[4096];

                foreach (string file in pLstFile)
                {
                    var entry = new ZipEntry(Path.GetFileName(file))
                    {
                        DateTime = DateTime.Now
                    };

                    this._archive.PutNextEntry(entry);

                    using (FileStream fs = File.OpenRead(file))
                    {
                        int read;
                        while ((read = fs.Read(buffer, 0, buffer.Length)) > 0)
                        {
                            this._archive.Write(buffer, 0, read);
                        }
                    }

                    this._archive.CloseEntry();
                }
            }
            catch (Exception ex)
            {
                throw new System.Exception("Problema durante la creazione del file compresso", ex);
            }
        }

        /// <summary>
        /// Metodo usato per creare un array di byte a partire da un file .zip
        /// </summary>
        /// <returns>Array di byte tornato dal metodo</returns>
        public byte[] CreateZipByteArray()
        {
            byte[] bytes;
            try
            {
                this._archive.Finish();
            }
            catch (Exception ex)
            {
                throw new Exception("Problema durante la chiusura dell'archivio ZIP", ex);
            }
            finally
            {
                this._archive?.Close();
            }

            return File.ReadAllBytes(_zipFilePath);
        }
    }
}
