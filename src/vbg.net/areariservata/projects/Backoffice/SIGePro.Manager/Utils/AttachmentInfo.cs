using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Utils
{
    public class AttachmentInfo
    {
        public string FileName { get; private set; }
        public byte[] Image { get; private set; }
        public int Index { get; private set; }

        public AttachmentInfo(string fileName, byte[] buffer, int index)
        {
            this.FileName = fileName;
            this.Image = buffer;
            this.Index = index;
        }
    }
}
