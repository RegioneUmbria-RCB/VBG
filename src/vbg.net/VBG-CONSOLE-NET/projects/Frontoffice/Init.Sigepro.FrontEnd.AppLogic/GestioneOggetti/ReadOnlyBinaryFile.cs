using Init.Sigepro.FrontEnd.Infrastructure.MimeTypes.MimeTypes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    public class ReadOnlyBinaryFile: BinaryFile
    {

        public override int Size { get; }

        public ReadOnlyBinaryFile(string fileName, int fileSize)
        {
            this.FileName = fileName;
            this.Size = fileSize;
            this.MimeType = MimeTypeMap.GetMimeType(fileName);
            this.FileContent = new byte[0];
        }
    }
}
